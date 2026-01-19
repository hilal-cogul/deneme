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
