package com.demo.solr.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;



public class SolrDemo {
	private static final String solrUrl="http://192.168.232.130:8983/solr/new_core";
	protected static HttpSolrClient connectSolr() {
		return new HttpSolrClient.Builder(solrUrl)
				.withConnectionTimeout(10000)
				.withSocketTimeout(60000)
				.build();
	}
	
	//查询
	public static void querySolr() throws SolrServerException, IOException {
		HttpSolrClient solrClient = connectSolr();
		Map<String,String> queryParamMap = new HashMap<>();
		queryParamMap.put("q","*:*");
		MapSolrParams queryParams = new MapSolrParams(queryParamMap);
		QueryResponse response = solrClient.query(queryParams);
		SolrDocumentList documentList = response.getResults();
		   //[6]内容遍历
        for(SolrDocument doc : documentList) {
              System.out.println("id:"+doc.get("id")
                 +"\t name:"+doc.get("name")
                +"\t sex:"+doc.get("sex"));
        }
        solrClient.close();
	}
	
	//查询二
	public static void querySolrEasy() throws SolrServerException, IOException {
		 //[2]封装查询参数
		SolrQuery solrQuery = new SolrQuery("*:*");
		 //[3]添加需要回显得内容
		solrQuery.addField("id");
		solrQuery.addField("name");
		solrQuery.addField("sex");
		solrQuery.setRows(20);//设置每页显示多少条
		//[4]执行查询返回QueryResponse
		QueryResponse response = connectSolr().query(solrQuery);
		  //[5]获取doc文档
		 SolrDocumentList documentList = response.getResults();
		 //[6]内容遍历
        for(SolrDocument doc : documentList) {
              System.out.println("id:"+doc.get("id")
                 +"\t name:"+doc.get("name")
                +"\t sex:"+doc.get("sex"));
        }
        connectSolr().close();
	}
	
	//查询三--设置高亮
	public static void queryToHighlighting() throws SolrServerException, IOException {
		String keywords="张";
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("name:" + keywords);
		solrQuery.setStart(1);
		solrQuery.setRows(20);
		boolean isHighlighting=!StringUtils.equals("*",keywords) && StringUtils.isNotBlank(keywords);
		if(isHighlighting) { // 设置高亮
			solrQuery.setHighlight(true);
			solrQuery.addHighlightField("name");// 开启高亮组件
			solrQuery.addHighlightField("sex");// 高亮字段
			solrQuery.setHighlightSimplePre("<span style='color:red;'>");// 标记，高亮关键字前缀
			solrQuery.setHighlightSimplePost("</span>");// 后缀
		}
		QueryResponse response = connectSolr().query(solrQuery);
		SolrDocumentList documentList = response.getResults();
		List<Map<String,Object>> objs = new ArrayList<>();
		for(SolrDocument doc : documentList) {
			Map<String,Object> map = new HashMap<>();
			map.put("id",doc.get("id"));
			map.put("name",doc.get("name"));
			map.put("sex",doc.get("sex"));
			objs.add(map);
		}
		if(isHighlighting) {
			// 将高亮的标题数据写回到数据对象中
			  Map<String, Map<String, List<String>>> map = response.getHighlighting();
			  for(Map.Entry<String, Map<String, List<String>>> highlighting:map.entrySet()) {
				  for(Map<String,Object> obj:objs) {
					  if(highlighting.getKey().equals("id")) {
						  continue;
					  }
					  if(highlighting.getValue().get("name")!= null) {
						  obj.put("name", StringUtils.join(highlighting.getValue().get("name"), ""));
					  }
					  if(highlighting.getValue().get("sex")!=null) {
						  obj.put("sex",StringUtils.join(highlighting.getValue().get("sex"),""));
					  }
				  }
			  }
		}
		  for(Map<String,Object> obj : objs) {
              System.out.println("id:"+obj.get("id")
                 +"\t name:"+obj.get("name")
                +"\t sex:"+obj.get("sex"));
		  }
	}
	
	//删除
	public static void delSolr() throws SolrServerException, IOException {
//		connectSolr().deleteById("6");
		connectSolr().deleteByQuery("id:6");
		connectSolr().commit();
		connectSolr().close();
	}
	
	//删除 list集合
	public static void delSolrByList() throws SolrServerException, IOException {
		List<String> ids = new ArrayList<>();
		ids.add("6");
		connectSolr().deleteById(ids);
		connectSolr().commit();
		connectSolr().close();
	}
	
	//添加
	public static void addSolr() throws SolrServerException, IOException {
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id","6");
		document.addField("name","add Solr");
		document.addField("sex","M");
		UpdateResponse updateResponse = connectSolr().add(document);
		System.out.println(updateResponse.getElapsedTime());
		connectSolr().commit();
		connectSolr().close();
	}
	public static void main(String[] args) {
		try {
			SolrDemo.addSolr();
			System.out.println("--------------");
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}
}
