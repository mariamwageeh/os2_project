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
public class client implements Runnable{
    // Time to program before asking for help (in seconds).
    private int waitToAsk;
    
    // client number.
    private int clientNum;

    // Semaphore used to wakeup TA.
    private SignalSemaphore wakeup;

    // Semaphore used to wait in chairs outside office.
    private Semaphore chairs;

    // Mutex lock (binary semaphore) used to determine if bA is available.
    private Semaphore braberAvailable;

    // A reference to the current thread.
    private Thread t;
    
    
    
    // Non-default constructor.
    public client(int waitToAsk, SignalSemaphore w, Semaphore c, Semaphore a, int clientNum)
    {
        this.waitToAsk = waitToAsk;    
        wakeup = w;
        chairs = c;
        braberAvailable = a;
        this.clientNum = clientNum;
        t = Thread.currentThread();
        
    }

    /**
     * The run method will infinitely loop between programming and
     * asking for help until the thread is interrupted.
     */
    @Override
    public void run()
    {
        // Infinite loop.
        while(true)
        {
            try
            {
               // Program first.
               System.out.println("clientt " + clientNum + " has started programming for " + waitToAsk + " seconds.");
               t.sleep(waitToAsk * 1000);
                
               // Check to see if bA is available first.
               System.out.println("client " + clientNum + " is checking to see if bA is available.");
               if (braberAvailable.tryAcquire())//1->0
               {
                   try
                   {
                       // Wakeup the TA.
                       wakeup.take();
                       System.out.println("client " + clientNum + " has woke up the bA. ");
                       System.out.println("client " + clientNum + " has started working with the bA." );
                       t.sleep(5000);
                       System.out.println("client " + clientNum + " has stopped working with the bA. ");
                   }
                   catch (InterruptedException e)
                   {
                       // Something bad happened.
                       continue;
                   }
                   finally
                   {
                       braberAvailable.release();
                       break;
                   }
               }
               else
               {
                   // Check to see if any chairs are available.
                   System.out.println("client " + clientNum + " could not see the bA.  Checking for available chairs.");
                   if (chairs.tryAcquire())//3->2   2->1    1->0 
                   {
                       try
                       {
                           // Wait for TA to finish with other student.
                           System.out.println("client " + clientNum + " is sitting outside the office.  "
                                   + "He is #" + ((3 - chairs.availablePermits())) + " in line.");
                           braberAvailable.acquire();
                           System.out.println("client " + clientNum + " has started working with the bA. ");
                           t.sleep(5000);
                           System.out.println("client " + clientNum + " has stopped working with the bA. ");
                           braberAvailable.release();
                           break;
                       }
                       catch (InterruptedException e)
                       {
                           // Something bad happened.
                           continue;
                       }
                   }
                   else
                   {
                       System.out.println("client " + clientNum + " could not see the bA and all chairs were taken.  Back to programming!");
                   }
               }
            }
            catch (InterruptedException e)
            {
                break;
            }
        }
    }
}
