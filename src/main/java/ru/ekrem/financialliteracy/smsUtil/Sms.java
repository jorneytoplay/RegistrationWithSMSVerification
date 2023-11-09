package ru.ekrem.financialliteracy.smsUtil;

public interface Sms {
    String[] send_sms(
            String phones,
            String message,
            int translit,
            String time,
            String id,
            int format,
            String sender,
            String query
    );



}
