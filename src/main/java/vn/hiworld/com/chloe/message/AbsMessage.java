package vn.hiworld.com.chloe.message;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.vertx.core.MultiMap;
import io.vertx.core.json.Json;
import vn.com.hiworld.chloe.event.EventList;

public abstract class AbsMessage {
	private static final Gson gson = new Gson();
	private final static String TOKEN = "accessToken";
	private String event;

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
	
	public static AbsMessage fromJson(String str, Class<? extends AbsMessage> cls) {
		return gson.fromJson(str, cls);
	}
	
	public String toGson(){
		return gson.toJson(this);
	}
	
	public io.vertx.core.json.JsonObject toJson(){
		return new io.vertx.core.json.JsonObject(gson.toJson(this));
	}
	
	public static AbsMessage parse(Object data) {
		// TODO Auto-generated constructor stub
		System.out.println(data);
		JsonObject jObj = new JsonParser().parse(data.toString()).getAsJsonObject();
		String msgEvent = jObj.get("event").getAsString();
		Class<?> msgCls = EventList.getEventMessageClass(msgEvent);
		return (AbsMessage) gson.fromJson(jObj, msgCls);
	}
	
	public static AbsMessage parseMap(MultiMap data) {
		// TODO Auto-generated constructor stub
//		
		String _api = String.format("{"
				+ "event:%s,"
				+ "accessToken:%s,"
				+ "data:{%s"
				+ "}}", data.get("event"),data.get(TOKEN),"%s");
		data.remove("event");
		data.remove(TOKEN);
		String _data = data.entries().stream()
			.map( e -> String.format("%s:%s", e.getKey(),e.getValue()))
			.reduce( (x,y) ->
			x + "," + y).get();
		return parse(String.format(_api,_data));
	}
	
	
}
