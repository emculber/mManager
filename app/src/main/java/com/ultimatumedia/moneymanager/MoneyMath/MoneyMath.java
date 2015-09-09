package com.ultimatumedia.moneymanager.MoneyMath;

import android.util.Log;

/**
 * Created by Erik on 7/7/15.
 */
public class MoneyMath {
    public static double addMoney(double num1, double num2) {
        double addedNumber = Math.round(((num1*100.0) + (num2*100.0)))/100.0;
        Log.i("Money Math", num1 + "+" + num2 + "=" + addedNumber);
        return addedNumber;
    }
    public static double subtractMoney(double num1, double num2) {
        double subtractedNumber = Math.round(((num1*100.0) - (num2*100.0)))/100.0;
        Log.i("Money Math", num1 + "-" + num2 + "=" + subtractedNumber);
        return subtractedNumber;
    }
    public static double addPercent(double num1, double num2) {
        double addedPercent = Math.round(((num1*10000.0) + (num2*10000.0)))/10000.0;
        Log.i("Money Math", num1 + "+" + num2 + "=" + addedPercent);
        return addedPercent;
    }
    public static double subtractPercent(double num1, double num2) {
        double subtractedPercent = Math.round(((num1*10000.0) - (num2*10000.0)))/10000.0;
        Log.i("Money Math", num1 + "-" + num2 + "=" + subtractedPercent);
        return subtractedPercent;
    }
    public static double multiplyMoneyPercent(double money, double percent) {
        double multiplyNumber = (money * 100.0) * (percent / 100.0);
        double decimal = Math.round((multiplyNumber - (int)(multiplyNumber)) * 10);
        multiplyNumber = Math.round((((int)(multiplyNumber))*1.0) + (decimal / 10))/ 100.0;
        Log.i("Money Math", money + "*" + percent + "=" + multiplyNumber);
        return multiplyNumber;
    }
    public static double divideTwoPercents(double percent1, double percent2) {
        double divideNumber = (percent1) / (percent2);
        Log.i("Money Math", percent1 + "/" + percent2 + "=" + divideNumber);
        return divideNumber;
    }
    public static double multiplyTwoPercents(double percent1, double percent2) {
        double multiplyNumber = (percent1) * (percent2);
        multiplyNumber = Math.round(multiplyNumber * 1000.0) / 1000.0;
        Log.i("Money Math", percent1 + "*" + percent2 + "=" + multiplyNumber);
        return multiplyNumber;
    }
}
