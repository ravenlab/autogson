package com.github.ravenlab.autogson.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.ravenlab.AutoGson;
import com.github.ravenlab.autogson.test.obj.FooBar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class AutoGsonTest {

	@Test
	public void testSerialize()
	{
		Gson gson = new Gson();
		FooBar foo = new FooBar();
		String json = AutoGson.serialize(gson, foo);
		JsonObject obj = gson.fromJson(json, JsonObject.class);
		String autoGsonClass = obj.get(AutoGson.AUTO_GSON_CLASS).getAsString();
		assertTrue(autoGsonClass.equals(FooBar.class.getName()));
	}
	
	@Test
	public void testDeserialize()
	{
		Gson gson = new Gson();
		FooBar orginalFoo = new FooBar();
		String json = AutoGson.serialize(gson, orginalFoo);
		try 
		{
			FooBar fooBar = AutoGson.deserialize(gson, json);
			String foo = fooBar.getFoo();
			assertTrue(foo.equals("bar"));
		} 
		catch (JsonSyntaxException | ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
}