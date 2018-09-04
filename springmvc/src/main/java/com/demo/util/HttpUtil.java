package com.demo.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
  
/** 
 * @author Administrator 
 * 
 */  
public class HttpUtil {  
  
    /** 
     * 浣跨敤Get鏂瑰紡鑾峰彇鏁版嵁 
     *  
     * @param url 
     *            URL鍖呮嫭鍙傛暟锛宧ttp://HOST/XX?XX=XX&XXX=XXX 
     * @param charset 
     * @return 
     */  
    public static String sendGet(String url, String charset) {  
        String result = "";  
        BufferedReader in = null;  
        try {  
            URL realUrl = new URL(url);  
            // 鎵撳紑鍜孶RL涔嬮棿鐨勮繛鎺�  
            URLConnection connection = realUrl.openConnection();  
            // 璁剧疆閫氱敤鐨勮姹傚睘鎬�  
            connection.setRequestProperty("accept", "*/*");  
            connection.setRequestProperty("connection", "Keep-Alive");  
            connection.setRequestProperty("user-agent",  
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
            // 寤虹珛瀹為檯鐨勮繛鎺�  
            connection.connect();  
            // 瀹氫箟 BufferedReader杈撳叆娴佹潵璇诲彇URL鐨勫搷搴�  
            in = new BufferedReader(new InputStreamReader(  
                    connection.getInputStream(), charset));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            System.out.println("鍙戦�丟ET璇锋眰鍑虹幇寮傚父锛�" + e);  
            e.printStackTrace();  
        }  
        // 浣跨敤finally鍧楁潵鍏抽棴杈撳叆娴�  
        finally {  
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (Exception e2) {  
                e2.printStackTrace();  
            }  
        }  
        return result;  
    }  
  
    /**  
     * POST璇锋眰锛屽瓧绗︿覆褰㈠紡鏁版嵁  
     * @param url 璇锋眰鍦板潃  
     * @param param 璇锋眰鏁版嵁  
     * @param charset 缂栫爜鏂瑰紡  
     */  
    public static String sendPostUrl(String url, String param, String charset) {  
  
        PrintWriter out = null;  
        BufferedReader in = null;  
        String result = "";  
        try {  
            URL realUrl = new URL(url);  
            // 鎵撳紑鍜孶RL涔嬮棿鐨勮繛鎺�  
            URLConnection conn = realUrl.openConnection();  
            // 璁剧疆閫氱敤鐨勮姹傚睘鎬�  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent",  
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            // 鍙戦�丳OST璇锋眰蹇呴』璁剧疆濡備笅涓よ  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            // 鑾峰彇URLConnection瀵硅薄瀵瑰簲鐨勮緭鍑烘祦  
            out = new PrintWriter(conn.getOutputStream());  
            // 鍙戦�佽姹傚弬鏁�  
            out.print(param);  
            // flush杈撳嚭娴佺殑缂撳啿  
            out.flush();  
            // 瀹氫箟BufferedReader杈撳叆娴佹潵璇诲彇URL鐨勫搷搴�  
            in = new BufferedReader(new InputStreamReader(  
                    conn.getInputStream(), charset));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            System.out.println("鍙戦�� POST 璇锋眰鍑虹幇寮傚父锛�" + e);  
            e.printStackTrace();  
        }  
        // 浣跨敤finally鍧楁潵鍏抽棴杈撳嚭娴併�佽緭鍏ユ祦  
        finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }  
    /**  
     * POST璇锋眰锛孧ap褰㈠紡鏁版嵁  
     * @param url 璇锋眰鍦板潃  
     * @param param 璇锋眰鏁版嵁  
     * @param charset 缂栫爜鏂瑰紡  
     */  
    public static String sendPost(String url, Map<String, String> param,  
            String charset) {  
  
        StringBuffer buffer = new StringBuffer();  
        if (param != null && !param.isEmpty()) {  
            for (Map.Entry<String, String> entry : param.entrySet()) {  
                buffer.append(entry.getKey()).append("=")  
                        .append(URLEncoder.encode(entry.getValue()))  
                        .append("&");  
  
            }  
        }  
        buffer.deleteCharAt(buffer.length() - 1);  
  
        PrintWriter out = null;  
        BufferedReader in = null;  
        String result = "";  
        try {  
            URL realUrl = new URL(url);  
            // 鎵撳紑鍜孶RL涔嬮棿鐨勮繛鎺�  
            URLConnection conn = realUrl.openConnection();  
            // 璁剧疆閫氱敤鐨勮姹傚睘鎬�  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent",  
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
            // 鍙戦�丳OST璇锋眰蹇呴』璁剧疆濡備笅涓よ  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            // 鑾峰彇URLConnection瀵硅薄瀵瑰簲鐨勮緭鍑烘祦  
            out = new PrintWriter(conn.getOutputStream());  
            // 鍙戦�佽姹傚弬鏁�  
            out.print(buffer);  
            // flush杈撳嚭娴佺殑缂撳啿  
            out.flush();  
            // 瀹氫箟BufferedReader杈撳叆娴佹潵璇诲彇URL鐨勫搷搴�  
            in = new BufferedReader(new InputStreamReader(  
                    conn.getInputStream(), charset));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            System.out.println("鍙戦�� POST 璇锋眰鍑虹幇寮傚父锛�" + e);  
            e.printStackTrace();  
        }  
        // 浣跨敤finally鍧楁潵鍏抽棴杈撳嚭娴併�佽緭鍏ユ祦  
        finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }  
    
    /**
     * 鍙戦�乸ost璇锋眰锛屼腑鏂囩紪鐮佹甯�
     * @param url
     * @param param
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String sendPost(String url ,String param) throws Exception{
    	String body = "";  
        //鍒涘缓httpclient瀵硅薄  
        CloseableHttpClient client = HttpClients.createDefault();  
        //鍒涘缓post鏂瑰紡璇锋眰瀵硅薄  
        HttpPost httpPost = new HttpPost(url);  
    	
        HttpEntity entity = new StringEntity(param,Charset.forName("UTF-8"));
        
        httpPost.setEntity(entity);
        
        //璁剧疆header淇℃伅  
        //鎸囧畾鎶ユ枃澶淬�怌ontent-type銆戙�併�怳ser-Agent銆�  
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");  
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
        
        //鎵ц璇锋眰鎿嶄綔锛屽苟鎷垮埌缁撴灉锛堝悓姝ラ樆濉烇級  
        CloseableHttpResponse response = client.execute(httpPost);  
        //鑾峰彇缁撴灉瀹炰綋  
        HttpEntity r_entity = response.getEntity();  
        if (r_entity != null) {  
            //鎸夋寚瀹氱紪鐮佽浆鎹㈢粨鏋滃疄浣撲负String绫诲瀷  
            body = EntityUtils.toString(r_entity, "utf-8");  
        }  
        EntityUtils.consume(entity);  
        //閲婃斁閾炬帴  
        response.close();  
        return body;  
    }
    
    public static String postXml(String url,String xml) throws ClientProtocolException, IOException{
    	String body = "";  
        //鍒涘缓httpclient瀵硅薄  
        CloseableHttpClient client = HttpClients.createDefault();  
        //鍒涘缓post鏂瑰紡璇锋眰瀵硅薄  
        HttpPost httpPost = new HttpPost(url);  
    	
        HttpEntity entity = new StringEntity(xml,Charset.forName("UTF-8"));
        
        httpPost.setEntity(entity);
        
        //璁剧疆header淇℃伅  
        //鎸囧畾鎶ユ枃澶淬�怌ontent-type銆戙�併�怳ser-Agent銆�  
        httpPost.setHeader("Content-type", "text/xml");  
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
        
        //鎵ц璇锋眰鎿嶄綔锛屽苟鎷垮埌缁撴灉锛堝悓姝ラ樆濉烇級  
        CloseableHttpResponse response = client.execute(httpPost);  
        //鑾峰彇缁撴灉瀹炰綋  
        HttpEntity r_entity = response.getEntity();  
        if (r_entity != null) {  
            //鎸夋寚瀹氱紪鐮佽浆鎹㈢粨鏋滃疄浣撲负String绫诲瀷  
            body = EntityUtils.toString(r_entity, "utf-8");  
        }  
        EntityUtils.consume(entity);  
        //閲婃斁閾炬帴  
        response.close();  
        return body;  
    }
    
  /*  public static void main(String[] args) {
    	String s;
    	SortedMap<Object, Object> map = new TreeMap<>();
    	map.put("appid", "wx241d1d1594e25771");
    	map.put("mch_id", "1330205501");
    	map.put("device_info", "WEB");
    	map.put("nonce_str", "ibuaiVcKdpRxkhJA");
    	map.put("body","娴嬭瘯鏀粯");
    	map.put("detail","娴嬭瘯鏀粯璇︽儏");
    	map.put("attach","闄勫姞鍐呭");
    	map.put("out_trade_no","40288a616033c61d0163016673020796");//璁㈠崟鍙�
    	map.put("total_fee","1");//閲戦  鎸夊垎璁＄畻   1 = 1鍒�
    	//map.put("spbill_create_ip", WechatPayCommonUtil.getIpAddr(request));
    	map.put("spbill_create_ip", "121.32.166.120");
    	map.put("notify_url", "http://www.caibab.com/onlinepay/createOrderNotify");
    	map.put("trade_type", "JSAPI");
    	map.put("openid", "o3kTuwwjC29a_oSbUx_SNOrzgXs8");
    	map.put("sign",WechatPayCommonUtil.createSign("UTF-8", map, "40288a616033c61d0163016673020796"));
		try {
			s = postXml("https://api.mch.weixin.qq.com/pay/unifiedorder",WechatPayCommonUtil.getRequestXml(map));
	    	System.out.println(s);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
} 