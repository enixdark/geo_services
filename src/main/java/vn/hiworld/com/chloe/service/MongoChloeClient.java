package vn.hiworld.com.chloe.service;


import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import com.google.common.base.Preconditions;
import com.hazelcast.logging.ILogger;
import com.hazelcast.logging.Logger;

import io.netty.handler.codec.http.HttpResponse;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;
import vn.hiworld.com.chloe.util.Runner;

public class MongoChloeClient{

//    private static final ExecutorService threadpool = Executors.newFixedThreadPool(3);
	private static final String DB_NAME = "db_name";
	private static final String CONN = "connection_string";
	private static String URL = "mongo://localhost:27017";
	private static String USERNAME = "";
	private static String PASSWORD = "";
	private static String DATABASE_NAME = "google_service";
	private static final String COLLECTION_NAME = "geo";
	private MongoClient mongoClient = null;
	private Vertx vertx = null;
	private final ILogger _log = Logger.getLogger(MongoChloeClient.class); 
	
	
	public MongoChloeClient(Vertx vertx){
		this(vertx,URL,DB_NAME);
	}
	
	public MongoChloeClient(Vertx vertx,String path,String dbname){
		Preconditions.checkNotNull(vertx);
		Preconditions.checkNotNull(path);
		Preconditions.checkNotNull(dbname);

		this.DATABASE_NAME = dbname;
		this.vertx = vertx;
//		URI uri = new URI(path);
//		String username = uri.getUserInfo()
		JsonObject config = Vertx.currentContext().config();
		JsonObject mongoconfig = new JsonObject()
		        .put(CONN, path)
		        .put(DB_NAME, dbname);
		mongoClient = MongoClient.createShared(vertx, mongoconfig);
	}
	
	public MongoClient getClient(){
		return mongoClient;
	}
	
	public void insert(JsonObject data,RoutingContext rc){
		insert(COLLECTION_NAME,data, rc);
	}
	
	public void insert(String col_name, JsonObject data,RoutingContext rc){
		HttpServerResponse response = rc.response().putHeader("content-type", "application/json");
		mongoClient.insert(col_name, data, res -> {
			if(res.succeeded()){
				response.end("{result:OK}");
			}
			else{
//				res.cause().printStackTrace();
				response.end("{result:ERROR}");
			}
		});
	}
	
	public void update(String col_name, JsonObject query,JsonObject data){
		mongoClient.update(col_name, query , data, res -> {
			if(res.succeeded()){
				_log.info("update data success");
			}
			else{
				res.cause().printStackTrace();
			}
		});
	}
	
	public MongoClient findAll(RoutingContext rc) throws Exception{
		return find(COLLECTION_NAME, new JsonObject(),rc);
	}
	
	public MongoClient findAll(RoutingContext rc,int limit) throws Exception{
		return find(COLLECTION_NAME, new JsonObject(),new FindOptions().setLimit(limit),rc);
	}
	
	public MongoClient findAll(String col_name,RoutingContext rc,int limit) throws Exception{
		return find(col_name, new JsonObject(),new FindOptions().setLimit(limit),rc);
	}
	
	public MongoClient find(JsonObject query, RoutingContext rc) throws Exception{
		return find(COLLECTION_NAME, query,rc);
	}
	
	public MongoClient find(String col_name, JsonObject query, RoutingContext rc) throws Exception{
		return find(col_name, query,new FindOptions(),rc);
	}
	
	public MongoClient find(String col_name, JsonObject query,FindOptions optinal, RoutingContext rc) throws Exception{
		return mongoClient.findWithOptions(col_name, query,optinal ,res -> {
			HttpServerResponse response = rc.response().putHeader("content-type", "application/json");
			if (res.succeeded()){
				response.end(res.result().stream().map( x -> x.encode()).reduce( (x,y) -> x + y ).get());
			} else {
				response.end("{result:ERROR}");
//			    res.cause().printStackTrace();
			}
		});
	}
	


  
}