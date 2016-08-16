package com.lc.util;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.*;



public class xmlUtil {
	


	 /**
	 * @param
	 * @param
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static HashMap<String, String>  readXMLDocument(String beanName,String xmlName){
		 
		String path=Contants.xmlUrl+xmlName+".xml";
		 HashMap<String, String> locatorMap=new HashMap<String, String>(); 
		/* locatorMap.clear();*/
		 File file = new File(path);
		 if (!file.exists()) {
				try {
					throw new IOException("Can't find " + path);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		  SAXReader saxReader = new SAXReader();
	      Document document;
	      try {
			document = saxReader.read(file);
	        //
	        for (Iterator iter = document.getRootElement().elementIterator(); iter.hasNext();)
	        {
	            Element e1 = (Element) iter.next();
	            
	            if(e1.attributeValue("beanName").equalsIgnoreCase(beanName)){
	            	//
	            	
		            for (Iterator iter1 = e1.elementIterator(); iter1.hasNext();)
			        {
			            Element e2 = (Element) iter1.next();
			         // 
//			            System.out.println("e2.getData()"+e2.getData());


			            String elementName=e2.attributeValue("name").toString();
			            String elementValue=e2.attributeValue("value").toString();
			            locatorMap.put(elementName, elementValue);
			            	
			        }
		            return locatorMap;
	            }
	          
	        }
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	        
	        return locatorMap;
			

	 }
	 

	
	

}

