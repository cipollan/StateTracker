package utilPackage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.github.lbovolini.mapper.ObjectMapper;
 

public class TrasformaFile {
	
	public TrasformaFile() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	 public  long countLine (String fileName) {

		 System.out.println ("countLine	:" + fileName    );
	      Path path = Paths.get(fileName);
	      System.out.println ("countLine	path :" + path    );

	      long lines = 0;
	      try {

	          // much slower, this task better with sequence access
	          //lines = Files.lines(path).parallel().count();

	    	  BufferedReader reader = new BufferedReader(new FileReader(fileName));
	    	  while (reader.readLine() != null) lines++;
	    	  reader.close();
	    	  

	      } catch (Exception e) {
	    	  System.out.println ("Exception countLine	:" + fileName    );
	          e.printStackTrace();
	      }

	      return lines;

	  }
	 
	 
	public  void trasponiFile(String fileIn, String fileOut) throws IOException
	{
		
		Integer cntOk = 0;
		 
		File file = new File(fileIn);
		File fileNew = new File(fileOut);
		
		int cntLines = 0;
		
		Files.deleteIfExists(new File(fileNew.toString()).toPath());
	  
		if (file.exists())
		{
			
			cntLines  = (int) countLine(fileIn);
		
			System.out.println ("trasponiFile-fileIn	:" + fileIn + " cntLines " + cntLines );
			System.out.println ("trasponiFile-fileOut	:" + fileOut);
		
		 
	    try {// Creating an object of BuffferedReader class
	        	
	        	List<String> listaCodiciDatoS 	= listaCodiciDatoSorted(fileIn);
	        	List<DaggPosVal> 	title 		= buildTitle2(listaCodiciDatoS);  
	        	
	        	BufferedReader br 		= new BufferedReader(new FileReader(file)) ;
	        	BufferedWriter writer 	= new BufferedWriter(new FileWriter(fileNew));
	         
				// Declaring a string variable
				String st;
				
				
				while ((st = br.readLine()) != null)
				{
					String[] parts = st.split(";");
					String ultimo = parts[parts.length-1];
					String ultimo2 = "";
					
					
					if (st.startsWith("ID_PROCESSO"))
					{
						//System.out.println ("TITOLO		:" + st);
						//System.out.println("title.size:" + title.size());
						
						for (DaggPosVal value : title) 
			        	{
							//System.out.println("TITLE getCOD_DATO_AGG:" + value.getCOD_DATO_AGG());
							//System.out.println("TITLE getTitlePos    :" + value.getTitlePos());
							//System.out.println("TITLE getVAL_DATO_AGG:" + value.getVAL_DATO_AGG());
							ultimo2 = ultimo2 + " " + value.getCOD_DATO_AGG_S() + ";";
						}
					}
					else
					{
				         List<DaggPosVal> dtaggPop  = popolaDatiAggSJJ(title, ultimo);
						 ultimo2 = "";
						 
						 for (DaggPosVal value : dtaggPop) 
						 {
							ultimo2 = ultimo2 + value.getVAL_DATO_AGG() + ";";
						 }
						 
						 dtaggPop.clear();
					}
					
					parts[parts.length-1] = ultimo2;
			

					String textLine = String.join(";", parts); // join it back to the text
					//System.out.println("textLine:" + textLine);	 
					
					
					writer.write(textLine +'\n');	
					cntOk++;
					
	        		}
				
					br.close();
					writer.close();
				
			}
	        finally
	        {
	        	int cntLinesOut = (int) countLine(fileOut);
				System.out.println 	(" finally-trasponiFile-fileIn	:" + fileIn 	+ " cntLines " + cntLines);
				System.out.println 	(" finally-trasponiFile-fileOut	:" + fileOut 	+ " cntLinesOut " + cntLinesOut);
	        } 
		}
		 
		
	}
	
	public List<String> listaCodiciDatoSorted(String filename) throws IOException 
	{
		//System.out.println ( " listaCodiciDatoSorted (Begin) " );
		
		List<String> listCodiciDato = new ArrayList<String>();
		HashSet<String> uniqueValues = listaCodiciDato(filename);
		
		for (String value : uniqueValues) 
		{
			listCodiciDato.add(value);
		}
		
		Collections.sort(listCodiciDato);
		
		Integer ii = 0;
		for (String value : listCodiciDato) 
		{
			//System.out.println (ii + " SORTEDVALUE:" + value);
			ii++;
		}
		
		//System.out.println (ii + " listaCodiciDatoSorted< " + ii);
		return listCodiciDato;		
	}
	

	private   List<DaggPosVal> popolaDatiAggS(List<DaggPosVal> dagg, 
													String line, 
													String splitChar)  
	{
		List<DaggPosVal> titleAndPos = new ArrayList<DaggPosVal>();
		Integer cnt 					= 0;
		Integer jjjj 					= 0;
		String[] parts = line.split(splitChar);
		
		//System.out.println("popolaDatiAgg-value-line:" + parts.length); 
		//System.out.println("popolaDatiAgg-value-line:" + line); 
		//System.out.println("INIZIO popolaDatiAgg-dagg.size:" + dagg.size()); 
		
		 
		for (DaggPosVal value : dagg) 
		{
			DaggPosVal dpv = new DaggPosVal(value);
			for (int jj = 0;jj<parts.length;jj++)
			{
				String sst = parts[jj];
				String[] codDato = sst.split(":");
					
				if (value.getCOD_DATO_AGG().length()>0)
				{
					dpv.setCOD_DATO_AGG(value.getCOD_DATO_AGG());
					dpv.setTitlePos(value.getTitlePos());	 
					if (value.COD_DATO_AGG.equalsIgnoreCase(codDato[0]))
					{
						//System.out.println("---------------------------------------------------" );
						//System.out.println("popolaDatiAgg-value-line value.getCOD_DATO_AGG:" + value.getCOD_DATO_AGG());
						//System.out.println("popolaDatiAgg-value-line codDato[0]           :" + codDato[0]);
							 
						if (codDato.length>1)
						{
							dpv.setVAL_DATO_AGG(codDato[1]);
							//System.out.println("popolaDatiAgg-value-line codDato[1]          :" + codDato[1]);
								
							//System.out.println("---------------------------------------------------" );
							//System.out.println("popolaDatiAgg-value-line dpv.getCOD_DATO_AGG:" + dpv.getCOD_DATO_AGG());
							//System.out.println("popolaDatiAgg-value-line dpv.setTitlePos	:" + dpv.getTitlePos());
							//System.out.println("popolaDatiAgg-value-line dpv.getCOD_DATO_AGG:>" + dpv.getVAL_DATO_AGG() + "<");
						
						}
						else
						{
							dpv.setVAL_DATO_AGG("");
						}
						 
					} // end if
				} // end if
			} // end-for
			cnt++;
			titleAndPos.add(dpv);
			
		}// end-for
		
		System.out.println("FINE-popolaDatiAgg-value-line titleAndPos.size:" + titleAndPos.size()); 
		System.out.println("FINE-popolaDatiAgg-value-line cnt             :" + cnt); 	
		return titleAndPos;
	}
	
	

	private static HashSet<DaggPosVal> buildTitle(HashSet<String> titlist)  
	{
		HashSet<DaggPosVal> titleAndPos = new HashSet<DaggPosVal>();
		 
		Integer iPos = 0;
		for (String value : titlist) 
		{
			DaggPosVal tt = new DaggPosVal(value,iPos);
			//System.out.println ("buildTitle-DaggPosVal.tt.getCOD_DATO_AGG:" + tt.getCOD_DATO_AGG());
			//System.out.println ("buildTitle-DaggPosVal.tt.getVAL_DATO_AGG:" + tt.getVAL_DATO_AGG());
			//System.out.println ("buildTitle-DaggPosValtt.getTitlePos	:" + tt.getTitlePos());
			titleAndPos.add(tt);
			iPos++;
		}
		
		return titleAndPos;
	}
	
	private static List<DaggPosVal> buildTitle2(List<String> titlist)  
	{
		 
		List<DaggPosVal> titleAndPosList = new  ArrayList<DaggPosVal>();
		Integer iPos = 0;
		
		for (String value : titlist) 
		{
			DaggPosVal tt = new DaggPosVal(value,iPos);
			
			titleAndPosList.add(tt);
			iPos++;
		}
		
		
		Collections.sort(titleAndPosList, Comparator.comparingInt(DaggPosVal::getTitlePos));
		
		return titleAndPosList;
	}
	
	private static HashSet<String> listaCodiciDato(String filename) throws IOException 
	{
		HashSet<String> uniqueValues = null;
		List<String> listCodiciDato = new ArrayList<String>();
		File file = new File(filename);
		List<DaggPosVal> ll = null;
		
		try {// Creating an object of BuffferedReader class
        	BufferedReader br = new BufferedReader(new FileReader(file)) ;	 
			// Declaring a string variable
			String st;
			
			while ((st = br.readLine()) != null)
			{
				if (!st.startsWith("ID_PROCESSO"))
				{
					String[] parts 	= st.split(";");
					String ultimo 	= parts[parts.length-1];
					ll = estrayDatiAggArrSJ(ultimo);  
					for (DaggPosVal dd : ll)
					{
						//listCodiciDato.add(dd.getCOD_DATO_AGG());
						listCodiciDato.add(dd.getCOD_DATO_AGG_S());
					}
					ll.clear();
					
				}
        	}
			
			br.close();
		}
		finally
		{
			Collections.sort(listCodiciDato);
			uniqueValues = new HashSet<String>(listCodiciDato);
			System.out.println("listCodiciDato.uniqueValues.size:" + uniqueValues.size());
		}
		
		return uniqueValues;		
	}
	
	
	private static List<DaggPosVal> popolaDatiAggSJJ(List<DaggPosVal> dagg, 
			String line)  
{
		List<DaggPosVal> titleAndPos = new ArrayList<DaggPosVal>();
		List<DaggPosVal> listDatiAgg = estrayDatiAggArrSJ(line);

		for (DaggPosVal value : dagg) 
		{
			if (value.getCOD_DATO_AGG_S().length()>0)
			{
				DaggPosVal dpv = new DaggPosVal(value);
				for (DaggPosVal valueInner : listDatiAgg)
				{
					if (value.getCOD_DATO_AGG_S().equalsIgnoreCase(valueInner.getCOD_DATO_AGG_S()))
					{
						if (valueInner.getVAL_DATO_AGG()  != null)
						{
							if (dpv.getVAL_DATO_AGG().length() > 0)
								dpv.setVAL_DATO_AGG(dpv.getVAL_DATO_AGG() + "-" + valueInner.getVAL_DATO_AGG());
							else
								dpv.setVAL_DATO_AGG(valueInner.getVAL_DATO_AGG());
						}


						if (valueInner.getCOD_DATO_AGG_SL() != null)
						{
							for (String str : valueInner.getCOD_DATO_AGG_SL())
							{
								dpv.COD_DATO_AGG_SL.add(str);
							}

						}
					}
				}
				titleAndPos.add(dpv);
			}
		}

		//System.out.println("FINE-popolaDatiAgg-value-line titleAndPos.size:" + titleAndPos.size()); 
		return titleAndPos;
}



private static List<DaggPosVal> estrayDatiAggArrSJ(String line)  
{
	List<DaggPosVal> listDatiAgg = new ArrayList<DaggPosVal>();

	try 
	{
		//System.out.println("estrayDatiAggArrSJ-line:" + line); 
		JSONObject myjson = new JSONObject(line);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();



		JSONArray nameArray = myjson.names();
		JSONArray valArray 	= myjson.toJSONArray(nameArray);
		if (valArray != null)
		{
			for(int i=0;i<valArray.length();i++)
			{
				DaggPosVal d = new DaggPosVal(nameArray.getString(i),i);
				d.setCOD_DATO_AGG(nameArray.getString(i));
				d.setVAL_DATO_AGG(valArray.getString(i));
				d.setTitlePos(i);
				listDatiAgg.add(d);
			} 
		}

	} catch (JSONException e) {
		e.printStackTrace();
		System.out.println ("exception " + e.toString());
		System.out.println ("exception e.getCause():" + e.getCause());
		System.out.println ("exception " + line);
	}
 

	return listDatiAgg;
}

	

}
