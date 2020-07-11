import javax.net.ssl.*;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class SomaConnection {

    private URL url;
    private HttpsURLConnection connection;
    private OutputStream os;
    private static int firstRun = 0;


    public void run(String data, String ip, String port, String username, String password)
    {
        if(firstRun==0)
        {
            connectToDP(ip, port, username, password);
            firstRun = 1;
        }
        sendDataToDP(data);

    }


    public void sendDataToDP(String data)
    {
        try {

            os.write(data.getBytes());
            os.flush();
            os.close();

            InputStream content = (InputStream)connection.getInputStream();
            BufferedReader in   = new BufferedReader (new InputStreamReader(content));
            String ipLine;
            String message="";
            while ((ipLine = in.readLine()) != null) {

                message += ipLine;

                //System.out.println(ipLine);
            }

            int startIndex = message.indexOf("<dp:result>");
            int endIndex = message.indexOf("</dp:result>");

            //String log = message.substring(startIndex, endIndex + "</dp:result>".length())
             //       .replaceAll("\\s+", " ").trim();
            //System.out.println(log);

        }

        catch(UnknownHostException e) {
            System.out.println("wrong ip address");
        }

        catch(ConnectException e) {
            System.out.println("wrong port, connection refused");
        }

        catch(IOException e) {
            System.out.println("wrong data sent to DP");
        }

        catch(Exception e) {
            e.printStackTrace();
        }

    }


    public void connectToDP(String ip, String port, String username, String password)
    {
        try {

            url = new URL ("https://" + ip + ":" + port);
            String encoding = Base64.getEncoder().encodeToString((username+":"+password).getBytes());

            disableSslVerification();

            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty  ("Authorization", "Basic " + encoding);

            os = connection.getOutputStream();

        }

        catch(UnknownHostException e) {
            System.out.println("wrong ip address");
        }

        catch(ConnectException e) {
            System.out.println("wrong port, connection refused");
        }

        catch(Exception e) {
            e.printStackTrace();
        }

    }

//    public void sendPostRequest(String data, String ip, String port, String username, String password)
//    {
//        try {
//            URL url = new URL ("https://" + ip + ":" + port);
//            String encoding = Base64.getEncoder().encodeToString((username+":"+password).getBytes());
//
//            disableSslVerification();
//
//            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setDoOutput(true);
//            connection.setRequestProperty  ("Authorization", "Basic " + encoding);
//
//            OutputStream os = connection.getOutputStream();
//
//            os.write(data.getBytes());
//            os.flush();
//            os.close();
//
//            InputStream content = (InputStream)connection.getInputStream();
//            BufferedReader in   = new BufferedReader (new InputStreamReader(content));
//            String ipLine;
//            String message="";
//            while ((ipLine = in.readLine()) != null) {
//
//                message += ipLine;
//
//                //System.out.println(ipLine);
//            }
//
//            int startIndex = message.indexOf("<dp:result>");
//            int endIndex = message.indexOf("</dp:result>");
//
//            String log = message.substring(startIndex, endIndex + "</dp:result>".length())
//                    .replaceAll("\\s+", " ").trim();
//            System.out.println(log);
//
//        }
//
//        catch(UnknownHostException e) {
//            System.out.println("wrong ip address");
//        }
//
//        catch(ConnectException e) {
//            System.out.println("wrong port, connection refused");
//        }
//
//        catch(IOException e) {
//            System.out.println("wrong data sent to DP");
//        }
//
//        catch(Exception e) {
//            e.printStackTrace();
//        }
//
//    }


    private void disableSslVerification() {
        try
        {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
}
