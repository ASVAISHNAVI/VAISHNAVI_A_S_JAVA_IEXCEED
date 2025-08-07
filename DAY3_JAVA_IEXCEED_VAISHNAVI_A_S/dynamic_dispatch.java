
class Shape {
    void draw() {
        System.out.println("Drawing a shape");
    }
}

class Circle extends Shape {
    @Override
    void draw() {
        System.out.println("Drawing a circle");
    }
}

class Square extends Shape {
    @Override
    void draw() {
        System.out.println("Drawing a square");
    }
}

public class dynamic_dispatch {
    public static void main(String[] args) {
        Shape ref;           // Superclass reference

        Circle c = new Circle();
        Square s = new Square();

        ref = c;             // ref refers to Circle object
        ref.draw();         

        ref = s;             // ref now refers to Square object
        ref.draw();     
    }
}
