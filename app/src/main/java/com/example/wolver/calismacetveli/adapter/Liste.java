package com.example.wolver.calismacetveli.adapter;


import java.util.ArrayList;

public class Liste {

    //1-RECYCLERVIEW DA GÖRÜNMESİ İSTENİLEN, DEĞİŞKENLER BELİRTİLİR.
    //2-GETTER/SETTER VE CONSTRUCTER OLUŞTURULUR.
    //3-CLASS NESNESİNDEN OLUŞTURULUR. BOŞ OLACAK
    public ArrayList<Liste> tumCetvelListesi;

    int id;
    String tarih;
    String aciklama;
//    int resim_id;


    //   BURASI MANUEL
    public Liste(){
    }
    //   BURASI MANUEL


    public Liste(int id, String tarih, String aciklama/*, int resim_id*/) {
        this.id = id;
        this.tarih = tarih;
        this.aciklama = aciklama;
//        this.resim_id = resim_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

//    public int getResim_id() {
//        return resim_id;
//    }
//
//    public void setResim_id(int resim_id) {
//        this.resim_id = resim_id;
//    }
}

