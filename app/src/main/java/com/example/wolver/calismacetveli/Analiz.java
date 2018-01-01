package com.example.wolver.calismacetveli;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.service.autofill.RegexValidator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.wolver.calismacetveli.data.Provider;
import com.example.wolver.calismacetveli.data.Sabitler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analiz extends AppCompatActivity {

    TextView txtTrh, txtTef, txtRp, txtYz, txtBd, txtDos, txtTopTl, txtTaksi, txtOtobus, txtKonak, txtDiger, txtDigerTl;
    EditText txtHrc;
    String ay, yil;
    Context context = this;
    ImageView mImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarx);
        toolbar.setTitle("Analiz Sayfası");
        setSupportActionBar(toolbar);

        txtTrh = (TextView) findViewById(R.id.txtTrh);
        txtTef = (TextView) findViewById(R.id.txtTef);
        txtRp = (TextView) findViewById(R.id.txtRp);
        txtYz = (TextView) findViewById(R.id.txtYz);
        txtBd = (TextView) findViewById(R.id.txtBd);
        txtDos = (TextView) findViewById(R.id.txtDos);
        txtTopTl = (TextView) findViewById(R.id.txtTopTl);
        txtTaksi = (TextView) findViewById(R.id.txtTaksi);
        txtOtobus = (TextView) findViewById(R.id.txtOtobus);
        txtKonak = (TextView) findViewById(R.id.txtKonak);
        txtDiger = (TextView) findViewById(R.id.txtDiger);
        txtDigerTl = (TextView) findViewById(R.id.txtDigerTl);
        txtHrc = (EditText) findViewById(R.id.edtHrc);
        mImg = (ImageView) findViewById(R.id.imageView2);

        yil = sharedprefencesAl();

        if (String.valueOf(MainActivity.ayAnaliz).length() < 2) {
            ay = String.valueOf(MainActivity.ayAnaliz);
            ay = "0" + MainActivity.ayAnaliz;
        } else {
            ay = String.valueOf(MainActivity.ayAnaliz);
        }

        txtTrh.setText(ay + "." + yil);

        glideİslemleri();

        textleriDoldur();

        final int ilkToplam = Integer.parseInt(txtTopTl.getText().toString().replaceAll("\\D+", ""));
        final int[] hrcGun = {0};


        txtHrc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    hrcGun[0] = Integer.parseInt(txtHrc.getText().toString());
                    hrcGun[0] = hrcGun[0] * 67;
                    Integer toplam = hrcGun[0] + Integer.parseInt(txtTopTl.getText().toString().replaceAll("\\D+", ""));

                    if (hrcGun[0] == 0) {
                        txtTopTl.setText("" + ilkToplam + " TL");
                    } else {
                        txtTopTl.setText("" + toplam + " TL");
                    }
                } catch (Exception e) {
                    txtTopTl.setText("" + ilkToplam + " TL");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void glideİslemleri() {

        Glide.with(this)
                .load(R.drawable.bayrakgri2)
                .apply(new RequestOptions().centerCrop())
                .into(mImg);
    }


    // MENÜ İŞLEMLERİ
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.analiz, menu);
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

        return super.onOptionsItemSelected(item);
    }
    // MENÜ İŞLEMLERİ

    public String sharedprefencesAl() {

        SharedPreferences sharedPreferences = context.getSharedPreferences("share", MODE_PRIVATE);
        yil = sharedPreferences.getString("shrYil", "yok");
        return yil;
    }

    private void textleriDoldur() {

        sayimlar();
        harcamalar();
    }

    private void sayimlar() {

        String[] projection = {Sabitler.TblCetvelClass.CETVEL_ID, Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1, Sabitler
                .TblCetvelClass.CETVEL_ACIKLAMA_1};
        String selection = Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1 + " LIKE ? AND " + Sabitler.TblCetvelClass
                .CETVEL_ACIKLAMA_1 + " LIKE ?";

        String suzTef = "Teftiş";
        String[] argsTef = {"%." + ay + "." + yil + "%", "%" + suzTef + "%"};
        Cursor crTef = getContentResolver().query(Provider.CETVEL_CONTENT_URI, projection, selection, argsTef, null);

        String suzRp = "Rapor";
        String[] argRp = {"%." + ay + "." + yil + "%", "%" + suzRp + "%"};
        Cursor crRp = getContentResolver().query(Provider.CETVEL_CONTENT_URI, projection, selection, argRp, null);

        String suzYz = "Yazı";
        String[] argsYz = {"%." + ay + "." + yil + "%", "%" + suzYz + "%"};
        Cursor crYz = getContentResolver().query(Provider.CETVEL_CONTENT_URI, projection, selection, argsYz, null);

        String suzBd = "Bildirim";
        String[] argsBd = {"%." + ay + "." + yil + "%", "%" + suzBd + "%"};
        Cursor crBd = getContentResolver().query(Provider.CETVEL_CONTENT_URI, projection, selection, argsBd, null);

        String suzDos = "Dosya";
        String[] argsDos = {"%." + ay + "." + yil + "%", "%" + suzDos + "%"};
        Cursor crDos = getContentResolver().query(Provider.CETVEL_CONTENT_URI, projection, selection, argsDos, null);

        String suzDiger = "Diğer";
        String[] argsDiger = {"%." + ay + "." + yil + "%", "%" + suzDiger + "%"};
        Cursor crDiger = getContentResolver().query(Provider.CETVEL_CONTENT_URI, projection, selection, argsDiger, null);

        txtTef.setText("" + crTef.getCount());
        txtRp.setText("" + crRp.getCount());
        txtYz.setText("" + crYz.getCount());
        txtBd.setText("" + crBd.getCount());
        txtDos.setText("" + crDos.getCount());
        txtDiger.setText("" + crDiger.getCount());
    }

    private void harcamalar() {

        String[] projection = {Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1, Sabitler.TblCetvelClass.CETVEL_GIDER_1};
        String selection = Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1 + " LIKE ?";
        String[] args = {"%." + ay + "." + yil + "%"};
        Cursor crTL = getContentResolver().query(Provider.CETVEL_CONTENT_URI, projection, selection, args, null);

        int giderInt = 0;
        if (crTL != null) {
            while (crTL.moveToNext()) {
                String giderStr = crTL.getString(crTL.getColumnIndex(Sabitler.TblCetvelClass.CETVEL_GIDER_1));
                try {
                    giderStr = giderStr.replaceAll("\\D+", "");
                    giderInt = giderInt + Integer.parseInt(giderStr);
                } catch (Exception e) {
                }
            }
            txtTopTl.setText("" + giderInt + " TL");
        }


        String slTaksi = Sabitler.TblCetvelClass.CETVEL_GIDER_1 + " LIKE ? AND " + selection;
        String[] argsTaksi = {"%Taksi%", args[0]};
        Cursor crTaksi = getContentResolver().query(Provider.CETVEL_CONTENT_URI, projection, slTaksi, argsTaksi, null);

        int giderTaksiInt = 0;
        if (crTaksi != null) {
            while (crTaksi.moveToNext()) {
                String giderTaksiStr = crTaksi.getString(crTaksi.getColumnIndex(Sabitler.TblCetvelClass.CETVEL_GIDER_1));
                try {
                    giderTaksiStr = giderTaksiStr.replaceAll("\\D+", "");
                    giderTaksiInt = giderTaksiInt + Integer.parseInt(giderTaksiStr);
                } catch (Exception e) {
                }
            }
            txtTaksi.setText("" + giderTaksiInt + " TL");
        }

        String slOtobus = Sabitler.TblCetvelClass.CETVEL_GIDER_1 + " LIKE ? AND " + selection;
        String[] argsOtobus = {"%Otobüs%", args[0]};
        Cursor crOtobus = getContentResolver().query(Provider.CETVEL_CONTENT_URI, projection, slOtobus, argsOtobus, null);

        int giderOtobusInt = 0;
        if (crOtobus != null) {
            while (crOtobus.moveToNext()) {
                String giderOtobusStr = crOtobus.getString(crOtobus.getColumnIndex(Sabitler.TblCetvelClass.CETVEL_GIDER_1));
                try {
                    giderOtobusStr = giderOtobusStr.replaceAll("\\D+", "");
                    giderOtobusInt = giderOtobusInt + Integer.parseInt(giderOtobusStr);
                } catch (Exception e) {
                }
            }
            txtOtobus.setText("" + giderOtobusInt + " TL");
        }

        String slKonak = Sabitler.TblCetvelClass.CETVEL_GIDER_1 + " LIKE ? AND " + selection;
        String[] argsKonak = {"%Konaklama%", args[0]};
        Cursor crKonak = getContentResolver().query(Provider.CETVEL_CONTENT_URI, projection, slKonak, argsKonak, null);

        int giderKonakInt = 0;
        if (crKonak != null) {
            while (crKonak.moveToNext()) {
                String giderKonakStr = crKonak.getString(crKonak.getColumnIndex(Sabitler.TblCetvelClass.CETVEL_GIDER_1));
                try {
                    giderKonakStr = giderKonakStr.replaceAll("\\D+", "");
                    giderKonakInt = giderKonakInt + Integer.parseInt(giderKonakStr);
                } catch (Exception e) {
                }
            }
            txtKonak.setText("" + giderKonakInt + " TL");
        }

        String slDiger = Sabitler.TblCetvelClass.CETVEL_GIDER_1 + " LIKE ? AND " + selection;
        String[] argsDiger = {"%Diğer%", args[0]};
        Cursor crDiger = getContentResolver().query(Provider.CETVEL_CONTENT_URI, projection, slDiger, argsDiger, null);

        int giderDigerInt = 0;
        if (crDiger != null) {
            while (crDiger.moveToNext()) {
                String giderDigerStr = crDiger.getString(crDiger.getColumnIndex(Sabitler.TblCetvelClass.CETVEL_GIDER_1));
                try {
                    giderDigerStr = giderDigerStr.replaceAll("\\D+", "");
                    giderDigerInt = giderDigerInt + Integer.parseInt(giderDigerStr);
                } catch (Exception e) {
                }
            }
            txtDigerTl.setText("" + giderDigerInt + " TL");
        }

    }
}
