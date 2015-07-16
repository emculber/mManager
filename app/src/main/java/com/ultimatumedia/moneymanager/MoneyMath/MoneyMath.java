package com.ultimatumedia.moneymanager.MoneyMath;

import android.util.Log;

/**
 * Created by Erik on 7/7/15.
 */
public class MoneyMath {
    public double addMoney(double num1, double num2) {
        double addedNumber = Math.round(((num1*100.0) + (num2*100.0)))/100.0;
        Log.i("Money Math", num1 + "+" + num2 + "=" + addedNumber);
        return addedNumber;
    }
    public double subtractMoney(double num1, double num2) {
        double subtractedNumber = Math.round(((num1*100.0) - (num2*100.0)))/100.0;
        Log.i("Money Math", num1 + "-" + num2 + "=" + subtractedNumber);
        return subtractedNumber;
    }
    public double addPercent(double num1, double num2) {
        double addedPercent = Math.round(((num1*10000.0) + (num2*10000.0)))/10000.0;
        Log.i("Money Math", num1 + "+" + num2 + "=" + addedPercent);
        return addedPercent;
    }
    public double subtractPercent(double num1, double num2) {
        double subtractedPercent = Math.round(((num1*10000.0) - (num2*10000.0)))/10000.0;
        Log.i("Money Math", num1 + "-" + num2 + "=" + subtractedPercent);
        return subtractedPercent;
    }
    public double multiplyMoneyPercent(double money, double percent) {
        double multiplyNumber = (money * 100.0) * (percent / 100.0);
        double decimal = Math.round((multiplyNumber - (int)(multiplyNumber)) * 10);
        multiplyNumber = Math.round((((int)(multiplyNumber))*1.0) + (decimal / 10))/ 100.0;
        Log.i("Money Math", money + "*" + percent + "=" + multiplyNumber);
        return multiplyNumber;
    }
}
