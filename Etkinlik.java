package gemini_karisik_sorular;

import java.util.List;
import java.util.ArrayList;

class KapasiteAsildiException extends Exception{
    public KapasiteAsildiException(String mesaj){
        super(mesaj);
    }
}
interface IndirimUygulanabilir {
    double ogrenciIndirimiUygula(double fiyat);
    double grupIndirimiUygula(double fiyat);
}

abstract class Etkinlik {
    protected String etkinlikAdi;
    protected int salonNo;
    protected double temelFiyat;
    protected int doluKoltukSayisi;
    protected int toplamKapasite=100;

    public Etkinlik(String etkinlikAdi , int salonNo , double temelFiyat , int doluKoltukSayisi){
        this.etkinlikAdi=etkinlikAdi;
        this.salonNo=salonNo;
        this.temelFiyat=temelFiyat;
        this.doluKoltukSayisi=doluKoltukSayisi;
    }

    abstract void etkinlikBilgisiGoster();

    void biletSat(int adet) throws KapasiteAsildiException{
        if((adet+doluKoltukSayisi)>toplamKapasite){
            throw new KapasiteAsildiException("yetersiz kapasite");
        }
        doluKoltukSayisi+=adet;
        System.out.println(adet + " adet bilet satıldı. Güncel dolu koltuk sayısı: " + doluKoltukSayisi);
    }
}

class Film extends Etkinlik implements IndirimUygulanabilir{
    String yonetmen;
    int sure;

    public Film(String etkinlikAdi , int salonNo , double temelFiyat , int doluKoltukSayisi, String yonetmen , int sure){
        super(etkinlikAdi , salonNo , temelFiyat , doluKoltukSayisi);
        this.yonetmen=yonetmen;
        this.sure=sure;
    }

    @Override
    public void etkinlikBilgisiGoster(){
        System.out.println("film adı :" + etkinlikAdi + " salon no : " + salonNo + " yonetmen adı : " + yonetmen + " süre : " + sure);
    }

    @Override
    public double ogrenciIndirimiUygula(double fiyat){
        return fiyat*0.80;
    }

    @Override
    public double grupIndirimiUygula(double fiyat){
        return fiyat*.90;
    }
}

class Tiyatro extends Etkinlik implements IndirimUygulanabilir{
    String yonetmen;
    int sure;
    int oyuncuSayisi;

    public Tiyatro(String etkinlikAdi , int salonNo , double temelFiyat , int doluKoltukSayisi, String yonetmen , int sure , int oyuncuSayisi){
        super(etkinlikAdi , salonNo , temelFiyat , doluKoltukSayisi);
        this.yonetmen=yonetmen;
        this.sure=sure;
        this.oyuncuSayisi=oyuncuSayisi;
    }

    @Override
    public void etkinlikBilgisiGoster(){
        System.out.println("tiyatro adı :" + etkinlikAdi + " salon no : " + salonNo + " yonetmen adı : " + yonetmen + " süre : " + sure + " oyuncu sayısı : " + oyuncuSayisi);
    }

    @Override
    public double ogrenciIndirimiUygula(double fiyat){
        return fiyat*0.70;
    }

    @Override
    public double grupIndirimiUygula(double fiyat){
        return fiyat*.85;
    }
}

class BiletSistemi{
    class BiletDetayi{
        String biletID;
        String koltukNo;

        public BiletDetayi(String biletID , String koltukNo){
            this.biletID=biletID;
            this.koltukNo=koltukNo;
        }

        void biletBilgiGoster(){
            System.out.println("bilet ID : " + biletID);
            System.out.println("koltuk no : " + koltukNo);
        }
    }

    public static void main(String[] args) {
        List<Etkinlik> etkinlikListesi = new ArrayList<>();

        Film f1= new Film("avatar 3 ", 2, 150.0, 124, "A", 124);
        Tiyatro t1= new Tiyatro("romeo ve juliet ", 4, 100.0, 57, "jane austune", 15, 20);

        etkinlikListesi.add(f1);
        etkinlikListesi.add(t1);

        BiletSistemi sistem=new BiletSistemi();

        for(Etkinlik e : etkinlikListesi){
            try{
                e.biletSat(10);
                if(e instanceof IndirimUygulanabilir){
                    IndirimUygulanabilir indirimliEtkinlik = (IndirimUygulanabilir) e;
                    double ogrenciFiyati = indirimliEtkinlik.ogrenciIndirimiUygula(e.temelFiyat);
                    double grupFiyati = indirimliEtkinlik.grupIndirimiUygula(e.temelFiyat);
                    System.out.println(e.etkinlikAdi + " için öğrenci indirimi uygulanmış fiyat: " + ogrenciFiyati);
                    System.out.println(e.etkinlikAdi + " için grup indirimi uygulanmış fiyat: " + grupFiyati);

                    BiletSistemi.BiletDetayi detay= sistem.new BiletDetayi("ID-" + e.salonNo + "001", "A10");
                    detay.biletBilgiGoster();
                }
            } catch(KapasiteAsildiException ex){
                System.out.println("Hata: " + ex.getMessage());
            }
            System.out.println("-----------------------");
        }
    }
}
    
