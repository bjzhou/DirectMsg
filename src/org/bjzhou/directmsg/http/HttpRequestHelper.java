
package org.bjzhou.directmsg.http;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.bjzhou.directmsg.App;

public class HttpRequestHelper {

    private StringBuffer urlBuffer;
    private StringBuffer parameters;
    private boolean first;

    public enum HttpMethod {
        GET, POST
    }

    public HttpRequestHelper(String basicUrl) {
        urlBuffer = new StringBuffer(basicUrl);
        parameters = new StringBuffer();
        first = true;
    }

    public void addParameter(String key, String value) {
        if (first)
            first = false;
        else
            parameters.append("&");
        try {
            parameters.append(URLEncoder.encode(key,"UTF-8"));
            parameters.append("=");
            parameters.append(URLEncoder.encode(value,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public String excute(HttpMethod method) {
        switch (method) {
		case GET:
			return doGet();
		case POST:
			return doPost();
		default:
			return null;
		}
    }

    private String doGet() {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlBuffer.append("?").append(parameters).toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return handleResponse(connection);
    }

    private String doPost() {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlBuffer.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.connect();
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.write(parameters.toString().getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return handleResponse(connection);
    }

    private String handleResponse(HttpURLConnection connection) {
        InputStream is = null;
        BufferedReader reader = null;
        try {
            int code = connection.getResponseCode();
            if (code != HttpURLConnection.HTTP_OK) {
                System.out.println("error code:" + code);
            }
            is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder strBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                strBuilder.append(line);
            }
            if (App.DEBUG)
            	System.out.println(strBuilder.toString());
            return strBuilder.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            close(is);
            close(reader);
            connection.disconnect();
        }
        return null;
    }
    
    private void close(Closeable obj) {
        try {
            obj.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NullPointerException e1) {
        	e1.printStackTrace();
        }
    }
    

}
