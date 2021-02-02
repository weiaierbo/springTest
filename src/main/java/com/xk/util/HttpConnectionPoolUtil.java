package com.xk.util;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * http连接池工具类
 *
 * @author kai.xu
 * @create 2020-11-18 14:21
 */
public class HttpConnectionPoolUtil {

    private static Logger log = LoggerFactory.getLogger(HttpConnectionPoolUtil.class);

    private HttpConnectionPoolUtil(){}

    private static CloseableHttpClient httpClient;

    private static PoolingHttpClientConnectionManager clientConnectionManager;

    private static RequestConfig requestConfig;

    private static ScheduledExecutorService monitorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r,"http-monitor-schedule-thread");
            return thread;
        }
    });


    static {
        init();
    }

    private static void init(){

        ConnectionSocketFactory plainSf = PlainConnectionSocketFactory
                .getSocketFactory();
        LayeredConnectionSocketFactory sslSf = SSLConnectionSocketFactory
                .getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder
                .<ConnectionSocketFactory> create().register("http", plainSf)
                .register("https", sslSf).build();

        clientConnectionManager = new PoolingHttpClientConnectionManager(registry);
        clientConnectionManager.setDefaultMaxPerRoute(20);
        clientConnectionManager.setMaxTotal(200);
        httpClient = HttpClients.custom().setConnectionManager(clientConnectionManager).build();

        requestConfig = RequestConfig.custom().setConnectionRequestTimeout(30000)
                .setConnectTimeout(10000).setSocketTimeout(60000).build();

        monitorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if(clientConnectionManager != null) {
                    log.info("----schedule-thread close expired connection-----");
                    clientConnectionManager.closeExpiredConnections();
                }
            }
        },0,1, TimeUnit.HOURS);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                if(httpClient != null) {
                    try {
                        log.info("shutdown hook invoked");
                        httpClient.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }));

    }

    public static HttpClient getHttpClient(){
        return httpClient;
    }


    /**
     * get请求
     * @param url
     * @return
     */
    public static String httpGet(String url) {
        long start = System.currentTimeMillis();

        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);

            log.info("url:{}请求耗时[{}]毫秒",url, System.currentTimeMillis()-start);

            if(response.getStatusLine().getStatusCode() == 200){
                return EntityUtils.toString(response.getEntity(),StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            log.error("http get error,msg:{}",e.getMessage(),e);
        } finally {
            if(response != null) {

                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    log.error("http get consume error,msg:{}",e.getMessage(),e);
                }
            }
        }
        return null;
    }

    /**
     * post请求，json格式
     * @param url 目标地址
     * @param body 内容体，json字符串
     * @return
     */
    public static String postJson(String url,String body) {
        long start = System.currentTimeMillis();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        CloseableHttpResponse httpResponse = null;
        try{
            StringEntity stringEntity = new StringEntity(body, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
            httpResponse = httpClient.execute(httpPost);

            log.info("url:{}请求耗时[{}]毫秒",url, System.currentTimeMillis()-start);

            if(200 == httpResponse.getStatusLine().getStatusCode()) {
                return EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
            }
        } catch (ClientProtocolException e) {
            log.error("http post protocolException,msg:{}",e.getMessage(),e);
        } catch (IOException e) {
            log.error("http post ioException ,msg:{}",e.getMessage(),e);
        } finally {
            if(httpResponse != null) {
                try {
                    EntityUtils.consume(httpResponse.getEntity());
                } catch (IOException e) {
                    log.error("http post consume error,msg:{}",e.getMessage(),e);
                }
            }
        }
        return null;
    }


}
