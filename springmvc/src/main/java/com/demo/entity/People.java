package com.demo.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="people")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class People implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8555523640042764544L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String sex;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * jsonData.put("bidPrice",quote.getSumPrice().substring(0,quote.getSumPrice().indexOf(".")+3)+"Ԫ");
	 * @param args
	 */
	
	public static void main(String[] args) {
		String string="240.0";
		System.out.println(string.indexOf("."));
		if(string.indexOf(".")>0) {//
			System.out.println(string.split(".")[1].length());
			System.out.println(string.charAt(string.indexOf(".")+1));
//			
//			if()) {
//				System.out.println(string.substring(0,string.indexOf(".")+3));
//			}else {
//				System.out.println(string);
//			}
		}
//		System.out.println(string.substring(0,string.indexOf(".")+3));
	}
	
}
