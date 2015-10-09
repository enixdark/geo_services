package vn.hiworld.com.chloe.handler;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import vn.hiworld.com.chloe.service.MongoChloeClient;

public class AddHanler extends ChloeHandler{
	
	
	/*
	 * example API 
	 * Request
	 * {
	 * 		event:'insert',
	 * 		accessToken:'dsfdsdsf7s7s',
	 * 		data:{
	 * 			company_name:'',
	 * 			geocoding:[]
	 * 		}
	 * }
	 * 
	 * Response
	 * {
	 *     event:'insert'
	 *     result:'OK'
	 * }
	 */
	
	
	public AddHanler(MongoChloeClient client) {
		super(client);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void handle(RoutingContext context) {
		// TODO Auto-generated method stub
		context.request().bodyHandler( rq -> {
			try {
				client.insert(new JsonObject(rq.toString()),context);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		    	  
		});
	}

}
