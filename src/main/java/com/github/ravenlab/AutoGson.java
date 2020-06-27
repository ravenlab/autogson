package com.github.ravenlab;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class AutoGson {
	
	public static final String AUTO_GSON_CLASS = "auto_gson_class";
	
	private Map<String, String> refactored;
	
	private AutoGson(Map<String, String> refactored) {
		this.refactored = refactored;
	}
	
	public String toJson(Gson gson, Object obj) {
		JsonElement element = gson.toJsonTree(obj);
		JsonObject jsonObject = element.getAsJsonObject();
		String className = obj.getClass().getName();
		jsonObject.addProperty(AUTO_GSON_CLASS, className);
		String json = gson.toJson(jsonObject);
		return json;
	}

	@SuppressWarnings("unchecked")
	public <T> T fromJson(Gson gson, String json) throws JsonSyntaxException{
		try {
			JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
			JsonElement classElement = jsonObject.get(AUTO_GSON_CLASS);
			String className = classElement.getAsString();
			Class<?> clazz = null;
			if(this.classExists(className)) {
				clazz = this.getClass(className);
			} else {
				String refactoredClassName = this.refactored.get(className);
				if(this.classExists(refactoredClassName)) {
					clazz = this.getClass(refactoredClassName);
				}
			}

			if(clazz == null) {
				return null;
			}
			
			jsonObject.remove(AUTO_GSON_CLASS);
			Object fromJsonGeneric = gson.fromJson(jsonObject, clazz);
			T fromJson = (T) fromJsonGeneric;
			return fromJson;
		} catch(JsonSyntaxException ex) {
			throw(ex);
		}
	}
	
	private Class<?> getClass(String className) {
		if(className == null) {
			return null;
		}
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	private boolean classExists(String className) {
		return getClass(className) != null;
	}
	
	public static class Builder {
		
		private Map<String, String> refactored;
		
		public Builder() {
			this.refactored = new HashMap<>();
		}
		
		public Builder addRefactoredClass(String mapFrom, String mapTo) {
			this.refactored.put(mapFrom, mapTo);
			return this;
		}
		
		public AutoGson build() {
			return new AutoGson(refactored);
		}
	}
}