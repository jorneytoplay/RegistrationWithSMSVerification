package ru.ekrem.financialliteracy.util;

import java.util.Random;
import java.sql.Timestamp;

public final class GeneratorUtil {

    private static final Random rand = new Random();

    public static Integer getRandomNumber(int digit){
        return rand.nextInt((int) Math.pow(10,digit));
    }

    public static Long getTimestamp(){
        return System.currentTimeMillis();
    }
}
