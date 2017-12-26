package com.example.wolver.calismacetveli;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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

    int sabah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.veri_girisi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarx);
        setSupportActionBar(toolbar);

        eklenenListviewTanitimi();
        autocomplateText();

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

        gelenVeri();

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


    public ContentValues valuesYukleme() {

        ContentValues contentValues = new ContentValues();

        contentValues.put(Sabitler.TblCetvelClass.CETVEL_TUR_1, mSpinnerTur1.getSelectedItem().toString());
        contentValues.put(Sabitler.TblCetvelClass.CETVEL_ACIKLAMA_1, mTxtAciklama1.getText().toString());
        contentValues.put(Sabitler.TblCetvelClass.CETVEL_GIDER_1, mTxtGider1.getText().toString());
        contentValues.put(Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1, mBtnBas1.getText().toString());
        contentValues.put(Sabitler.TblCetvelClass.CETVEL_TARIH_BIT_1, mBtnBit1.getText().toString());

        return contentValues;
    }


    public void veriGirisi() {
        if (gelenID != 0) {
            guncelle();
        } else {
            yeniVeriGirisi();
        }
    }

    public void yeniVeriGirisi() {

        if (mSpinnerTur1.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Yeni Kayıt Yapabilmek İçin" + "\n" + "\n" + "Bir Giriş Türü Seçmelisiniz!", Toast
                    .LENGTH_SHORT)
                    .show();
        } else {
            ContentValues contentValues = valuesYukleme();

            Uri uri = getContentResolver().insert(Provider.CETVEL_CONTENT_URI, contentValues);
            Toast.makeText(getApplicationContext(), "Yeni Kayıt Başarılı", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
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


    public void gelenVeri() {

        try {

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


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnTrBas1) {
            setmBtnBas1();
        }
        if (view.getId() == R.id.btnTrBit1) {
            setmBtnBit1();
        }
    }
}
