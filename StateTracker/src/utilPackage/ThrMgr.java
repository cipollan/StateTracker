package utilPackage;
import java.security.Timestamp;
import java.util.List;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;


public class ThrMgr implements Runnable {
	
	public ThrMgr() {
		super();
		// TODO Auto-generated constructor stub
		setFlAllCompleted(false);
		iNumOfActive = getTaskListActiveThread();
		
	}

	List<Thread> taskListThread;
	Boolean flAllCompleted;
	int iNumOfActive;

	public ThrMgr(List<Thread> taskListThread) {
		super();
		this.taskListThread = taskListThread;
		setFlAllCompleted(false);
		iNumOfActive = getTaskListActiveThread();
		
	}

	synchronized public List<Thread> getTaskListThread() {
		return taskListThread;
	}
	
	synchronized public int getTaskListActiveThread() {
		return taskListThread.size();
	}
	
	synchronized public Boolean getTaskIsAlive(Thread thr) {
		return thr.isAlive();
	}
	
	
	synchronized public void updTaskListThread(Thread thr) {
		taskListThread.remove(thr);
		
	}
	
	synchronized public void updActiveThreadCnt()  {
		
		for (Thread thr : taskListThread) 
		{
			if (getTaskIsAlive(thr))
			{
				setiNumOfActive(iNumOfActive++);
			}
			else
			{
				setiNumOfActive(iNumOfActive--);
			}
				
		}
	}
	
	
	synchronized public void updActiveThreadFl()  {
		
		setFlAllCompleted(true);
		
		for (Thread thr : taskListThread) 
		{
			if (getTaskIsAlive(thr))
			{
				setFlAllCompleted(false);
			}
		}
	}

	

	synchronized public void setTaskListThread(List<Thread> taskListThread) {
		this.taskListThread = taskListThread;
	}

	@Override
	public void run() {
		 // Check Until all threads are completed
		try
		{
			
			setiNumOfActive();
			System.out.println( " ----------------------------------------------------------------------------------------- " );
			System.out.println( " ThrMgr Run getFlAllCompleted " + getFlAllCompleted() + " getiNumOfActive:" + getiNumOfActive() );
			System.out.println( " ----------------------------------------------------------------------------------------- " ); 
		
			while(!getFlAllCompleted())
			{
			  Date date = new Date();
			  Long datetime = System.currentTimeMillis();
			  SimpleDateFormat date1 = new SimpleDateFormat("yyyy.MM.dd.HH:mm:ss");
		      String timeStamp = date1.format(new Date());
		      System.out.println( " +--- Check For All Completed " + getiNumOfActive()  + " timeStamp: " + timeStamp);
			
		      updActiveThreadFl();
		      updActiveThreadCnt();
		      
		      for (Thread thr : taskListThread) 
		      {
		    	  	System.out.println("ThrMgr.run:" + thr.getName() + " -> isAlive : " + thr.isAlive() + " NumOfActive:" + getiNumOfActive() + " AllCompleted:" + getFlAllCompleted() );	
					 
		      }
		      
		      System.out.println( " +----------------------------------------------------------+");
				
		      Thread.sleep(99000);
				  
			}// end while
		}catch (Exception e) {e.printStackTrace();}
		finally {
			System.out.println( " -------------------------------------------------------------------------------->" + " getiNumOfActive:" + getiNumOfActive() );
			System.out.println( " finally Check For All Completed " + " getiNumOfActive:" + getiNumOfActive() );
			System.out.println( " -------------------------------------------------------------------------------->" + " getiNumOfActive:" + getiNumOfActive() );
		}	
	}

	synchronized public Boolean getFlAllCompleted() {
		return flAllCompleted;
	}

	synchronized public void setFlAllCompleted(Boolean flAllCompleted) {
		this.flAllCompleted = flAllCompleted;
	}

	synchronized public int getiNumOfActive() 
	{
		return iNumOfActive;
	}

	synchronized public void setiNumOfActive() 
	{
		this.iNumOfActive = taskListThread.size();
	}
	
	synchronized public void setiNumOfActive(int iNumOfAcrive) 
	{
		this.iNumOfActive = iNumOfAcrive;
	}

}
