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
public class airCheck
{
  public String excute(String args)
    throws IOException
  {
    StringBuilder check = new StringBuilder("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty");
    String key = "EuGhwQJR7q8d74gm2fFjUUh2KSBXpbBhOmjq622aQhkWrNMHMtEvlcwb0ooJyweCS7ZhhnbjvqJwDPEl37yYDw%3D%3D";
    
    check.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + key);
    check.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
    check.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
    check.append("&" + URLEncoder.encode("sidoName", "UTF-8") + "=" + URLEncoder.encode(args, "UTF-8"));
    
    Document document = null;
    String result = "";
    try
    {
      document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(check.toString());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    document.getDocumentElement().normalize();
    Element root = document.getDocumentElement();
    NodeList n_list = root.getElementsByTagName("item");
    Element el = null;
    NodeList sub_n_list = null;
    Element sub_el = null;
    Node v_txt = null;
    
    String[] tagList = { "stationName", "so2Value", "coValue" };
    String[] info = { "위치", "이산화황", "일산화탄소" };
    for (int i = 0; i < n_list.getLength(); i++)
    {
      el = (Element)n_list.item(i);
      for (int k = 0; k < tagList.length; k++)
      {
        sub_n_list = el.getElementsByTagName(tagList[k]);
        for (int j = 0; j < sub_n_list.getLength(); j++)
        {
          sub_el = (Element)sub_n_list.item(j);
          v_txt = sub_el.getFirstChild();
          result += info[k] + " : " + v_txt.getNodeValue() + "\n";
        }
      }
      result += "\n";
    }
    return result;
  }
}
