import java.util.List;
import java.util.ArrayList;

class AgirlikLimitiAsildiException extends Exception{
    public AgirlikLimitiAsildiException (String mesaj){
        super(mesaj);
    }
}

class GecersizAdresException extends Exception{
    public GecersizAdresException(String mesaj){
        super(mesaj);
    }
}

interface TakipEdilebilir{
    void konumGuncelle(String yeniKonum);
}

abstract class Gonderi{
    protected String gonderiID;
    protected String adres;
    protected double temelUcret;

    public Gonderi(String gonderiID , String adres , double temelUcret){
        this.gonderiID=gonderiID;
        this.adres=adres;
        this.temelUcret=temelUcret;
    }

    abstract double ucretHesapla();

    void gonderiBilgisi(){
        System.out.println("gönderi ID: " + gonderiID);
        System.out.println("Adres: " + adres);
    }

    void gonderiDogrula()throws GecersizAdresException{
        if(adres==null){
            throw new GecersizAdresException("Geçersiz adres girişi");
        }
    }
}

class Paket extends Gonderi implements TakipEdilebilir{
    public double agirlik;

    public Paket(String gonderiID , String adres , double temelUcret , double agirlik){
        super(gonderiID, adres, temelUcret);
        this.agirlik=agirlik;
    }

    void yuklemeYap(double agirlik) throws AgirlikLimitiAsildiException{
        if(agirlik>=50){
            throw new AgirlikLimitiAsildiException("Ağırlık snırı aşıldı");
        }
    }

    @Override
    double ucretHesapla(){
        return agirlik*0.58;
    }

    @Override
    void gonderiBilgisi(){
        super.gonderiBilgisi();
        System.out.println("paket ağırlığı : " + agirlik);
    }

    @Override
    public void konumGuncelle(String yeniKonum){
        System.out.println("yeni konum : " + yeniKonum);
    }
}

class Zarf extends Gonderi{
    public boolean acilKodlu;

    public Zarf(String gonderiID , String adres , double temelUcret , boolean acilKodlu){
        super(gonderiID, adres, temelUcret);
        this.acilKodlu=acilKodlu;
    }

    @Override
    double ucretHesapla(){
        return 120;
    }

    @Override
    void gonderiBilgisi(){
        super.gonderiBilgisi();
        if(acilKodlu){
            System.out.println("acil kod durumu : acil kodlu");
        }
    }
}

public class LojistikMerkezi {
    class TeslimatSorumlusu{
        public String isim; 
        public String personelID;

        public TeslimatSorumlusu(String isim , String personelID){
            this.isim=isim;
            this.personelID=personelID;
        }

        public void teslimatRaporu( Gonderi gönderi , int mesafe){
            double ekMesafeUcreti=mesafe*1.5;
            double toplamTutar= gönderi.ucretHesapla() + ekMesafeUcreti;

            System.out.println("=====TESLİMAT RAPORU=====");
            System.out.println("teslimat sorumlusu : " + isim + " | " + " personel ID : " + personelID);
            System.out.println("gönderi ID : " + gönderi.gonderiID);
            System.out.println("gönderi ücreti : " + gönderi.ucretHesapla());
            System.out.println("ek mesafe ücreti : " + ekMesafeUcreti);
            System.out.println("adres : " + gönderi.adres);
            System.out.println("-------------------------");
            System.out.println("toplam tutar : " + toplamTutar);
            System.out.println("=========================");
        }
    }

    public static void main(String[] args) {
        List<Gonderi> gonderi=new ArrayList<>();
        LojistikMerkezi merkez=new LojistikMerkezi();
        gonderi.add(new Paket("123456", "yurt", 12, 1.2));
        gonderi.add(new Zarf("567891", "ofis", 5, true));

        for(Gonderi g :gonderi){
            try{
                g.gonderiDogrula();

                if(g instanceof Paket){
                    ((Paket)g).yuklemeYap(1.0);
                    TeslimatSorumlusu sorumlu=merkez.new TeslimatSorumlusu("ali", "A-123");
                    sorumlu.teslimatRaporu(g, 14);
                    ((Paket)g).yuklemeYap(51.0);
                }

                if(g instanceof TakipEdilebilir){
                    ((TakipEdilebilir)g).konumGuncelle("İSTANBUL");
                }
            } catch(AgirlikLimitiAsildiException e){
                System.out.println("HATA: " + e.getMessage());
            } catch(GecersizAdresException e){
                System.out.println("HATA: " + e.getMessage());
            } catch(Exception e){
                System.out.println("BİLİNMEYEN HATA... ");
            }
        }
    }
}

