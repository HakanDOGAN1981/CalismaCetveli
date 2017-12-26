package com.example.wolver.calismacetveli.data;


import android.provider.BaseColumns;

public class Sabitler implements BaseColumns {

    public static String DatabaseName = "calisma.db";
    public static Integer DatabaseVersion = 21;

    public static final class TblCetvelClass {

        //TÜM DEĞİŞKENLER, VERİLEN TABLO VE KOLON ADLARI TÜRKÇE OLMALI
        public static final String TBL_CETVEL = "TBL_CETVEL";
        public static final String CETVEL_ID = "TBL_ID";
        public static final String CETVEL_TUR_1 = "TUR_1";
        public static final String CETVEL_TUR_2 = "TUR_2";
        public static final String CETVEL_TUR_3 = "TUR_3";
        public static final String CETVEL_GIDER_1 = "GIDER_1";
        public static final String CETVEL_GIDER_2 = "GIDER_2";
        public static final String CETVEL_GIDER_3 = "GIDER_3";
        public static final String CETVEL_ACIKLAMA_1 = "ACIKLAMA_1";
        public static final String CETVEL_ACIKLAMA_2 = "ACIKLAMA_2";
        public static final String CETVEL_ACIKLAMA_3 = "ACIKLAMA_3";
        public static final String CETVEL_TARIH_BAS_1 = "TARIH_BAS_1";
        public static final String CETVEL_TARIH_BIT_1 = "TARIH_BIT_1";
        public static final String CETVEL_TARIH_BAS_2 = "TARIH_BAS_2";
        public static final String CETVEL_TARIH_BIT_2 = "TARIH_BIT_2";
        public static final String CETVEL_TARIH_BAS_3 = "TARIH_BAS_3";
        public static final String CETVEL_TARIH_BIT_3 = "TARIH_BIT_3";


        public static final String CREATE_TBL_CETVEL = "CREATE TABLE " + TBL_CETVEL + " (" + CETVEL_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + CETVEL_TUR_1 + " TEXT, " +
                CETVEL_TUR_2 + " TEXT, " + CETVEL_TUR_3 + " TEXT, " + CETVEL_GIDER_1 + " INTEGER, "
                + CETVEL_GIDER_2 + " INTEGER, " + CETVEL_GIDER_3 + " INTEGER, " +
                CETVEL_ACIKLAMA_1 + " TEXT, " + CETVEL_ACIKLAMA_2 + " TEXT, " + CETVEL_ACIKLAMA_3 + " TEXT, " +
                CETVEL_TARIH_BAS_1 + " TEXT, " + CETVEL_TARIH_BIT_1 + " TEXT, " +
                CETVEL_TARIH_BAS_2 + " TEXT, " + CETVEL_TARIH_BIT_2 + " TEXT, " +
                CETVEL_TARIH_BAS_3 + " TEXT, " + CETVEL_TARIH_BIT_3 + " TEXT)";

    }

    public static final class TblGiderClass {

        public static final String TBL_GIDER = "TBL_GIDER";
        public static final String GIDER_ID = "GIDER_ID";
        public static final String GIDER_TUR_1 = "GIDER_TUR_1";

        public static final String CREATE_TBL_GIDER = "CREATE TABLE " + TBL_GIDER + " (" + GIDER_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + GIDER_TUR_1 + " TEXT)";
    }

    public static final class TblTurClass {

        public static final String TBL_TUR = "TBL_TUR";
        public static final String TUR_ID = "TUR_ID";
        public static final String TUR_TUR_1 = "TUR_TUR_1";

        public static final String CREATE_TBL_TUR = "CREATE TABLE " + TBL_TUR + " (" + TUR_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + TUR_TUR_1 + " TEXT)";
    }

}
