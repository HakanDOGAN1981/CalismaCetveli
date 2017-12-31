package com.example.wolver.calismacetveli;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wolver.calismacetveli.data.Provider;
import com.example.wolver.calismacetveli.data.Sabitler;

public class Analiz extends AppCompatActivity {

    TextView txtTrh, txtTef, txtRp, txtYz, txtBd, txtDos, txtTopTl, txtTaksi, txtOtobus, txtKonak,
            txtDiger, txtDigerTl;
    String ay, yil;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analiz);

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

        yil = sharedprefencesAl();
        ay = MainActivity.ayAnaliz;

        txtTrh.setText(ay + "." + yil);

        textleriDoldur();

    }

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

        txtTef.setText("" + crTef.getCount());
        txtRp.setText("" + crRp.getCount());
        txtYz.setText("" + crYz.getCount());
        txtBd.setText("" + crBd.getCount());
        txtDos.setText("" + crDos.getCount());
    }

    private void harcamalar() {
    }
}
