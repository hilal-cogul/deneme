package karışık_sorular;

import java.util.ArrayList;
import java.util.List;

class YetersizBakiyeException extends Exception{
    public YetersizBakiyeException(String mesaj){
        super(mesaj);
    }
}

class HesapBulunamadiHatasi extends Exception{
    public HesapBulunamadiHatasi(String mesaj){
        super(mesaj);
    }
}

class GecersizTutarException extends Exception{
    public GecersizTutarException(String mesaj){
        super(mesaj);
    }
}

interface Hesap{
    String getHesapNo();
    double  getBakiye();
    void paraYatir(double miktar) throws GecersizTutarException;
    void paraCek(double miktar) throws YetersizBakiyeException; 
}

abstract class SoyutHesap implements Hesap{
    private String hesapNo;
    protected String musteriAdi;
    protected double bakiye;

    public SoyutHesap(String hesapNo , String musteriAdi){
        this.hesapNo=hesapNo;
        this.musteriAdi=musteriAdi;
        this.bakiye=0.0;
    }

    @Override
    public String getHesapNo(){
        return hesapNo;
    }

    @Override
    public double getBakiye(){
        return bakiye;
    }

    @Override
    public void paraYatir(double miktar) throws GecersizTutarException{
       if(miktar<0){
        throw new GecersizTutarException("tutar sıfırdan küçük olamaz");
       }
        bakiye+=miktar;
        System.out.println(getHesapNo() + " hesabına " + miktar + " yatırıldı.");
        System.out.println("güncel bakiye : " + getBakiye());
    }

    @Override
    public void paraCek(double miktar) throws YetersizBakiyeException{
        if(miktar>bakiye){
            throw new YetersizBakiyeException("yetersiz bakiye");
        }

        if(miktar<0){
            throw new YetersizBakiyeException("çekilecek tutar sıfırdan küçük olamaz");
        }

        bakiye-=miktar;
        System.out.println(getHesapNo() + " hesabından " + miktar + " çekildi");
        System.out.println("güncel bakiye : " + getBakiye());
    }
}

class VadesizHesap extends SoyutHesap{
    public VadesizHesap(String hesapNo , String musteriAdi){
        super(hesapNo, musteriAdi);
    }

    @Override
    public void paraCek(double miktar) throws YetersizBakiyeException{
        super.paraCek(miktar);
        System.out.println("işlem başı ücret 1 TL dir.");
    }
}

class VadeliHesap extends SoyutHesap{
    public VadeliHesap(String hesapNo , String musteriAdi){
        super(hesapNo , musteriAdi);
        this.bakiye=5000;
    }
}

public class BankaSistemi {
    class MusteriHizmetleri{
        List<Hesap> hesapListesi = new ArrayList<>();

        public void hesapAc(Hesap h){
            hesapListesi.add(h);
            System.out.println(h.getHesapNo() + " hesabınız açıldı ve müşteri hizmetleri sistemine eklenmiştir.");
        }

        public void hesapKapat(String hesapNo) throws HesapBulunamadiHatasi {
            if (!hesapListesi.removeIf(h -> h.getHesapNo().equals(hesapNo))) {
                throw new HesapBulunamadiHatasi("Hesap bulunamadı: " + hesapNo);
            }
            System.out.println(hesapNo + " nolu hesap kapatıldı.");
        }

        public void hesaplarıListele(){
            for(Hesap h:hesapListesi){
                System.out.println("-" + h.getHesapNo());
            }
        }
    }

    class IslemMerkezi{
        public void havaleYap(Hesap gonderen , Hesap alici , double miktar){
            System.out.println("----HAVALE İŞLEMİ----");
            System.out.println("GÖNDERİCİ: " + gonderen + " | " + " ALICI: " + alici + " | " + " MİKTAR: " + miktar);
        }
    }

    public static void main(String[] args) {
        BankaSistemi sistem=new BankaSistemi();
        MusteriHizmetleri musteriHizmetleri=sistem.new MusteriHizmetleri();
        IslemMerkezi islemMerkezi=sistem.new IslemMerkezi();

        VadesizHesap vadesizHesap=new VadesizHesap("1234", "AHMET BEY");
        VadeliHesap vadeliHesap=new VadeliHesap("5678", "AYŞE HANIM");

        musteriHizmetleri.hesapAc(vadesizHesap);
        musteriHizmetleri.hesapAc(vadeliHesap);

        musteriHizmetleri.hesaplarıListele();
        try{
            vadesizHesap.paraYatir(500);
            vadeliHesap.paraCek(1000);
            vadeliHesap.paraYatir(-50);
        } catch(GecersizTutarException e){
            System.out.println("HATA: " + e.getMessage());
        } catch(YetersizBakiyeException e){
            System.out.println("HATA: " + e.getMessage());
        }

        islemMerkezi.havaleYap(vadesizHesap, vadeliHesap, 200);

        try{
            musteriHizmetleri.hesapKapat("5896");
        } catch(HesapBulunamadiHatasi e){
            System.out.println("HATA: " + e.getMessage());
        }
    }
}
