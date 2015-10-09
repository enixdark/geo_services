package vn.hiworld.com.chloe.message;

import java.util.Map;

import com.google.gson.JsonObject;

public class SearchMessage extends AbsMessage {

	private JsonObject data;
	private String accessToken;

	public JsonObject getData() {
		return data;
	}

	public void setData(JsonObject data) {
		this.data = data;
	}
	
	public String getAccessToken(){
		return this.accessToken;
	}
}
