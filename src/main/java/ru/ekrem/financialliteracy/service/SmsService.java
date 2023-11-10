package ru.ekrem.financialliteracy.service;

public interface SmsService {
    String[] sendSms(String phone, String message, int translit, int cost);
    String getBalance();
    String[] getStatus(int id, String phone, int all);




}
