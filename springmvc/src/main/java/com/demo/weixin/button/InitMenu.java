package com.demo.weixin.button;

/**
 * ��װ�˵�
 * @author Administrator
 *
 */
public class InitMenu {
	public static Menu initMenu() {
		ClickButton button11 = new ClickButton();
		button11.setName("click��ť1");
		button11.setType("click");
		button11.setKey("11");
		
		ClickButton button12 = new ClickButton();
		button12.setName("click��ť2");
		button12.setType("click");
		button12.setKey("12");
		
		ViewButton button21 = new ViewButton();
		button21.setName("view��ť1");
		button21.setType("view");
		button21.setUrl("http://www.caibab.com");
		
		ViewButton button22 = new ViewButton();
		button22.setName("view��ť2");
		button22.setType("view");
		button22.setUrl("http://www.baidu.com");
		
		ClickButton button31 = new ClickButton();
		button31.setKey("31");
		button31.setName("clickButton31");
		button31.setType("click");
		
		Button button1 = new Button();
		button1.setName("Clickmenu1");//��11/12����button��Ϊ�����˵���װ��һ��һ���˵�
		button1.setSub_button(new Button[] {button11,button12});
		
		Button button2 = new Button();
		button2.setName("hrefMenu2");
		button2.setSub_button(new Button[] {button21,button22});//��21/22����button��Ϊ�����˵���װ�ڶ��������˵�
		
		Menu menu = new Menu();
		menu.setButtons(new Button[] {button1,button2,button31});
		return menu;
	}
}
