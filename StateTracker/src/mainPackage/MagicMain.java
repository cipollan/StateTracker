package mainPackage;
import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.*;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.github.lbovolini.mapper.ObjectMapper;

import httpConnector.MyHttpGateWay;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.*;

import utilPackage.*;

public class MagicMain extends Thread {
	
	/**
	 * @param args
	 * @throws IOException 
	 * 
	 */
	
	
	public static Set<String> listFilesUsingDirectoryStream(String dir) throws IOException 
	{
	    Set<String> fileList = new HashSet<>();
	    try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
	        for (Path path : stream) {
	            if (!Files.isDirectory(path)) 
	            {
	                fileList.add(path.getFileName()
	                    .toString());
	            }
	        }
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    finally
	    {
	    	System.out.println("listFilesUsingDirectoryStream finally " );
	    }
	    
	    
	    return fileList;
	}
	
 
	
	public static Set<String> listFilesUsingDirectoryStreamFilter(String dir, String filter) throws IOException {
	    Set<String> fileList = listFilesUsingDirectoryStream(dir);
	    Set<String> fileList2 =  new HashSet<>();
	    try {
	        for (String fname : fileList) 
	        {
	        	Pattern pattern = Pattern.compile(filter, Pattern.CASE_INSENSITIVE);
	    	    Matcher matcher = pattern.matcher(fname);
	    	    boolean matchFound = matcher.find();
	        	if (matchFound)
	        	{
	        		System.out.println("listFilesUsingDirectoryStreamFilter GROUP:" + matcher.group());
	        		fileList2.add(fname); 
	        	}
	            		 
	        	
	        	if (fname.matches(filter))
	        	{
	        		  System.out.println("listFilesUsingDirectoryStreamFilter fname  MATCHED:" + fname);
	        		  System.out.println("listFilesUsingDirectoryStreamFilter filter MATCHED:" + filter);
	        	}
	        		  
	        }
	    }
	    finally
	    {
	    	System.out.println("listFilesUsingDirectoryStreamFilter finally "  );
	    }
	    
	    return fileList2;
	}
	
	 
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String appConfigPath = rootPath + "config.properties";
		String catalogConfigPath = rootPath + "catalog";
		 
		Properties appProps = new Properties();
		appProps.load(new FileInputStream(appConfigPath));

		Properties catalogProps = new Properties();
		catalogProps.load(new FileInputStream(appConfigPath));
		
		MyHttpGateWay myMyHttpGateWay = new MyHttpGateWay();
		
		
		try {
			int errCode = myMyHttpGateWay.doCallRest();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			int errCode = myMyHttpGateWay.doCallRest2();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//SendEmail snd = new SendEmail();

		String iRepo = catalogProps.getProperty("file.scriptBatchI");
		CmdExecutor cmdExe = new CmdExecutor();
		cmdExe.setMyCmd(iRepo + "/myoCatSortStateTrackerFile.bat");
		cmdExe.run();
		 
		System.out.println ("rootPath			:" + rootPath);
		System.out.println ("catalogConfigPath->:" + catalogConfigPath);
		System.out.println ("appConfigPath		:" + appConfigPath);
		System.out.println ("filestoricoI		:" + catalogProps.getProperty("file.filestoricoI"));
		System.out.println ("file.datiI			:" + catalogProps.getProperty("file.datiI"));
		System.out.println ("filestoricoO		:" + catalogProps.getProperty("file.filestoricoO"));
		System.out.println ("file.datiO			:" + catalogProps.getProperty("file.datiO"));
		System.out.println ("file.repoI			:" + catalogProps.getProperty("file.repoI"));
		System.out.println ("file.repoO			:" + catalogProps.getProperty("file.repoO"));
		
		try
		{
			
			
			
			Set<String> fileList1 = listFilesUsingDirectoryStreamFilter(catalogProps.getProperty("file.repoI"),"StateTracker-(StoricoS)-PROD-([A-Z0-9\\\\.\\\\-])*\\.(txt)");
			Set<String> fileList2 = listFilesUsingDirectoryStreamFilter(catalogProps.getProperty("file.repoI"),"StateTracker-(Storico)-DATI-([A-Z0-9\\\\.\\\\-])*\\.(txt)");
			Set<String> fileList3 = listFilesUsingDirectoryStreamFilter(catalogProps.getProperty("file.repoI"),"StateTracker-(Storico)-PROD-([A-Z0-9\\\\.\\\\-])*\\.(txt)");
			
			String fileIn 	= "";
			String fileOut 	= "";
			
			 
			List<Thread> taskListThread = new ArrayList<>();
			
			Runnable runnableCheck = new ThrMgr(taskListThread);
			
			for (String value : fileList1) 
			{
				System.out.println ( " fileList1:" + value); 
				fileIn = catalogProps.getProperty("file.repoI")+value;
				fileOut = catalogProps.getProperty("file.repoO")+"1-"+value ;
				
				System.out.println ( " TRASPONI1 fileIn :" + fileIn); 
				System.out.println ( " TRASPONI1 fileOut:" + fileOut); 
				
				Runnable runnableTrans = new Transponer(fileIn,fileOut); // or an anonymous class, or lambda...
				Thread thread = new Thread(runnableTrans);
				thread.start();
				taskListThread.add(thread);
			}
			
			 
			
			TrasformaFile tf2 = new TrasformaFile();
			for (String value : fileList2) 
			{
				System.out.println ( " fileList2:" + value); 
				fileIn = catalogProps.getProperty("file.repoI")+value;
				fileOut = catalogProps.getProperty("file.repoO")+"2-"+value ;
				System.out.println ( " TRASPONI2 fileIn :" + fileIn); 
				System.out.println ( " TRASPONI2 fileOut:" + fileOut); 
				tf2.trasponiFile(fileIn, fileOut);
			}
			
			TrasformaFile tf3 = new TrasformaFile();
			for (String value : fileList3) 
			{
				System.out.println ( " fileList3:" + value); 
				fileIn = catalogProps.getProperty("file.repoI")+value;
				fileOut = catalogProps.getProperty("file.repoO")+"3-"+value ;
				System.out.println ( " TRASPONI3 fileIn :" + fileIn); 
				System.out.println ( " TRASPONI3 fileOut:" + fileOut); 
				tf3.trasponiFile(fileIn, fileOut);
			}
			
			
			 Thread thread = new Thread(runnableCheck);
			 thread.start();
			
			
		}
		catch(Exception e)
		{
			System.out.println("Exception:" + e.getMessage().toString());
		}
		finally
        {
			System.out.println("MAIN Finally" );
        } 
		
	}

}
