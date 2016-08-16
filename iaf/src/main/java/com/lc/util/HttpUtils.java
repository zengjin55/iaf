package com.lc.util;

/**
 * Created by 000001159 on 2016/8/12.
 */



import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;



import com.lc.bean.ResponseBean;





public class HttpUtils {

    private static Log logger = LogFactory.getLog(HttpUtils.class);

    ResponseBean messages = null;

    Charset s=Consts.UTF_8;
    String CommonUrl=Contants.url+Contants.commonUrl;
    ;
    public static HttpUtils  getInstance(){
        return new HttpUtils();
    }


    public CloseableHttpResponse post(String url, Map<String, String> params,CloseableHttpClient httpclient,CookieStore cookieStore){

        UrlEncodedFormEntity entitys = getFormEntity(params);
        HttpPost httppost = new HttpPost(CommonUrl+url);

        if(cookieStore!=null){
            httppost.setHeader("Cookie", "JSESSIONID=" + cookieStore.getCookies().get(0).getValue().trim());
//            System.out.println("cookieStore,不为空");
//            System.out.println("Cookie: JSESSIONID="+cookieStore.toString());
        }
        httppost.setEntity(entitys);
        return RequestMessages(httppost, httpclient);
    }

    public CloseableHttpResponse put(String url, Map<String, String> params,CloseableHttpClient httpclient,CookieStore cookieStore){

        UrlEncodedFormEntity entitys = getFormEntity(params);
        HttpPut httpput = new HttpPut(CommonUrl+url);
        if(cookieStore!=null){
            httpput.setHeader("Cookie", "JSESSIONID="+cookieStore.getCookies().get(0).getValue().trim());
        }
        httpput.setEntity(entitys);

        return RequestMessages(httpput, httpclient);
    }

    public CloseableHttpResponse get(String url, Map<String, String> params,CloseableHttpClient httpclient,CookieStore cookieStore){
        UrlEncodedFormEntity entitys = getFormEntity(params);

        HttpGet httpget=null;
        try {
            httpget = new HttpGet(CommonUrl+url+'?'+EntityUtils.toString(entitys));
            if(cookieStore!=null){
                httpget.setHeader("Cookie", "JSESSIONID="+cookieStore.getCookies().get(0).getValue().trim());
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block


            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block


            e.printStackTrace();
        }

        return RequestMessages(httpget, httpclient);
    }

    public CloseableHttpResponse delete(String url, Map<String, String> params,CloseableHttpClient httpclient,CookieStore cookieStore){

        UrlEncodedFormEntity entitys = getFormEntity(params);

        HttpDelete httpdelete=null;
        try {
            httpdelete = new HttpDelete(CommonUrl+url+'?'+EntityUtils.toString(entitys));
            if(cookieStore!=null){
                httpdelete.setHeader("Cookie", "JSESSIONID="+cookieStore.getCookies().get(0).getValue().trim());
            }
//			System.out.println(httpdelete.getURI());


            logger.debug(httpdelete.getURI());
            logger.info(httpdelete.getURI());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block


            e.printStackTrace();
        }

        return RequestMessages(httpdelete, httpclient);
    }


    public UrlEncodedFormEntity getFormEntity(Map<String, String> params,Charset...ucode){
        Charset f=null;
        if(ucode.equals("")){
            f=Consts.UTF_8;
        }else{
            f=s;
        }
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        for(String key:keySet){
            formparams.add(new BasicNameValuePair(key, params.get(key)));
        }
        UrlEncodedFormEntity entitys = new UrlEncodedFormEntity(formparams,f);
        return entitys;
    }

    /*
    * 请求数据
    * */
    public CloseableHttpResponse RequestMessages(HttpUriRequest httpRequest ,CloseableHttpClient httpclient){

        CloseableHttpResponse response = null;
        try {


            logger.info("\n"+"***************************请求信息**********************************" + "\n"
                            + httpRequest.getRequestLine().toString() + "\n" +
                            "***************************请求结束**********************************"
            );

            response = httpclient.execute(httpRequest);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  response;
    }


}


