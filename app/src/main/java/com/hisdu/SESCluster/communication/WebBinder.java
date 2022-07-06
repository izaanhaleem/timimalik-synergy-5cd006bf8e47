package com.hisdu.SESCluster.communication;

import android.util.Base64;
import android.util.Log;

import com.hisdu.SESCluster.constants.Globals;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;


public class WebBinder {
    public static String doGet(String urlStr) throws TimeoutException, ApiException {
        String result = null;

        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(urlStr.replaceAll(" ", "%20"));
            Log.d("TAG", urlStr);

            urlConnection = httpURLConnectionWithBasicSetup(url, "GET");
            urlConnection.setRequestProperty("Authorization", "Basic " + getHeader());
            result = readStream(urlConnection);

            int statusCode = urlConnection.getResponseCode();
            if (statusCode != 200) {
                throw new ApiException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return result;
    }

    public static String doGet(String urlStr, ArrayList<RequestObject> headers) throws TimeoutException, ApiException {
        String result = null;

        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(urlStr.replaceAll(" ", "%20"));
            Log.d("TAG", urlStr);

            urlConnection = httpURLConnectionWithBasicSetup(url, "GET");
//            if (headers != null)
//                for (RequestObject request : headers) {
//                    urlConnection.setRequestProperty(request.getParamName(), request.getParamValue());
//                }
//            else
                urlConnection.setRequestProperty("Authorization", "Basic " + getHeader());

            result = readStream(urlConnection);

            int statusCode = urlConnection.getResponseCode();
            if (statusCode != 200) {
                throw new ApiException();
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                int statusCode = 0;
                if (urlConnection != null) {
                    statusCode = urlConnection.getResponseCode();
                }
                if (urlConnection != null) {
                    result = urlConnection.getResponseMessage();
                    BufferedReader br;
                    StringBuilder sb;
                    br = new BufferedReader(new InputStreamReader((urlConnection.getErrorStream())));
                    sb = new StringBuilder();
                    String output;
                    while ((output = br.readLine()) != null) {
                        sb.append(output);
                    }
                    Log.d("Response_sb", sb.toString());
                    result = sb.toString();
                }
                Log.d("Response", statusCode + "" + result);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return result;
    }

    public static String doGet(String urlStr, ArrayList<RequestObject> headers, String userName, String password) throws TimeoutException, ApiException {
        String result = null;

        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(urlStr.replaceAll(" ", "%20"));
            Log.d("TAG", urlStr);

            urlConnection = httpURLConnectionWithBasicSetup(url, "GET");
            if (headers != null)
                for (RequestObject request : headers) {
                    urlConnection.setRequestProperty(request.getParamName(), request.getParamValue());
                }
            else
                urlConnection.setRequestProperty("Authorization", "Basic " + getHeader("hisdu", "egadev"));

            result = readStream(urlConnection);

            int statusCode = urlConnection.getResponseCode();
            if (statusCode != 200) {
                throw new ApiException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return result;
    }

    public static String doPut(JSONObject data, String urlStr) throws TimeoutException, ApiException {
        String result = null;
        String base64EncodedCredentials = Base64.encodeToString(
                (Globals.USER_NAME + ":" + Globals.PASSWORD).getBytes(),
                Base64.NO_WRAP);
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            Log.d("TAG", urlStr);
            url = new URL(urlStr.replaceAll(" ", "%20"));
            urlConnection = httpURLConnectionWithBasicSetup(url, "PUT");

            urlConnection.setRequestProperty("Authorization", "Basic " + base64EncodedCredentials);
            writeStream(urlConnection, data);
            result = readStream(urlConnection);

            int statusCode = urlConnection.getResponseCode();
            if (statusCode != 200) {
                throw new ApiException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;
    }

    public static String doPostFormEncoded(ArrayList<RequestObject> data, String urlStr, List<RequestObject> headers) throws TimeoutException, ApiException {
        String result = null;
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(urlStr.replaceAll(" ", "%20"));
            urlConnection = httpURLConnectionWithBasicSetup(url, "POST");
            if (headers != null)
                for (RequestObject request : headers) {
                    urlConnection.setRequestProperty(request.getParamName(), request.getParamValue());
                }

            String body = "";
            if (data != null && data.size() > 0) {
                for (RequestObject param : data
                        ) {
                    body = body + param.getParamName() + "=" + param.getParamValue() + "&";
                }

            }

            writeStream(urlConnection, body);

            result = readStream(urlConnection);

            int statusCode = urlConnection.getResponseCode();
            if (statusCode != 200) {
                throw new ApiException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;
    }

    public static String doPost(JSONObject data, String urlStr) throws TimeoutException, ApiException {
        String result = null;
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            Log.d("TAG", urlStr);
            url = new URL(urlStr.replaceAll(" ", "%20"));
            urlConnection = httpURLConnectionWithBasicSetup(url, "POST");
            urlConnection.setRequestProperty("Authorization", "Basic " + getHeader("hisdu","egadev"));
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setDoOutput(false);
            writeStream(urlConnection, data);
            result = readStream(urlConnection);
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == 200 || statusCode == 202) {
            } else {
                throw new ApiException();
            }
        } catch (
                IOException e)

        {
            e.printStackTrace();
        } finally

        {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;
    }

    public static String doPost(JSONObject data, String urlStr, String userName, String password) throws TimeoutException, ApiException {
        String result = null;
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            Log.d("TAG", urlStr);
            url = new URL(urlStr.replaceAll(" ", "%20"));
            urlConnection = httpURLConnectionWithBasicSetup(url, "POST");
//            urlConnection.setRequestProperty("Authorization", "Basic " + getHeader(userName, password));
            urlConnection.setRequestProperty("Authorization", "Basic " + getHeader("hisdu", "egadev"));
            urlConnection.setDoOutput(true);
            writeStream(urlConnection, data);

            result = readStream(urlConnection);

            int statusCode = urlConnection.getResponseCode();
            if (statusCode == 200 || statusCode == 202) {

            } else {
                throw new ApiException();
            }

        } catch (IOException e) {
            try {
                int statusCode = 0;
                if (urlConnection != null) {
                    statusCode = urlConnection.getResponseCode();
                }
                if (urlConnection != null) {
                    result = urlConnection.getResponseMessage();
                    BufferedReader br;
                    StringBuilder sb;
                    br = new BufferedReader(new InputStreamReader((urlConnection.getErrorStream())));
                    sb = new StringBuilder();
                    String output;
                    while ((output = br.readLine()) != null) {
                        sb.append(output);
                    }
                    Log.d("Response_sb", sb.toString());
                    result = sb.toString();
                }
                Log.d("Response", statusCode + "" + result);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
//            result = e.getMessage();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;
    }

    public static String doSoapRequest(String urlStr, String method, String xml, String namespace) throws TimeoutException, ApiException {

        String result = null;
        URL url;
        HttpURLConnection urlConnection = null;
        String soapaction = namespace + method;
        try {
            Log.d("TAG", urlStr);
            url = new URL(urlStr.replaceAll(" ", "%20"));
            urlConnection = httpURLConnectionWithBasicSetup(url, "POST");
            urlConnection.setRequestProperty("User-Agent", "ksoap2-android/2.6.0+");
            urlConnection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
            urlConnection.setRequestProperty("SOAPAction", soapaction);
            urlConnection.setRequestProperty("Accept-Encoding", "gzip");
            urlConnection.setRequestProperty("Content-Length", "" + xml.getBytes("UTF-8").length);
            urlConnection.setDoOutput(true);
            writeStream(urlConnection, xml);

            result = readStream(urlConnection);

            int statusCode = urlConnection.getResponseCode();
            if (statusCode != 200) {
                throw new ApiException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;


        /*String output = null;
        //String url = "https://83.111.43.229/MobileAPI/MobileAPI.svc/basic";
        String soapaction = namespace + method;
        try {
            this.postRequest = new HttpPost(url);
            StringEntity entity = new StringEntity(xml);
            postRequest.setHeader("Content-Type", "text/xml;charset=UTF-8");
            postRequest.setHeader("SOAPAction", soapaction);
            postRequest.setEntity(entity);
            HttpResponse httpResponse = client.execute(postRequest);
            HttpEntity httpEntity = httpResponse.getEntity();
            output = EntityUtils.toString(httpEntity);
//			ProjectUtils.createTempFile("<-----Response--->");
//			ProjectUtils.createTempFile(output);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;*/
    }


    private static HttpURLConnection httpURLConnectionWithBasicSetup(URL url, String type) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod(type);
//        urlConnection.setConnectTimeout(3000);
        urlConnection.setConnectTimeout(15000);

        return urlConnection;
    }

    private static void writeStream(HttpURLConnection urlConnection, JSONObject data) {
        writeStream(urlConnection, data.toString());
    }

    private static void writeStream(HttpURLConnection urlConnection, String data) {
        try {
            Log.d("TAG", "Data ==> " + data);
            OutputStream os = urlConnection.getOutputStream();
            os.write(data.getBytes("UTF-8"));
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readStream(HttpURLConnection urlConnection) throws IllegalStateException, IOException {
        InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

        BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();

        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);

        Log.d("TAG", "Response : " + responseStrBuilder.toString());
        return responseStrBuilder.toString();
    }

    public static class ApiException extends Exception {

        public ApiException() {
        }

    }

    private static String getHeader() {
        return "";
    }

    public static String getHeader(String userName, String password) {
        String credentials = userName + ":" + password;
        String credBase64 = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT).replace("\n", "");
        return credBase64;
    }
    private static String getHeaderVersionCheck() {
        return "";
    }
}
