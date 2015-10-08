package vn.hiworld.com.chloe.util;

import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.maps.model.GeocodingResult;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class GoogleGeoCodeResponse {
	private static Gson gson = new Gson();
	public String status;
	public results[] results;

	public GoogleGeoCodeResponse() {
	}

	public class results {
		public boolean partial_match;
		public String place_id;
		public String formatted_address;
		public geometry geometry;
		public String[] types;
		public address_component[] address_components;
	}

	public class geometry {
		public bounds bounds;
		public String location_type;
		public location location;
		public bounds viewport;
	}

	public class bounds {

		public location northeast;
		public location southwest;
	}

	public class location {
		public String lat;
		public String lng;
	}

	public class address_component {
		public String long_name;
		public String short_name;
		public String[] types;
	}
	
	public JsonObject toJson(){
		JsonObject json = new JsonObject();
		for(results result: this.results){
			json.put("formatted_address", result.formatted_address)
				.put("partial_match", result.partial_match)
				.put("place_id",result.place_id)
				.put("types", new JsonArray(Arrays.asList(result.types)))
				.put("address_components",new JsonArray(
					Arrays.asList(Arrays.asList(result.address_components).stream().map(
					address -> {
						return new JsonObject()
								.put("long_name", address.long_name)
								.put("short_name", address.short_name)
								.put("types", new JsonArray(Arrays.asList(address.types)));
					}
			).toArray())))
				.put("geometry", new JsonObject()
						.put("viewport",new JsonObject()
								.put("northeast", 
								new JsonObject().put("lat",result.geometry.bounds.northeast.lat)
												.put("lng",result.geometry.bounds.northeast.lng)))
								.put("southwest", 
										new JsonObject().put("lat",result.geometry.bounds.southwest.lat)
														.put("lng",result.geometry.bounds.southwest.lng))
						.put("location_type", result.geometry.location_type)
						.put("location",new JsonObject().put("lat",result.geometry.location.lat)
								.put("lng",result.geometry.location.lng))
					);
		}
		return json;
	}

	public static GoogleGeoCodeResponse parse(String result){
		return gson.fromJson(result,GoogleGeoCodeResponse.class);
	}
}
