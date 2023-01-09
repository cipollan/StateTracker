package utilPackage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class CmdExecutor {
	
	String myCmd;

	public String getMyCmd() {
		return myCmd;
	}

	public void setMyCmd(String myCmd) {
		this.myCmd = myCmd;
	}
	
	public void show() {
		System.out.println("utilPackage.CmdExecutor.run  <" + myCmd);
	}

	public CmdExecutor(String myCmd) {
		super();
		this.myCmd = myCmd;
	}
	
	public CmdExecutor() {
		 
		super();
		System.out.println("utilPackage.CmdExecutor ");
		
	}

	public void run() {
		try {
			System.out.println("utilPackage.CmdExecutor.run Begin <" + myCmd);
			Process p = Runtime.getRuntime().exec(myCmd);
			System.out.println("CmdExecutor.run End > ");
			String ss = null;
			BufferedWriter writeer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
	        writeer.write("dir");
	        writeer.flush();
	        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
	        System.out.println("Here is the standard output of the command:\n");
	        while ((ss = stdInput.readLine()) != null) 
	        {
	            System.out.println(ss);
	        }
	        System.out.println("Here is the standard error of the command (if any):\n");
	        while ((ss = stdError.readLine()) != null) {
	            System.out.println(ss);
	        }
	        
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			System.out.println("\n CmdExecutor Finally \n");
		}
		
		
	}
 
	

}
