package vn.hiworld.com.chloe.handler;
import org.apache.http.protocol.RequestContent;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;
import vn.hiworld.com.chloe.message.AbsMessage;
import vn.hiworld.com.chloe.message.SearchMessage;
import vn.hiworld.com.chloe.service.MongoChloeClient;

public class SearchHandler extends ChloeHandler{
	
	/*
	 * example API 
	 * Request
	 * {
	 * 		event:'search',
	 * 		accessToken:'dsfdsdsf7s7s',
	 * 		data:[
	 * 			{field1:''},
	 * 			{field2:''},
	 * 			.........,
	 * 			{fieldn:''}
	 * 		]
	 * }
	 * 
	 * Response
	 * {
	 *     event:'search'
	 *     result:'OK',
	 *     data:[]
	 * }
	 */
	
	
	public SearchHandler(MongoChloeClient client) {
		super(client);
	}

	@Override
	public void handle(RoutingContext context) {
		// TODO Auto-generated method stub
		context.request().bodyHandler( rq -> {
			SearchMessage mes = (SearchMessage) AbsMessage.parseMap(context.request().params());
//			System.out.println(mes.toJson().getJsonObject("data"));
			try {
				client.find(mes.toJson().getJsonObject("data"),context);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		    	  
		});
	}

}
