package com.github.ravenlab;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public final class AutoGson {
	
	private AutoGson() {}
	
	public static String AUTO_GSON_CLASS = "auto_gson_class";
	
	public static String serialize(Gson gson, Object obj)
	{
		JsonElement element = gson.toJsonTree(obj);
		JsonObject jsonObject = element.getAsJsonObject();
		String className = obj.getClass().getName();
		jsonObject.addProperty(AUTO_GSON_CLASS, className);
		String json = gson.toJson(jsonObject);
		return json;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T deserialize(Gson gson, String json) throws JsonSyntaxException, ClassNotFoundException
	{
		try
		{
			JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
			JsonElement classElement = jsonObject.get(AUTO_GSON_CLASS);
			String className = classElement.getAsString();
			jsonObject.remove(AUTO_GSON_CLASS);
			Class<?> clazz = Class.forName(className);
			Object fromJsonGeneric = gson.fromJson(jsonObject, clazz);
			T fromJson = (T) fromJsonGeneric;
			return fromJson;
		}
		catch(JsonSyntaxException ex)
		{
			throw(ex);
		}
	}
}