import java.time.LocalDateTime;


public class listenThread extends Thread {

    //reference to data object
    private testData data;

    public listenThread(testData data){

        //set reference to data object
        this.data=data;
    }
    //called when .start()
    public void run()
    {

        while(true)
        {
            try {
                //this thread "delay" for 1 second
                sleep(1000);
                LocalDateTime ldt = LocalDateTime.now();
                //print out time
                System.out.println(ldt);

                //increase the data object's i value
                data.increaseI();
            }
            catch (InterruptedException e)
            {
                System.out.println(e);
            }

        }
    }
}
