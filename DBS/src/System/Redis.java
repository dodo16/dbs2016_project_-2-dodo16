package System;

import redis.clients.jedis.Jedis;

public class Redis {
	private Jedis jedis;

	public void connection(){
		jedis = new Jedis("localhost",6379); 
	}
	
	public void vytvorObjednavku(String datum){
		jedis.select(0);
	    jedis.sadd("1", datum,"Vytvorenie objednávky");	
	}
	
	public void zmenHeslo(String datum){
		jedis.select(0);
	    jedis.sadd("2", datum,"Zmena hesla");	
	}
	
	public void zmenProfil(String datum){
		jedis.select(0);
	    jedis.sadd("3", datum,"Zmena profilu");	
	}
	
	public void vratDatabazu(){
		jedis.select(0);
		System.out.println(jedis.smembers("3"));
		/*Set<String> names = jedis.keys("3");
	
	    java.util.Iterator<String> it = names.iterator();
	    while(it.hasNext()) {
	        String s = it.next();
	        System.out.println(s + " : " + jedis.smembers("3"));
	    }*/
		
	}
}
