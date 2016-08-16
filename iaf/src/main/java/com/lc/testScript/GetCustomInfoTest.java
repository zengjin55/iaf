package com.lc.testScript;

import com.lc.bean.ResponseBean;
import com.lc.util.CookieUtil;
import com.lc.util.HttpUtils;
import com.lc.util.ReponseUtil;
import com.lc.util.xmlUtil;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Created by 000001159 on 2016/8/16.
 */
public class GetCustomInfoTest {


    static CookieStore cookieStore ;
    static CookieUtil cookieUtil=new CookieUtil() ;
    CloseableHttpClient client;
    HttpUtils httpUtils=HttpUtils.getInstance();


    @Parameters({ "url", "objBean" ,"statusCode","xmlName"})
    @BeforeSuite

	/*
	* 登录进入系统获取JSESSIONID放入到CookieStore中
	* */
    public  void TestLoginIn(String url ,String objBean, String statusCode,String xmlName) {

        Map<String,String> params= xmlUtil.readXMLDocument(objBean, xmlName);
        client = HttpClients.createDefault();
        CloseableHttpResponse httpResponse= httpUtils.post(url, params, client, cookieStore);
        //cookieUtil.printResponse(httpResponse);
        cookieStore=cookieUtil.setCookieStore(httpResponse);

    }

    @Parameters({ "url", "objBean" ,"statusCode","body","xmlName"})
    @Test(priority = 2)
    public  void TestGetCustomInfo(String url ,String objBean, String statusCode,String body,String xmlName) {
        Map<String,String> params=xmlUtil.readXMLDocument(objBean,xmlName);

        client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        CloseableHttpResponse httpResponse= httpUtils.post(url, params, client, cookieStore);
        ResponseBean rb=new ReponseUtil().setResponseBean(httpResponse);


//		add Assert
        Assert.assertEquals("OK", rb.getStatus());
        Assert.assertEquals(statusCode, rb.getStatusCode());
        Assert.assertEquals(true, rb.getBody().contains(body));

    }



    @AfterSuite
    public void closeClient(){
        try {
            // 关闭流并释放资源
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
