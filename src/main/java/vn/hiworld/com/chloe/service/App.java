package vn.hiworld.com.chloe.service;

import java.io.IOException;

import com.hazelcast.logging.ILogger;
import com.hazelcast.logging.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import vn.hiworld.com.chloe.util.GeoCoding;
import vn.hiworld.com.chloe.util.GoogleGeoCodeResponse;

public class App extends AbstractVerticle {
	  
	  ILogger log = Logger.getLogger(App.class);
	  @Override
	  public void start() throws IOException {
		  
		MongoChloeClient client = new MongoChloeClient(vertx,"mongodb://localhost:27017", "trangvangvietnam");
		GeoCoding geo = GeoCoding.createGeoCoding("AIzaSyDTGMoAL8ve-u4zCe3M8V1nEQGQCVV3YzU");
	    Router router = Router.router(vertx);
	    
//	    router.post("/search").handler( rc -> {
//	      System.out.println(rc.);
//	      rc.response()
//	          .putHeader("content-type", "application/json")
//	          .end(new JsonObject().put("greeting", "Hello World!").encode());
//	    });
		GoogleGeoCodeResponse result = GoogleGeoCodeResponse.parse(geo.search("67 hang bai, ha noi"));

	    
	    router.get("/search").handler( rc -> {

		      rc.request().bodyHandler( rq -> {
		    	  client.find();

		    	  client.insert("crawler.trangvangvietnam.com", new JsonObject().put("name", result.toJson()));
		    	  rc.response()
		          .putHeader("content-type", "application/json")
		          .end(new JsonObject().put("greeting", "Hello World!").encode());
		      });
		    });
	    

	    vertx.createHttpServer().requestHandler(router::accept).listen(8080);
	  }
	}
