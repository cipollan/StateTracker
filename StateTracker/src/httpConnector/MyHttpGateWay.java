package httpConnector;

public class MyHttpGateWay {
	
	private String myUrl = "https://myonionlab.altervista.org/php/myserviceprovider.php?TagFunct=CHECKCONNECTION&MachineName=MachineName&UserName=USER_NAME&Pwd=Ciao";
	 
	private String user = "Andre";
	private String password = "Andre";
	
	public MyHttpGateWay() {
		super();
		System.out.println ( " MyHttpGateWay CONSTRUCTOR:" + myUrl); 
	}
	
	public int doCallRest() throws Exception
	{
		int errCode = 200;
		System.out.println ( " BEGIN MyHttpGateWay.doCallRest  "  ); 
		MyHttpClient myHttpClient = new MyHttpClient();
		String token              = InvokeRESTService.getToken(myUrl, user, password) ;
		String jsonInput = "Ciao:Ciao";
		
		String response =  InvokeRESTService.invokeRestApi("GET", myUrl, token,  jsonInput);
		
		System.out.println ( " END MyHttpGateWay.doCallRest token:" + token); 
		return errCode;
	}
	
	public int doCallRest2() throws Exception
	{
		int errCode = 200;
		System.out.println ( " BEGIN MyHttpGateWay.doCallRest2  " ); 
		MyHttpClient myHttpClient = new MyHttpClient();
		myHttpClient.setStubsApiBaseUri(myUrl);
		errCode = myHttpClient.doCallApi();
		System.out.println ( " END MyHttpGateWay.doCallRest2  " ); 
		return errCode;
	}
	
	

}
