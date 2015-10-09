package vn.hiworld.com.chloe.handler;

import com.hazelcast.logging.ILogger;
import com.hazelcast.logging.Logger;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import vn.hiworld.com.chloe.service.MongoChloeClient;

public abstract class ChloeHandler implements Handler<RoutingContext> {
	protected ILogger _log = Logger.getLogger(ChloeHandler.class);
	protected MongoChloeClient client;
	protected JsonObject data = new JsonObject();
	protected ChloeHandler(MongoChloeClient client) {
		// TODO Auto-generated constructor stub
		this.client = client;
	}
	
	@Override
	public void handle(RoutingContext event) {
		// TODO Auto-generated method stub
		
	}
	
	public JsonObject getData() {
		return data;
	}

	public void setData(JsonObject data) {
		this.data = data;
	}
}
