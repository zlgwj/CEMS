import java.io.IOException;
import java.util.Date;
import java.util.Random;

public class Test {
    public static void main(String[] args) throws IOException {
        Random random = new Random(new Date().getTime());
        int i = random.nextInt() % 6;
        System.out.println(i);
        Random random1 = new Random(new Date().getTime() + i * i);
        int i1 = random1.nextInt() % 6;
        System.out.println(i1);
        Random random2 = new Random(new Date().getTime() + i1 * i1);
        int i2 = random2.nextInt() % 6;
        System.out.println(i2);
    }
}
