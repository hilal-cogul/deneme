package karışık_sorular;

import java.util.List;
import java.util.ArrayList;

public interface Device {
    String getName();
    void turnOn();
    void turnOff();
}

abstract class AbstractDevice implements Device{
    private String name;
    private boolean isOn;

    public AbstractDevice ( String name){
        this.name=name;
        this.isOn=false;
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public void turnOn(){
        if(!isOn){
            isOn=true;
            System.out.println(name + " açıldı");
        }
        else{
            System.out.println(name + " zaten açık");
        }
    }

    @Override
    public void turnOff(){
        if(isOn){
            isOn=false;
            System.out.println(name + " kapatıldı");
        }
        else{
            System.out.println(name + " zaten kapalı");
        }
    }
}

class Lamp extends AbstractDevice{
    public Lamp(String name){
        super(name);
    }

    @Override
    public void turnOn(){
        super.turnOn();
        System.out.println(getName() + " yanıyor");
    }
}

class AC extends AbstractDevice {
    public AC (String name){
        super(name);
    }

    @Override
    public void turnOn(){
        super.turnOn();
        System.out.println(getName() + " soğutmaya başladı");
    }
}

class CoffeeMaker extends AbstractDevice{
    public CoffeeMaker (String name){
        super(name);
    }

    @Override
    public void turnOn(){
        super.turnOn();
        System.out.println(getName() + " kahve haırlamaya başlıyor");
    }
}

class SmartHome{
    class DeviceManager{
        private List<Device> devices = new ArrayList<>();

        public void addDevice(Device device){
            devices.add(device);
            System.out.println(device.getName() + " listeye eklendi");
        }

        public void removeDevice(Device device){
            devices.remove(device);
            System.out.println(device.getName() + " listeden kaldırıldı");
        }

        public void getDevices(){
            System.out.println(" mevcut cihazlar : ");
            for(Device d:devices){
                System.out.println("-" + d.getName());
            }
        }
    }

    class Automation{
        public void scheduleDevice (Device device , String time){
            System.out.println(device.getName() + time + " çalışmaya programlandı");
        }

        public void triggerDevice (Device device){
            System.out.println(device.getName() + " etkinleştirdi");
            device.turnOn();
        }

        public void disableDevice(){
            System.out.println(" otomasyon devre dışı bırakıldı.");
        }
    }

    public static void main(String[] args) {
        SmartHome smartHome = new SmartHome();
        DeviceManager manager = smartHome.new DeviceManager();
        Automation automation = smartHome.new Automation();

        Lamp lamp = new Lamp("salon lambası");
        AC ac = new AC("oturma odası kliması");
        CoffeeMaker coffeeMaker = new CoffeeMaker("kahve makinesi");

        manager.addDevice(lamp);
        manager.addDevice(ac);
        manager.addDevice(coffeeMaker);

        manager.getDevices();
        
        automation.scheduleDevice(coffeeMaker , " 14:50");
        automation.triggerDevice(lamp);
        ac.turnOff();
        automation.disableDevice();
    }
}