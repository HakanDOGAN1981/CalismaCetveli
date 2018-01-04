package com.example.wolver.calismacetveli.adapter;


import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wolver.calismacetveli.R;
import com.example.wolver.calismacetveli.data.Provider;
import com.example.wolver.calismacetveli.data.Sabitler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//1-public class "RecyclerAdapter" extends RecyclerView.Adapter<> !!!!!!SON<> BOŞ
//2-public class "Holder" extends RecyclerView.ViewHolder. EN SONDA
//3-public class RecAdapter extends RecyclerView.Adapter<RecAdapter.Holder> DOLDUR
//4-HATALARI ALT+ENTER İLE IMPLEMENT ET.
//5-KULLANDIĞIN NESLERİN TANITIMINI YAP.(EN ALTTAKİ CLASS'DA)
//6-ANA SINIFIN GERİ DÖNDERDİĞİ NESNEDEN(RecAdapter) OLUŞTUR. public RecAdapter (Context context,ArrayList<Liste> liste
//7-LAYOUTINFLATER NESNESİ OLUŞTUR.
//8-ONCREATEVIEWHOLDER METODU İÇİN; A-)TEK SATIRI INFLATE ET B-)METODUN DÖNDERDİĞİ NESNEDEN OLUŞTUR.

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.Holder> implements SwipeListener {

    public LayoutInflater mInflater;
    public ArrayList<Liste> tumCetvelListesi;
    Context mContext;
    private ContentResolver contentResolver;

    public RecAdapter(Context context, ArrayList<Liste> liste) {
        mContext = context;
        contentResolver = context.getContentResolver();
        mInflater = LayoutInflater.from(context);
        tumCetvelListesi = liste;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.teksatir, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date simdi = new Date();
        Date tarih = null;

        try {
            tarih = df.parse(tumCetvelListesi.get(position).getTarih());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.mTextTarih.setText(tumCetvelListesi.get(position).getTarih());
        holder.mTextİcerik.setText(tumCetvelListesi.get(position).getAciklama());

        if (holder.mTextİcerik.getText().equals("")) {
            holder.itemView.setBackgroundColor(Color.parseColor("#E6212121"));//ANA RENK
            holder.mTextİcerik.setTextColor(Color.parseColor("#AD1457"));//KIRMIZI
            holder.mTextTarih.setTextColor(Color.parseColor("#AD1457"));//KIRMIZI
        }

        if (holder.mTextİcerik.getText().equals("Cumartesi") || holder.mTextİcerik.getText().equals("Pazar")) {
            holder.itemView.setBackgroundColor(Color.parseColor("#E6212121"));
            holder.mTextİcerik.setTextColor(Color.parseColor("#424242"));//colorPrimaryDarkgreen
            holder.mTextTarih.setTextColor(Color.parseColor("#424242"));//colorPrimaryDarkgreen
        }

        if (holder.mTextİcerik.getText().toString().contains("Dosya")) {
            if (tarih.getTime() < simdi.getTime()) {
                holder.itemView.setBackgroundColor(Color.parseColor("#E6424242"));//ANA RENK
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#E6455a64"));
            }
            holder.mTextİcerik.setTextColor(Color.parseColor("#C7BB5C"));//colorSecondary2Tacha
            holder.mTextTarih.setTextColor(Color.parseColor("#C7BB5C"));//colorSecondary2Tacha
        }

        if (holder.mTextİcerik.getText().toString().contains("Yolculuk") || holder.mTextİcerik.getText().toString().contains
                ("Konaklama")) {
            if (tarih.getTime() < simdi.getTime()) {
                holder.itemView.setBackgroundColor(Color.parseColor("#E6424242"));//ANA RENK
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#E6455a64"));
            }
            holder.mTextİcerik.setTextColor(Color.parseColor("#19ACF3"));//colorSecondaryDodgerBlue
            holder.mTextTarih.setTextColor(Color.parseColor("#19ACF3"));//colorSecondaryDodgerBlue
        }


        if (holder.mTextİcerik.getText().toString().contains("Teftiş") || holder.mTextİcerik.getText().toString().contains
                ("Bildirim")) {
            if (tarih.getTime() < simdi.getTime()) {
                holder.itemView.setBackgroundColor(Color.parseColor("#E6424242"));//ANA RENK
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#E6455a64"));
            }
            holder.mTextİcerik.setTextColor(Color.parseColor("#D6D7D6"));//WHİTESMOKE
            holder.mTextTarih.setTextColor(Color.parseColor("#D6D7D6"));//WHİTESMOKE
        }


        if (holder.mTextİcerik.getText().toString().contains("Rapor") || holder.mTextİcerik.getText().toString().contains
                ("Yazı")) {
            if (tarih.getTime() < simdi.getTime()) {
                holder.itemView.setBackgroundColor(Color.parseColor("#E6424242"));//ANA RENK
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#E6455a64"));
            }
            holder.mTextİcerik.setTextColor(Color.parseColor("#43A047"));//YEŞİL
            holder.mTextTarih.setTextColor(Color.parseColor("#43A047"));//YEŞİL
        }

        if (holder.mTextİcerik.getText().toString().contains("Diğer")) {
            if (tarih.getTime() < simdi.getTime()) {
                holder.itemView.setBackgroundColor(Color.parseColor("#E6424242"));//ANA RENK
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#E6455a64"));
            }
            holder.mTextİcerik.setTextColor(Color.parseColor("#ffab40"));//İNDİGO
            holder.mTextTarih.setTextColor(Color.parseColor("#ffab40"));//İNDİGO5C6BC0
        }

    }

    @Override
    public int getItemCount() {
        return tumCetvelListesi.size();
    }


    @Override
    public void swipeIleSil(final int position) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        final int pozisyon = position;
        alertDialog.setTitle("Dikkat!")
                .setIcon(R.drawable.ic_announcement)
                .setMessage("Kayıdın İçeriği Silinsin mi?")
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        ContentValues values = new ContentValues();
                        values.put(Sabitler.TblCetvelClass.CETVEL_ACIKLAMA_1, "");
                        values.put(Sabitler.TblCetvelClass.CETVEL_GIDER_1, "");
                        values.put(Sabitler.TblCetvelClass.CETVEL_TUR_1, "");

                        Liste silinecek = tumCetvelListesi.get(position);
                        int silinecekID = silinecek.getId();
                        String silinecekTr = silinecek.getTarih();

                        Liste eklenecek = tumCetvelListesi.get(position);
                        eklenecek.setAciklama("");
                        eklenecek.setTarih(silinecekTr);
                        eklenecek.setId(silinecekID);

                        String selection = Sabitler.TblCetvelClass.CETVEL_ID + " = " + silinecekID;
                        int etkilenen = contentResolver.update(Provider.CETVEL_CONTENT_URI, values, selection, null);

                        if (etkilenen != 0) {
                        }

                        tumCetvelListesi.remove(silinecek);
                        tumCetvelListesi.add(position, eklenecek);
                        notifyDataSetChanged();

                    }
                })
                .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        notifyDataSetChanged();
                    }
                }).show();


    }


    public void swipeIleYoket(final int position) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        final int pozisyon = position;
        alertDialog.setTitle("Dikkat!")
                .setIcon(R.drawable.ic_announcement)
                .setMessage("Kayıt Tablodan Silinsin mi?")
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Liste silinecek = tumCetvelListesi.get(position);
                        int silinecekID = silinecek.getId();
                        String silinecekTr = silinecek.getTarih();

                        String selection = Sabitler.TblCetvelClass.CETVEL_ID + " = " + silinecekID;
                        int etkilenen = contentResolver.delete(Provider.CETVEL_CONTENT_URI, selection, null);

                        if (etkilenen != 0) {
                        }

                        tumCetvelListesi.remove(silinecek);
                        notifyDataSetChanged();

                    }
                })
                .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        notifyDataSetChanged();
                    }
                }).show();


    }


    public static class Holder extends RecyclerView.ViewHolder {

        //        ImageView mImageView;
        TextView mTextTarih, mTextİcerik;

        public Holder(View itemView) {
            super(itemView);
//            mImageView = itemView.findViewById(R.id.imageView);
            mTextTarih = itemView.findViewById(R.id.textTarih);
            mTextİcerik = itemView.findViewById(R.id.textIcerik);
        }
    }

}
