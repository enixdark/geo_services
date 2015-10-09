package vn.hiworld.com.chloe.service;

import java.io.IOException;

import com.hazelcast.logging.ILogger;
import com.hazelcast.logging.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import vn.hiworld.com.chloe.handler.AddHanler;
import vn.hiworld.com.chloe.handler.SearchHandler;
import vn.hiworld.com.chloe.routes.GeoRouter;
import vn.hiworld.com.chloe.util.GeoCoding;
import vn.hiworld.com.chloe.util.GoogleGeoCodeResponse;

public class App extends AbstractVerticle {

	ILogger log = Logger.getLogger(App.class);
	@Override
	public void start() throws IOException {

		MongoChloeClient client = new MongoChloeClient(vertx,"mongodb://localhost:27017", "google_service");
//		GeoCoding geo = GeoCoding.createGeoCoding("AIzaSyDTGMoAL8ve-u4zCe3M8V1nEQGQCVV3YzU");
		Router router = Router.router(vertx);
//		GoogleGeoCodeResponse result = GoogleGeoCodeResponse.parse(geo.search("67 hang bai, ha noi"));
		

		
		router.get(GeoRouter.SEARCH).handler(new SearchHandler(client));
		router.post(GeoRouter.ADD).handler(new AddHanler(client));
	  
		vertx.createHttpServer().requestHandler(router::accept).listen(8080);
	}
}
