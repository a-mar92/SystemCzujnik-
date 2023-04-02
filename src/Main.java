import java.util.ArrayList;
import java.util.Random;

abstract class ElementSystemu
{
    public ElementSystemu()
    {
        id = "Nienazwany";
        wlaczony = false;
    }
    public ElementSystemu(String id)
    {
        this.id = id;
        wlaczony = false;
    }
    abstract public String podajTyp();
    public void pokazInfo()
    {
        System.out.println( "Element: " + id + " to " + podajTyp());
    }
    public void wlacz()
    {
        wlaczony = true;
        System.out.println( id + ": zostal wlaczony");
    }
    public void wylacz()
    {
        wlaczony = false;
        System.out.println( id + ": zostal wylaczony");
    }
    public boolean jestWlaczony()
    {
        return wlaczony;
    }
    public boolean jestAlarm()
    {
        return genAlarmu.nextBoolean();
    }
    public String podajId()
    {
        return id;
    }
    private String id;
    private boolean wlaczony;

    private static Random genAlarmu = new Random();
}

class CzujnikRuchu extends ElementSystemu
{
    // Zakładam, że czujnik ruchu ma własne implementacje operacji włącz i wyłącz
    public CzujnikRuchu(String id)
    {
        super(id);
    }
    public String podajTyp()
    {
        return "Czujnik ruchu";
    }
    public void wlacz()
    {
        super.wlacz();
        System.out.println( "Wlaczono czujnik ruchu " + podajId());
    }
    public void wylacz()
    {
        super.wylacz();
        System.out.println( "Wylaczono czujnik ruchu " + podajId());
    }
}

class CzujnikDymu extends ElementSystemu
{
    // Zakładam, że czujnik ruchu ma własne implementacje operacji włącz i wyłącz
    public CzujnikDymu(String id)
    {
        super(id);
    }
    public String podajTyp()
    {
        return "Czujnik dymu";
    }
    public void wlacz()
    {
        super.wlacz();
        System.out.println( "Wlaczono czujnik dymu " + podajId());
    }
    public void wylacz()
    {
        super.wylacz();
        System.out.println( "Wylaczono czujnik dymu " + podajId());
    }
}

class CzujnikOtwarcia extends ElementSystemu
{
    // Zakładam, że czujnik ruchu ma własne implementacje operacji włącz i wyłącz
    public CzujnikOtwarcia(String id)
    {
        super(id);
    }
    public String podajTyp()
    {
        return "Czujnik otwarcia";
    }
    public void wlacz()
    {
        super.wlacz();
        System.out.println( "Wlaczono czujnik otwarcia " + podajId());
    }
    public void wylacz()
    {
        super.wylacz();
        System.out.println( "Wylaczono czujnik otwarcia " + podajId());
    }
}

class SystemAlarmowy extends ElementSystemu
{
    public SystemAlarmowy()
    {
        super();
    }
    public SystemAlarmowy(String id)
    {
        super(id);
    }
    public String podajTyp()
    {
        return "System alarmowy";
    }
    public void dodaj(ElementSystemu c)
    {
        elementy.add(c);
    }

    public void pokazInfo()
    {
        super.pokazInfo();
        for(ElementSystemu e: elementy)
            e.pokazInfo();
    }
    public void wlacz()
    {
        super.wlacz();
        for(ElementSystemu e: elementy)
            e.wlacz();
    }
    public void wylacz()
    {
        super.wylacz();
        for(ElementSystemu e: elementy)
            e.wylacz();
    }
    public boolean jestAlarm()
    {
        boolean wykrytoAlarm = false;
        for (ElementSystemu e : elementy)
            if (e.jestWlaczony())
                if (e.jestAlarm())
                {
                    wykrytoAlarm = true;
                    System.out.println("Alarm na czujniku: " + e.podajId() + " " + e.podajTyp());
                }
                else
                    System.out.println("OK na czujniku: " + e.podajId() + " " + e.podajTyp());
        return wykrytoAlarm;
    }
    private ArrayList<ElementSystemu> elementy = new ArrayList();
}

class TesterSystemuAlarmowego
{
    public TesterSystemuAlarmowego(SystemAlarmowy system)
    {
        this.system = system;
    }
    public void zbudujPrzykladowySystem()
    {
        SystemAlarmowy s1 = new SystemAlarmowy("Budynek 1");
        SystemAlarmowy s2 = new SystemAlarmowy("Budynek 2");

        system.dodaj(s1);
        system.dodaj(s2);
        system.dodaj(new CzujnikRuchu("CRG Brama 1"));
        system.dodaj(new CzujnikRuchu("CRG Brama 2"));

        s1.dodaj(new CzujnikRuchu("CR01"));
        s1.dodaj(new CzujnikOtwarcia("CO02"));
        s1.dodaj(new CzujnikDymu("CD03"));
        s1.dodaj(new CzujnikOtwarcia("CO04"));

        s2.dodaj(new CzujnikOtwarcia("WCO01"));
        s2.dodaj(new CzujnikRuchu("WCR02"));
        s2.dodaj(new CzujnikRuchu("WCR03"));
        s2.dodaj(new CzujnikOtwarcia("WC04"));
    }
    public void pokazStruktureSystemu()
    {
        System.out.println("Struktura systemu ----------------------");
        system.pokazInfo();
    }
    public void testuj()
    {
        System.out.println("Aktywacja ------------------------------");
        system.wlacz();
        System.out.println("Monitoring -----------------------------");
        if(system.jestAlarm())
            System.out.println("!!!Wykryto alarm, dzwon po ochrone");
        System.out.println("Dezaktywacja ---------------------------");
        system.wylacz();
    }

    private SystemAlarmowy system;
}

public class Main
{
    public static void main(String[] args)
    {
        TesterSystemuAlarmowego tester = new TesterSystemuAlarmowego(new SystemAlarmowy("Glowny system alarmowy"));

        tester.zbudujPrzykladowySystem();
        tester.pokazStruktureSystemu();
        tester.testuj();
    }
}