/*🏫 VERSİYON 3 – ÜNİVERSİTE DERS KAYIT SİSTEMİ
🎯 Hikâye

Öğrenciler farklı ders türlerine otomatik olarak kayıt edilir.

🔧 Yapı Eşlemesi
SmartHome	Üniversite
Device	Course
AbstractDevice	AbstractCourse
Lamp / AC / CoffeeMaker	MathCourse / CS_Course / PhysicsCourse
DeviceManager	CourseManager
Automation	RegistrationAutomation
🧠 Örnek Senaryo
Matematik dersi eklendi.
Bilgisayar Programlama dersi eklendi.
Fizik dersi eklendi.
Bilgisayar Programlama dersi Pazartesi 10:00'a kaydedildi.
Matematik dersi başlatıldı.
Kayıt sistemi kapatıldı.
 */

/*🔹 VERSİYON 3 – ÜNİVERSİTE DERS KAYIT OTOMASYONU
Interface: Course
Metotlar
String getCourseName()
void start()
void drop()

Abstract Class: AbstractCourse
Alanlar
protected String courseName
protected boolean active

Metotlar
String getCourseName()
abstract void start()
abstract void drop()

Concrete Class’lar
MathCourse, ProgrammingCourse, PhysicsCourse

Override Metotlar
void start()
void drop()

Inner Class: CourseManager
Alan
private List<Course> courses

Metotlar
void addCourse(Course course)

Inner Class: RegistrationAutomation
Metotlar
void register(Course course)
void disable()
 */
package karışık_sorular;

import java.util.ArrayList;
import java.util.List;

public interface Course {
    String getCourseName();
    void start();
    void drop();
}

abstract class AbstractCourse implements Course{
    protected String courseName;
    protected boolean active;

    public AbstractCourse (String courseName){
        this.courseName=courseName;
        this.active=false;
    }

    public String getCourseName(){
        return courseName;
    }

    public abstract void start();
    public abstract void drop();
}

class MathCourse extends AbstractCourse{
    public MathCourse(String courseName){
        super("Matematik");
    }

    @Override
    public void start(){
        active=true;
        System.out.println(courseName + " başlatıldı. ");
    }

    @Override
    public void drop(){
        active=false;
        System.out.println(courseName + " bitirildi. ");
    }
}

class ProgrammingCourse extends AbstractCourse{
    public ProgrammingCourse(String courseName){
        super("Bilgisayar Programlama");
    }

    @Override
    public void start(){
        active=true;
        System.out.println(courseName + " başlatıldı. ");
    }

    @Override
    public void drop(){
        active=false;
        System.out.println(courseName + " bitirildi. ");
    }
}

class PhysicsCourse extends AbstractCourse{
    public PhysicsCourse(String courseName){
        super("Fizik");
    }

    @Override
    public void start(){
        active=true;
        System.out.println(courseName + " başlatıldı. ");
    }

    @Override
    public void drop(){
        active=false;
        System.out.println(courseName + " bitirildi. ");
    }
}

class University{
    class CourseManager{
        private List<Course> courses = new ArrayList<>();

        public void addCourse(Course course){
            courses.add(course);
            System.out.println(course.getCourseName() + " listeye eklendi. ");
        }

        //ek olarak eklendi
        public void getCourse(){
            System.out.println(" mevcut kurslar : ");
            for (Course c : courses) {
               System.out.println("-" + c.getCourseName()); 
            }
        }
    }

    class RegistrationAutomation{
        public void registerCourse(Course course , String time){
            System.out.println(course.getCourseName() + time + "'a kaydedildi.");
        }

        public void disableCourse(){
            System.out.println(" kayıt sistemi kapatılmıştır. ");
        }
    }

    public static void main(String[] args) {
        University university = new University();
        CourseManager manager = university.new CourseManager();
        RegistrationAutomation automation = university.new RegistrationAutomation();

        Course c1 = new MathCourse("Matematik");
        Course c2 = new ProgrammingCourse(  "Bilgisayar Programlama");
        Course c3 = new PhysicsCourse( "Fizik");

        manager.addCourse(c1);
        manager.addCourse(c2);
        manager.addCourse(c3);

        manager.getCourse();

        automation.registerCourse(c2 , "10:00");
        c1.start();
        automation.disableCourse();
    }

}

