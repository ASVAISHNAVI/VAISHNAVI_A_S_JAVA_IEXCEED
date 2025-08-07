
class University{
    String Universityname="zxc";
   
    
    class Department{
        String deptName;
        Department(String deptName){
            this.deptName=deptName;

        }
    
        class teachers{
            String teachername;
            teachers(String teachername){
                this.teachername=teachername;

            }
            public void display(){
                System.out.println("teacher of cse");

            }
        }}
}
public class Nestedinner{
    public static void main(String[] args) {
        University u1=new University();
        University.Department d1=u1.new Department("cse");

        University.Department.teachers t1=d1.new teachers("raj");
        t1.display();
     
    }
}






























