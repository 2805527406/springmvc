package com.demo.util;

import java.security.MessageDigest;
import java.util.Date;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class WeixinUtil {
	
	private static Logger logger = Logger.getLogger(WeixinUtil.class) ;
	private static String accessToken = null;
	private static Date refreshDate = null;
	private static Integer expires_in = null;
	public static String appId = Configer.getProperty("wx.appId");
	public static String appsecret = Configer.getProperty("wx.appsecret");
	public static String redirect_url = "http://www.caibab.com/onlinepay/weixinRedirect";
	private static final String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+appsecret;
	public static final String user_accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+appsecret+"&code=CODE&grant_type=authorization_code";
	private static final String getWeixinUserInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN ";
	private static final String getJSApiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	private static final String getJSApiGetImgUrl = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
	//创建菜单
	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	private static String jsApiTicket = null;
	private static Date ticketRefreshDate = null;
	private static Integer ticketExpires_in = null;
	/**
	 * 鑾峰彇accessToken
	 * @return
	 */
	public static String getAccessToken(){
		try{
			if(accessToken==null||refreshDate==null){ //绗竴娆¤幏鍙�
				JSONObject json = JSONObject.parseObject(HttpUtil.sendGet(accessTokenUrl, "UTF-8"));
				refreshDate = new Date();
				accessToken = json.getString("access_token");
				expires_in = json.getInteger("expires_in");
				return accessToken;
			}else{
				Date nowDate = new Date();
				int c = (int)(nowDate.getTime() - refreshDate.getTime())/1000; //鍒锋柊鏃堕棿涓庡綋鍓嶆椂闂寸浉宸鏁�
				if(c>expires_in/3*2){		// 澶т簬鏈夋晥鏃堕棿鐨勪笁鍒嗕箣浜�
					JSONObject json = JSONObject.parseObject(HttpUtil.sendGet(accessTokenUrl, "UTF-8"));
					refreshDate = new Date();
					accessToken = json.getString("access_token");
					expires_in = json.getInteger("expires_in");
					return accessToken;
				}else{
					logger.info("鍒锋柊鏃堕棿涓庡綋鍓嶆椂闂寸浉宸鏁�  :" + c);
					return accessToken;
				}
			}
		}catch (Exception e) {
			logger.error("鑾峰彇寰俊accessToken鍑洪敊锛�");
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	/**
	 * 鑾峰彇鐢ㄦ埛accessToken
	 * @param code
	 * @return
	 */
//	public static UserAccessTokenRet getUserAccessTokenAndOpenId(String code){
//		try{
//			
//			String url = user_accessTokenUrl.replace("CODE", code);
//			JSONObject json = JSONObject.parseObject(HttpUtil.sendGet(url, "UTF-8"));
//			UserAccessTokenRet ret = new UserAccessTokenRet();
//			ret.setOpenid(json.getString("openid"));
//			ret.setAccess_token(json.getString("access_token"));
//			ret.setExpires_in(json.getString("expires_in"));
//			ret.setScope(json.getString("scope"));
//			ret.setRefresh_token(json.getString("refresh_token"));
//			return ret;
//		
//		}catch (Exception e) {
//			logger.error("鑾峰彇鐢ㄦ埛accessToken鍑洪敊锛�");
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	/**
	 * 鑾峰彇寰俊鐢ㄦ埛淇℃伅
	 */
	public static JSONObject getWeixinUserInfoByOpenId(String openId){
		String url = getWeixinUserInfoUrl;
		url = url.replace("ACCESS_TOKEN", getAccessToken());
		url = url.replace("OPENID", openId);
		JSONObject json = JSONObject.parseObject(HttpUtil.sendGet(url, "UTF-8"));
		
		return json;//鏆傛湭澶勭悊璇︾粏淇℃伅
	}
	
	
	public static String getJSApiTicket(){
		String url = getJSApiTicketUrl;
		url = url.replace("ACCESS_TOKEN", getAccessToken());
		try{
			if(jsApiTicket==null||ticketRefreshDate==null){ //绗竴娆¤幏鍙�
				JSONObject json = JSONObject.parseObject(HttpUtil.sendGet(url, "UTF-8"));
				ticketRefreshDate = new Date();
				jsApiTicket = json.getString("ticket");
				ticketExpires_in = json.getInteger("expires_in");
				return jsApiTicket;
			}else{
				Date nowDate = new Date();
				int c = (int)(nowDate.getTime() - ticketRefreshDate.getTime())/1000; //鍒锋柊鏃堕棿涓庡綋鍓嶆椂闂寸浉宸鏁�
				if(c>ticketExpires_in/3*2){		// 澶т簬鏈夋晥鏃堕棿鐨勪笁鍒嗕箣浜�
					JSONObject json = JSONObject.parseObject(HttpUtil.sendGet(url, "UTF-8"));
					ticketRefreshDate = new Date();
					jsApiTicket = json.getString("jsApiTicket");
					ticketExpires_in = json.getInteger("expires_in");
					return jsApiTicket;
				}else{
					logger.info("鍒锋柊鏃堕棿涓庡綋鍓嶆椂闂寸浉宸鏁�  :" + c);
					return jsApiTicket;
				}
			}
		}catch (Exception e) {
			logger.error("鑾峰彇寰俊jsApiTicket鍑洪敊锛�");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * js-sdk 鎺堟潈
	 * @param request
	 * @return
	 */
	public static SortedMap<Object,Object> getJSApiRet(HttpServletRequest request){
		StringBuffer url = request.getRequestURL();
		Enumeration<String> paramNames = request.getParameterNames();
		String format_url = url.toString();
		String firstParam =  paramNames.nextElement();
		format_url = format_url + "?" +firstParam + "=" + request.getParameter(firstParam);
		while(paramNames.hasMoreElements()){
			String param = paramNames.nextElement();
			format_url = format_url +"&"+ param+ "=" +request.getParameter(param) ;
		}
		//		model.addAttribute("name", "123");
		SortedMap<Object, Object> params = new TreeMap<>();
		String randomStr = UUID.randomUUID().toString().replace("-", "");
		String dateCur ="" + System.currentTimeMillis()/1000;
		String jsApiTicket = null;
		jsApiTicket = WeixinUtil.getJSApiTicket();
		String signature = "jsapi_ticket="+jsApiTicket;
        signature += "&noncestr="+randomStr;
        signature += "&timestamp="+dateCur;
        signature += "&url="+format_url;
        try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(signature.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.put("jsapi_ticket",jsApiTicket);
		params.put("nonceStr",randomStr);
		params.put("timestamp",dateCur);
		params.put("signature", signature);
		params.put("appId", Configer.getProperty("wx.appId"));
		
		return params;
	}
	
	public static int createMenu(String menu) throws Exception {
		int result = 0;
		String accessToken = getAccessToken();
		System.out.println(accessToken);
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN",accessToken);
		String obj = HttpUtil.sendPost(url, menu);
		JSONObject jsonObject = JSONObject.parseObject(obj);
		if(jsonObject != null ) {
			result = jsonObject.getIntValue("errcode");
		}
		return result;
	}
	
	
	private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
