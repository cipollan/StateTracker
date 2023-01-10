package httpConnector;

import java.io.*;
import java.net.*;
import javax.xml.xpath.*;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.*;
import org.apache.http.entity.*;
import org.apache.http.impl.client.*;
import org.apache.http.util.*;
import org.xml.sax.*;

public class MyHttpClient {
	
	public int doCallApi() 
	{
		int errCode = 200;
	    String stubsApiBaseUri = "http://localhost:7819/RTCP/rest/stubs/";
        String domain = "default";
        String environment = "addNumbers";
        String stubName = "1+1=2";

      
        HttpClient client = HttpClients.createDefault();

        URIBuilder builder;
        
		try {
			builder = new URIBuilder(stubsApiBaseUri);
			builder.addParameter("domain", domain);
	        builder.addParameter("env", environment);
	        builder.addParameter("stub", stubName);
	        String listStubsUri = builder.build().toString();
	        HttpGet getStubMethod = new HttpGet(listStubsUri);
	        HttpResponse getStubResponse = client.execute(getStubMethod);
	        errCode = getStubResponse.getStatusLine()
	              .getStatusCode();
	        if (errCode < 200 || errCode >= 300) {
	           // Handle non-2xx status code
	           return errCode;
	        }
	        String responseBody = EntityUtils
	              .toString(getStubResponse.getEntity());
	        // Assuming only one stub matches
	        String stubRelativeUri = XPathFactory
	              .newInstance()
	              .newXPath()
	              .evaluate("/stubs/stub/@href",
	                    new InputSource(new StringReader(responseBody)));
	        String stubAbsoluteUri = new URI(stubsApiBaseUri).resolve(
	              stubRelativeUri).toString();

	        HttpPost startStubMethod = new HttpPost(stubAbsoluteUri);
	        ContentType contentType = ContentType.APPLICATION_XML
	              .withCharset("utf-8");
	        String data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
	              + "<start-stub />";
	        startStubMethod.setEntity(new ByteArrayEntity(data
	              .getBytes(contentType.getCharset()), contentType));
	        HttpResponse startStubResponse = client.execute(startStubMethod);
	        errCode = startStubResponse.getStatusLine()
	              .getStatusCode();
	        if (-errCode < 200 || errCode >= 300) {
	           // Handle non-2xx status code
	           return errCode;
	        }
	        // If you want to check the status of the stub that is starting, you
	        // can use the response data to get the stub instance URI and poll it
	        // for updates
	        System.out.println(errCode);
	        String startStubResponseBody = EntityUtils.toString(startStubResponse
	              .getEntity());
	        System.out.println(startStubResponseBody);
			
			
		} catch (URISyntaxException | IOException | XPathExpressionException e) {
			// TODO Auto-generated catch block
			errCode = -1;
			e.printStackTrace();
		}
        

       
		return errCode;
	}
	

}
