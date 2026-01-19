import java.util.List;
import java.util.ArrayList;

interface ISatınAlınabilir{
    String getAd();
    double fiyatHesapla();
    void stokDus(int adet) throws StokTukendiHatasi;
}

abstract class Urun implements ISatınAlınabilir{
    protected String urunAdi;
    protected double birimFiyat;
    protected int stokAdedi;

    public Urun(String urunAdi , double birimFiyat , int stokAdedi){
        this.urunAdi=urunAdi;
        this.birimFiyat=birimFiyat;
        this.stokAdedi=stokAdedi;
    }

    public String getAd(){
        return urunAdi;
    }

    @Override
    public void stokDus(int adet) throws StokTukendiHatasi{
        if(stokAdedi<adet){
            throw new StokTukendiHatasi(urunAdi + " için yeterli stok yok. Mevcut stok: " + stokAdedi);
        }
        stokAdedi-=adet;
        System.out.println(urunAdi + " stoğu güncellendi. Kalan: " + stokAdedi);

    }
}

class Elektronik extends Urun{
    public Elektronik(String urunAdi , double birimFiyat , int stokAdedi){
        super(urunAdi, birimFiyat, stokAdedi);
    }

    @Override
    public double fiyatHesapla(){
        return (birimFiyat*1.18); 
    }
}

class Giyim extends Urun{
    public Giyim(String urunAdi , double birimFiyat , int stokAdedi){
        super(urunAdi, birimFiyat, stokAdedi);
    }

    @Override
    public double fiyatHesapla(){
        return (birimFiyat*1.08) + birimFiyat; 
    }
}

class StokTukendiHatasi extends Exception{
    public StokTukendiHatasi(String mesaj){
        super(mesaj);
    }
}

public class ETicaretSistemi {
    class SepetYoneticisi{
        private List<ISatınAlınabilir> sepetListesi = new ArrayList<>();

        public void urunEkle(ISatınAlınabilir urun){
            sepetListesi.add(urun);
            System.out.println(urun.getAd() + " sepete eklendi.");
        }

        public void sepetiTamamla(){
            System.out.println("\n--- Satın Alma İşlemi Başlatılıyor ---");
            double toplamTutar=0;

            for(ISatınAlınabilir urun:sepetListesi){
                try{
                    urun.stokDus(1);
                    double fiyat = urun.fiyatHesapla();
                    toplamTutar+=fiyat;
                    System.out.println("alındı: " + urun.getAd() + " | Tutar:  " + fiyat + "TL");
                } catch (StokTukendiHatasi e){
                    System.out.println("HATA: " + e.getMessage());
                }
            }
            System.out.println("TOPLAM ÖDENECEK TUTAR: " + toplamTutar + "TL\n");
        }

        public void sepetiListele(){
            System.out.println("--- SEPETTEKİ ÜRÜNLER ---");
            for(ISatınAlınabilir u :sepetListesi){
                System.out.println("- " + u.getAd());
            }
        }
    }

    public static void main(String[] args) {
        ETicaretSistemi sistem= new ETicaretSistemi();
        ETicaretSistemi.SepetYoneticisi sepetim = sistem.new SepetYoneticisi();
        Urun urun1 = new Elektronik("iphone 15", 500000, 5);
        Urun urun2 = new Giyim("mavi gömlek", 1000, 0);

        sepetim.urunEkle( urun1);
        sepetim.urunEkle(urun2);

        sepetim.sepetiTamamla();
    }
}


