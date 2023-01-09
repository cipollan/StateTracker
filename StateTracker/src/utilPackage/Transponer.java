package utilPackage;
import java.io.IOException;

public class Transponer implements Runnable{
	
	public Transponer(String fileIn, String fileOut) {
		super();
		this.fileIn = fileIn;
		this.fileOut = fileOut;
	}

	String fileIn ;
	String fileOut ;
	Boolean flIsRunning;

	public Transponer() {
		super();
		// TODO Auto-generated constructor stub
		fileIn = "";
		fileOut = "";
		
	}

	@Override
	public void run() {
		String name = Thread.currentThread().getName();
        System.out.println(name+" started : " + this.getFileIn());
		try {
			
			System.out.println(name+" started dentro try " + fileIn);
			System.out.println(name+" started dentro try " + fileOut);
			setFlIsRunning(true);
			TrasformaFile tr = new TrasformaFile();
			tr.trasponiFile(fileIn, fileOut);
        } 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			System.out.println(name + " finally " + fileOut);
			setFlIsRunning(false);
		}
	}

	 synchronized public Boolean getFlIsRunning() {
		return flIsRunning;
	}

	 synchronized public void setFlIsRunning(Boolean flIsRunning) {
		this.flIsRunning = flIsRunning;
	}

	public synchronized String getFileIn() {
		return fileIn;
	}

	public synchronized void setFileIn(String fileIn) {
		this.fileIn = fileIn;
	}

	public synchronized String getFileOut() {
		return fileOut;
	}

	public synchronized void setFileOut(String fileOut) {
		this.fileOut = fileOut;
	}
	

}
