package ru.ekrem.financialliteracy.util;

public final class ConstValue {
    public static String SEND_SMS_HTTP = "http://www%s.smsc.ru/sys/%s.php?login=%s&psw=%s&fmt=1&charset=%s&%s";
    public static String SEND_SMS_HTTPS = "https://www%s.smsc.ru/sys/%s.php?login=%s&psw=%s&fmt=1&charset=%s&%s";
    public static String SEND_SMS_ARGS = "cost=3&phones=%s&mes=%s&translit=%s&id=%s%s%s%s%s";
}