package gemini_karisik_sorular;

class KapasiteYetersizException extends Exception {
    public KapasiteYetersizException(String message) {
        super(message);
    }
}

class TarihCakismaException extends Exception {
    public TarihCakismaException(String message) {
        super(message);
    }
}

public interface Musteriler {
    void rezervasyonYap(KonaklamaBirimi birim, String tarih , int kisiSayisi)
            throws KapasiteYetersizException, TarihCakismaException;
    void iptalEt();
}

class Musteri implements Musteriler{
    private String ad;
    private Rezervasyon aktifRezervasyon;

    public Musteri(String ad){
        this.ad=ad;
    }

    @Override
    public void rezervasyonYap(KonaklamaBirimi birim, String tarih , int kisiSayisi)
            throws KapasiteYetersizException, TarihCakismaException {
        if(kisiSayisi > birim.kapasite){
            throw new KapasiteYetersizException("Seçilen birimin kapasitesi yetersiz.");
        }

        if(birim.doluMu){
            throw new TarihCakismaException("Seçilen birim bu tarihte dolu.");
        }

        birim.doluMu = true;
        aktifRezervasyon = new Rezervasyon(this.ad, birim, tarih);
        System.out.println(ad + " için rezervasyon yapıldı.");
        aktifRezervasyon.rezervasyonOzeti();
    }

    @Override
    public void iptalEt() {
        if(aktifRezervasyon != null){
            // rezervasyon iptal edildiğinde birimi boşalt
            aktifRezervasyon.getKonaklamaBirimi().doluMu = false;
            aktifRezervasyon = null;
            System.out.println(ad + " için rezervasyon iptal edildi.");
        }
    }
}

abstract class KonaklamaBirimi{
    protected int kapasite;
    protected double fiyat;
    protected String odaNo;
    protected boolean doluMu=false;

    abstract void birimBilgileriniGoruntule();

    public KonaklamaBirimi(int kapasite , double fiyat , String odaNo ){
        this.kapasite=kapasite;
        this.fiyat=fiyat;
        this.odaNo=odaNo;
    }
}

class OtelOdasi extends KonaklamaBirimi{
    public OtelOdasi(int kapasite , double fiyat , String odaNo){
        super(kapasite, fiyat, odaNo);
    }

    @Override
    public void birimBilgileriniGoruntule(){
        System.out.println("-------otel odasi için------");
        System.out.println("kapasite sayısı : " + kapasite);
        System.out.println("fiyat bilgisi : " + fiyat);
        System.out.println("oda no : " + odaNo);
    }
}

class Bungalov extends KonaklamaBirimi{
    public Bungalov(int kapasite , double fiyat , String odaNo){
        super(kapasite, fiyat, odaNo);
    }

    @Override
    public void birimBilgileriniGoruntule(){
        System.out.println("-------bungalov için------");
        System.out.println("kapasite sayısı : " + kapasite);
        System.out.println("fiyat bilgisi : " + fiyat);
        System.out.println("oda no : " + odaNo);
    }
}

class Rezervasyon {
    private String musteriAdi;
    private KonaklamaBirimi konaklamaBirimi;
    private RezervasyonDetaylari detaylar;

    class RezervasyonDetaylari{
        private String tarih;

        public RezervasyonDetaylari(String tarih){
            this.tarih=tarih;
        }

        public String getTarih() {
            return tarih;
        }
    }

    public Rezervasyon(String musteriAdi, KonaklamaBirimi konaklamaBirimi, String tarih) {
        this.musteriAdi = musteriAdi;
        this.konaklamaBirimi = konaklamaBirimi;
        this.detaylar = new RezervasyonDetaylari(tarih);
    }

    public KonaklamaBirimi getKonaklamaBirimi(){
        return konaklamaBirimi;
    }

    public void rezervasyonOzeti(){
        System.out.println("Musteri Adı: " + musteriAdi);
        System.out.println("Rezervasyon Tarihi: " + detaylar.getTarih());
        konaklamaBirimi.birimBilgileriniGoruntule();
    }

    public static void main(String[] args) {
        KonaklamaBirimi oda1 = new OtelOdasi(2, 150.0, "101");
        KonaklamaBirimi bungalov1 = new Bungalov(4, 300.0, "B1");

        Musteriler musteri1 = new Musteri("Ahmet");
        Musteriler musteri2 = new Musteri("Ayşe");

        try{
            musteri1.rezervasyonYap(oda1, "2024-07-15", 2);
            System.out.println("-----------------------");
            musteri1.rezervasyonYap(bungalov1, "2024-07-15", 5); // Kapasite yetersiz
        } catch (KapasiteYetersizException | TarihCakismaException e) {
            System.out.println("REZERVASYON HATASI: " + e.getMessage());
        } 
        try{
            musteri2.rezervasyonYap(oda1, "2024-07-15", 1); // Tarih çakışması
        }catch (Exception e) {
            System.out.println("TARİH ÇAKIŞMASI HATASI : " + e.getMessage());
        }
    }
}