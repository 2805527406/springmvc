package com.demo.util;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;




public class XMLUtil {
	/**

	 * 瑙ｆ瀽xml,杩斿洖绗竴绾у厓绱犻敭鍊煎銆傚鏋滅涓�绾у厓绱犳湁瀛愯妭鐐癸紝鍒欐鑺傜偣鐨勫�兼槸瀛愯妭鐐圭殑xml鏁版嵁銆�

	 * 

	 * @param strxml

	 * @return

	 * @throws JDOMException

	 * @throws IOException

	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map doXMLParse(String strxml) throws JDOMException, IOException {
		strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

		if (null == strxml || "".equals(strxml)) {
			return null;
		}

		Map m = new HashMap();

		InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if (children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = XMLUtil.getChildrenText(children);
			}

			m.put(k, v);
		}

		// 鍏抽棴娴�

		in.close();

		return m;
	}

	/**

	 * 鑾峰彇瀛愮粨鐐圭殑xml

	 * 

	 * @param children

	 * @return String

	 */
	@SuppressWarnings({ "rawtypes" })
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if (!children.isEmpty()) {
			Iterator it = children.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if (!list.isEmpty()) {
					sb.append(XMLUtil.getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}

		return sb.toString();
	}

}
