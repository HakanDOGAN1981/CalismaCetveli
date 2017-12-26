package com.example.wolver.calismacetveli.adapter;


import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wolver.calismacetveli.R;
import com.example.wolver.calismacetveli.data.Provider;
import com.example.wolver.calismacetveli.data.Sabitler;

import java.util.ArrayList;

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

    private ContentResolver contentResolver;
    Context mContext;

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

        // "" koymamızın sebebi çıkan sonucu integer yapmak.
        holder.mTextTarih.setText(tumCetvelListesi.get(position).getTarih());
        holder.mTextİcerik.setText(tumCetvelListesi.get(position).getAciklama());

        //////////////////////////////////////////////////////////
//        holder.mImageView.setImageResource(R.drawable.icon_cetvel);

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
                .setMessage("Kayıt Silinsin mi?")
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Liste silinecek = tumCetvelListesi.get(position);
                        int silinecekID = silinecek.getId();

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
