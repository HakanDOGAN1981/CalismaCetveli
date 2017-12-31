package com.example.wolver.calismacetveli;

import android.content.Context;
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

        ay = getIntent().getExtras().getString("ay", "yok");
        yil = getIntent().getExtras().getString("yil", "yok");

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

        txtTrh.setText(ay + "." + yil);

        textleriDoldur();

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
        String[] args1 = {"%." + ay + "." + yil + "%", "%" + suzTef + "%"};

        Toast.makeText(context,args1[0].toString(), Toast.LENGTH_LONG).show();

        Cursor crTef = getContentResolver().query(Provider.CETVEL_CONTENT_URI, projection, selection, args1, null);

        if (crTef.getCount() == 0) {
            txtTef.setText("0");
        } else {
            txtTef.setText("" + crTef.getCount());
        }

//        if (crTef != null) {
//            if (crTef.moveToNext()) {
//
//            }
//        }

    }

    private void harcamalar() {
    }
}
