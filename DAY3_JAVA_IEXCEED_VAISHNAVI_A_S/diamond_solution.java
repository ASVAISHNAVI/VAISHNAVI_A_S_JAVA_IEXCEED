interface Printer{
    default void connect(){
        System.out.println("printer connects");

    }}
interface Scanner{
    default void connect(){
        System.out.println("scanner connects.");
    }
}
class phone implements Printer,Scanner{
    public void connect(){
        System.out.println("connecting..");
        Printer.super.connect();
        Scanner.super.connect();
    }
} 

public class diamond_solution {
    public static void main(String[] args) {
        phone p1=new phone();
        p1.connect();
        
    }
    
}
