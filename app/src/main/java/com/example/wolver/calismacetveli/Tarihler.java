package com.example.wolver.calismacetveli;


import android.content.Context;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class Tarihler {

    static Calendar simdi, calendar;
    static int month, simdiYilInt, simdiAyInt, ilkGunInt, ilkCumartesiInt, aydakiGunSayısıInt, simdiGunInt,
            ilkCumartesiGerekliGunInt, ayInt, simdiAydakiGunSayısıInt;
    static String simdiSonAyStr, ilkGunStr, aydakiGunSayısıStr, simdiSonGunStr, simdiYilStr, ayStr;
    static String[] simdiStrDizi;
    static Integer[] ayinGunleriDizi;

    public static String[] simdiOlustur() {
        simdi = Calendar.getInstance();

        simdiGunInt = simdi.get(Calendar.DAY_OF_MONTH);
        month = simdi.get(Calendar.MONTH);
        simdiAyInt = month + 1;
        simdiYilInt = simdi.get(Calendar.YEAR);

        if (String.valueOf(simdiGunInt).length() < 2) {
            simdiSonGunStr = "0" + String.valueOf(simdiGunInt);
        } else {
            simdiSonGunStr = String.valueOf(simdiGunInt);
        }

        if (String.valueOf(simdiAyInt).length() < 2) {
            simdiSonAyStr = "0" + String.valueOf(simdiAyInt);
        } else {
            simdiSonAyStr = String.valueOf(simdiAyInt);
        }

        simdiYilStr = String.valueOf(simdiYilInt);

        return simdiStrDizi = new String[]{simdiSonGunStr, simdiSonAyStr, simdiYilStr};
    }

    public static Integer[] ayinGunleri() {
        calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
//        simdiAyInt = month + 1;
        ayStr = VeriGirisi.ayStr;
        ayInt = 0;
        try {
            ayInt = Integer.parseInt(ayStr);
        } catch (Exception e) {
        }

        //AYIN İLK GÜNÜNÜ BULMAK(STRING)
        calendar.set(Calendar.DATE, 1);
        ilkGunStr = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        //AYIN İLK GÜNÜNÜ BULMAK(STRING)

        //AYIN İLK GÜNÜNÜ BULMAK(INT)-HAFTALIK DEĞER
        ilkGunInt = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        ilkCumartesiGerekliGunInt = 6 - ilkGunInt;
        ilkCumartesiInt = 1 + ilkCumartesiGerekliGunInt;
        //AYIN İLK GÜNÜNÜ BULMAK(INT)

        if (ayInt == 1 || ayInt == 3 || ayInt == 5 || ayInt == 7 || ayInt == 8 || ayInt == 10 || ayInt == 12) {
            aydakiGunSayısıInt = 31;
        } else if (ayInt == 4 || ayInt == 6 || ayInt == 9 || ayInt == 11) {
            aydakiGunSayısıInt = 30;
        } else if (ayInt == 2 && ayInt % 4 == 0) {
            aydakiGunSayısıInt = 29;
        } else {
            aydakiGunSayısıInt = 28;
        }

        if (simdiAyInt == 1 || simdiAyInt == 3 || simdiAyInt == 5 || simdiAyInt == 7 || simdiAyInt == 8 || simdiAyInt == 10 || simdiAyInt == 12) {
            simdiAydakiGunSayısıInt = 31;
        } else if (simdiAyInt == 4 || simdiAyInt == 6 || simdiAyInt == 9 || simdiAyInt == 11) {
            simdiAydakiGunSayısıInt = 30;
        } else if (simdiAyInt == 2 && simdiAyInt % 4 == 0) {
            simdiAydakiGunSayısıInt = 29;
        } else {
            simdiAydakiGunSayısıInt = 28;
        }

        aydakiGunSayısıStr = String.valueOf(aydakiGunSayısıInt);

        return ayinGunleriDizi = new Integer[]{aydakiGunSayısıInt, ilkCumartesiInt, simdiAydakiGunSayısıInt};

    }

}
