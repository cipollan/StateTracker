package httpConnector;

public class MyHttpGateWay {
	
	private String myUrl = "http://iesofficinameccanica.altervista.org/php/myserviceprovider.php";
	 
	private String user = "Andre";
	private String password = "Andre";
	
	
	
	public MyHttpGateWay() {
		super();
		System.out.println ( " MyHttpGateWay CONSTRUCTOR:" + myUrl); 
	}
	
	public int doCallRest() throws Exception
	{
		int errCode = 200;
		MyHttpClient myHttpClient = new MyHttpClient();
		String token              = InvokeRESTService.getToken(myUrl, user, password) ;
		String jsonInput = "Ciao:Ciao";
		
		String response =  InvokeRESTService.invokeRestApi("GET", myUrl, token,  jsonInput);
		
		System.out.println ( " MyHttpGateWay.doCallRest token:" + token); 
		return errCode;
	}
	
	

}
