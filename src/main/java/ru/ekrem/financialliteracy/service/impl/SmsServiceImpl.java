package ru.ekrem.financialliteracy.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.ekrem.financialliteracy.service.SmsService;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import static ru.ekrem.financialliteracy.util.ConstUrl.*;

@Component
public class SmsServiceImpl implements SmsService {

    String[] formats = {"", "flash=1", "push=1", "hlr=1", "bin=1", "bin=2", "ping=1", "mms=1", "mail=1", "call=1", "viber=1", "soc=1"};


    private String SMS_LOGIN;

    private String SMS_PASSWORD;

    private String SMSC_CHARSET  = "utf-8";       // кодировка сообщения
    private boolean SMSC_DEBUG   = false;         // флаг отладки
    private boolean SMSC_POST    = false;         // Использовать метод POST

    public SmsServiceImpl(
            @Value("${sms.login}") String SMS_LOGIN,
            @Value("${sms.password}") String SMS_PASSWORD) {
        this.SMS_LOGIN = SMS_LOGIN;
        this.SMS_PASSWORD = SMS_PASSWORD;
    }


    public String[] sendSms(String phone, String message, int translit, int cost)
    {

        String[] m = {};

        try {
            String formattedMessage = String.format(SEND_SMS_ARGS,
                    cost,
                    URLEncoder.encode(phone, SMSC_CHARSET),
                    URLEncoder.encode(message, SMSC_CHARSET),
                    translit);


            m = sendCmd("send", formattedMessage);
        }
        catch (UnsupportedEncodingException e) {
            System.out.print("Указанная кодировка символов не поддерживается!\n" + e + "\n");
        }


        if(m.length<=1)
            System.out.println("Не получен ответ от сервера.");

        if(SMSC_DEBUG){
            switch (cost){
                case 1:
                    if (Integer.parseInt(m[1]) > 0) {
                        System.out.println("Сообщение отправлено успешно. ID: " + m[0] + ", всего SMS: " + m[1] + ", стоимость: " + m[2] + ", баланс: " + m[3]);
                    }
                    else {
                        System.out.print("Ошибка №" + Math.abs(Integer.parseInt(m[1])));
                        System.out.println(Integer.parseInt(m[0])>0 ? (", ID: " + m[0]) : "");
                    }
                    break;

                case 3:
                    if (Integer.parseInt(m[1]) > 0)
                        System.out.println("Стоимость рассылки: " + m[0] + ", Всего SMS: " + m[1]);

                    else
                        System.out.print("Ошибка №" + Math.abs(Integer.parseInt(m[1])));

            }
        }

        return m;
    };



    /**
     * Проверка статуса отправленного SMS или HLR-запроса
     *
     * @param id - ID cообщения
     * @param phone - номер телефона
     * @param all - дополнительно возвращаются элементы в конце массива:
     *  (<время отправки>, <номер телефона>, <стоимость>, <sender id>, <название статуса>, <текст сообщения>)
     * @return array
     * для отправленного SMS (<статус>, <время изменения>, <код ошибки sms>)
     * для HLR-запроса (<статус>, <время изменения>, <код ошибки sms>, <код страны регистрации>, <код оператора абонента>,
     * <название страны регистрации>, <название оператора абонента>, <название роуминговой страны>, <название роумингового оператор
     * <код IMSI SIM-карты>, <номер сервис-центра>)
     * либо array(0, -<код ошибки>) в случае ошибки
     */

    public String[] getStatus(int id, String phone, int all)
    {
        String[] m = {};
        String tmp;

        try {
            m = sendCmd("status", "phone=" + URLEncoder.encode(phone, SMSC_CHARSET) + "&id=" + id + "&all=" + all);

            if (m.length > 1) {
                if (SMSC_DEBUG) {
                    if (!m[1].equals("") && Integer.parseInt(m[1]) >= 0) {
                        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Integer.parseInt(m[1]));
                        System.out.println("Статус SMS = " + m[0]);
                    }
                    else
                        System.out.println("Ошибка №" + Math.abs(Integer.parseInt(m[1])));
                }

                if (all == 1 && m.length > 9 && (m.length < 14 || !m[14].equals("HLR"))) {
                    tmp = implode(m, ",");
                    m = tmp.split(",", 9);
                }
            }
            else
                System.out.println("Не получен ответ от сервера.");
        }
        catch (UnsupportedEncodingException e) {
            System.out.print("Указанная кодировка символов не поддерживается!\n" + e + "\n");
        }

        return m;
    }

    /**
     * Получениe баланса
     * @return String баланс или пустую строку в случае ошибки
     */

    public String getBalance() {
        String[] m = {};

        m = sendCmd("balance", ""); // (balance) или (0, -error)

        if (m.length >= 1) {
            if (SMSC_DEBUG) {
                if (m.length == 1)
                    System.out.println("Сумма на счете: " + m[0]);
                else
                    System.out.println("Ошибка №" + Math.abs(Integer.parseInt(m[1])));
            }
        }
        else {
            System.out.println("Не получен ответ от сервера.");
        }
        return m.length == 2 ?    "" : m[0];
    }

    /**
     * Формирование и отправка запроса
     * @param cmd - требуемая команда
     * @param arg - дополнительные параметры
     */

    private String[] sendCmd(String cmd, String arg){
        String ret = "";
        String url;
        try {
            int i = 2;

            while (ret.isEmpty() && i < 6){
                url = String.format(SEND_SMS_HTTP,i,cmd,URLEncoder.encode(SMS_LOGIN, SMSC_CHARSET),URLEncoder.encode(SMS_PASSWORD, SMSC_CHARSET),SMSC_CHARSET,arg);
                ret = _smsc_read_url(url);
                i++;
            }
        }
        catch (UnsupportedEncodingException e) {
            System.out.print("Указанная кодировка символов не поддерживается!\n" + e + "\n");
        }

        return ret.split(",");
    }

    /**
     * Чтение URL
     * @param url - ID cообщения
     * @return line - ответ сервера
     */
    private String _smsc_read_url(String url) {

        String line = "";
        String[] param = {};
        boolean is_post = (SMSC_POST || url.length() > 2000);

        if (is_post) {
            param = url.split("\\?",2);
            url = param[0];
        }

        try {
            URL u = new URL(url);
            InputStream is = InputStream.nullInputStream();


            if (is_post){
                URLConnection conn = u.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream(), SMSC_CHARSET);
                os.write(param[1]);
                os.flush();
                os.close();
                System.out.println("post");
                is = conn.getInputStream();
            }
            else {
                is = u.openStream();
            }

            InputStreamReader reader = new InputStreamReader(is, SMSC_CHARSET);

            int ch;
            while ((ch = reader.read()) != -1) {
                line += (char)ch;
            }

            reader.close();
        }
        catch (MalformedURLException e) { // Неверный урл, протокол...
            System.out.print("Ошибка при обработке URL-адреса!\n" + e + "\n");
        }
        catch (IOException e) {
            System.out.print("Ошибка при операции передачи/приема данных!\n" + e + "\n");
        }

        return line;
    }

    private static String implode(String[] ary, String delim) {
        String out = "";

        for (int i = 0; i < ary.length; i++) {
            if (i != 0)
                out += delim;
            out += ary[i];
        }

        return out;
    }
}