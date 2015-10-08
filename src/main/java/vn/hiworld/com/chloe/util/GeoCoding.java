package vn.hiworld.com.chloe.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.PendingResult;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.GeocodingResult;

import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Place;

public class GeoCoding {
	
	private final static String GEOCODING_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=%s=%s";
	private static GeoCoding geoCodingContext = null;
	private static GeoApiContext geoContext = null;
	private static String APIKEY = "AIzaSyDTGMoAL8ve-u4zCe3M8V1nEQGQCVV3YzU";
	private static GooglePlaces place = null;
	private static Map<String,String> ConvertGeoCodings = Maps.newHashMap();  
	private final static List<String> ADDR_COMPONENTS = Arrays.asList(
		"country","administrative_area_level_1","locality","administrative_area_level_2","sublocality_level_1","route"
	);
	
	public static final String COUNTRY = "country";
	public static final String STREET = "route";
	public static final String WARD = "sublocality_level_1";
	public static final String DISTRICT = "administrative_area_level_2";
	public static final String CITY = "administrative_area_level_1";
	public static final String LOCALITY = "locality";

	static {
		ConvertGeoCodings.put(COUNTRY, "country_name");
		ConvertGeoCodings.put(STREET, "street_name");
		ConvertGeoCodings.put(WARD, "ward_name");
		ConvertGeoCodings.put(DISTRICT, "district_name");
		ConvertGeoCodings.put(CITY, "city_name");
		ConvertGeoCodings.put(LOCALITY, "district_name");
	}
	
	
	private GeoCoding(){
		geoContext = new GeoApiContext().setApiKey(APIKEY);
		place = new GooglePlaces(APIKEY);
	}
	
	private GeoCoding(String ApiKey){
		APIKEY = ApiKey;
		geoContext = new GeoApiContext().setApiKey(APIKEY);
		place = new GooglePlaces(APIKEY);
	}
	
	public static GeoCoding createGeoCoding(){
		return createGeoCoding(APIKEY);
	}
	
	public static GeoCoding createGeoCoding(String ApiKey){
		if(geoCodingContext == null){
			geoCodingContext = new GeoCoding(ApiKey);
		}
		return geoCodingContext;
	}
	
	public GeoApiContext getGeoContext(){
		return geoContext;
	}
	
	public GooglePlaces getPlaceContext(){
		return place;
	}
	
	public String getApiKey(){
		return APIKEY;
	}
	
	public void setApiKey(final String ApiKey){
		APIKEY = ApiKey;
		geoContext.setApiKey(APIKEY);
		place.setApiKey(APIKEY);
	}
	
	public String search(final String address) throws IOException{
		URL uri = new URL(String.format(GEOCODING_URL,URLEncoder.encode(address, "UTF-8"),APIKEY));
		URLConnection connection = uri.openConnection();
		try(BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
			StringBuffer json = new StringBuffer();
			in.lines().forEach( x -> {
				json.append(x);
			});
			return json.toString();
		}
	}
	
	public ImmutableMap<String, String> getAddressInformationMin(final String address){
		final Map<String,String> data = getAddressInformation(address);
		String min_local = ADDR_COMPONENTS.get(0);
		for(String local : ADDR_COMPONENTS){
			if(data.containsKey(local)){
				min_local = local;
			}
		}
		return ImmutableMap.of(ConvertGeoCodings.get(min_local),data.get(min_local));
	}
	
	public Map<String,String> getAddressInformation(final String address){
		final Map<String,String> data = new HashMap<String, String>();
		GeocodingApiRequest req = GeocodingApi.newRequest(geoContext).address(address);
		try {
			GeocodingResult[] results = req.await();
		    if(results.length != 0){
		    	AddressComponent[] add = results[0].addressComponents;
		    	
		    	for(AddressComponent a: add){
		    		String type = a.types[0].toString().toLowerCase();
			    	if(ADDR_COMPONENTS.contains(type)){
			    		data.put(type,a.longName);
			    	}
		    	}
//				data.put("location",String.format("%f, %f",results[0].geometry.location.lat,
//						results[0].geometry.location.lng));
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	

	
	public Map<String,String> getGeoInformation(final String address, final String company_name){
		final Map<String,String> data = getAddressInformation(address);
		try {
		    if(data.isEmpty()){
		    	List<Place> places = place.getPlacesByQuery(company_name, 
						GooglePlaces.DEFAULT_RESULTS);
				
				if(!places.isEmpty()){
				  for(se.walkercrou.places.AddressComponent a: places.get(0).getAddressComponents()){
			    		String type = a.getTypes().get(0);
				    	if(ADDR_COMPONENTS.contains(type)){
				    		data.put(type,a.getLongName());
				    	}
			      }
//				  data.put("location",String.format("%f, %f",places.get(0).getLatitude(),places.get(0).getLongitude()));
				}
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}

//		req.setCallback(new PendingResult.Callback<GeocodingResult[]>() {
//			  @Override
//			  public void onResult(GeocodingResult[] result) {
//				 
//			  }
//
//			  @Override
//			  public void onFailure(Throwable e) {
//				  List<Place> places = place.getPlacesByQuery(company_name, 
//							GooglePlaces.DEFAULT_RESULTS);
//				  if(!places.isEmpty()){
//					  data.put("address",places.get(0).getAddress());
//					  data.put("location",String.format("%f, %f",places.get(0).getLatitude(),places.get(0).getLongitude()));
//				  }
//			  }
//		});
		return data;
		
			


	}
}
