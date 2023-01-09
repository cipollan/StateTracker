package utilPackage;
 


import static java.util.concurrent.TimeUnit.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
public class ScheduledService {
	
   private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

   public void beepForAnHour() 
   {
	   System.out.println ( " beepForAnHour "  ); 
       final Runnable beeper = new Runnable() {
    	   public void run() { 
    		   
    		   Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    		   Date date = new Date();
    		   Timestamp timestamp2 = new Timestamp(date.getTime());
    		   
    		   System.out.println("beeper beep run di runnable " + timestamp.toString()); 
    		   System.out.println("beeper beep run di runnable " + timestamp2.toString()); 
    	   
    	   } };
       final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 10, 40, SECONDS);
       scheduler.schedule(new Runnable() { public void run() { beeperHandle.cancel(true); } }, 60 * 60, SECONDS);
   }
}
