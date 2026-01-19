package gemini_karisik_sorular;

import java.util.ArrayList;
import java.util.List;

class ErisimKisitliException extends Exception{
    public ErisimKisitliException(String mesaj){
        super(mesaj);
    }
}

class GecikmeException extends Exception{
    public GecikmeException(String mesaj){
        super(mesaj);
    }
}

interface DijitalServis {
    void onlineGoruntule();    
}

abstract class Medya {
    protected int id;
    protected String baslik;
    protected double gunlukCezaOrani;

    public Medya(int id , String baslik , double gunlukCezaOrani){
        this.id=id;
        this.baslik=baslik;
        this.gunlukCezaOrani=gunlukCezaOrani;
    }

    abstract void erisimKontrolEt(int kullaniciYasi) throws ErisimKisitliException;
    abstract double cezaHesapla(int gecikmeGunu);
    
    void bilgiGoster(){
        System.out.println("ID: " + id + " | " + "BAŞLIK: " + baslik);
    }
}

class Kitap extends Medya{
    public int sayfaSayisi;
    public int yasSiniri;

    public Kitap(int id , String baslik , double gunlukCezaOrani , int sayfaSayisi , int yasSiniri){
        super(id, baslik , gunlukCezaOrani);
        this.sayfaSayisi=sayfaSayisi;
        this.yasSiniri=yasSiniri;
    }

    @Override
    double cezaHesapla(int gecikmeGunu){
        return gecikmeGunu*2.0;
    }

    @Override
    void erisimKontrolEt(int kullaniciYasi) throws ErisimKisitliException{
        if(kullaniciYasi<=18){
            throw new ErisimKisitliException("bu içeriğe erişim engellenmiştir.");
        }
    }
}

class Dergi extends Medya implements DijitalServis{
   public String kategori;

    public Dergi(int id , String baslik , double gunlukCezaOrani , String kategori){
        super(id, baslik , gunlukCezaOrani);
        this.kategori=kategori;
    }

    @Override
    double cezaHesapla(int gecikmeGunu){
        return gecikmeGunu*1.0;
    }

     @Override
    void erisimKontrolEt(int kullaniciYasi) throws ErisimKisitliException{
        if(kullaniciYasi<=18){
            throw new ErisimKisitliException("bu içeriğe erişim engellenmiştir.");
        }
    } 

    @Override
    public void onlineGoruntule(){
        System.out.println("bu dergi online görüntülenebilir.");
    }
}
public class KutuphaneSistemi {
    class OduncDetayi{
        public String kullaniciAdi;
        public int gunSayisi;

        public void raporOlustur(Medya m){
            double ToplamCeza=m.cezaHesapla(gunSayisi);
            System.out.println("TOPLAM CEZA : " + ToplamCeza);
        }

        void iadeKontrol(int gunSayisi) throws GecikmeException{
            if(gunSayisi>15){
                throw new GecikmeException("iade etmeniz için size verilen süre geçikmiştir. en kısa süre içinde iade işleminizi tamamlayınız.");
            }
        }
    }

    public static void main(String[] args) {
    KutuphaneSistemi sistem = new KutuphaneSistemi();

    List<Medya> icerikListesi = new ArrayList<>();
    icerikListesi.add(new Kitap(123, "Aski Memnu", 0.5, 400, 18));
    icerikListesi.add(new Dergi(456, "Bilim Teknik", 0.3, "Bilim"));

    for (Medya m : icerikListesi) {
        System.out.println("\n-----------------------------------");
        m.bilgiGoster(); 
        try {
            if (m instanceof Kitap) {
                Kitap k = (Kitap) m;
                k.erisimKontrolEt(15); 

                KutuphaneSistemi.OduncDetayi detay = sistem.new OduncDetayi();
                detay.kullaniciAdi = "Ahmet";
                detay.gunSayisi = 20; 
                detay.raporOlustur(k); 
                detay.iadeKontrol(detay.gunSayisi); 
            }

            if (m instanceof DijitalServis) {
                ((DijitalServis) m).onlineGoruntule();
            }

        } catch (ErisimKisitliException e) {
            System.err.println("GÜVENLİK UYARISI: " + e.getMessage());
        } catch (GecikmeException e) {
            System.err.println("GECİKME UYARISI: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("BEKLENMEDİK HATA: " + e.getMessage());
        }
    }
}
}
