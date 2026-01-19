package gemini_karisik_sorular;
import java.util.ArrayList;
import java.util.List;

class AsiriYukException extends Exception{
    public AsiriYukException(String mesaj){
        super(mesaj);
    }
}

interface Operasyonel {
    void rotayiBelirle(String varisNoktasi);
    void goreviTamamla();
}

abstract class Tasit{
    protected String plaka;
    protected int hizLimiti;
    protected double mevcutYuk;
    protected double maxKapasite;

    public Tasit(String plaka , int hizLimiti , double mevcutYuk , double maxKapasite){
        this.plaka=plaka;
        this.hizLimiti=hizLimiti;
        this.mevcutYuk=mevcutYuk;
        this.maxKapasite=maxKapasite;
    }

    abstract void tasitDetaylariniGoster();

    void yukEkle(double miktar) throws AsiriYukException{
        if((mevcutYuk+miktar)>maxKapasite){
            throw new AsiriYukException("HATA: asırı yük hatası");
        }
        this.mevcutYuk+=miktar;
        System.out.println(miktar + " kg yük eklendi. Güncel yük: " + mevcutYuk + " kg");
    }
}

class KargoUcak extends Tasit implements Operasyonel{
    int maxIrtifa;
    public KargoUcak(String plaka , int hizLimiti , double mevcutYuk , double maxKapasite , int maxIrtifa){
        super(plaka , hizLimiti , mevcutYuk , maxKapasite);
        this.maxIrtifa=maxIrtifa;
    }

    @Override
    public void tasitDetaylariniGoster(){
        System.out.println("------KARGO UÇAK DETAYLARI------");
        System.out.println("uçak plaka : " + plaka);
        System.out.println("hız limiti : " + hizLimiti);
        System.out.println("mevcut yuk : " + mevcutYuk);
        System.out.println("max kapasite : " + maxKapasite);
        System.out.println("max irtifa : " + maxIrtifa);
    }

    @Override 
    public void rotayiBelirle(String varisNoktasi){
        System.out.println("uçuş rotası belirlendi: " + varisNoktasi);
    }

    @Override
    public void goreviTamamla(){
        System.out.println("iniş yapıldı, kargolar teslim edildi.");
    }
}

class DagitimAraci extends Tasit implements Operasyonel{
    int kapiSayisi;
    public DagitimAraci(String plaka , int hizLimiti , double mevcutYuk , double maxKapasite , int kapiSayisi){
        super(plaka , hizLimiti , mevcutYuk , maxKapasite);
        this.kapiSayisi=kapiSayisi;
    }

    @Override
    public void tasitDetaylariniGoster(){
        System.out.println("------DAĞITIM ARACI (KAMYONET) DETAYLARI------");
        System.out.println("araç plaka : " + plaka);
        System.out.println("hız limiti : " + hizLimiti);
        System.out.println("mevcut yuk : " + mevcutYuk);
        System.out.println("max kapasite : " + maxKapasite);
        System.out.println("kapı sayısı : " + kapiSayisi);
    }

     @Override 
    public void rotayiBelirle(String varisNoktasi){
        System.out.println("varış noktası belirlendi: " + varisNoktasi);
    }

    @Override
    public void goreviTamamla(){
        System.out.println("kargolar adrese teslim edildi.");
    }
}

class Sevkiyat{
    public Tasit tasit;
    public Sevkiyat(Tasit tasit){
        this.tasit=tasit;
    }

    class sevkiyatDetayi{
        String takipNo;
        String durum;
        public sevkiyatDetayi(String takipNo , String durum){
            this.takipNo=takipNo;
            this.durum=durum;
        }

        public void bilgiGoster(){
            System.out.println("takip no : " + takipNo);
            System.out.println("durum : " + durum);
        }
    }

    public static void main(String[] args) {
        KargoUcak uçak=new KargoUcak("01 AA 132" , 450 , 100 , 1200 , 5000);
        DagitimAraci arac= new DagitimAraci("02 CC 245" , 142 , 458 , 760 , 3);
        List<Tasit> tasitlar= new ArrayList<>();
        tasitlar.add(uçak);
        tasitlar.add(arac);

        for(Tasit t : tasitlar){
            t.tasitDetaylariniGoster();
            try{
                t.yukEkle(500);
            }catch(AsiriYukException e){
                System.out.println(e.getMessage());
            }
            System.out.println();
        }
    }
}

