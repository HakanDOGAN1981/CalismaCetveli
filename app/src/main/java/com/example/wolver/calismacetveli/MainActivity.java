package com.example.wolver.calismacetveli;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static String ayAnaliz;
    Context context = this;
    Toolbar mToolbar;
    ImageView mImg;
    Button mCetvel;
    Button mYeniVeri;
    TextView mtxtAy;
    ImageButton mImgIleri, mImgGeri;
    RecyclerView mRecyclerView;
    RecAdapter mRecAdapter;
    //    BOŞ ARRAYLIST TANIMLARKEN NULL DEDİĞİNDE HATA ALIRSIN
    //    NULL KULLANMAYACAKSIN YERİNE
    //    NEW ARRAYLIST<>(); ŞEKLİNDE OLACAK
    ArrayList<Liste> tumCetvelListe = new ArrayList<>();
    ArrayList<Liste> suzTumCetvelListe = new ArrayList<>();
    int shrGelenInt, simdiCumartesiInt;
    String shrGelenTur;
    String shrTrAy;
    String shrTrYil;
    int mTxtAyInt;

    String[] simdiStrDizi, ayinGunleriDiziStr;
    Integer[] ayinGunleriDizi;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        eklenenViewTanitimi();
        glideİslemleri();

        guncel_Tarih_Yoksa_VeriOlustur();//İLK AÇILDIĞINDA GÜNÜMÜZ TARİHİ YOKSA VERİ OLUŞTURUR.

        sharedPrefencesAl_VeriSuz();

        // RECYCLERVIEW TANITIMI
        mRecyclerView = (RecyclerView) findViewById(R.id.Recyclerview);

        /////////////////SÜZME OLUP OLMADIĞINI KONTROL EDİYOR. ONAGÖRE YA SÜZYOR YADA NE VARSA GETİRİYOR
        if (shrGelenInt == 4) {
            tumVerileriListele();
        } else {
            suzupVerileriListele();
        }
        /////////////////SÜZME OLUP OLMADIĞINI KONTROL EDİYOR. ONAGÖRE YA SÜZYOR YADA NE VARSA GETİRİYOR

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

        //İZİN ALINDI-MANIFESTTEDE VAR
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        } else {

        }
        //İZİN ALINDI-MANIFESTTEDE VAR
    }

    //İZİN ALINDI-MANIFESTTEDE VAR
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    //İZİN ALINDI-MANIFESTTEDE VAR

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
        mImgIleri = (ImageButton) findViewById(R.id.ayIleri);
        mImgGeri = (ImageButton) findViewById(R.id.ayGeri);
    }


    private void glideİslemleri() {

        Glide.with(this)
                .load(R.drawable.arkaplan_cetvelx)
                .apply(new RequestOptions().centerCrop())
                .into(mImg);
    }


    public void tumVerileriListele() {

        tumCetvelListe = tumCetvelListe();//METOD AŞAĞIDA tumCetvelListe()
        mRecAdapter = new RecAdapter(this, tumCetvelListe);
        mRecyclerView.setAdapter(mRecAdapter);
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
            suzgecFragmentAc();
        }

        if (view.getId() == R.id.ayIleri) {
            mTxtAyInt = Integer.parseInt(mtxtAy.getText().toString());
            mTxtAyInt = (mTxtAyInt + 1) % 12;
            if (mTxtAyInt == 0) {
                mTxtAyInt = 12;
            }
            if (String.valueOf(mTxtAyInt).toString().length() < 2) {
                mtxtAy.setText("0" + mTxtAyInt);
                ayAnaliz = "" + mTxtAyInt;
            } else {
                mtxtAy.setText("" + mTxtAyInt);
                ayAnaliz = "" + mTxtAyInt;
            }
            ileri_Geri_Ay();
        }

        if (view.getId() == R.id.ayGeri) {
            mTxtAyInt = Integer.parseInt(mtxtAy.getText().toString());
            mTxtAyInt = (mTxtAyInt - 1) % 12;
            if (mTxtAyInt == 0) {
                mTxtAyInt = 12;
            }

            if (String.valueOf(mTxtAyInt).toString().length() < 2) {
                mtxtAy.setText("0" + mTxtAyInt);
                ayAnaliz = "" + mTxtAyInt;
            } else {
                mtxtAy.setText("" + mTxtAyInt);
                ayAnaliz = "" + mTxtAyInt;
            }
            ileri_Geri_Ay();
        }

        if (view.getId() == R.id.btnAnaliz) {
            Intent analiz = new Intent(context, Analiz.class);

            if (shrGelenInt == 4) {
                startActivity(analiz);
            } else {
                startActivity(analiz);
            }
        }
    }

    private void ileri_Geri_Ay() {

        sharedPrefencesAl_VeriSuz();

        String selection;
        String[] selectionArgs;
        String[] projection = {Sabitler.TblCetvelClass.CETVEL_ID, Sabitler.TblCetvelClass.CETVEL_ACIKLAMA_1, Sabitler
                .TblCetvelClass.CETVEL_TARIH_BAS_1};

        selection = Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1 + " LIKE ?";

        if (shrTrYil.equalsIgnoreCase("Seçiniz")) {
            shrTrYil = Tarihler.simdiYilStr;
        }

        selectionArgs = new String[]{"%." + mtxtAy.getText().toString() + "." + shrTrYil + "%"};

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

        mRecAdapter = new RecAdapter(this, suzTumCetvelListe);
        mRecyclerView.setAdapter(mRecAdapter);
        mRecAdapter.notifyDataSetChanged();

        sharedPrefencesOlustur2();

        SharedPreferences sharedPreferences = context.getSharedPreferences("analiz", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ay", mtxtAy.getText().toString());
        editor.putString("yil", shrTrYil);
        editor.commit();
    }


    // MENÜ İŞLEMLERİ
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
                suzgecFragmentAc();
                break;

            case R.id.aySil:
                aySil();
                break;

            case R.id.veriTabanınıSil:
                veriTabanınıSil();
                break;

            case R.id.veriTabanınıYedekle:
                veriTabanınıYedekle();
                break;

            case R.id.veriTabanınıGuncelle:
                veriTabanınıGuncelle();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void veriTabanınıGuncelle() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Dikkat!")
                .setIcon(R.drawable.ic_announcement)
                .setMessage("Yedeği Alınmış Olan" + "\n" + "\n" + "Son Veri Tabanı Yüklenecektir." + "\n" + "\n" + "Devam Edilsin mi?")
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            File currentDB = new File("/data/data/com.example.wolver.almacetveli/databases/calisma.db");
                            File backupDB = new File("/sdcard/Download/", "kopya_calisma.db");

                            if (currentDB.exists()) {
                                FileChannel src = new FileInputStream(backupDB).getChannel();
                                FileChannel dst = new FileOutputStream(currentDB).getChannel();
                                dst.transferFrom(src, 0, src.size());
                                src.close();
                                dst.close();
                                Toast.makeText(getApplicationContext(), "Veri Tabanı Değiştirildi!!", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(context, MainActivity.class);
                                startActivity(intent);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();
    }

    private void veriTabanınıYedekle() {
        try {
            File currentDB = new File("/data/data/com.example.wolver.almacetveli/databases/calisma.db");
            File backupDB = new File("/sdcard/Download/", "kopya_calisma.db");

            if (currentDB.exists()) {
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getApplicationContext(), "Download Klasörüne Yedek Alındı.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }//SANAL GERİ TUŞU İLE ÇIKIŞ
    // MENÜ İŞLEMLERİ

    private void suzgecFragmentAc() {

        SuzgecFragment fragment = new SuzgecFragment();
        fragment.show(getSupportFragmentManager(), "Yeni");
    }


    public void suzupVerileriListele() {

        suzTumCetvelListe = suzTumCetvelListe();//METOD AŞAĞIDA tumCetvelListe()
        mRecAdapter = new RecAdapter(this, suzTumCetvelListe);
        mRecyclerView.setAdapter(mRecAdapter);
    }

    public ArrayList<Liste> suzTumCetvelListe() {
        String selection = null;
        String[] selectionArgs = new String[0];
        String[] projection = {Sabitler.TblCetvelClass.CETVEL_ID, Sabitler.TblCetvelClass.CETVEL_ACIKLAMA_1, Sabitler
                .TblCetvelClass.CETVEL_TARIH_BAS_1};

        //1. ihtimal Tür boş , ay ve yıla dolu
        if (shrGelenInt == 1) {
            selection = Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1 + " LIKE ?";
            selectionArgs = new String[]{"%." + shrTrAy + "." + shrTrYil + "%"};
        }

        //2. ihtimal Tür dolu, ay ve yıl boş

        if (shrGelenInt == 2) {
            selection = Sabitler.TblCetvelClass.CETVEL_TUR_1 + " LIKE ?";
            selectionArgs = new String[]{"%" + shrGelenTur + "%"};
        }


        //3. ihtimal üçüde dolu

        if (shrGelenInt == 3) {
            selection = Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1 + " LIKE ? AND " + Sabitler.TblCetvelClass.CETVEL_TUR_1 + " LIKE ?";
            selectionArgs = new String[]{"%." + shrTrAy + "." + shrTrYil + "%", "%" + shrGelenTur + "%"};
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


        if (shrTrAy.equalsIgnoreCase("Seçiniz")) {
        } else {
            mtxtAy.setText(shrTrAy);
            ayAnaliz = "" + shrTrAy;

        }

        return suzTumCetvelListe;
    }

    private void guncel_Tarih_Yoksa_VeriOlustur() {

        simdiStrDizi = Tarihler.simdiOlustur();
        ayinGunleriDizi = Tarihler.ayinGunleri();
        ayinGunleriDiziStr = Tarihler.ayinGunleriStr();
        simdiCumartesiInt = Tarihler.simdiCumartesi();
        guncel_Tarih_Yoksa_VeriOlustur_Devam();
    }

    public void guncel_Tarih_Yoksa_VeriOlustur_Devam() {

        String selection = Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1 + " LIKE ?";
        String[] selectionArgs = {"%." + simdiStrDizi[1] + "." + simdiStrDizi[2] + "%"};
        final Cursor cursor = getContentResolver().query(Provider.CETVEL_CONTENT_URI, null,
                selection, selectionArgs, null);
        final int cursorCount = cursor.getCount();

        if (cursorCount == 0) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle("Dikkat!")
                    .setIcon(R.drawable.ic_announcement)
                    .setMessage("Seçmiş Olduğunuz Ay" + "\n" + "\n" + "Veri Tabanında Mevcut Değildir" +
                            "." + "\n" + "\n" + "Veriler Oluşturulsun mu?")
                    .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            ContentValues contentValues = new ContentValues();

                            String soni2;
                            for (int i2 = 1; i2 <= ayinGunleriDizi[2]; i2++) {

                                if (String.valueOf(i2).length() < 2) {
                                    soni2 = String.valueOf(i2);
                                    soni2 = "0" + soni2;
                                } else {
                                    soni2 = String.valueOf(i2);
                                }

                                contentValues.put(Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1, soni2 + "." + simdiStrDizi[1] + "" + "" +
                                        "." + simdiStrDizi[2]);

                                Uri uri = getContentResolver().insert(Provider.CETVEL_CONTENT_URI, contentValues);
                            }

                            String soni3;
                            ContentValues contentValues2 = new ContentValues();

                            for (int i3 = simdiCumartesiInt; i3 <= ayinGunleriDizi[2]; i3 += 7) {
                                if (String.valueOf(i3).length() < 2) {
                                    soni3 = String.valueOf(i3);
                                    soni3 = "0" + soni3;
                                } else {
                                    soni3 = String.valueOf(i3);
                                }

                                String select = Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1 + " = ?";
                                String[] args = {soni3 + "." + simdiStrDizi[1].toString() + "." + simdiStrDizi[2].toString()};

                                contentValues2.put(Sabitler.TblCetvelClass.CETVEL_ACIKLAMA_1, "Cumartesi");
                                getContentResolver().update(Provider.CETVEL_CONTENT_URI, contentValues2, select, args);
                            }

                            String soni4;
                            ContentValues contentValues3 = new ContentValues();

                            int simdipazar;
                            if (simdiCumartesiInt == 7) {
                                simdipazar = 1;
                            } else {
                                simdipazar = simdiCumartesiInt + 1;
                            }

                            for (int i4 = simdipazar; i4 <= ayinGunleriDizi[2]; i4 = i4 + 7) {
                                if (String.valueOf(i4).length() < 2) {
                                    soni4 = String.valueOf(i4);
                                    soni4 = "0" + soni4;
                                } else {
                                    soni4 = String.valueOf(i4);
                                }
                                String select = Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1 + " = ?";
                                String[] args = {soni4 + "." + simdiStrDizi[1].toString() + "." + simdiStrDizi[2]
                                        .toString()};

                                contentValues3.put(Sabitler.TblCetvelClass.CETVEL_ACIKLAMA_1, "Pazar");
                                getContentResolver().update(Provider.CETVEL_CONTENT_URI, contentValues3, select, args);
                            }

                            sharedPrefencesOlustur();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).show();
        }
        cursor.close();

        if (simdiStrDizi[1].length() < 2) {
            String ayTxt = "0" + simdiStrDizi[1];
            mtxtAy.setText(ayTxt);

        } else {
            mtxtAy.setText(simdiStrDizi[1]);
        }

    }

    public void sharedPrefencesAl_VeriSuz() {

        SharedPreferences preferences = context.getSharedPreferences("share", MODE_PRIVATE);
        shrGelenInt = preferences.getInt("secilen", 4);
        shrGelenTur = preferences.getString("shrTur", "Sonuç Yok");
        shrTrAy = preferences.getString("shrAy", "Sonuç Yok");
        shrTrYil = preferences.getString("shrYil", "Sonuç Yok");
    }

    public void sharedPrefencesOlustur() {

        SharedPreferences preferences = getSharedPreferences("share", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt("secilen", 1);
        editor.putString("shrAy", simdiStrDizi[1]);
        editor.putString("shrYil", simdiStrDizi[2]);

        editor.commit();
    }

    public void sharedPrefencesOlustur2() {

        SharedPreferences preferences = getSharedPreferences("share", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt("secilen", 1);
        editor.putString("shrAy", mtxtAy.getText().toString());
        editor.putString("shrYil", simdiStrDizi[2]);

        editor.commit();
    }

    private void veriTabanınıSil() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Dikkat!")
                .setIcon(R.drawable.ic_announcement)
                .setMessage("Tüm Veri Tabanı Silinecektir." + "\n" + "\n" + "Geri Dönüş Mümkün Değildir." + "\n" + "\n" + "Tüm Veriler " +
                        "Silinsin mi?")
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getContentResolver().delete(Provider.CETVEL_CONTENT_URI, null, null);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }

    private void aySil() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Dikkat!")
                .setIcon(R.drawable.ic_announcement)
                .setMessage("Seçmiş Olduğunuz Ay" + "\n" + "\n" + "Veri Tabanından Silinecektir." +
                        "." + "\n" + "\n" + "Veriler Silinsin mi?")
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String selection = Sabitler.TblCetvelClass.CETVEL_TARIH_BAS_1 + " LIKE ?";
                        String selectionArgs[] = new String[]{"%." + mtxtAy.getText().toString() + "." + shrTrYil + "%"};

                        getContentResolver().delete(Provider.CETVEL_CONTENT_URI, selection, selectionArgs);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }
}

