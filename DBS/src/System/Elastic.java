package System;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;	

public class Elastic {

	void spustElastic() throws UnknownHostException{
		BasicConfigurator.configure();
	//Pripojenie
			Client client = TransportClient.builder().build()
			        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
			        //Ano, port je 9300. Elastic ma port 9200 ale transport port je 9300
			        
		//Test query na otestovanie pripojenia, nepojde ak neexistuje index "artists" s prvkom s id "AVQ..."
		/*
		GetResponse response = client.prepareGet("artists", "logs", "AVQTtA8SFUtMQpgHZB2U").get(); //Artist je nazov indexu, logs je typ indexu (elastic vec) a to posledne je id v elasticu
		System.out.println(response.getSourceAsString());
		*/
		
		
		//Vytvorenie indexu s nazvom artists
		SearchResponse sResponse = client.prepareSearch("artists")				//V indexe artists
			        .setTypes("logs")								//Ktory ma typ logs
			        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)					
			        .setQuery(QueryBuilders.matchPhrasePrefixQuery("name", "M"))                	// name = atribut, m = query 
			        //.setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     		// Filter, nepotrebne
			        .setFrom(0).setSize(60).setExplain(true)
			        .execute()
			        .actionGet();
					
	        	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
			List<String> valuesList= new ArrayList<String>();		
	        	for (SearchHit hit : sResponse.getHits()) {                      			//Prechadzanie vysledkom searchy
	        		result.add(hit.getSource());
	        		valuesList.add(hit.getSource().get("name").toString());   			//Chceme atribut "name"	
	        		System.out.println(hit.getSource().get("name").toString());			//Vypis
	        	}
	        
	        
	        
	        //Pridavanie
	        /*
	        IndexResponse iResponse = client.prepareIndex("artists", "logs")				//Do indexu artists typu logs
	                .setSource(jsonBuilder()								//json
	                            .startObject()
	                            	.field("id", 8)								
	                                .field("name", "Yanaginagi") 						//Prve je atribut, druhe je hodnota
	                                .field("signing_year", 2012)                        			//Do atributu "signing_year" hodnota "2012"
	                            .endObject()
	                          )
	                .get();
		*/
			
		//Vymazanie
	        
	        //Ziskanie id vymazaneho prvku
	         sResponse = client.prepareSearch("artists")							//z indexu artist
			        .setTypes("logs")
			        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.matchQuery("name", "Yanaginagi"))               	// Query
			        //.setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     		
			        .setFrom(0).setSize(60).setExplain(true)
			        .execute()
			        .actionGet();
					
	        result = new ArrayList<Map<String, Object>>();
		
	        String id = "";
	        
	        for (SearchHit hit : sResponse.getHits()) {             					//Ziskame id prvku       
	        		id = hit.getId();
	        		System.out.println(id);
	        		
	        }

	        //Samotne vymazanie
	        			//Vymazanie z indexu artists, typu logs, prvku s id 


		
		// on shutdown
		client.close();
	}
}
