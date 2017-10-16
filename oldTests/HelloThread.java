import java.util.Random;
import java.util.stream.IntStream;

public class HelloThread extends Thread {

    public void run() {
        IntStream intStream = new Random().ints(1, 0, 5);

        for(int i = 0; i < 5; i++){
            System.out.println("Hello from a thread: " + intStream);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        (new HelloThread()).start();
        (new HelloThread()).start();
        (new HelloThread()).start();
    }
}
