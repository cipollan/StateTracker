package utilPackage;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

 

public class DaggPosVal {
	
	public DaggPosVal(String cOD_DATO_AGG, String vAL_DATO_AGG, Integer titlePos) {
		super();
		COD_DATO_AGG_SL = new ArrayList<String>();
		COD_DATO_AGG = cOD_DATO_AGG;
		VAL_DATO_AGG = vAL_DATO_AGG;
		this.titlePos = titlePos;
		this.COD_DATO_AGG_S = this.simpliFy(COD_DATO_AGG);
		this.COD_DATO_AGG_SL.add(this.getCOD_DATO_AGG_S());
	}
	public DaggPosVal(String cOD_DATO_AGG, Integer titlePos) {
		super();
		COD_DATO_AGG_SL = new ArrayList<String>();
		COD_DATO_AGG 	= cOD_DATO_AGG;
		this.titlePos 	= titlePos;
		VAL_DATO_AGG 	= "";
		this.COD_DATO_AGG_S = this.simpliFy(COD_DATO_AGG);
		this.COD_DATO_AGG_SL.add(this.getCOD_DATO_AGG_S());
	}
	
	public DaggPosVal(DaggPosVal value) {
		 COD_DATO_AGG_SL = new ArrayList<String>();
		 COD_DATO_AGG 		= value.COD_DATO_AGG;
		 VAL_DATO_AGG 		= value.VAL_DATO_AGG;
		 titlePos     		= value.titlePos;
		 this.COD_DATO_AGG_S = this.simpliFy(COD_DATO_AGG);
		 this.COD_DATO_AGG_SL.add(this.getCOD_DATO_AGG_S());
		 
	}
	
	public DaggPosVal() {
		 COD_DATO_AGG_SL = new ArrayList<String>(); 
	}
	
	String 		COD_DATO_AGG;
	String 		COD_DATO_AGG_S;
	List<String> COD_DATO_AGG_SL;
	String VAL_DATO_AGG;
	Integer titlePos;
	
	
	public String simpliFy(String in) 
	{
		
		String strOut = in;
		Pattern pattern = Pattern.compile("([a-zA-Z])*LR_([0-9])*$", Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(in);
	    boolean matchFound = matcher.find();
	    Pattern pattern2  ;
	    Matcher matcher2  ;
	    String strM2 = "";
	    if(matchFound) {
	    	 
	      //System.out.println("Match found in:" + in );
	       pattern2 = Pattern.compile("_([0-9])*$", Pattern.CASE_INSENSITIVE);
	       matcher2 = pattern2.matcher(in);
	       matchFound 		= matcher2.find();
	      
	      if (matchFound) 
	      {
	    	 strM2 = matcher2.group(0);
	    	//System.out.println("Match strM2: " + strM2);
	    	strOut = in.replaceAll(strM2, "");
	      }
	      
	      //System.out.println("Match: " + matcher.group(0));
	      //System.out.println("Start: " + matcher.start());
	      //System.out.println("End  : " + matcher.end());
	      //System.out.println("Match found dopo in :" + in );
	      //System.out.println("Match found dopo out:" + strOut );
	    } 
	    else
	    {
	    	 pattern = Pattern.compile("^([0-9])*_PDF$", Pattern.CASE_INSENSITIVE);
		     matcher  = pattern.matcher(in);
		     matchFound = matcher .find();
		     if (matchFound) 
		     {
		    	  pattern2 = Pattern.compile("([0-9])*_", Pattern.CASE_INSENSITIVE);
			      matcher2 = pattern2.matcher(in);
			      matchFound 		= matcher2.find();
			       
		    	  strM2 = matcher2.group(0);
		    	 //System.out.println("Match strM2: " + strM2);
		    	 if (matchFound)
		    	   strOut = in.replaceAll(strM2, "");
		     }
		     else
		     {
		    	 pattern = Pattern.compile("^([0-9])*_PDF_KoAntifrode$", Pattern.CASE_INSENSITIVE);
		    	 matcher  = pattern.matcher(in);
		    	 matchFound = matcher .find();
		    	 if (matchFound) 
		    	 {
		    		  pattern2 = Pattern.compile("([0-9])*_", Pattern.CASE_INSENSITIVE);
				      matcher2 = pattern2.matcher(in);
				      matchFound 		= matcher2.find();
				       
			    	  strM2 = matcher2.group(0);
			    	 //System.out.println("Match strM2: " + strM2);
			    	 if (matchFound)
			    	   strOut = in.replaceAll(strM2, "");
		    		 
		    	 }
		    	 else
		    	 {
		    		 pattern = Pattern.compile("^([0-9])*_PSD2$", Pattern.CASE_INSENSITIVE);
			    	 matcher  = pattern.matcher(in);
			    	 matchFound = matcher .find();
			    	 if (matchFound) 
			    	 {
			    		  pattern2 = Pattern.compile("([0-9])*_", Pattern.CASE_INSENSITIVE);
					      matcher2 = pattern2.matcher(in);
					      matchFound 		= matcher2.find();
					       
				    	  strM2 = matcher2.group(0);
				    	 //System.out.println("Match strM2: " + strM2);
				    	 if (matchFound)
				    	   strOut = in.replaceAll(strM2, "");
			    		 
			    	 } 
		    	 } 
		     }
	    }
	    	
	     
		return strOut;
	}
	
	
	public String getCOD_DATO_AGG() 
	{
		return COD_DATO_AGG;
	}
	
	public void setCOD_DATO_AGG(String cOD_DATO_AGG) {
		COD_DATO_AGG = cOD_DATO_AGG;
		simpliFy(cOD_DATO_AGG);
		
	}
	public Integer getTitlePos() {
		return titlePos;
	}
	public void setTitlePos(Integer titlePos) {
		this.titlePos = titlePos;
	}
	public String getVAL_DATO_AGG() {
		return VAL_DATO_AGG;
	}
	public void setVAL_DATO_AGG(String vAL_DATO_AGG) {
		VAL_DATO_AGG = vAL_DATO_AGG;
	}
	public String getCOD_DATO_AGG_S() {
		return COD_DATO_AGG_S;
	}
	public void setCOD_DATO_AGG_S(String cOD_DATO_AGG_S) {
		COD_DATO_AGG_S = cOD_DATO_AGG_S;
	}
	
	public List<String> getCOD_DATO_AGG_SL() {
		return COD_DATO_AGG_SL;
	}
	public void setCOD_DATO_AGG_SL(List<String> cOD_DATO_AGG_SL) {
		COD_DATO_AGG_SL = cOD_DATO_AGG_SL;
	}
	

}
