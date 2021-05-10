package db;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Holiday {
	private static final String URL = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo";
	private static final String KEY = "G1ZPRotImv3j%2Fc2pKpbUQOxvdvRSdbiKgBh8TBASCjf4AmfjveOkPlleiYCOleDHAYkM7NrdA5CvdqBlnSaIxA%3D%3D";
	
	private static String getTagValue(String tag, Element element) {
		NodeList list = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = list.item(0);
		if(node != null) return node.getNodeValue();
		else return null;
	}
	public static Map<String, String> getHoliday(int year, int month) {
		Map<String, String> holidays = new HashMap<>();
		String urlStr = URL + "?ServiceKey=" + KEY + "&solYear=" + year + "&solMonth=" + String.format("%02d", month);
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(urlStr);
			
			document.getDocumentElement().normalize();
			NodeList list = document.getElementsByTagName("item");
			
			for(int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					holidays.put(getTagValue("locdate", element), getTagValue("dateName", element));
				}
			}
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return holidays;
	}
}
