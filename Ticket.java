/*🎬 VERSİYON 2 – SİNEMA BİLET OTOMASYONU
🎯 Hikâye

Bir sinema salonunda farklı film seansları ve bilet türleri yönetilmektedir.

🔧 Yapı Eşlemesi
SmartHome	Sinema
Device	Ticket
AbstractDevice	AbstractTicket
Lamp / AC / CoffeeMaker	StudentTicket / FullTicket / VIPTicket
DeviceManager	TicketManager
Automation	BookingAutomation
📌 Temel Sınıf İsimleri

Ticket

AbstractTicket

StudentTicket, FullTicket, VIPTicket

Cinema

TicketManager

BookingAutomation

🧠 Örnek Senaryo
Öğrenci Bileti sisteme eklendi.
Tam Bilet sisteme eklendi.
VIP Bilet sisteme eklendi.
VIP Bilet 20:30 seansına ayrıldı.
Öğrenci Bileti kullanıldı.
Bilet otomasyonu durduruldu.
 */

/*
🔹 VERSİYON 2 – SİNEMA BİLET OTOMASYONU
Interface: Ticket

Metotlar

String getType()
void use()
void cancel()

Abstract Class: AbstractTicket

Alanlar

protected String type
protected boolean used

Metotlar

String getType()
abstract void use()
abstract void cancel()

Concrete Class’lar
StudentTicket, FullTicket, VIPTicket

Override Metotlar
void use()
void cancel()

Inner Class: TicketManager

Alan
private List<Ticket> tickets

Metotlar
void addTicket(Ticket ticket)

Inner Class: BookingAutomation

Metotlar
void trigger(Ticket ticket)
void disable()
 */
package karışık_sorular;

import java.util.ArrayList;
import java.util.List;

interface Ticket {
    String getType();
    void use();
    void cancel();
}

abstract class AbstractTicket implements Ticket{
    protected String type;
    protected boolean used;

    public AbstractTicket (String type){
        this.type=type;
        this.used=false;
    }

    public String getType(){
        return type;
    }

    public abstract void use();
    public abstract void cancel();
}

class StudentTicket extends AbstractTicket{
    public StudentTicket(){
        super(" öğrenci bileti ");
    }

    @Override
    public void use(){
        used=true;
        System.out.println(getType() + " kullanıldı");
    }

    @Override
    public void cancel(){
        used=false;
        System.out.println(getType() + " iptal edildi");
    }
}

class FullTicket extends AbstractTicket{
    public FullTicket(){
        super(" tam bilet ");
    }

    @Override
    public void use(){
        used=true;
        System.out.println(getType() + " kullanıldı");
    }

    @Override
    public void cancel(){
        used=false;
        System.out.println(getType() + " iptal edildi");
    }
}

class VIPTicket extends AbstractTicket{
    public VIPTicket(){
        super(" VIP bilet ");
    }
    @Override
    public void use(){
        used=true;
        System.out.println(getType() + " kullanıldı");
    }

    @Override
    public void cancel(){
        used=false;
        System.out.println(getType() + " iptal edildi");
    }
}

class Cinema{
    class TicketManager{
        private List<Ticket> tickets = new ArrayList<>();

        public void addTicket(Ticket ticket){
            tickets.add(ticket);
            System.out.println(ticket.getType() + " listeye eklendi");
        }
    }

    class BookingAutomation{
        public void triggerTicket( Ticket ticket){
            ticket.use();
        }

        public void disableTicket(){
            System.out.println("bilet otomasyonu durduruldu.");
        }
    }

    public static void main(String[] args) {
        Cinema cinema = new Cinema();
        TicketManager manager = cinema.new TicketManager();
        BookingAutomation automation = cinema.new BookingAutomation();

        Ticket t1 = new StudentTicket();
        Ticket t2 = new FullTicket();
        Ticket t3 = new VIPTicket();

        manager.addTicket(t1);
        manager.addTicket(t2);
        manager.addTicket(t3);

        automation.triggerTicket(t3);
        automation.triggerTicket(t1);
        automation.disableTicket();
    }
}