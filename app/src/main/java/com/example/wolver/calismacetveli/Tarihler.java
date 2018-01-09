package com.example.wolver.calismacetveli;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Tarihler {

    public static int month, simdiYilInt, simdiAyInt, ilkGunInt, ilkCumartesiInt, aydakiGunSayısıInt, simdiGunInt,
            ilkCumartesiGerekliGunInt, ayInt, simdiAydakiGunSayısıInt, gunInt, yilInt, simdiCumartesi;
    public static String simdiSonAyStr, aydakiGunSayısıStr, simdiSonGunStr, simdiYilStr, ayStr, gunStr, yilStr;
    static Calendar simdi, calendar;
    static String[] simdiStrDizi, ayinGunleriDiziStr;
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

    public static Integer simdiCumartesi() {
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

//////////////////////////////////////////////////////////////////////////////
        DateFormat df = new SimpleDateFormat("dd.MM.yyy");
        try {
            Date date = df.parse(simdiSonGunStr + "." + simdiSonAyStr + "." + simdiYilStr);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ilkGunInt = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        if (ilkGunInt == 0) {
            ilkGunInt = 7;
        }


        ilkCumartesiGerekliGunInt = 8 - ilkGunInt;
        ilkCumartesiInt = ilkCumartesiGerekliGunInt;

        return simdiCumartesi = ilkCumartesiInt;
    }

    public static Integer[] ayinGunleri() {
        calendar = Calendar.getInstance();

        gunStr = VeriGirisi.btnStrDizi[0];
        ayStr = VeriGirisi.btnStrDizi[1];
        yilStr = VeriGirisi.btnStrDizi[2];

        gunInt = 0;
        ayInt = 0;
        yilInt = 0;

        try {
            gunInt = Integer.parseInt(gunStr);
            ayInt = Integer.parseInt(ayStr);
            yilInt = Integer.parseInt(yilStr);
        } catch (Exception e) {
        }

        DateFormat df = new SimpleDateFormat("dd.MM.yyy");
        try {
            Date date = df.parse(gunStr + "." + ayStr + "." + yilStr);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ilkGunInt = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        if (ilkGunInt == 0) {
            ilkGunInt = 7;
        }

        ilkCumartesiGerekliGunInt = 7 - ilkGunInt;
        ilkCumartesiInt = ilkCumartesiGerekliGunInt;

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

        return ayinGunleriDizi = new Integer[]{aydakiGunSayısıInt, ilkCumartesiInt, simdiAydakiGunSayısıInt, ilkGunInt, ayInt,
                yilInt};
    }


    public static String[] ayinGunleriStr() {
        calendar = Calendar.getInstance();

        gunStr = VeriGirisi.btnStrDizi[0];
        ayStr = VeriGirisi.btnStrDizi[1];
        yilStr = VeriGirisi.btnStrDizi[2];

        return ayinGunleriDiziStr = new String[]{ayStr, yilStr};
    }

}
