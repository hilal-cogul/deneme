import java.util.ArrayList;
import java.util.List;

class CihazZatenAcikException extends Exception{
    public CihazZatenAcikException(String mesaj){
        super(mesaj);
    }
}

class CihazZatenKapaliException extends Exception{
    public CihazZatenKapaliException(String mesaj){
        super(mesaj);
    }
}

class CihazBulunamadiException extends Exception{
    public CihazBulunamadiException(String mesaj){
        super(mesaj);
    }
}

interface Cihaz{
    String getAd();
    void ac() throws CihazZatenAcikException;
    void kapat() throws CihazZatenKapaliException;
}

abstract class SoyutCihaz implements Cihaz{
    private String ad;
    protected boolean acikMi;

    public SoyutCihaz(String ad){
        this.ad=ad;
        this.acikMi=false;
    }

    @Override 
    public String getAd(){
        return ad;
    }

    @Override
    public void ac() throws CihazZatenAcikException{
        if(acikMi){
            throw new CihazZatenAcikException("cihaz açık");
        }
        acikMi=true;
    }

    @Override
    public void kapat() throws CihazZatenKapaliException{
        if(!acikMi){
            throw  new CihazZatenKapaliException("cihaz zaten kapalı");
        }
        System.out.println("cihaz kapatıldı");
        acikMi=false;
    }
}

class Lamba extends SoyutCihaz {
    public Lamba(String ad){
        super(ad); 
    }

    @Override
    public void ac() throws CihazZatenAcikException {
        super.ac();
        System.out.println(getAd() + " yakıldı. Ortam aydınlanıyor...");
    }
}

class Klima extends SoyutCihaz {
    public Klima(String ad){ 
        super(ad); 
    }

    @Override
    public void ac() throws CihazZatenAcikException {
        super.ac();
        System.out.println(getAd() + " çalıştırıldı. Soğutma başlıyor...");
    }
}

class KahveMakinesi extends SoyutCihaz {
    public KahveMakinesi(String ad){
        super(ad); 
    }

    @Override
    public void ac() throws CihazZatenAcikException {
        super.ac();
        System.out.println(getAd() + " ısınmaya başladı. Kahveniz hazırlanıyor...");
    }
}

public class AkilliEv {
    public class CihazYoneticisi {
        private List<Cihaz> cihazlar = new ArrayList<>();

        public void cihazEkle(Cihaz cihaz) {
            cihazlar.add(cihaz);
            System.out.println(cihaz.getAd() + " sisteme başarıyla eklendi.");
        }

        public void cihazSil(String ad) throws CihazBulunamadiException {
            if (!cihazlar.removeIf(c -> c.getAd().equals(ad))) {
                throw new CihazBulunamadiException("Hata: '" + ad + "' isimli cihaz bulunamadı!");
            }
            System.out.println(ad + " sistemden kaldırıldı.");
        }

        public void cihazlariListele() {
            System.out.println("\n--- Kayıtlı Cihazlar ---");
            for (Cihaz c : cihazlar) {
                System.out.println("- " + c.getAd());
            }
        }
    }

    public class Otomasyon {
        public void programla(Cihaz cihaz, String zaman) {
            System.out.println(cihaz.getAd() + " için saat " + zaman + " değerine plan oluşturuldu.");
        }

        public void cihaziTetikle(Cihaz cihaz) {
            try {
                cihaz.ac();
            } catch (CihazZatenAcikException e) {
                System.out.println("Uyarı: Otomasyon çalıştı ama " + e.getMessage());
            }
        }

        public void otomasyonuKapat(){
            System.out.println("Otomasyon sistemi kapatıldı.");
        }
    }

    // TEST SENARYOSU (Main Metodu)
    public static void main(String[] args) {
        AkilliEv ev = new AkilliEv();
        CihazYoneticisi yonetici = ev.new CihazYoneticisi();
        Otomasyon otomasyon = ev.new Otomasyon();

        // 1. Cihazları Oluştur ve Ekle
        Lamba lamba = new Lamba("Salon Lambası");
        Klima klima = new Klima("Yatak Odası Kliması");
        KahveMakinesi kahveMakinesi = new KahveMakinesi("Mutfak Kahve Makinesi");

        yonetici.cihazEkle(lamba);
        yonetici.cihazEkle(klima);
        yonetici.cihazEkle(kahveMakinesi);

        yonetici.cihazlariListele();

        otomasyon.programla(kahveMakinesi, "07:00");

        otomasyon.cihaziTetikle(kahveMakinesi);

        System.out.println("-----HATA TESTLERİ-----");
        try {
            lamba.ac();
        }catch (CihazZatenAcikException e) {
            System.out.println("YAKALANAN HATA: " + e.getMessage());
        }
        
        try{
            klima.kapat();
            klima.kapat();
        }catch(CihazZatenKapaliException e){
            System.out.println("YAKALANAN HATA: " + e.getMessage());
        }

        try{
            yonetici.cihazSil("Bulaşık Makinesi");
        }catch (CihazBulunamadiException e) {
            System.out.println("YAKALANAN HATA: " + e.getMessage());
        }

       yonetici.cihazlariListele();

        otomasyon.otomasyonuKapat();
    }

}
