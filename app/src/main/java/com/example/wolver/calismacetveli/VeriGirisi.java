package com.example.wolver.calismacetveli;

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

    Button mBtnBas1, mBtnBit1;
    Context context = this;
    TextView mtxt;
    int gelenID;
    int cursorCount2;

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

        mSpinnerTur1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                if (mSpinnerTur1.getSelectedItemPosition() == 0) {
                    mTxtAciklama1.setText("");
                } else {
                    txtDegisimi();
                    mTxtAciklama1.requestFocus();
                    mTxtAciklama1.selectAll();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

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

        Calendar simdikizaman = Calendar.getInstance();
        int yil = simdikizaman.get(Calendar.YEAR);
        int ay = simdikizaman.get(Calendar.MONTH);
        int gun = simdikizaman.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog
                .OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                String sonAyStr = null;
                String sonDayStr = null;
                int sonAy = month + 1;
                if (String.valueOf(month).length() < 2) {
                    sonAy = month + 1;
                    if (String.valueOf(sonAy).length() < 2) {
                        sonAyStr = String.valueOf(sonAy);
                        sonAyStr = "0" + sonAyStr;
                    }
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
        }, yil, ay, gun);
        datePickerDialog.show();
    }

    public void setmBtnBit1() {

        Calendar simdikizaman = Calendar.getInstance();
        int yil = simdikizaman.get(Calendar.YEAR);
        int ay = simdikizaman.get(Calendar.MONTH);
        int gun = simdikizaman.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String sonAyStr = null;
                String sonDayStr = null;
                int sonAy = month + 1;
                if (String.valueOf(month).length() < 2) {
                    sonAy = month + 1;
                    if (String.valueOf(sonAy).length() < 2) {
                        sonAyStr = String.valueOf(sonAy);
                        sonAyStr = "0" + sonAyStr;
                    }
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
        }, yil, ay, gun);
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

            int gelenPosition = 0;

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    try {
                        String spinnerGelen = cursor.getString(cursor.getColumnIndex(Sabitler.TblCetvelClass.CETVEL_TUR_1));

                        if (spinnerGelen != null) {
                            switch (spinnerGelen) {
                                case "Yolculuk":
                                    gelenPosition = 1;
                                    break;
                                case "Konaklama":
                                    gelenPosition = 2;
                                    break;
                                case "Dosya İncelemesi":
                                    gelenPosition = 3;
                                    break;
                                case "Teftiş":
                                    gelenPosition = 4;
                                    break;
                                case "Bildirim":
                                    gelenPosition = 5;
                                    break;
                                case "Rapor":
                                    gelenPosition = 6;
                                    break;
                                case "Yazı":
                                    gelenPosition = 7;
                                    break;
                                case "Diğer":
                                    gelenPosition = 8;
                                    break;
                            }
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

                    final int finalGelenPosition = gelenPosition;
                    mSpinnerTur1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                            if (finalGelenPosition != position) {

                                if (mSpinnerTur1.getSelectedItemPosition() == 0) {
                                    mTxtAciklama1.setText("");
                                } else {
                                    txtDegisimi();
                                    mTxtAciklama1.requestFocus();
                                    mTxtAciklama1.selectAll();
                                }
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

    public void yeniVeriGirisi() {

        cursorCount2 = yeniVeriTarihiVarMı();

        if (mSpinnerTur1.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Yeni Kayıt Yapabilmek İçin" + "\n" + "\n" + "Bir Giriş Türü Seçmelisiniz!", Toast
                    .LENGTH_SHORT).show();
        } else {
            if (cursorCount2 == 0) {
                yeniVeriTarihYoksaListeOlustur();
                YeniVerileriGir();
            } else {
                YeniVerileriGir();
            }
        }
    }

    private void YeniVerileriGir() {

        ContentValues contentValues = valuesYukleme();

        Uri uri = getContentResolver().insert(Provider.CETVEL_CONTENT_URI, contentValues);
//        Toast.makeText(getApplicationContext(), "Yeni Kayıt Başarılı", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void guncelle() {

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

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("tur", mSpinnerTur1.getSelectedItem().toString());
        editor.putString("trAy", mBtnBas1.getText().toString().substring(3, 5));
        editor.putString("trYil", mBtnBas1.getText().toString().substring(6, mBtnBas1.getText().length()));
        editor.apply();
    }

    public void yeniVeriTarihYoksaListeOlustur() {
        String mBtnStr = mBtnBas1.getText().toString();
        String mBtnGun = mBtnStr.substring(0, 2);
        final String mBtnAy = mBtnStr.substring(3, 5);
        final String mBtnYil = mBtnStr.substring(6, mBtnStr.length());
        int mBtnAyGun = Integer.parseInt(mBtnGun);
        int mBtnAyInt = Integer.parseInt(mBtnAy);
        int mBtnYilInt = Integer.parseInt(mBtnYil);
        final int aydakiGunSayısı;
        final int ilkGunInt;
        final int ilkCumartesi;


        if (mBtnAyInt == 1 || mBtnAyInt == 3 || mBtnAyInt == 5 || mBtnAyInt == 7 || mBtnAyInt == 8 || mBtnAyInt == 10 || mBtnAyInt == 12) {
            aydakiGunSayısı = 31;
        } else if (mBtnAyInt == 4 || mBtnAyInt == 6 || mBtnAyInt == 9 || mBtnAyInt == 11) {
            aydakiGunSayısı = 30;
        } else if (mBtnAyInt == 2 && mBtnYilInt % 4 == 0) {
            aydakiGunSayısı = 29;
        } else {
            aydakiGunSayısı = 28;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(mBtnYilInt, mBtnAyInt, mBtnAyGun);
        ilkGunInt = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int ilkCumartesiGerekliGun = 6 - ilkGunInt;
        ilkCumartesi = 1 + ilkCumartesiGerekliGun;

        String selection = Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1 + " LIKE ?";
        String[] selectionArgs = {"%." + mBtnAy + "." + mBtnYil + "%"};
        final Cursor cursor = getContentResolver().query(Provider.CETVEL_CONTENT_URI, null,
                selection, selectionArgs, null);
        final int cursorCount = cursor.getCount();

        if (cursorCount == 0) {
            ContentValues contentValues = new ContentValues();

            String soni2;
            for (int i2 = 1; i2 <= aydakiGunSayısı; i2++) {

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

            for (int i3 = ilkCumartesi; i3 <= aydakiGunSayısı; i3 += 7) {
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

            for (int i4 = ilkCumartesi + 1; i4 <= aydakiGunSayısı; i4 = i4 + 7) {
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

            Toast.makeText(context, "Girilen Veri Tarihi Bulunmadığı İçin \n Girilen Ay Oluşturulmuştur." + aydakiGunSayısı,
                    Toast.LENGTH_LONG).show();
        }
        cursor.close();
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
