package vn.hiworld.com.chloe.service;


import java.net.URI;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import vn.hiworld.com.chloe.util.Runner;

public class MongoChloeClient{

	
	private static final String DB_NAME = "db_name";
	private static final String CONN = "connection_string";
	private static String URL = "mongo://localhost:27017";
	private static String USERNAME = "";
	private static String PASSWORD = "";
	private static String DATABASE_NAME = "google_service";
	private MongoClient mongoClient = null;
	
	public MongoChloeClient(Vertx vertx,String path,String dbname){
		this.DATABASE_NAME = dbname;
//		URI uri = new URI(path);
//		String username = uri.getUserInfo()
		JsonObject config = Vertx.currentContext().config();
		JsonObject mongoconfig = new JsonObject()
		        .put(CONN, path)
		        .put(DATABASE_NAME, dbname);
		mongoClient = MongoClient.createShared(vertx, mongoconfig);
	}
	
	public MongoClient getClient(){
		return mongoClient;
	}
	
//	public static void main(String[] args) {
	
//	    Runner.runExample(MongoChloeClient.class);
//	  }
//
//	  @Override
//	  public void start() throws Exception {String
//
//	    JsonObject config = Vertx.currentContext().config();
//
//	    String uri = config.getString("mongo_uri");
//	    if (uri == null) {
//	      uri = "mongodb://localhost:27017";
//	    }
//	    String db = config.getString("mongo_db");
//	    if (db == null) {
//	      db = "test";
//	    }
//
//	    JsonObject mongoconfig = new JsonObject()
//	        .put("connection_string", uri)
//	        .put("db_name", db);
//
//	    MongoClient mongoClient = MongoClient.createShared(vertx, mongoconfig);
//
//	    JsonObject product1 = new JsonObject().put("itemId", "12345").put("name", "Cooler").put("price", "100.0");
//
//	    mongoClient.save("products", product1, id -> {
//	      System.out.println("Inserted id: " + id.result());
//
//	      mongoClient.find("products", new JsonObject().put("itemId", "12345"), res -> {
//	        System.out.println("Name is " + res.result().get(0).getString("name"));
//
//	        mongoClient.remove("products", new JsonObject().put("itemId", "12345"), rs -> {
//	          if (rs.succeeded()) {
//	            System.out.println("Product removed ");
//	          }
//	        });
//
//	      });
//
//	    });
//
//	  }

  
}