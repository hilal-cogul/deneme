package gemini_karisik_sorular;

interface ITibbiIslem {
    void tedaviUygula()  throws HataliIslemHatasi;
}

abstract class Doktor implements ITibbiIslem{
    String ad;

    public Doktor(String ad){
        this.ad=ad;
    }
}

class Cerrah  extends Doktor{
    public Cerrah(String ad){
        super(ad);
    }
    @Override
    public void tedaviUygula(){
        System.out.println("cerrah " + ad + " ameliyatı başarıyla uyguladı");
    }
}

class Stajyer extends Doktor{
    public Stajyer(String ad){
        super(ad);
    }

    @Override
    public void tedaviUygula() throws HataliIslemHatasi{
        throw new HataliIslemHatasi("stajyer " + ad + " tek başına ameliyat yapamaz!");
    }
}

class HataliIslemHatasi extends Exception{
    public HataliIslemHatasi(String mesaj){
        super(mesaj);
    }
}

class Hastane{
    class AcilServis{
        public void doktorAta(Doktor d){
            try{
                System.out.println("hasta için doktor : " + d.ad);
                d.tedaviUygula();
            }catch(HataliIslemHatasi e){
                System.out.println("HATA: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Hastane hastane= new Hastane();
        AcilServis acilServis = hastane.new AcilServis();

        Doktor d1= new Cerrah("ali veli");
        Doktor d2 = new Stajyer("ayşe yılmaz");

        acilServis.doktorAta(d1);
        acilServis.doktorAta(d2);
    }
}
