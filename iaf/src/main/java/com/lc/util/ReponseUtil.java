package com.lc.util;


import com.lc.bean.ResponseBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;


/**
 * Created by 000001159 on 2016/8/11.
 */
public class ReponseUtil {

    private static Log logger = LogFactory.getLog(CookieUtil.class);

    public ResponseBean setResponseBean(CloseableHttpResponse httpResponse) {
        ResponseBean messages = new ResponseBean();

        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) try {
            String responseString = EntityUtils.toString(entity);
            String rs = responseString.replace("\r\n", "");


            messages.setStatus(httpResponse.getStatusLine().getReasonPhrase());
            messages.setStatusCode(Integer.toString(httpResponse.getStatusLine().getStatusCode()));
            messages.setBody(rs);
            logger.info("\n" +"***************************返回开始**********************************" + "\n" +
                    httpResponse.getStatusLine().getReasonPhrase() + "\n" +
                    Integer.toString(httpResponse.getStatusLine().getStatusCode()) + "\n" +
                    "Context" + rs + "\n" +
                    "***************************返回结束**********************************"
            );

            HeaderIterator iterator = httpResponse.headerIterator();
            while (iterator.hasNext()) {
                logger.debug("\t" + iterator.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;

    }


}
