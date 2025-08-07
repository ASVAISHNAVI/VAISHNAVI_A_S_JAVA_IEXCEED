interface Printer {
    static void connect(){
        System.out.println("printer connects");
    }
    public void devices();

    
}
class Bluetooth implements Printer{
    Bluetooth(){
        System.out.println("bluetooth also connects");

    }
    public void devices(){
        System.out.println("Printer can be connected to few devices");

    }
}
public class static_interface {
    public static void main(String[] args) {
        Bluetooth b1=new Bluetooth();
        b1.devices();
        Printer.connect();
    }
    
}
