package gemini_karisik_sorular;

interface ITeslimEdilebilir {
    void teslimEt() throws KuryeBulunamadiHatasi;
}

abstract class Restoran implements ITeslimEdilebilir{
    String ad;
    public Restoran(String ad){
        this.ad=ad;
    }

    abstract void yemekhazirla();
}

class HizliYemek extends Restoran {
    public HizliYemek(String ad){
        super(ad);
    }

    @Override
    public void yemekhazirla(){
        System.out.println("hizli yemek restoranı " + ad + " için yemek hazır.");
    }

    @Override
    public void teslimEt(){
        System.out.println(ad + " kurye yola çıktı.");
    }
}

class LuksYemek extends Restoran{
    public LuksYemek(String ad){
        super(ad);
    }

    @Override
    public void yemekhazirla(){
        System.out.println("lüks yemek restoranı " + ad + " için yemek hazır.");
    }

    @Override
    public void teslimEt() throws KuryeBulunamadiHatasi{
        throw new KuryeBulunamadiHatasi(ad+ " teslim için araç yok");
    }
}

class KuryeBulunamadiHatasi extends Exception{
    public KuryeBulunamadiHatasi(String mesaj){
        super(mesaj);
    }
}

class YemekUygulamasi{
    class SiparisYoneticisi{
        public void siparisIsle(Restoran r){
            r.yemekhazirla();
            try{
                r.teslimEt();
            }catch(KuryeBulunamadiHatasi e){
                System.out.println("TESLİMAT İPTAL: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        YemekUygulamasi uygulama = new YemekUygulamasi();
        SiparisYoneticisi siparisYoneticisi = uygulama.new SiparisYoneticisi();

        Restoran r1= new HizliYemek("HIZLI DOY");
        Restoran r2= new LuksYemek("LUKS DOY");

        siparisYoneticisi.siparisIsle(r1);
        System.out.println("-------------");
        siparisYoneticisi.siparisIsle(r2);
    }
}