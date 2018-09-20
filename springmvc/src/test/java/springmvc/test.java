package springmvc;

import com.alibaba.fastjson.JSONArray;
import com.demo.util.WeixinUtil;
import com.demo.weixin.button.InitMenu;

public class test {
	public static void main(String[] args) {
		String sum = "1231.123456789";
		System.out.println(sum.substring(0,sum.indexOf(".")+3));
	}
}
