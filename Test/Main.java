import java.util.Scanner;

public class Main {

    public static void main(String args[])
    {
        //create data object
        testData data=new testData();
        //set it to 0
        data.setI(0);

        //create thread object
        listenThread lt= new listenThread(data);

        //run thread
        lt.start();

        while(true)
        {
            try {
                //current application "delay" for 2 sec
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                System.out.println(e);
            }
            //print out data object's i value
            System.out.println("i is " + data.getI());

        }

    }
}
