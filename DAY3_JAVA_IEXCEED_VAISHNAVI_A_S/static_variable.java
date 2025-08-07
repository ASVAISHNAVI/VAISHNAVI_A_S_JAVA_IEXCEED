class Sample{
    float x=6;
    static float y;
    public void Sample1(){
        System.out.println("y="+y);
        System.out.println("y="+y);
    }
    static void Sample2(){
        System.out.println("x="+new Sample().x);
        System.out.println("y:"+y);
    }
}
public class static_variable {
    public static void main(String[] args) {
        Sample s1=new Sample();
        s1.Sample1();
        Sample.Sample2();
    }
    
}
