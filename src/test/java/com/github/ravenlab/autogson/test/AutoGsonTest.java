package com.github.ravenlab.autogson.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.github.ravenlab.AutoGson;
import com.github.ravenlab.autogson.test.obj.FooBar;
import com.github.ravenlab.autogson.test.obj.FooBarChild;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class AutoGsonTest {

	@Test
	public void testToJson()
	{
		Gson gson = new Gson();
		FooBar foo = new FooBar();
		String json = AutoGson.toJson(gson, foo);
		JsonObject obj = gson.fromJson(json, JsonObject.class);
		String autoGsonClass = obj.get(AutoGson.AUTO_GSON_CLASS).getAsString();
		assertTrue(autoGsonClass.equals(FooBar.class.getName()));
	}
	
	@Test
	public void testFromJson()
	{
		Gson gson = new Gson();
		FooBar orginalFoo = new FooBar();
		String json = AutoGson.toJson(gson, orginalFoo);
		try 
		{
			FooBar fooBar = AutoGson.fromJson(gson, json);
			String foo = fooBar.getFoo();
			assertTrue(foo.equals("bar"));
		} 
		catch (JsonSyntaxException | ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Test(expected = JsonSyntaxException.class)
	public void testInvalidJsonDeserialize() throws JsonSyntaxException
	{
		Gson gson = new Gson();
		String json = "{foo/thing}";
		try 
		{
			AutoGson.fromJson(gson, json);
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
			fail("Class not found exception was thrown");
		}
	}
	
	@Test
	public void testInheritance()
	{
		FooBar child = new FooBarChild();
		Gson gson = new Gson();
		String json = AutoGson.toJson(gson, child);
		
		try 
		{
			FooBar foo = AutoGson.fromJson(gson, json);
			assertTrue(foo instanceof FooBarChild);
			assertTrue(foo instanceof FooBar);
		} 
		catch (JsonSyntaxException | ClassNotFoundException e) 
		{
			e.printStackTrace();
			fail("Class not found or an issue with the original json");
		}
	}
}