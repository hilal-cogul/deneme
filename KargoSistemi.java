import java.util.ArrayList;
import java.util.List;

class KargoZatenTEslimEdildiException extends Exception{
    public KargoZatenTEslimEdildiException(String mesaj){
        super(mesaj);
    }
}

class GecersizAgirlikException extends Exception{
    public GecersizAgirlikException(String mesaj){
        super(mesaj);
    }
}

class KargoBulunamadiException extends Exception{
    public KargoBulunamadiException(String mesaj){
        super(mesaj);
    }
}

interface Kargo{
    void gonder();
    void teslimEt() throws KargoZatenTEslimEdildiException;
    double ucretHesapla();
}

abstract class SoyutKargo implements Kargo{
    protected String takipNo;
    protected String aliciAdi;
    protected double agirlik;
    protected boolean teslimEdildiMi;

    public SoyutKargo(String takipNo , String aliciAdi , double agirlik){
        this.takipNo=takipNo;
        this.aliciAdi=aliciAdi;
        this.agirlik=agirlik;
        this.teslimEdildiMi=false;
    }

    @Override
    public void teslimEt() throws KargoZatenTEslimEdildiException{
        if(teslimEdildiMi){
            throw new KargoZatenTEslimEdildiException("kargonuz zaten teslim edilmiştir.");
        }
        teslimEdildiMi=true;
    }
}

class StandartKargo extends SoyutKargo{
    public StandartKargo(String takipNo , String aliciAdi , double agirlik) {
        super(takipNo, aliciAdi, agirlik);
    }

    @Override 
    public double ucretHesapla(){
        return agirlik*10;
    }

    @Override
    public void gonder(){
        System.out.println(takipNo + " nolu kargonuz standart hızda yola çıktı");
    }
}

class HassasKargo extends SoyutKargo{
    public HassasKargo(String takipNo , String aliciAdi , double agirlik) {
        super(takipNo, aliciAdi, agirlik);
    }

    @Override 
    public double ucretHesapla(){
        return (agirlik*10)+50;
    }

    @Override
    public void gonder(){
        System.out.println(takipNo + " nolu kargo DİKKAT! kırılabilir kargo . özel personel atandı");
    }
}

class VipKargo extends SoyutKargo{
    public VipKargo(String takipNo , String aliciAdi , double agirlik) {
        super(takipNo, aliciAdi, agirlik);
    }

    @Override 
    public double ucretHesapla(){
        return agirlik*30;
    }

    @Override
    public void gonder(){
        System.out.println(takipNo + " nolu kargonuz VIP KARGO : 24 saatte teslimat garantisi başladı");
    }
}
public class KargoSistemi {
    class KargoMerkezi{
        List<Kargo> kargoListesi = new ArrayList<>();

        public void kargoEkle(Kargo kargo){
            kargoListesi.add(kargo);
            System.out.println(" kargonuz sisteme eklenmistir");
        }

        public void kargoSil(String takipNo) throws KargoBulunamadiException{
            if(!kargoListesi.equals(takipNo)){
                throw new KargoBulunamadiException(" HATA:kargo bulunamadı");
            }
            System.out.println("kargo başarıyla silindi");
        }
    }

    public static void main(String[] args) {
        KargoSistemi sistem=new KargoSistemi();
        KargoMerkezi merkez=sistem.new KargoMerkezi();

        StandartKargo standartKargo=new StandartKargo("1234", "ayşe", 1);
        HassasKargo hassasKargo=new HassasKargo("5678", "leyla", 2);
        VipKargo vipKargo = new VipKargo("6987", "banu", 2.4);

        merkez.kargoEkle(standartKargo);
        merkez.kargoEkle(hassasKargo);
        merkez.kargoEkle(vipKargo);

        standartKargo.ucretHesapla();
        vipKargo.gonder();

        try{
            hassasKargo.teslimEt();
            hassasKargo.teslimEt();
        }catch(KargoZatenTEslimEdildiException e){
            System.out.println("HATA: " + e.getMessage());
        }
        
        try{
            merkez.kargoSil("5784");
        }catch(KargoBulunamadiException e){
            System.out.println("HATA: " + e.getMessage());
        }

    }
}

