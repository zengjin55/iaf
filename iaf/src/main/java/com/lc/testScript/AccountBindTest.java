package com.lc.testScript;

import java.io.IOException;
import java.util.Map;

import org.apache.http.ParseException;
import org.dom4j.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.lc.bean.ResponseBean;
import com.lc.util.HttpUtil;
import com.lc.util.xmlUtil;

public class AccountBindTest {


	@Parameters({ "url", "objBean" ,"status","statusCode","body"})
	@Test
	public  void TestBindList(String url ,String objBean,String status,String statusCode,String body) {
		Map<String,String> params=xmlUtil.readXMLDocument(objBean);
		ResponseBean s=HttpUtil.getInstance().get(url, params);
/*		System.out.println(s.getMethod());
		System.out.println(s.getUrl());
		System.out.println(s.getStatus());
		System.out.println(s.getStatusCode());*/
		System.out.println(s.getBody());
//		add Assert
		Assert.assertEquals(status, s.getStatus());
		Assert.assertEquals(statusCode, s.getStatusCode());
		Assert.assertEquals(true, s.getBody().contains(body));

	}
	
	@Parameters({ "url", "objBean" ,"status","statusCode","body"})
	@Test
	public  void TestBindAuthBind(String url ,String objBean,String status,String statusCode,String body) {
		Map<String,String> params=xmlUtil.readXMLDocument(objBean);
		ResponseBean s=HttpUtil.getInstance().post(url, params);

		System.out.println(s.getBody());
//		add Assert
		Assert.assertEquals(status, s.getStatus());
		Assert.assertEquals(statusCode, s.getStatusCode());
		Assert.assertEquals(true, s.getBody().contains(body));

	}
}
