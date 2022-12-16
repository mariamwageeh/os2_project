/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sleepingbraberassistantos;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Zeko
 */
public class braberAssistant implements Runnable{
    // Semaphore used to wakeup TA.
    private SignalSemaphore wakeup;

    // Semaphore used to wait in chairs outside office.
    private Semaphore chairs;

    // Mutex lock (binary semaphore) used to determine if TA is available.
    private Semaphore braberAvailable;

    // A reference to the current thread.
    private Thread t;
    private int numberOfbraber;
    private int numberofchairs;
    public braberAssistant(SignalSemaphore w, Semaphore c, Semaphore a,int numberOfbraber,int numberofchairs)
    {
        t = Thread.currentThread();
        wakeup = w;
        chairs = c;
        braberAvailable = a;
        this.numberOfbraber = numberOfbraber;
        this.numberofchairs=numberofchairs;
    }

    
    
    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                System.out.println("No client left.  The bA "+numberOfbraber+ " is going to nap.");
                wakeup.release();
                System.out.println("The bA "+numberOfbraber+ " was awoke by a client.");
                
                t.sleep(5000);
                
                // If there are other students waiting.
                if (chairs.availablePermits() != numberofchairs)
                {
                    do
                    {
                        t.sleep(5000);
                        chairs.release();
                    }
                    while (chairs.availablePermits() != numberofchairs);                   
                }
            }
            catch (InterruptedException e)
            {
                // Something bad happened.
                continue;
            }
        }
    }
}
