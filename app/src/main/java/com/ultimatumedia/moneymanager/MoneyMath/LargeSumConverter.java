package com.ultimatumedia.moneymanager.MoneyMath;

/**
 * Created by Erik on 7/28/15.
 */
public class LargeSumConverter {
    public static String AutoConvert(double amount) {
        if(amount < 1000) {
            return NoConversiton(amount);
        }else if(amount < 1000000) {
            return ThousandConversiton(amount);
        }else if(amount < 1000000000) {
            return MillionConversiton(amount);
        }
        return NoConversiton(amount);
    }

    public static String NoConversiton(double amount){
        return "$" + Double.toString(amount);
    }

    public static String ThousandConversiton(double amount){
        String sAmount = Double.toString(Math.floor(amount));
        sAmount = sAmount.substring(0, sAmount.length() - 3);
        return "$" + sAmount + "k";
    }

    public static String MillionConversiton(double amount){
        String sAmount = Double.toString(Math.floor(amount));
        sAmount = sAmount.substring(0, sAmount.length() - 6);
        return "$" + sAmount + "M";
    }
}
