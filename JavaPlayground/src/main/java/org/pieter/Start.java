package org.pieter;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Start {

    public static void main(String[] args) {
        // Do a request
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://192.168.1.16:8888/beeswax/");

        // The underlying HTTP connection is still held by the response object
        // to allow the response content to be streamed directly from the
        // network socket.
        // In order to ensure correct deallocation of system resources
        // the user MUST either fully consume the response content or abort
        // request
        // execution by calling CloseableHttpResponse#close().

        CloseableHttpResponse response1 = null;
        try {
            response1 = httpclient.execute(httpGet);
            System.out.println(response1.getStatusLine());
            HttpEntity entity1 = response1.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity1);
        } catch (IOException ioe) {
            System.out.println("Got error");
        } finally {
            if (response1 != null) {
                try {
                    response1.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        // parse the json

        // store to file
    }

}
