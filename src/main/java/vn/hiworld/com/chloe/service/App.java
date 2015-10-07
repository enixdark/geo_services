package vn.hiworld.com.chloe.service;

import com.hazelcast.logging.ILogger;
import com.hazelcast.logging.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import vn.hiworld.com.chloe.util.GeoCoding;

public class App extends AbstractVerticle {
	  
	  ILogger log = Logger.getLogger(App.class);
	  @Override
	  public void start() {
		  
		MongoChloeClient client = new MongoChloeClient(vertx,"mongodb://localhost:27017", "google_service");
		GeoCoding geo = GeoCoding.createGeoCoding("AIzaSyDTGMoAL8ve-u4zCe3M8V1nEQGQCVV3YzU");
	    Router router = Router.router(vertx);
	    
//	    router.post("/search").handler( rc -> {
//	      System.out.println(rc.);
//	      rc.response()
//	          .putHeader("content-type", "application/json")
//	          .end(new JsonObject().put("greeting", "Hello World!").encode());
//	    });
	    
	    router.post("/search").handler( rc -> {
		      rc.request().bodyHandler( rq -> {
		    	  rc.response()
		          .putHeader("content-type", "application/json")
		          .end(new JsonObject().put("greeting", "Hello World!").encode());
		      });
//		      rc.response()
//		          .putHeader("content-type", "application/json")
//		          .end(new JsonObject().put("greeting", "Hello World!").encode());
		    });
	    

	    vertx.createHttpServer().requestHandler(router::accept).listen(8080);
	  }
	}
