/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prices.auth.providers;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author ichla
 */
public class ConnectionUtils {

    public static HttpsURLConnection getConnection(String url) throws MalformedURLException, IOException {
        return (HttpsURLConnection) new URL(url).openConnection();
    }

    public static String getResponseString(String url) throws MalformedURLException, IOException {
        HttpsURLConnection conn = getConnection(url);
        return getResponseFromConnection(conn);
    }

    public static Map<String, Object> getJson(String url) throws MalformedURLException, IOException {
        HttpsURLConnection conn = getConnection(url);
        String raw = getResponseFromConnection(conn);
        return (new Gson()).fromJson(raw, Map.class);
    }

    public static String getResponseStringLessSecure(String url) throws MalformedURLException, IOException {
        HttpsURLConnection conn = trustEveryone(getConnection(url));
        return getResponseFromConnection(conn);
    }

    public static Map<String, Object> getJsonLessSecure(String url) throws MalformedURLException, IOException {
        HttpsURLConnection conn = trustEveryone(getConnection(url));
        String raw = getResponseFromConnection(conn);
        return (new Gson()).fromJson(raw, Map.class);
    }

    private static String getResponseFromConnection(HttpsURLConnection conn) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                Charset.forName("UTF-8")));
        StringBuilder b = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            b.append(inputLine);
            b.append("\n");
        }
        in.close();
        return b.toString();
    }

    private static HttpsURLConnection trustEveryone(HttpsURLConnection conn) {
        try {
            conn.setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            conn.setSSLSocketFactory(context.getSocketFactory());
        } catch (KeyManagementException e) {
            System.out.println("SSL trust everyone failed because of key management error.");
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("SSL trust everyone failed because TLS is not a valid algorithm.");
            e.printStackTrace();
        }

        return conn;
    }
}
