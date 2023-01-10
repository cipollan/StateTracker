package httpConnector;

 
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.DatatypeConverter;

public class InvokeRESTService {
	Properties property;

	public static String getToken(String url, 
			String user, 
			String password) throws Exception {

		String response = "";
		try {
			System.out.println("BEGIN getToken urlHttps=" + url);
			java.net.HttpURLConnection conn = getConnectionHttps(url);
			conn.setRequestMethod("POST");
			String credentials = user + ":" + password;
			byte[] message = "hello world".getBytes("UTF-8");
			String encoded = DatatypeConverter.printBase64Binary(message);
			System.out.println("getToken encoded=" + encoded);
			
			String encoding3 = DatatypeConverter.printBase64Binary(credentials.getBytes());
			encoding3 = encoding3.replaceAll("\n", "");

			System.out.println("getToken encoding3=" + encoding3);
			
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "Basic " + encoding3);

			conn.setDoOutput(true);
			
			
			String str = "" + "{ \"grant_type\": \"client_credentials\" }" + "";
			
			
			System.out.println("AX-getToken  ");
			java.io.OutputStream out = conn.getOutputStream();
			System.out.println("BX-getToken  ");
			java.io.Writer wr = new java.io.OutputStreamWriter(out);
			System.out.println("CX-getToken  ");
			wr.write(str);
			System.out.println("DX-getToken  ");
			wr.close();
			System.out.println("EX-getToken  ");
			System.out.println("X-getToken  ");
			java.io.InputStream  in = conn.getInputStream();
			System.out.println("X1-getToken  ");
			java.io.Reader       reader = new java.io.InputStreamReader(in);
			System.out.println("X2-getToken  ");
			java.io.StringWriter sw = new java.io.StringWriter();
			System.out.println("X3-getToken  ");
			char[] buf = new char[500];
			int bread = 0;

			while ((bread = reader.read(buf)) != -1) 
			{
				System.out.println("XX-getToken  bread:" + bread);
				sw.write(buf, 0, bread);
				response = response + new String(buf);
				System.out.println("XX-getToken  response:" + response);
			}

			reader.close();

			int inx = response.indexOf("201");
			if (response.indexOf("201") > 0) {
			} else {
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Exception ex=" + ex);
			ex.printStackTrace(System.out);
		}
		System.out.println("EnD getToken response=" + response);
		return response;
	}

	public static String invokeRestApi(String method, String url, String token, String jsonInput) throws Exception {
		String response = "";

		System.out.println("invokeRestApi "  );
		
		try {
			
			System.out.println("invokeRestApi "  );
			java.net.HttpURLConnection conn = getConnectionHttps(url);
			conn.setRequestMethod(method);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "Bearer " + token);
			conn.setDoOutput(true);
			java.io.OutputStream out = conn.getOutputStream();
			java.io.Writer wr = new java.io.OutputStreamWriter(out);
			System.out.println("----3Setting input xml3--");
			if (jsonInput != null) {
				wr.write(jsonInput);
			}

			wr.close();
			System.out.println("----444--");
			java.io.InputStream in = conn.getInputStream();
			java.io.Reader reader = new java.io.InputStreamReader(in);
			java.io.StringWriter sw = new java.io.StringWriter();
			char[] buf = new char[500];
			int bread = 0;
			System.out.println("----About To Read Responset--");
			while ((bread = reader.read(buf)) != -1) {
				sw.write(buf, 0, bread);
				System.out.println("5->Read:" + buf);
				response = response + new String(buf);
			}
			System.out.println("----before closing reader--");
			reader.close();
			System.out.println("RESp**:=" + response);
			int inx = response.indexOf("201");
			// ---call--//
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("ex=" + ex);
			ex.printStackTrace(System.out);
		}
		return response;
	}

	public static String invokeRESTApiByGet(String url, String token, String jsonInput1) throws Exception {

		String response = "";
		url = url + jsonInput1;
		try {
			System.out.println("urlHttps=" + url);
			java.net.HttpURLConnection conn = getConnectionHttps(url);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "Bearer " + token);
			conn.setDoOutput(true);
			int code = conn.getResponseCode();
			System.out.println("code=" + code);
			java.io.InputStream in = conn.getInputStream();
			java.io.Reader reader = new java.io.InputStreamReader(in);
			java.io.StringWriter sw = new java.io.StringWriter();
			int bread = 0;
			while ((bread = reader.read()) != -1) {
				char ch = (char) bread;
				response = response + ch;

			}

			reader.close();
			System.out.println("RESp**:=" + response);
			int inx = response.indexOf("201");

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("ex=" + ex);
			ex.printStackTrace(System.out);
		}
		return response;
	}

	public static String invokeRestApiByPost(String url, String token, String jsonInput, List storeResponse)
			throws Exception {

		String response = "";

		try {
			System.out.println("urlHttps=" + url);

			java.net.HttpURLConnection conn = getConnectionHttps(url);
			conn.setRequestMethod("POST");

			String credentials = token;

			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "Bearer " + token);

			conn.setDoOutput(true);
			java.io.OutputStream out = conn.getOutputStream();
			java.io.Writer wr = new java.io.OutputStreamWriter(out);

			if (jsonInput != null) {
				wr.write(jsonInput);
			}

			wr.close();
			int code = conn.getResponseCode();

			java.io.InputStream in = conn.getInputStream();
			java.io.Reader reader = new java.io.InputStreamReader(in);
			int bread = 0;
			while ((bread = reader.read()) != -1) {
				char ch = (char) bread;
				response = response + ch;
			}

			reader.close();

			System.out.println("RESpcode**:=" + code);
			storeResponse.add(response);
			response = " {\"ResponseCode\": " + code + "}";

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("ex=" + ex);
			ex.printStackTrace(System.out);
			storeResponse.add("" + ex);
		}
		System.out.println("response:" + response);
		return response;
	}

	public static String invokeRestApiByPUT(String url, String token, String jsonInput) throws Exception {
		String response = "";
		try {
			System.out.println("urlHttps=" + url);
			java.net.HttpURLConnection conn = getConnectionHttps(url);
			conn.setRequestMethod("PUT");

			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "Bearer " + token);
			conn.setDoOutput(true);
			java.io.OutputStream out = conn.getOutputStream();
			java.io.Writer wr = new java.io.OutputStreamWriter(out);

			if (jsonInput != null) {
				wr.write(jsonInput);
			}

			wr.close();
			int code = conn.getResponseCode();
			java.io.InputStream in = conn.getInputStream();
			java.io.Reader reader = new java.io.InputStreamReader(in);
			java.io.StringWriter sw = new java.io.StringWriter();
			char[] buf = new char[500];
			int bread = 0;

			while ((bread = reader.read(buf)) != -1) {
				sw.write(buf, 0, bread);
				response = response + new String(buf);
			}

			reader.close();
			int inx = response.indexOf("201");
			System.out.println("inx=" + inx + response);
			if (code == 202) {
				response = " {\"ResponseCode\": 202}";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			ex.printStackTrace(System.out);
		}
		return response;
	}

	public static HttpURLConnection getConnectionHttps(String urlHttps) throws Exception {
		System.out.println("urlHttps=" + urlHttps);
		TrustManager[] trustCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };

		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		URL url = new URL(urlHttps);
		HttpURLConnection con = (java.net.HttpURLConnection) url.openConnection();
		return con;
	}
}
