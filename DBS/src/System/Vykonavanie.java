package System;

import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import redis.clients.jedis.Jedis;


import GUI.Login;
import GUI.Rozhranie;
import Pouzivatelia.Pouzivatel;


public class Vykonavanie {
   static Login gui = new Login();
   static Rozhranie prihlaseny= new Rozhranie();
   static Pouzivatel pouzivatel;
   static Elastic elastic = new Elastic();
   
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/DBS";

   static final String USER = "root";
   static final String PASS = "TELEVIZOR123";
   static String sql;
   static ResultSet rs ;
   private static Jedis jedis;
   static Connection conn = null;
   static Statement stmt = null;
   private PreparedStatement stmt2;
   final static String kluc = "0";
   String key = "1";
   
   
   public static void main(String[] args)throws IOException, ClassNotFoundException, SQLException  {
   
   gui.initialize();
   try{
      Class.forName("com.mysql.jdbc.Driver");

      conn = DriverManager.getConnection(DB_URL,USER,PASS);      
   
   }catch(SQLException se){
      se.printStackTrace();
   }catch(Exception e){
      e.printStackTrace();
   }
   connection();
   
   //elastic.spustElastic();
}
   static String aktualnyCas(){
	DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

	Date today = Calendar.getInstance().getTime();        
	return df.format(today);
   }
   
   public boolean prihlasenie(String name,String pass) throws SQLException{
	   PreparedStatement stmt = conn.prepareStatement("SELECT pass, typ FROM Osoba WHERE login = ?");    
	   stmt.setString(1, name);
	   rs = stmt.executeQuery();
	   if(rs.next()){
		   if (rs.getString("pass").equals(pass)){
			   pouzivatel = new Pouzivatel(name);
			   prihlaseny.initialize(rs.getString("typ"));
			   return true;
		   }
		   else
			   return false;
	   }
	   return false;
   }
   
   public String vratCisloObjednavky() throws SQLException{
	   int cislo = 0;
	   PreparedStatement stmt = conn.prepareStatement("SELECT cislo_obj FROM objednavka ORDER BY cislo_obj DESC");    
	   rs = stmt.executeQuery();
	   if(rs.next())
		   cislo = Integer.parseInt(rs.getString("cislo_obj"));
	   cislo++;
	   
	return Integer.toString(cislo);   
   }
   
   public void pridatPouzivatela(String login,String pass, String typ) throws SQLException{
	   PreparedStatement stmt = conn.prepareStatement("INSERT INTO osoba (login,pass,typ) VALUES(?,?,?)");    
	   stmt.setString(1, login);
	   stmt.setString(2, pass);
	   stmt.setString(3, typ);
	   stmt.execute();
	   
	   PreparedStatement stmt1 = conn.prepareStatement("SELECT id FROM osoba where login = ?");
	   stmt1.setString(1, login);
	   ResultSet sr = stmt1.executeQuery();
	   
	   PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO adresa (osobaID) VALUES (?)");    
	   if(sr.next())
		   stmt2.setInt(1,sr.getInt("id"));

	   stmt2.execute();
   }
   
   public void pridatVozidlo(String typ,String znacka, String spz, String pasaz, String login) throws SQLException{
	   int id = -1;
	   PreparedStatement stmt1 = conn.prepareStatement("SELECT id FROM osoba where login = ?");
	   stmt1.setString(1, login);
	   ResultSet sr = stmt1.executeQuery();
	   
	   
	   PreparedStatement stmt = conn.prepareStatement("INSERT INTO taxik (typAuta, znacka, SPZ, pocpasaz, osobaID) VALUES(?,?,?,?,?)");    
	   stmt.setString(1, typ);
	   stmt.setString(2, znacka);
	   stmt.setString(3, spz);
	   stmt.setString(4, pasaz);
	   if(sr.next())
		   stmt.setInt(5,sr.getInt("id"));
	   stmt.execute();
	   
	   PreparedStatement stmt2 = conn.prepareStatement("SELECT id FROM taxik where SPZ = ?");
	   stmt2.setString(1, spz);
	   rs = stmt2.executeQuery();
	   if(rs.next())
		   id = rs.getInt("id");
	   
	   stmt1 = conn.prepareStatement("INSERT INTO servis (taxikID) VALUES(?)");    
	   stmt1.setInt(1, id);

	   stmt1.execute();
	   
	   stmt = conn.prepareStatement("INSERT INTO poistenie (taxikID) VALUES(?)");    
	   stmt.setInt(1, id);

	   stmt.execute();
   }
   
   public void vytvorObjednavku(String cislo,String nastup, String vystup, String cas, String den) throws SQLException{
	   PreparedStatement stmt = conn.prepareStatement("INSERT INTO Objednavka (cislo_obj,nastup,ciel,casVyzdv,datum,vybavena) VALUES (?,?,?,?,STR_TO_DATE(?, '%d-%m-%Y'),false)");    
	   stmt.setString(1, cislo);
	   stmt.setString(2, nastup);
	   stmt.setString(3, vystup);
	   stmt.setString(4, cas);
	   stmt.setString(5, den);
	   stmt.execute();
	   
	   vytvorObjednavku(aktualnyCas(),cislo);
	   
	   PreparedStatement stmt1 = conn.prepareStatement("SELECT id FROM osoba where login = ?");
	   stmt1.setString(1, pouzivatel.getLogin());
	   rs = stmt1.executeQuery();
	   
	   PreparedStatement stmt2 = conn.prepareStatement("SELECT id FROM objednavka where cislo_obj = ?");
	   stmt2.setString(1, cislo);
	   ResultSet sr = stmt2.executeQuery();
	   
	   stmt = conn.prepareStatement("INSERT INTO objednavkaosoby (osobaID,objID) VALUES (?,?)");
	   if(rs.next())
		   stmt.setInt(1,rs.getInt("id"));
	   if(sr.next())
		   stmt.setInt(2,sr.getInt("id"));
	   stmt.execute();
	  
   }
   
   public ResultSet zobrPridObjed(String stav) throws SQLException{
	   PreparedStatement stmt1 = conn.prepareStatement("SELECT id FROM osoba where login = ?");
	   stmt1.setString(1, pouzivatel.getLogin());
	   ResultSet sr = stmt1.executeQuery();
	   
	   if (stav.equals("všetky")){
		   stmt2 = conn.prepareStatement(
				   	"SELECT o.cislo_obj, o.nastup, o.ciel, o.casVyzdv, o.datum, os.priezvisko, os.login, o.vybavena "
			   		+ "FROM Objednavka o, objednavkaosoby oo, osoba os "
			   		+ "where "
			   		+ "oo.osobaID = os.ID "
			   		+ "AND "
			   		+ "oo.objID = o.ID "
			   		+ "AND "
			   		+ "oo.vybavilID = ? "
			   		+ "GROUP BY o.cislo_obj"); 
		   if(sr.next())
			   stmt2.setInt(1,sr.getInt("id"));
		   rs = stmt2.executeQuery();
	   }
	   else {
		   if (stav.equals("vybavená")){
		   stmt2 = conn.prepareStatement(
				   	"SELECT o.cislo_obj, o.nastup, o.ciel, o.casVyzdv, o.datum, os.priezvisko, os.login, o.vybavena "
			   		+ "FROM Objednavka o, objednavkaosoby oo, osoba os "
			   		+ "where "
			   		+ "o.vybavena = true "
			   		+ "AND "
			   		+ "oo.osobaID = os.ID "
			   		+ "AND "
			   		+ "oo.objID = o.ID "
			   		+ "AND "
			   		+ "oo.vybavilID = ? "
			   		+ "GROUP BY o.cislo_obj"); 
		   if(sr.next())
			   stmt2.setInt(1,sr.getInt("id"));
		   }
		   else
			   stmt2 = conn.prepareStatement(
					   	"SELECT o.cislo_obj, o.nastup, o.ciel, o.casVyzdv, o.datum, os.priezvisko, os.login, o.vybavena "
				   		+ "FROM Objednavka o, objednavkaosoby oo, osoba os "
				   		+ "where "
				   		+ "oo.osobaID = os.ID "
				   		+ "AND "
				   		+ "oo.objID = o.ID "
				   		+ "AND "
				   		+ "o.vybavena = false "
				   		+ "AND "
				   		+ "oo.vybavilID = ? "
				   		+ "GROUP BY o.cislo_obj"); 
		   if(sr.next())
			   stmt2.setInt(1,sr.getInt("id"));
		   rs = stmt2.executeQuery();
	   }
	   
	   return rs;
   }
   
   public ResultSet zobrObjed(String stav) throws SQLException{
	   if (stav.equals("všetky")){
		   stmt2 = conn.prepareStatement(
				   	"SELECT o.cislo_obj, o.nastup, o.ciel, o.casVyzdv, o.datum, os.priezvisko, os.login, o.vybavena "
			   		+ "FROM Objednavka o, objednavkaosoby oo, osoba os "
			   		+ "where "
			   		+ "oo.osobaID = os.ID "
			   		+ "AND "
			   		+ "oo.objID = o.ID "
			   		+ "GROUP BY o.cislo_obj");  
		   rs = stmt2.executeQuery();
	   }
	   else {
		   if (stav.equals("vybavená"))
		   stmt2 = conn.prepareStatement(
				   	"SELECT o.cislo_obj, o.nastup, o.ciel, o.casVyzdv, o.datum, os.priezvisko, os.login, o.vybavena "
			   		+ "FROM Objednavka o, objednavkaosoby oo, osoba os "
			   		+ "where "
			   		+ "o.vybavena = true "
			   		+ "AND "
			   		+ "oo.osobaID = os.ID "
			   		+ "AND "
			   		+ "oo.objID = o.ID "
			   		+ "GROUP BY o.cislo_obj"); 
		   else
			   stmt2 = conn.prepareStatement(
					   	"SELECT o.cislo_obj, o.nastup, o.ciel, o.casVyzdv, o.datum, os.priezvisko, os.login, o.vybavena "
				   		+ "FROM Objednavka o, objednavkaosoby oo, osoba os "
				   		+ "where "
				   		+ "oo.osobaID = os.ID "
				   		+ "AND "
				   		+ "oo.objID = o.ID "
				   		+ "AND "
				   		+ "o.vybavena = false "
				   		+ "GROUP BY o.cislo_obj"); 

		   rs = stmt2.executeQuery();
	   }
	   
	   return rs;
	   
   }
   
   public void upravObj(String cisloObj) throws SQLException{
	   try{
	   stmt2 = conn.prepareStatement("UPDATE objednavka SET vybavena = true WHERE cislo_obj = ?"); 
	   stmt2.setString(1,cisloObj);
	   
	   conn.setAutoCommit(false);
	   stmt2.executeUpdate();
	   conn.commit();
   		}  catch (SQLException e) {
		if (conn != null)
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		e.printStackTrace();
	} finally {

       try {
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
   }
   }
   
   public void pridelObjedn(String cisloObj, String login) throws SQLException{
	  try {
	   PreparedStatement stmt1 = conn.prepareStatement("SELECT id FROM osoba where login = ?");
	   stmt1.setString(1, login);
	   
	   conn.setAutoCommit(false);
	   rs = stmt1.executeQuery();
	   
	   PreparedStatement stmt3 = conn.prepareStatement("SELECT id FROM objednavka where cislo_obj = ?");
	   stmt3.setString(1, cisloObj);
	   ResultSet sr = stmt3.executeQuery();
	   
	   stmt2 = conn.prepareStatement("UPDATE objednavkaOsoby SET vybavilID = ? WHERE objID = ?"); 
	   if(rs.next())
		   stmt2.setInt(1,rs.getInt("id"));
	   if(sr.next())
		   stmt2.setInt(2,sr.getInt("id"));
	   stmt2.executeUpdate();
	   conn.commit();
	  }  catch (SQLException e) {
			if (conn != null)
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			e.printStackTrace();
		} finally {

	       try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	   }
   }
   
   public ResultSet aktTaxik() throws SQLException{
	   PreparedStatement stmt = conn.prepareStatement(
			   	"SELECT login FROM osoba "
		   		+ "WHERE "
		   		+ "Typ = ?");  
	   stmt.setString(1, "taxikar");
	   rs = stmt.executeQuery();
	   return rs;
   }
   
   public ResultSet aktTaxikBezVoz() throws SQLException{
	   PreparedStatement stmt = conn.prepareStatement(
			   	"SELECT login FROM osoba "
		   		+ "WHERE "
		   		+ "Typ = ? ");  
	   stmt.setString(1, "taxikar");
	   rs = stmt.executeQuery();
	   return rs;
   }
   
   public ResultSet zobrPouzi(String typ) throws SQLException{
	   if(typ.equals("všetci")){
		   PreparedStatement stmt = conn.prepareStatement(
				   	"SELECT meno, priezvisko, telefon, email, login, cisloUctu, typ "
			   		+ "FROM osoba ");  
		   rs = stmt.executeQuery();
	   }
	   else if(typ.equals("dispecer")){
		   PreparedStatement stmt = conn.prepareStatement(
				   	"SELECT meno, priezvisko, telefon, email, login, cisloUctu, typ "
			   		+ "FROM osoba "
			   		+ "WHERE "
			   		+ "Typ = ?");  
		   stmt.setString(1, "dispecer");
		   rs = stmt.executeQuery();
	   }
	   else if(typ.equals("taxikar")){
		   PreparedStatement stmt = conn.prepareStatement(
				   	"SELECT meno, priezvisko, telefon, email, login, cisloUctu, typ "
			   		+ "FROM osoba "
			   		+ "WHERE "
			   		+ "Typ = ?");  
		   stmt.setString(1, "taxikar");
		   rs = stmt.executeQuery();
	   }
	   else if(typ.equals("zakaznik")){
		   PreparedStatement stmt = conn.prepareStatement(
				   	"SELECT meno, priezvisko, telefon, email, login, cisloUctu, typ "
			   		+ "FROM osoba "
			   		+ "WHERE "
			   		+ "Typ = ?");  
		   stmt.setString(1, "zakaznik");
		   rs = stmt.executeQuery();
	   }
	   return rs;
	   
   }
   
   public ResultSet zobrVozidla() throws SQLException{   
	   PreparedStatement stmt = conn.prepareStatement(
			   	"SELECT t.typAuta, t.znacka, t.SPZ, t.pocPasaz, o.login, p.dlzkaVRokoch, p.cenaZaMesiac, "
			   	+ "s.nazov, s.email, s.telefon  FROM taxik t,osoba o, servis s, poistenie p "
			   	+ "WHERE "
			   	+ "t.osobaID = o.id "
			   	+ "AND "
			   	+ "t.id = s.taxikID "
			   	+ "AND "
			   	+ "t.id = p.taxikID");  
	   rs = stmt.executeQuery();
	   return rs;
	   
   }
   
   public void registrovanie(String meno, String heslo) throws SQLException{
	   PreparedStatement stmt = conn.prepareStatement("INSERT INTO Osoba (login,pass,typ) VALUES (?,?,?)");    
	   stmt.setString(1, meno);
	   stmt.setString(2, heslo);
	   stmt.setString(3, "zakaznik");
	   stmt.execute();	
	   
	   PreparedStatement stmt1 = conn.prepareStatement("SELECT id FROM osoba where login = ?");
	   stmt1.setString(1, meno);
	   ResultSet sr = stmt1.executeQuery();
	   
	   PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO adresa (osobaID) VALUES (?)");    
	   if(sr.next())
		   stmt2.setInt(1,sr.getInt("id"));

	   stmt2.execute();
   }
   
   public String vratLogin(){
	   return pouzivatel.getLogin();
   }
   
   public String aktualnyDen(){
		DateFormat datumFormat = new SimpleDateFormat("dd.MM.yyyy"); 
		Date datum = new Date();
		return datumFormat.format(datum);
	}
   
   public boolean zmenaHesla(String passSt,String passNo,String passNo1) throws SQLException{
	   try{
		   zmenHeslo(aktualnyCas(),passSt,passNo);
		   PreparedStatement stmt = conn.prepareStatement("SELECT pass, typ FROM Osoba WHERE login = ?");    
		   stmt.setString(1, pouzivatel.getLogin());

		   conn.setAutoCommit(false);
		   rs = stmt.executeQuery();
		   if(rs.next()){
			   if (rs.getString("pass").equals(passSt)){
				   stmt = conn.prepareStatement("UPDATE Osoba SET pass = ? WHERE login = ?;"); 
				   stmt.setString(1, passNo);
				   stmt.setString(2, pouzivatel.getLogin());
				   stmt.executeUpdate();
				   return true;
			   }
			   else
				   return false;	 
	   } conn.commit();
		   }  catch (SQLException e) {
			if (conn != null)
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			e.printStackTrace();
		} finally {

	       try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	   }
	return false;
	   
   }
   
   public ResultSet pozrietAdresu() throws SQLException{
	   PreparedStatement stmt1 = conn.prepareStatement("SELECT id FROM osoba where login = ?");
	   stmt1.setString(1, pouzivatel.getLogin());
	   ResultSet sr = stmt1.executeQuery();
	   
	   PreparedStatement stmt = conn.prepareStatement("SELECT * FROM adresa WHERE osobaID = ?");    
	   if(sr.next())
		   stmt.setInt(1,sr.getInt("id"));
	   rs = stmt.executeQuery();
	   
	   if(rs.next()){
		   jedis.set("ulica", rs.getString("ulica"));
		    jedis.set("mesto", rs.getString("mesto"));
		    jedis.set("psc", rs.getString("PSC"));
		    jedis.set("stat", rs.getString("stat"));
	   }
	   rs = stmt.executeQuery();
	   
	   return rs;
   }
   
   public ResultSet pozrietProfil() throws SQLException{
	   
	   PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Osoba WHERE login = ?");    
	   stmt.setString(1,pouzivatel.getLogin());
	   rs = stmt.executeQuery();
	   if(rs.next()){
		   jedis.set("meno", rs.getString("meno"));
		   jedis.set("priezvisko", rs.getString("priezvisko"));
		   jedis.set("telefon", rs.getString("telefon"));
		   jedis.set("email", rs.getString("email"));
		   jedis.set("login", rs.getString("login"));
		   jedis.set("cUctu", rs.getString("cisloUctu"));
	   }
	   rs = stmt.executeQuery();
	   
	   return rs;
   }
   
   public void upravitProfil(String meno,String priezvisko, String telefon, String email,String login, String cUctu,String ulica, String mesto, String PSC, String stat)  {
	   PreparedStatement stmt1;
	try {
		   stmt1 = conn.prepareStatement("SELECT id FROM osoba where login = ?");
		   stmt1.setString(1, pouzivatel.getLogin());
		   zmenProfil(aktualnyCas());
		   
		   PreparedStatement stmt2 = conn.prepareStatement("UPDATE Osoba o SET o.meno = ?, o.priezvisko = ?, o.telefon = ?,"
		   		+ "o.email = ?, o.cisloUctu = ? WHERE o.login = ?"); 
		   stmt2.setString(1, meno);
		   stmt2.setString(2, priezvisko);
		   stmt2.setString(3, telefon);
		   stmt2.setString(4, email);
		   stmt2.setString(5, cUctu);
		   stmt2.setString(6, login);
		   
		   PreparedStatement stmt = conn.prepareStatement("UPDATE adresa SET ulica = ?, mesto = ?, PSC = ?, stat = ? WHERE osobaID = ?");
		   stmt.setString(1, ulica);
		   stmt.setString(2, mesto);
		   stmt.setString(3, PSC);
		   stmt.setString(4, stat);
		   
		   conn.setAutoCommit(false);
		   ResultSet sr = stmt1.executeQuery();
		   
		   stmt2.executeUpdate();
		   
		   if(sr.next())
			   stmt.setInt(5,sr.getInt("id"));
		   
		   stmt.executeUpdate();
		   conn.commit();
		   
	} catch (SQLException e) {
		if (conn != null)
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
          
		e.printStackTrace();
	} finally {

        try {
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } 
   }
   
   public ResultSet zobrazTaxik() throws SQLException{
	   PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM taxik t LEFT OUTER JOIN osoba o ON t.osobaID = o.id");
	   rs = stmt1.executeQuery();

	   return rs;
		
	}
   
   public int pocNevyb() throws SQLException{
	   int pocet = -1;
	   
	   PreparedStatement stmt1 = conn.prepareStatement("SELECT COUNT(vybavena) FROM objednavka where vybavena =  ?");
	   stmt1.setString(1, "nevybavena");
	   ResultSet sr = stmt1.executeQuery();
	   if(sr.next())
		   pocet = sr.getInt(1);
	   return pocet;
   }
   
   
   
   public void vymazatPou(String login){
	   PreparedStatement stmt2;
	   PreparedStatement stmt1;
	   int id = -1;
	   int idTax = -1;
	try {
		stmt1 = conn.prepareStatement("SELECT id FROM osoba where login = ?");
		stmt1.setString(1, login);
		conn.setAutoCommit(false);
		ResultSet sr = stmt1.executeQuery();
		if(sr.next())
			id= sr.getInt("id");
		
		stmt1 = conn.prepareStatement("SELECT id FROM taxik where osobaID = ?");
		stmt1.setInt(1, id);
		sr = stmt1.executeQuery();
		if(sr.next())
			idTax= sr.getInt("id");
	
		String sql = "DELETE FROM adresa WHERE osobaID = ?";
		stmt2 = conn.prepareStatement(sql);
		stmt2.setInt(1,id);
		stmt2.execute();
		
		sql = "DELETE FROM objednavkaosoby WHERE vybavilID = ?";
		stmt2 = conn.prepareStatement(sql);
		stmt2.setInt(1,id);
		stmt2.execute();
		
		sql = "DELETE FROM servis WHERE taxikID = ?";
		stmt2 = conn.prepareStatement(sql);
		stmt2.setInt(1,idTax);
		stmt2.execute();
		
		sql = "DELETE FROM poistenie WHERE taxikID = ?";
		stmt2 = conn.prepareStatement(sql);
		stmt2.setInt(1,idTax);
		stmt2.execute();
		
		sql = "DELETE FROM taxik WHERE osobaID = ?";
		stmt2 = conn.prepareStatement(sql);
		stmt2.setInt(1,id);
		stmt2.execute();
		
		sql = "DELETE FROM osoba WHERE id = ?";
		stmt2 = conn.prepareStatement(sql);
		stmt2.setInt(1,id);
		stmt2.execute();
		conn.commit();
		
	} catch (SQLException e) {
		if (conn != null)
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		e.printStackTrace();
	} finally {

        try {
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

   }
   
   public void vymazObjednavku(String cislo) throws SQLException{
	   int idobj = 0;
	   PreparedStatement stmt1 = conn.prepareStatement("SELECT id from objednavka WHERE cislo_obj = ?");
		stmt1.setString(1,cislo);
		rs = stmt1.executeQuery();
			   
		if(rs.next())
			idobj= rs.getInt("id");

	    PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM objednavkaosoby WHERE objID = ?");
		stmt2.setInt(1,idobj);
		stmt2.execute();
		
	    stmt1 = conn.prepareStatement("DELETE FROM objednavka WHERE cislo_obj = ?");
		stmt1.setString(1,cislo);
		stmt1.execute();
	}
   
   public static void connection(){
		jedis = new Jedis("localhost",6379); 
	}
	
	public void vytvorObjednavku(String datum, String cislObj){
		jedis.select(0);
	    jedis.sadd(Long.toString(jedis.incr(kluc)), datum,"Vytvorenie objednávky");	
	    jedis.set("cislo", cislObj);
	    jedis.del("cislo");
	}
	
	public void zmazObj() throws SQLException{
		vymazObjednavku(jedis.get("cislo"));
	}
	
	public void zmenHeslo(String datum,String stare, String nove){
		jedis.select(0);
	    jedis.sadd(Long.toString(jedis.incr(kluc)), datum,"Zmena hesla");	
	    jedis.set("stare", stare);
	    jedis.set("nove", nove);
	}
	
	public void zmeneneHesla() throws SQLException{

		zmenaHesla(jedis.get("nove"),jedis.get("stare"),jedis.get("stare"));
		jedis.del("nove","stare");
		
	}
	
	public void zmenProfil(String datum){
		jedis.select(0);
		
	    jedis.sadd(Long.toString(jedis.incr(kluc)), datum,"Zmena profilu");
	}
	
	public void zmenitProfil(){
		upravitProfil(jedis.get("meno"),jedis.get("priezvisko"),jedis.get("telefon"),jedis.get("email"),
				jedis.get("login"),jedis.get("cUctu"),jedis.get("ulica"),jedis.get("mesto"),jedis.get("psc"),jedis.get("stat"));
		jedis.del("login","meno","priezvisko","telefon","email","cUctu","ulica","mesto","psc","stat");
	}
	
	public List<String> vratDatabazu(){
		jedis.select(0);
		List<String> vysledok = new LinkedList<String>();
		List<String> kluce = new LinkedList<String>();
		
		Set<String> names = jedis.keys("*");

        java.util.Iterator<String> it = names.iterator();
        while(it.hasNext()) {
        	key = it.next();
        	if (key.matches("[1-9]+")){
        		kluce.add(key);
        	}
        }
		
        for (int i = 0;i < kluce.size();i++){;
		    vysledok.addAll(jedis.smembers(kluce.get(i)));
		}

	    return vysledok;
	}
	
	public void ukonciSpojenie(){
		jedis.flushAll();
		jedis.close();
	}
}







