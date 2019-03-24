package com.service;

import java.io.IOException;
import java.net.URLEncoder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Service
public class findBadAir {
	public String excute() throws IOException {
		StringBuilder api = new StringBuilder("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getUnityAirEnvrnIdexSnstiveAboveMsrstnList");
		String key = "EuGhwQJR7q8d74gm2fFjUUh2KSBXpbBhOmjq622aQhkWrNMHMtEvlcwb0ooJyweCS7ZhhnbjvqJwDPEl37yYDw%3D%3D";

		api.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + key);
		api.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
		api.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));

		Document document = null;
		String result = "";
		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(api.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		document.getDocumentElement().normalize();
		Element root = document.getDocumentElement();
		NodeList n_list = root.getElementsByTagName("item");
		Element el = null;
		NodeList sub_n_list = null; // sub_n_list
		Element sub_el = null; // sub_el
		Node v_txt = null;

		String[] badList = { "stationName" };

		for (int i = 0; i < n_list.getLength(); i++) {
			el = (Element) n_list.item(i);
			for (int k = 0; k < badList.length; k++) {
				sub_n_list = el.getElementsByTagName(badList[k]);
				for (int j = 0; j < sub_n_list.getLength(); j++) {
					sub_el = (Element) sub_n_list.item(j);
					v_txt = sub_el.getFirstChild();
					result += v_txt.getNodeValue() + ",";

				}
			}
		}

		return result;
	}
}
