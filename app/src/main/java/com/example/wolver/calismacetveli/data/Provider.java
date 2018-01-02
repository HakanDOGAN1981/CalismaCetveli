package com.example.wolver.calismacetveli.data;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Provider extends ContentProvider {

    //   CONTENT PROVIDER SABİTLERİ TANIMLANIYOR
    public static final String CONTENT_AUTHORITY = "com.example.wolver.calismacetveli.data.provider";
    public static final String PATH_CETVEL = Sabitler.TblCetvelClass.TBL_CETVEL;
    public static final String PATH_GIDER = Sabitler.TblGiderClass.TBL_GIDER;
    public static final String PATH_TUR = Sabitler.TblTurClass.TBL_TUR;

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final Uri CETVEL_CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CETVEL);
    public static final Uri GİDER_CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_GIDER);
    public static final Uri TUR_CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TUR);
    //   CONTENT PROVIDER SABİTLERİ TANIMLANIYOR


    //   URIMATCHER TANIMLANMASI
    public static UriMatcher matcher;

    static {

        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(CONTENT_AUTHORITY, PATH_CETVEL, 1);
        matcher.addURI(CONTENT_AUTHORITY, PATH_GIDER, 2);
        matcher.addURI(CONTENT_AUTHORITY, PATH_TUR, 3);
    }
    //   URIMATCHER TANIMLANMASI

    SQLiteDatabase db;


    @Override
    public boolean onCreate() {
        //   DATABASE BURADA OLUŞTURULUYOR.
        Helper helper = new Helper(getContext(), Sabitler.DatabaseName, null, Sabitler.DatabaseVersion);
        db = helper.getWritableDatabase();
        return false;
        //   DATABASE BURADA OLUŞTURULUYOR.
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] columns, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String s1) {

        Cursor cursor = null;

        switch (matcher.match(uri)) {

            case 1:
                cursor = db.query(Sabitler.TblCetvelClass.TBL_CETVEL, columns, selection, selectionArgs, null,
                        null, Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1 + " ASC");
                break;

            case 2:
                cursor = db.query(Sabitler.TblGiderClass.TBL_GIDER, columns, selection, selectionArgs, null, null,
                        Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1 + " ASC");
                break;

            case 3:
                cursor = db.query(Sabitler.TblTurClass.TBL_TUR, columns, selection, selectionArgs, null, null,
                        Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1 + " ASC");
                break;
        }
        return cursor;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        switch (matcher.match(uri)) {

            case 1:
                Long id = db.insert(Sabitler.TblCetvelClass.TBL_CETVEL, null, contentValues);

                //  SADECE BİLGİ AMAÇLI. KAYIT SORGULAMA İÇİN
                if (id > 0) {
                    Uri _uri = ContentUris.withAppendedId(CETVEL_CONTENT_URI, id);
                    return _uri;
                }
                //  SADECE BİLGİ AMAÇLI. KAYIT SORGULAMA İÇİN

                break;

            case 2:
                Long id2 = db.insert(Sabitler.TblGiderClass.TBL_GIDER, null, contentValues);
                //  SADECE BİLGİ AMAÇLI. KAYIT SORGULAMA İÇİN
                if (id2 > 0) {
                    Uri _uri = ContentUris.withAppendedId(CETVEL_CONTENT_URI, id2);
                    return _uri;
                }
                //  SADECE BİLGİ AMAÇLI. KAYIT SORGULAMA İÇİN
                break;

            case 3:
                Long id3 = db.insert(Sabitler.TblTurClass.TBL_TUR, null, contentValues);
                //  SADECE BİLGİ AMAÇLI. KAYIT SORGULAMA İÇİN
                if (id3 > 0) {
                    Uri _uri = ContentUris.withAppendedId(CETVEL_CONTENT_URI, id3);
                    return _uri;
                }
                //  SADECE BİLGİ AMAÇLI. KAYIT SORGULAMA İÇİN
                break;
        }
        return null;
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        switch (matcher.match(uri)) {

            case 1:
                db.delete(Sabitler.TblCetvelClass.TBL_CETVEL, selection, selectionArgs);
                break;

            case 2:
                db.delete(Sabitler.TblGiderClass.TBL_GIDER, selection, selectionArgs);
                break;

            case 3:
                db.delete(Sabitler.TblTurClass.TBL_TUR, selection, selectionArgs);
                break;
        }
        return 0;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {

        switch (matcher.match(uri)) {

            case 1:
                db.update(Sabitler.TblCetvelClass.TBL_CETVEL, contentValues, selection, selectionArgs);
                break;

            case 2:
                db.update(Sabitler.TblGiderClass.TBL_GIDER, contentValues, selection, selectionArgs);
                break;

            case 3:
                db.update(Sabitler.TblTurClass.TBL_TUR, contentValues, selection, selectionArgs);
                break;
        }
        return 0;
    }

}

