package com.lc.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;
import org.apache.http.Consts;
import org.dom4j.*;

import com.lc.bean.ResponseBean;





public class HttpUtil {
	
	private static Log logger = LogFactory.getLog(HttpUtil.class);
	CloseableHttpClient httpclient = HttpClients.createDefault();
	ResponseBean messages = null;
	CloseableHttpResponse response;
	Charset s=Consts.UTF_8; 
	String CommonUrl=Contants.url+Contants.commonUrl;
;
	public static HttpUtil  getInstance(){
		return new HttpUtil();
	}

	
	public ResponseBean post(String url, Map<String, String> params){
		
		UrlEncodedFormEntity entitys = getFormEntity(params);
		HttpPost httppost = new HttpPost(CommonUrl+url);
		httppost.setEntity(entitys);
			
		return returnMessage(httppost);
	}
	
public ResponseBean put(String url, Map<String, String> params){
		
	UrlEncodedFormEntity entitys = getFormEntity(params);
	HttpPut httpput = new HttpPut(CommonUrl+url);
		httpput.setEntity(entitys);
		return returnMessage(httpput);
	}
	
	public ResponseBean get(String url, Map<String, String> params){
		UrlEncodedFormEntity entitys = getFormEntity(params);
		
		HttpGet httpget=null;
		try {
			httpget = new HttpGet(CommonUrl+url+'?'+EntityUtils.toString(entitys));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnMessage(httpget);
	}
	
	public ResponseBean delete(String url, Map<String, String> params){
		
		UrlEncodedFormEntity entitys = getFormEntity(params);
		
		HttpDelete httpdelete=null;
		try {
			httpdelete = new HttpDelete(CommonUrl+url+'?'+EntityUtils.toString(entitys));
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
		
		return returnMessage(httpdelete);
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
		
	public ResponseBean returnMessage(HttpUriRequest httpRequest ){
		messages=new ResponseBean();
		try {
			response = httpclient.execute(httpRequest);
			HttpEntity entity = response.getEntity();
			
			messages.setMethod( httpRequest.getMethod());
			messages.setUrl(httpRequest.getURI().toString());
			System.out.println(httpRequest.getRequestLine().toString());
		
			messages.setStatus(response.getStatusLine().getReasonPhrase());
			messages.setStatusCode(Integer.toString(response.getStatusLine().getStatusCode()));
			messages.setBody(EntityUtils.toString(entity));
			response.close();
			
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		return messages;
	}
	
	
}
