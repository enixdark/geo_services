package vn.hiworld.com.chloe.service;

import java.util.List;
import java.util.concurrent.Callable;

import com.hazelcast.logging.ILogger;
import com.hazelcast.logging.Logger;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class FactoryFuture<T> implements Callable{
	private final ILogger _log = Logger.getLogger(MongoChloeClient.class); 
	private List<JsonObject> data = null;
	private MongoClient client;
	private JsonObject query;
	private String col_name;
	
	public FactoryFuture(MongoClient client,String col_name,JsonObject query){
		this.client = client;
		this.col_name = col_name;
		this.query = query;
	}
	public void setData(List<JsonObject> data){
		this.data = data;
	}
	public List<JsonObject> getData(){
		return this.data;
	}
	
	@Override
	public Object call() throws Exception {
		// TODO Auto-generated method stub
		client.find(col_name, query, res -> {
			if (res.succeeded()){
				setData(res.result());
			} else {
			    res.cause().printStackTrace();
			}
		});
		return null;
	}
}
