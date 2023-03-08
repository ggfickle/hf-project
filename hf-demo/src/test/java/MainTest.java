import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/8 22:02
 */
@SpringBootTest
public class MainTest {
    public static void main(String[] args) {
        A a = new B();
        a.a();
    }
}

class A{
    void a() {
        b();
    };
    void b() {
        System.out.println("a.b");
    }
}

class B extends A{
    void b() {
        System.out.println("B.b");
    }
}