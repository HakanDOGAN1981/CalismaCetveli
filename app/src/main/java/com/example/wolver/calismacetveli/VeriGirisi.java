package com.example.wolver.calismacetveli;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wolver.calismacetveli.data.Provider;
import com.example.wolver.calismacetveli.data.Sabitler;

import java.util.Calendar;

public class VeriGirisi extends AppCompatActivity implements View.OnClickListener {

    public static AutoCompleteTextView mTxtAciklama1, mTxtGider1;
    public static Spinner mSpinnerTur1;
    public static String ayStr, gunStr, yilStr;
    public static String[] btnStrDizi = {"1", "1", "1"};
    public Context context = this;
    Button mBtnBas1, mBtnBit1;
    TextView mtxt;
    int gelenID;
    int cursorCount2;

    int gelenPosition;

    RecyclerView mRecyclerView;

    String[] simdiStrDizi = new String[3];
    Integer[] ayinGunleriDizi = new Integer[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.veri_girisi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarx);
        setSupportActionBar(toolbar);

        eklenenListviewTanitimi();
        autocomplateText();

        gelenVeriVarsaYukle();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                veriGirisi();
            }
        });

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_tur, android.R.layout
                .simple_dropdown_item_1line);
        mSpinnerTur1.setAdapter(spinnerAdapter);

        if (gelenPosition == 0) {
            mSpinnerTur1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                    if (mSpinnerTur1.getSelectedItemPosition() == 0) {
                        mTxtAciklama1.setText("");
                    } else {
                        txtDegisimi();
                        mTxtAciklama1.requestFocus();
                        mTxtAciklama1.setSelection(mTxtAciklama1.length());
                    }

                    if (mSpinnerTur1.getSelectedItemPosition() == 6) {
                        mTxtGider1.setText("Konaklama: ");
                        mTxtGider1.requestFocus();
                        mTxtGider1.setSelection(mTxtGider1.length());
                    }

                    if (mSpinnerTur1.getSelectedItemPosition() == 8) {
                        mTxtGider1.setText("Diğer: ");
                        mTxtAciklama1.requestFocus();
                        mTxtAciklama1.setSelection(mTxtAciklama1.length());
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }

        mTxtAciklama1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mTxtAciklama1.getText().toString().equalsIgnoreCase("Yolculuk: İkamet-Otogar") ||
                        mTxtAciklama1.getText().toString().equalsIgnoreCase("Yolculuk: Otogar-İkamet")) {
                    mTxtGider1.setText("Taksi: ");
                    mTxtGider1.requestFocus();
                    mTxtGider1.setSelection(mTxtGider1.length());
                }

                if (mTxtAciklama1.getText().toString().equalsIgnoreCase("Yolculuk: Otogar-Görev Yeri") ||
                        mTxtAciklama1.getText().toString().equalsIgnoreCase("Yolculuk: Görev Yeri-Otogar")) {
                    mTxtGider1.setText("Otobüs: ");
                    mTxtGider1.requestFocus();
                    mTxtGider1.setSelection(mTxtGider1.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void txtDegisimi() {

        mTxtAciklama1.setText(mSpinnerTur1.getSelectedItem().toString() + ": ");
    }

    private void autocomplateText() {

        String[] arrayAciklama = {"Yolculuk: İkamet-Otogar", "Yolculuk: Otogar-İkamet", "Yolculuk: Otogar-Görev Yeri",
                "Yolculuk: Görev Yeri-Otogar", "Teftiş: ", "Rapor: ",
                "Yazı: ", "Bildirim: ", "Diğer: ", "Konaklama", "Dosya İnceleme: "};
        String[] arrayGider = {"Taksi: ", "Otobüs: ", "Konaklama: "};

        ArrayAdapter<String> arrayAdapterAcik = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arrayAciklama);
        ArrayAdapter<String> arrayAdapterGider = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arrayGider);

        mTxtAciklama1.setAdapter(arrayAdapterAcik);

        mTxtGider1.setAdapter(arrayAdapterGider);
    }

    private void eklenenListviewTanitimi() {

        mSpinnerTur1 = (Spinner) findViewById(R.id.spinnerTur1);
        mTxtAciklama1 = (AutoCompleteTextView) findViewById(R.id.editTextAcik1);
        mTxtGider1 = (AutoCompleteTextView) findViewById(R.id.editTextGider1);
        mBtnBas1 = (Button) findViewById(R.id.btnTrBas1);
        mBtnBit1 = (Button) findViewById(R.id.btnTrBit1);

        mtxt = (TextView) findViewById(R.id.mtxt);
        mRecyclerView = (RecyclerView) findViewById(R.id.Recyclerview);
    }

    // MENÜ İŞLEMLERİ
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_veri, menu);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.geri) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.ekle) {
            veriGirisi();
        }

        return super.onOptionsItemSelected(item);
    }
    // MENÜ İŞLEMLERİ

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnTrBas1) {
            setmBtnBas1();
        }
        if (view.getId() == R.id.btnTrBit1) {
            setmBtnBit1();
        }
    }

    public void setmBtnBas1() {

        String mBtnStr = mBtnBas1.getText().toString();
        String mBtnGun = mBtnStr.substring(0, 2);
        String mBtnAy = mBtnStr.substring(3, 5);
        String mBtnYil = mBtnStr.substring(6, mBtnStr.length());


        Calendar simdikizaman = Calendar.getInstance();
        int yil = simdikizaman.get(Calendar.YEAR);
        int ay = simdikizaman.get(Calendar.MONTH);
        int gun = simdikizaman.get(Calendar.DAY_OF_MONTH);

        Integer sonGun = 0;
        Integer sonAy = 0;
        Integer sonYil = 0;

        try {
            sonGun = Integer.parseInt(mBtnGun);
            sonAy = Integer.parseInt(mBtnAy);
            sonYil = Integer.parseInt(mBtnYil);
        } catch (Exception ex) {
        }


        if (sonGun == 0) {
            sonGun = gun;
            sonAy = ay;
            sonYil = yil;
        } else {
            sonGun = Integer.parseInt(mBtnGun);
            sonAy = Integer.parseInt(mBtnAy) - 1;
            sonYil = Integer.parseInt(mBtnYil);
        }


        DatePickerDialog datePickerDialog = new DatePickerDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK, new DatePickerDialog
                .OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                String sonAyStr = null;
                String sonDayStr = null;

                int sonAy = month + 1;
                if (String.valueOf(sonAy).length() < 2) {
                    sonAyStr = String.valueOf(sonAy);
                    sonAyStr = "0" + sonAyStr;
                } else {
                    sonAyStr = String.valueOf(sonAy);
                }


                if (String.valueOf(day).length() < 2) {
                    sonDayStr = String.valueOf(day);
                    sonDayStr = "0" + sonDayStr;
                } else {
                    sonDayStr = String.valueOf(day);
                }

                mBtnBas1.setText(sonDayStr + "." + sonAyStr + "." + year);
                mBtnBit1.setText(sonDayStr + "." + sonAyStr + "." + year);
            }
        }, sonYil, sonAy, sonGun);
        datePickerDialog.show();
    }

    public void setmBtnBit1() {

        String mBtnStr = mBtnBit1.getText().toString();
        String mBtnGun = mBtnStr.substring(0, 2);
        String mBtnAy = mBtnStr.substring(3, 5);
        String mBtnYil = mBtnStr.substring(6, mBtnStr.length());

        Calendar simdikizaman = Calendar.getInstance();
        int yil = simdikizaman.get(Calendar.YEAR);
        int ay = simdikizaman.get(Calendar.MONTH);
        int gun = simdikizaman.get(Calendar.DAY_OF_MONTH);

        Integer sonGun = 0;
        Integer sonAy = 0;
        Integer sonYil = 0;

        try {
            sonGun = Integer.parseInt(mBtnGun);
            sonAy = Integer.parseInt(mBtnAy);
            sonYil = Integer.parseInt(mBtnYil);
        } catch (Exception ex) {
        }


        if (sonGun == 0) {
            sonGun = gun;
            sonAy = ay;
            sonYil = yil;
        } else {
            sonGun = Integer.parseInt(mBtnGun);
            sonAy = Integer.parseInt(mBtnAy) - 1;
            sonYil = Integer.parseInt(mBtnYil);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK, new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String sonAyStr = null;
                        String sonDayStr = null;
                        int sonAy = month + 1;
                        if (String.valueOf(sonAy).length() < 2) {
                            sonAyStr = String.valueOf(sonAy);
                            sonAyStr = "0" + sonAyStr;
                        } else {
                            sonAyStr = String.valueOf(sonAy);
                        }

                        if (String.valueOf(day).length() < 2) {
                            sonDayStr = String.valueOf(day);
                            sonDayStr = "0" + sonDayStr;
                        } else {
                            sonDayStr = String.valueOf(day);
                        }
                        mBtnBit1.setText(sonDayStr + "." + sonAyStr + "." + year);
                    }
                }, sonYil, sonAy, sonGun);
        datePickerDialog.show();
    }


    public void gelenVeriVarsaYukle() {
        try {
            //GELEN VERİ VARSA(GÜNCELLEME İÇİN) BURASI ÇALIŞIYOR
            //YOKSA ONCREATE İŞLLEMLERİ DEVAM EDİYOR
            String veri = getIntent().getExtras().getString("veri");
            gelenID = Integer.parseInt(veri);

            Uri contentUri = Provider.CETVEL_CONTENT_URI;
            String selection = Sabitler.TblCetvelClass.CETVEL_ID + " = " + (gelenID);

            Cursor cursor = getContentResolver().query(contentUri, null, selection, null,
                    null);


            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    try {
                        String spinnerGelen = cursor.getString(cursor.getColumnIndex(Sabitler.TblCetvelClass.CETVEL_TUR_1));
                        if (spinnerGelen != null) {
                            switch (spinnerGelen) {
                                case "Teftiş":
                                    gelenPosition = 1;
                                    break;
                                case "Rapor":
                                    gelenPosition = 2;
                                    break;
                                case "Yazı":
                                    gelenPosition = 3;
                                    break;
                                case "Bildirim":
                                    gelenPosition = 4;
                                    break;
                                case "Yolculuk":
                                    gelenPosition = 5;
                                    break;
                                case "Konaklama":
                                    gelenPosition = 6;
                                    break;
                                case "Dosya İncelemesi":
                                    gelenPosition = 7;
                                    break;
                                case "Diğer":
                                    gelenPosition = 8;
                                    break;
                            }
                        } else {
                            gelenPosition = 0;
                        }

                        mSpinnerTur1.setOnItemSelectedListener(null);
                        mSpinnerTur1.setSelection(gelenPosition);
                        mTxtAciklama1.setText(cursor.getString(cursor.getColumnIndex(Sabitler.TblCetvelClass.CETVEL_ACIKLAMA_1)));
                        mTxtGider1.setText(cursor.getString(cursor.getColumnIndex(Sabitler.TblCetvelClass.CETVEL_GIDER_1)));
                        mBtnBas1.setText(cursor.getString(cursor.getColumnIndex(Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1)));
                        mBtnBit1.setText(cursor.getString(cursor.getColumnIndex(Sabitler.TblCetvelClass.CETVEL_TARIH_BIT_1)));

                        if (mBtnBit1.getText().equals("")) {
                            mBtnBit1.setText(mBtnBas1.getText().toString());
                        }

                    } catch (Exception ex) {
                    }
                    cursor.close();

                    mSpinnerTur1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                            if (mSpinnerTur1.getSelectedItemPosition() == 0) {
                                mSpinnerTur1.setSelection(gelenPosition);
                            } else if (mSpinnerTur1.getSelectedItemPosition() == gelenPosition) {
                            } else if (mSpinnerTur1.getSelectedItemPosition() == 0) {
                                mTxtAciklama1.setText("");
                            } else {
                                txtDegisimi();
                                mTxtAciklama1.requestFocus();
                                mTxtAciklama1.setSelection(mTxtAciklama1.length());
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
            }
        } catch (Exception e) {
        }
    }

    public void veriGirisi() {
        if (gelenID != 0) {
            guncelle();
        } else {
            yeniVeriGirisi();
        }
    }

    public String[] btnAyGunYil() {

        gunStr = mBtnBas1.getText().toString().substring(0, 2);
        ayStr = mBtnBas1.getText().toString().substring(3, 5);
        yilStr = mBtnBas1.getText().toString().substring(6, mBtnBas1.getText().toString().length());

        return btnStrDizi = new String[]{gunStr, ayStr, yilStr};
    }

    public void yeniVeriGirisi() {

        btnAyGunYil();

        cursorCount2 = yeniVeriTarihiVarMı();

        if (mSpinnerTur1.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Yeni Kayıt Yapabilmek İçin" + "\n" + "\n" + "Bir Giriş Türü Seçmelisiniz!", Toast
                    .LENGTH_SHORT).show();
        } else if (mBtnBas1.getText().equals("Tarih Seçiniz")) {
            Toast.makeText(getApplicationContext(), "Yeni Kayıt Yapabilmek İçin" + "\n" + "\n" + "Bir Tarih Seçmelisiniz!", Toast
                    .LENGTH_SHORT).show();
        } else {
            if (cursorCount2 == 0) {
                sharedPrefencesOlustur();
                yeniVeriTarihYoksaListeOlustur();
                yeniVerileriGir();

            } else {
                sharedPrefencesOlustur();
                yeniVerileriGir();
            }
        }
    }


    private void yeniVerileriGir() {

        ContentValues contentValues = valuesYukleme();

        Uri uri = getContentResolver().insert(Provider.CETVEL_CONTENT_URI, contentValues);
        Toast.makeText(getApplicationContext(), "Yeni Kayıt Başarılı", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void guncelle() {

        btnAyGunYil();

        if (mSpinnerTur1.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Güncelleme Yapabilmek İçin" + "\n" + "\n" + "Bir Giriş Türü Seçmelisiniz!", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Uri contentUri = Provider.CETVEL_CONTENT_URI;
            String selection = Sabitler.TblCetvelClass.CETVEL_ID + " = " + (gelenID);

            Cursor cursor = getContentResolver().query(contentUri, null, selection, null, null);

            ContentValues contentValues = valuesYukleme();

            try {
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        getContentResolver().update(contentUri, contentValues, selection, null);
                        cursor.close();
                    }

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            } catch (Exception ex) {
                Toast.makeText(context, "Hata: " + ex, Toast.LENGTH_LONG).show();
            }
        }
    }

    public ContentValues valuesYukleme() {

        ContentValues contentValues = new ContentValues();

        contentValues.put(Sabitler.TblCetvelClass.CETVEL_TUR_1, mSpinnerTur1.getSelectedItem().toString());
        contentValues.put(Sabitler.TblCetvelClass.CETVEL_ACIKLAMA_1, mTxtAciklama1.getText().toString());
        contentValues.put(Sabitler.TblCetvelClass.CETVEL_GIDER_1, mTxtGider1.getText().toString());
        contentValues.put(Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1, mBtnBas1.getText().toString());
        contentValues.put(Sabitler.TblCetvelClass.CETVEL_TARIH_BIT_1, mBtnBit1.getText().toString());

        sharedPrefencesOlustur();

        return contentValues;
    }


    public void sharedPrefencesOlustur() {

        SharedPreferences preferences = getSharedPreferences("share", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt("secilen", 1);
        editor.putString("shrAy", btnStrDizi[1].toString());
        editor.putString("shrYil", btnStrDizi[2].toString());

        editor.commit();
    }

    public void yeniVeriTarihYoksaListeOlustur() {
        simdiStrDizi = Tarihler.simdiOlustur();
        ayinGunleriDizi = Tarihler.ayinGunleri();

        String mBtnStr = mBtnBas1.getText().toString();
        final String mBtnAy = mBtnStr.substring(3, 5);
        final String mBtnYil = mBtnStr.substring(6, mBtnStr.length());

        String selection = Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1 + " LIKE ?";
        String[] selectionArgs = {"%." + mBtnAy + "." + mBtnYil + "%"};
        final Cursor cursor = getContentResolver().query(Provider.CETVEL_CONTENT_URI, null,
                selection, selectionArgs, null);
        final int cursorCount = cursor.getCount();

        if (cursorCount == 0) {
            ContentValues contentValues = new ContentValues();

            String soni2;
            for (int i2 = 1; i2 <= ayinGunleriDizi[0]; i2++) {

                if (String.valueOf(i2).length() < 2) {
                    soni2 = String.valueOf(i2);
                    soni2 = "0" + soni2;
                } else {
                    soni2 = String.valueOf(i2);
                }

                contentValues.put(Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1, soni2 + "." + mBtnAy + "" + "" +
                        "." + mBtnYil);

                Uri uri = getContentResolver().insert(Provider.CETVEL_CONTENT_URI, contentValues);
            }


            String soni3;
            ContentValues contentValues2 = new ContentValues();

            for (int i3 = ayinGunleriDizi[1]; i3 <= ayinGunleriDizi[0]; i3 += 7) {
                if (String.valueOf(i3).length() < 2) {
                    soni3 = String.valueOf(i3);
                    soni3 = "0" + soni3;
                } else {
                    soni3 = String.valueOf(i3);
                }

                String select = Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1 + " = ?";
                String[] args = {soni3 + "." + mBtnAy + "." + mBtnYil};

                contentValues2.put(Sabitler.TblCetvelClass.CETVEL_ACIKLAMA_1, "Cumartesi");
                getContentResolver().update(Provider.CETVEL_CONTENT_URI, contentValues2, select, args);
            }


            String soni4;
            ContentValues contentValues3 = new ContentValues();

            for (int i4 = ayinGunleriDizi[1] + 1; i4 <= ayinGunleriDizi[0]; i4 = i4 + 7) {
                if (String.valueOf(i4).length() < 2) {
                    soni4 = String.valueOf(i4);
                    soni4 = "0" + soni4;
                } else {
                    soni4 = String.valueOf(i4);
                }
                String select = Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1 + " = ?";
                String[] args = {soni4 + "." + mBtnAy + "." + mBtnYil};

                contentValues3.put(Sabitler.TblCetvelClass.CETVEL_ACIKLAMA_1, "Pazar");
                getContentResolver().update(Provider.CETVEL_CONTENT_URI, contentValues3, select, args);
            }

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        cursor.close();


        Intent main = new Intent(context, MainActivity.class);
        startActivity(main);
    }


    public int yeniVeriTarihiVarMı() {
        String mBtnStr = mBtnBas1.getText().toString();
        String mBtnGun = mBtnStr.substring(0, 2);
        String mBtnAy = mBtnStr.substring(3, 5);
        String mBtnYil = mBtnStr.substring(6, mBtnStr.length());

        String selection = Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1 + " LIKE ?";
        String[] selectionArgs = {"%." + mBtnAy + "." + mBtnYil + "%"};
        final Cursor cursor = getContentResolver().query(Provider.CETVEL_CONTENT_URI, null, selection, selectionArgs, null);
        cursorCount2 = cursor.getCount();

        return cursorCount2;
    }
}
