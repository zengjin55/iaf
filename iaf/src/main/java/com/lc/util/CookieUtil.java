package com.lc.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


/**
 * Created by 000001159 on 2016/8/11.
 */
public class CookieUtil {
    // 创建CookieStore实例
    static CookieStore cookieStore = null;
    private static Log logger = LogFactory.getLog(CookieUtil.class);


    /*
    * 通过CookieStore储存cookie
    * */
    public  CookieStore setCookieStore(HttpResponse httpResponse) {
        logger.info("----setCookieStore");
        cookieStore = new BasicCookieStore();
        // JSESSIONID
        String setCookie = httpResponse.getFirstHeader("Set-Cookie").getValue();
        String JSESSIONID = setCookie.substring("JSESSIONID=".length(),
        setCookie.indexOf(";"));
        logger.debug("JSESSIONID:" + JSESSIONID);
        // 新建一个Cookie
        BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", JSESSIONID);
        cookie.setVersion(0);
        cookie.setDomain("127.0.0.1");
        cookie.setPath("/CwlProClient");
        // cookie.setAttribute(ClientCookie.VERSION_ATTR, "0");
        // cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "127.0.0.1");
        // cookie.setAttribute(ClientCookie.PORT_ATTR, "8080");
        // cookie.setAttribute(ClientCookie.PATH_ATTR, "/CwlProWeb");
        cookieStore.addCookie(cookie);
        return cookieStore;
    }

    public static void printResponse(HttpResponse httpResponse)
            {
        // 获取响应消息实体
        HttpEntity entity = httpResponse.getEntity();
        // 响应状态
        logger.info("status:" + httpResponse.getStatusLine());
        logger.info("headers:");
        HeaderIterator iterator = httpResponse.headerIterator();
        while (iterator.hasNext()) {
            logger.info("\t" + iterator.next());
        }
        // 判断响应实体是否为空
        if (entity != null) {
            String responseString = null;
            try {
                responseString = EntityUtils.toString(entity);
            } catch (IOException e) {
                e.printStackTrace();
            }
            logger.info("response length:" + responseString.length());
            logger.info("response content:" + responseString.replace("\r\n", ""));
        }
    }





}
