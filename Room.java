/*🏨 VERSİYON 1 – OTEL REZERVASYON SİSTEMİ
🎯 Hikâye

Bir otelde farklı oda türleri vardır ve rezervasyonlar otomatik olarak yönetilir.

🔧 Yapı Eşlemesi
SmartHome	Otel
Device	Room
AbstractDevice	AbstractRoom
Lamp / AC / CoffeeMaker	SingleRoom / DoubleRoom / Suite
DeviceManager	RoomManager
Automation	ReservationAutomation
📌 Temel Sınıf İsimleri

Room (interface)

AbstractRoom

SingleRoom, DoubleRoom, Suite

Hotel

RoomManager (inner)

ReservationAutomation (inner)

🧠 Örnek Senaryo
101 numaralı Tek Kişilik Oda eklendi.
202 numaralı Çift Kişilik Oda eklendi.
303 numaralı Suit Oda eklendi.
202 numaralı Çift Kişilik Oda 15:00 saatine rezerve edildi.
101 numaralı Tek Kişilik Oda müşteriye tahsis edildi.
Rezervasyon sistemi kapatıldı.
 */

/*🏨 VERSİYON 1 – OTEL REZERVASYON SİSTEMİ
📌 Interface: Room

Metotlar
String getRoomNumber()
void reserve()
void release()

📌 Abstract Class: AbstractRoom
Alanlar (Fields)
protected String roomNumber
protected boolean reserved

Metotlar
String getRoomNumber()
abstract void reserve()
abstract void release()

📌 Concrete Class’lar
SingleRoom, DoubleRoom, SuiteRoom

Alan
(Abstract’tan gelir)

Override Metotlar
void reserve()
void release()

📌 Inner Class: RoomManager

Alan
private List<Room> rooms

Metotlar
void addRoom(Room room)
void listRooms()

📌 Inner Class: ReservationAutomation

Metotlar
void schedule(Room room)
void disable()
 */

import java.util.ArrayList;
import java.util.List;

public interface Room {
    String getRoomNumber();
    void reserve();
    void release();
}

abstract class AbstractRoom implements Room{
    protected String roomNumber;
    protected boolean reserved;

    public AbstractRoom(String roomNumber){
        this.roomNumber=roomNumber;
        this.reserved=false;
    }

    @Override
    public String getRoomNumber(){
        return roomNumber;
    }

    public abstract void reserve();
    public abstract void release();
}

class SingleRoom extends AbstractRoom{
    public SingleRoom(String roomNumber){
        super(roomNumber);
    }
    @Override
    public void reserve(){
        reserved=true;
        System.out.println(getRoomNumber() + " (tek kişilik oda) bu oda rezerve edildi");
    }

    @Override
    public void release(){
        reserved=false;
        System.out.println(getRoomNumber() + " (tek kişilik oda) bu oda razerve değil");
    }
}

class DoubleRoom extends AbstractRoom{
    public DoubleRoom(String roomNumber){
        super(roomNumber);
    }
    @Override
    public void reserve(){
        reserved=true;
        System.out.println(getRoomNumber() + " (çift kişilik oda) bu oda rezerve edildi");
    }

    @Override
    public void release(){
        reserved=false;
        System.out.println(getRoomNumber() + " (çift kişilik oda) bu oda razerve değil");
    }
}

class SuiteRoom extends AbstractRoom{
    public SuiteRoom(String roomNumber){
        super(roomNumber);
    }
    @Override
    public void reserve(){
        reserved=true;
        System.out.println(getRoomNumber() + " (suit oda) bu oda rezerve edildi");
    }

    @Override
    public void release(){
        reserved=false;
        System.out.println(getRoomNumber() + " (suit oda) bu oda razerve değil");
    }
}

class Hotel{
    class RoomManager{
        private List<Room> rooms = new ArrayList<>();

        public void addRoom(Room room){
            rooms.add(room);
            System.out.println(room.getRoomNumber() + " numaralı oda listeye eklendi");
        }

        public void listRooms(){
            System.out.println(" mevcut odalar : ");
            for(Room r : rooms){
                System.out.println("-" + r.getRoomNumber());
            }
        }
    }

    class ReservationAutomation{
        public void scheduleRoom( Room room){
            room.reserve();
        }

        public void disableRoom(){
            System.out.println("rezervasyon sistemi kapatılmıştır.");
        }
    }

    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        RoomManager manager = hotel.new RoomManager();
        ReservationAutomation automation = hotel.new ReservationAutomation();

        Room r1=new SingleRoom("101");
        Room r2 = new DoubleRoom("102");
        Room r3 = new SuiteRoom("303");

        manager.addRoom(r1);
        manager.addRoom(r2);
        manager.addRoom(r3);

        automation.scheduleRoom(r2);
        automation.scheduleRoom(r1);
        automation.disableRoom();
    }
}

