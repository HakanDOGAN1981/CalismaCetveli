package com.example.wolver.calismacetveli;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.wolver.calismacetveli.adapter.Liste;
import com.example.wolver.calismacetveli.adapter.RecAdapter;
import com.example.wolver.calismacetveli.adapter.RecListener;
import com.example.wolver.calismacetveli.adapter.RecTouchCallBack;
import com.example.wolver.calismacetveli.data.Provider;
import com.example.wolver.calismacetveli.data.Sabitler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Context context = this;
    Toolbar mToolbar;
    ImageView mImg;
    Button mCetvel;
    Button mYeniVeri;
    TextView mtxtAy;

    RecyclerView mRecyclerView;

    RecAdapter mRecAdapter;

    //    BOŞ ARRAYLIST TANIMLARKEN NULL DEDİĞİNDE HATA ALIRSIN
    //    NULL KULLANMAYACAKSIN YERİNE
    //    NEW ARRAYLIST<>(); ŞEKLİNDE OLACAK
    ArrayList<Liste> tumCetvelListe = new ArrayList<>();
    ArrayList<Liste> suzTumCetvelListe = new ArrayList<>();
    ArrayList<Liste> tarihTumCetvelListe = new ArrayList<>();

    SQLiteDatabase db;
    String alinan, gelenTur, gelenYil, gelenAy;

    Calendar calendar;

    String sonAyStr;
    String sonYilStr;
    String ilkGunStr;

    int aydakiGunSayısı;
    int simdiAy;
    int ilkGunInt;
    int ilkCumartesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        eklenenViewTanitimi();
        glideİslemleri();

        gelenVeriKontrolu();
        tarihKontorolu();


        // RECYCLERVIEW TANITIMI
        mRecyclerView = (RecyclerView) findViewById(R.id.Recyclerview);

        if (alinan.equalsIgnoreCase("")) {
            dataguncelle();
        } else {
            suzDataguncelle();
        }

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mRecAdapter);

        /////////////////////SWIPE İŞLEMLERİ

        RecTouchCallBack mRecTouchCallBack = new RecTouchCallBack(mRecAdapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mRecTouchCallBack);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        /////////////////////SWIPE İŞLEMLERİ

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        // RECYCLERVIEW TANITIMI


        // RECYCLERVIEW ONCLICK
        mRecyclerView.addOnItemTouchListener(new RecListener(this, mRecyclerView, new RecListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int guncellenenId;
                if (tumCetvelListe.size() != 0) {
                    Liste guncellenen = tumCetvelListe.get(position);
                    guncellenenId = guncellenen.getId();
                } else {
                    Liste guncellenen = suzTumCetvelListe.get(position);
                    guncellenenId = guncellenen.getId();
                }

                Intent veri = new Intent(context, VeriGirisi.class);
                veri.putExtra("veri", String.valueOf(guncellenenId));
                startActivity(veri);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        // RECYCLERVIEW ONCLICK
    }

    private void tarihKontorolu() {

        Calendar simdi = Calendar.getInstance();
        int month = simdi.get(Calendar.MONTH);
        simdiAy = month + 1;
        int simdiYil = simdi.get(Calendar.YEAR);
        sonYilStr = String.valueOf(simdiYil);

        //AYIN İLK GÜNÜNÜ BULMAK(STRING)
        calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        ilkGunStr = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        //AYIN İLK GÜNÜNÜ BULMAK(STRING)

        //AYIN İLK GÜNÜNÜ BULMAK(INT)-HAFTALIK DEĞER
        ilkGunInt = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int ilkCumartesiGerekliGun = 6 - ilkGunInt;
        ilkCumartesi = 1 + ilkCumartesiGerekliGun;
        //AYIN İLK GÜNÜNÜ BULMAK(INT)


        Toast.makeText(context, ""+ilkGunInt,Toast.LENGTH_LONG).show();

        mtxtAy.setText("" + simdiAy);

        if (String.valueOf(month).length() < 2) {
            if (String.valueOf(simdiAy).length() < 2) {
                sonAyStr = String.valueOf(simdiAy);
                sonAyStr = "0" + sonAyStr;
            }
        } else {
            sonAyStr = String.valueOf(simdiAy);
        }

        if (simdiAy == 1 || simdiAy == 3 || simdiAy == 5 || simdiAy == 7 || simdiAy == 8 || simdiAy == 10 || simdiAy == 12) {
            aydakiGunSayısı = 31;
        } else if (simdiAy == 4 || simdiAy == 6 || simdiAy == 9 || simdiAy == 11) {
            aydakiGunSayısı = 30;
        } else if (simdiAy == 2 && simdiYil % 4 == 0) {
            aydakiGunSayısı = 29;
        } else {
            aydakiGunSayısı = 28;
        }

        tariheGoreVeriOlustur();
    }


    private void yeniVeriEkranı() {
        Intent yeniveri = new Intent(getApplicationContext(), VeriGirisi.class);
        startActivity(yeniveri);
    }


    private void eklenenViewTanitimi() {

        //      TOOLBAR TANITIMI
        mToolbar = (Toolbar) findViewById(R.id.toolbarx);
        setSupportActionBar(mToolbar);
        //      TOOLBAR TANITIMI

        mCetvel = (Button) findViewById(R.id.btnSuz);
        mYeniVeri = (Button) findViewById(R.id.btnVeriGiris);
        mImg = (ImageView) findViewById(R.id.imageView);
        mtxtAy = (TextView) findViewById(R.id.txtAy);
    }


    public void dataguncelle() {

        tumCetvelListe = tumCetvelListe();//METOD AŞAĞIDA tumCetvelListe()
        mRecAdapter = new RecAdapter(this, tumCetvelListe);
        mRecyclerView.setAdapter(mRecAdapter);
    }


    private void glideİslemleri() {

        Glide.with(this)
                .load(R.drawable.arkaplan_cetvelx)
                .apply(new RequestOptions().centerCrop())
                .into(mImg);
    }


    public ArrayList<Liste> tumCetvelListe() {

        String[] projection = {Sabitler.TblCetvelClass.CETVEL_ID, Sabitler.TblCetvelClass.CETVEL_ACIKLAMA_1, Sabitler
                .TblCetvelClass.CETVEL_TARIH_BAS_1};
        Cursor cursor = getContentResolver().query(Provider.CETVEL_CONTENT_URI, projection, null,
                null, null);

        if (cursor != null) {
            tumCetvelListe.clear();

            while (cursor.moveToNext()) {
                Liste geciciListe = new Liste();
                geciciListe.setId(cursor.getInt(cursor.getColumnIndex(Sabitler.TblCetvelClass.CETVEL_ID)));
                geciciListe.setAciklama(cursor.getString(cursor.getColumnIndex(Sabitler.TblCetvelClass.CETVEL_ACIKLAMA_1)));
                geciciListe.setTarih(cursor.getString(cursor.getColumnIndex(Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1)));
                tumCetvelListe.add(geciciListe);
            }
            cursor.close();
        }
        return tumCetvelListe;
    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnVeriGiris) {
            yeniVeriEkranı();
        }

        if (view.getId() == R.id.btnSuz) {
            suzgec();
        }
    }


    // MENÜ İŞLEMLERİ
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
//        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.ekle:
                yeniVeriEkranı();
                break;

            case R.id.suz:
                suzgec();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    // MENÜ İŞLEMLERİ

    private void suzgec() {

        SuzgecFragment fragment = new SuzgecFragment();
        fragment.show(getSupportFragmentManager(), "Yeni");
    }


    public void gelenVeriKontrolu() {

        try {
            gelenTur = getIntent().getExtras().getString("tur");
            gelenAy = getIntent().getExtras().getString("ay");
            gelenYil = getIntent().getExtras().getString("yil");

            if (gelenTur.equalsIgnoreCase("Giriş Türünü Seçiniz")) {
                gelenTur = "";
            }
            if (gelenAy.equalsIgnoreCase("Seçiniz")) {
                gelenAy = "";
            }
            if (gelenYil.equalsIgnoreCase("Seçiniz")) {
                gelenYil = "";
            }

            alinan = gelenTur + gelenAy + gelenYil;

        } catch (Exception e) {
            alinan = "";
        }
    }


    public ArrayList<Liste> suzTumCetvelListe() {
        String selection = null;
        String[] selectionArgs = new String[0];
        String[] projection = {Sabitler.TblCetvelClass.CETVEL_ID, Sabitler.TblCetvelClass.CETVEL_ACIKLAMA_1, Sabitler
                .TblCetvelClass.CETVEL_TARIH_BAS_1};

        //1. ihtimal Tür boş , ay ve yıla dolu
        if (gelenTur.equalsIgnoreCase("") && !gelenAy.equalsIgnoreCase("") && !gelenYil.equalsIgnoreCase("")) {
            selection = Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1 + " LIKE ?";
            selectionArgs = new String[]{"%." + gelenAy + "." + gelenYil + "%"};
        }

        //2. ihtimal Tür dolu, ay ve yıl boş

        if (!gelenTur.equalsIgnoreCase("") && gelenAy.equalsIgnoreCase("") && gelenYil.equalsIgnoreCase("")) {
            selection = Sabitler.TblCetvelClass.CETVEL_TUR_1 + " LIKE ?";
            selectionArgs = new String[]{"%" + gelenTur + "%"};
        }


        //3. ihtimal üçüde dolu

        if (!gelenTur.equalsIgnoreCase("") && !gelenAy.equalsIgnoreCase("") && !gelenYil.equalsIgnoreCase("")) {
            selection = Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1 + " LIKE ? AND " + Sabitler.TblCetvelClass.CETVEL_TUR_1 + " LIKE ?";
            selectionArgs = new String[]{"%." + gelenAy + "." + gelenYil + "%", "%" + gelenTur + "%"};
        }


        Cursor cursor = getContentResolver().query(Provider.CETVEL_CONTENT_URI, projection, selection, selectionArgs, null);

        if (cursor != null) {
            suzTumCetvelListe.clear();

            while (cursor.moveToNext()) {
                Liste geciciListe = new Liste();
                geciciListe.setId(cursor.getInt(cursor.getColumnIndex(Sabitler.TblCetvelClass.CETVEL_ID)));
                geciciListe.setAciklama(cursor.getString(cursor.getColumnIndex(Sabitler.TblCetvelClass.CETVEL_ACIKLAMA_1)));
                geciciListe.setTarih(cursor.getString(cursor.getColumnIndex(Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1)));
                suzTumCetvelListe.add(geciciListe);
            }
            cursor.close();
        }
        Toast.makeText(this, "Süzülen Kayıt Sayısı: " + suzTumCetvelListe.size(), Toast.LENGTH_SHORT).show();
        return suzTumCetvelListe;

    }

    public void suzDataguncelle() {

        suzTumCetvelListe = suzTumCetvelListe();//METOD AŞAĞIDA tumCetvelListe()
        mRecAdapter = new RecAdapter(this, suzTumCetvelListe);
        mRecyclerView.setAdapter(mRecAdapter);
    }

    public void tariheGoreVeriOlustur() {
        String selection = Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1 + " LIKE ?";
        String[] selectionArgs = {"%." + sonAyStr + "." + sonYilStr + "%"};
        final Cursor cursor = getContentResolver().query(Provider.CETVEL_CONTENT_URI, null,
                selection, selectionArgs, null);
        final int cursorCount = cursor.getCount();

        if (cursorCount == 0) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle("Dikkat!")
                    .setIcon(R.drawable.ic_announcement)
                    .setMessage("Seçmiş Olduğunuz Ay" + "\n" + "Veri Tabanında Mevcut Değildir" +
                            "." + "\n" + "Veriler Oluşturulsun mu?")
                    .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            ContentValues contentValues = new ContentValues();

                            String soni2;
                            for (int i2 = 1; i2 <= aydakiGunSayısı; i2++) {

                                if (String.valueOf(i2).length() < 2) {
                                    soni2 = String.valueOf(i2);
                                    soni2 = "0" + soni2;
                                } else {
                                    soni2 = String.valueOf(i2);
                                }

                                contentValues.put(Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1, soni2 + "."
                                        + sonAyStr + "" + "." + sonYilStr);

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
                                String[] args = {soni3 + "." + sonAyStr + "." + sonYilStr};

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
                                String[] args = {soni4 + "." + sonAyStr + "." + sonYilStr};

                                contentValues3.put(Sabitler.TblCetvelClass.CETVEL_ACIKLAMA_1, "Pazar");
                                getContentResolver().update(Provider.CETVEL_CONTENT_URI, contentValues3, select, args);
                            }

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);

                            Toast.makeText(context, "Kayıt Yapılan Sayı: " + aydakiGunSayısı, Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).show();

        }
        cursor.close();
    }
}

