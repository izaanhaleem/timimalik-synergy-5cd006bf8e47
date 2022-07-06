package com.hisdu.SESCluster.communication.version;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeoutException;

public class WebBinderForVersion {
    public static String doGet(String urlStr) throws TimeoutException,ApiException {
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
            urlConnection.setRequestProperty("Authorization", "Basic " + getHeaderVersionCheck());

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

    private static HttpURLConnection httpURLConnectionWithBasicSetup(URL url, String type) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod(type);
//        urlConnection.setConnectTimeout(3000);
        urlConnection.setConnectTimeout(15000);

        return urlConnection;
    }
    private static String getHeaderVersionCheck() {
        return "YWRtaW5pc3RyYXRvcjpQaXRiITIzNCU=";
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
}
