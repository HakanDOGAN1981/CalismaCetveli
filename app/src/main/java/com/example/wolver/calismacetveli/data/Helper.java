package com.example.wolver.calismacetveli.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Helper extends SQLiteOpenHelper {

    public Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(Sabitler.TblCetvelClass.CREATE_TBL_CETVEL);
        sqLiteDatabase.execSQL(Sabitler.TblGiderClass.CREATE_TBL_GIDER);
        sqLiteDatabase.execSQL(Sabitler.TblTurClass.CREATE_TBL_TUR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Sabitler.TblCetvelClass.TBL_CETVEL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Sabitler.TblGiderClass.TBL_GIDER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Sabitler.TblTurClass.TBL_TUR);
        onCreate(sqLiteDatabase);
    }
}
