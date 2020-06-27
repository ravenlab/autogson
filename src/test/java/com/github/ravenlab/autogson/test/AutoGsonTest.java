package com.github.ravenlab.autogson.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.github.ravenlab.AutoGson;
import com.github.ravenlab.autogson.test.foo.FooBar;
import com.github.ravenlab.autogson.test.foo.FooBarChild;
import com.github.ravenlab.autogson.test.foo.data.ExtraFooData;
import com.github.ravenlab.autogson.test.foo.data.FooData;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class AutoGsonTest {

	@Test
	public void testToJson() {
		Gson gson = new Gson();
		FooBar foo = new FooBar();
		AutoGson autoGson = new AutoGson.Builder().build();
		String json = autoGson.toJson(gson, foo);
		JsonObject obj = gson.fromJson(json, JsonObject.class);
		String autoGsonClass = obj.get(AutoGson.AUTO_GSON_CLASS).getAsString();
		assertTrue(autoGsonClass.equals(FooBar.class.getName()));
	}
	
	@Test
	public void testFromJson() {
		Gson gson = new Gson();
		FooBar orginalFoo = new FooBar();
		AutoGson autoGson = new AutoGson.Builder().build();
		String json = autoGson.toJson(gson, orginalFoo);
		
		try {
			FooBar fooBar = autoGson.fromJson(gson, json);
			String foo = fooBar.getFoo();
			assertTrue(foo.equals("bar"));
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = JsonSyntaxException.class)
	public void testInvalidJsonDeserialize() throws JsonSyntaxException {
		Gson gson = new Gson();
		String json = "{foo/thing}";
		AutoGson autoGson = new AutoGson.Builder().build();
		autoGson.fromJson(gson, json);
	}
	
	@Test
	public void testInheritance() {
		FooBar child = new FooBarChild();
		Gson gson = new Gson();
		AutoGson autoGson = new AutoGson.Builder().build();
		String json = autoGson.toJson(gson, child);
		
		try {
			FooBar foo = autoGson.fromJson(gson, json);
			assertTrue(foo instanceof FooBarChild);
			assertTrue(foo instanceof FooBar);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			fail("Class not found or an issue with the original json");
		}
	}
	
	@Test
	public void testInnerCustomClass()
	{
		FooBar child = new FooBarChild();
		Gson gson = new Gson();
		AutoGson autoGson = new AutoGson.Builder().build();
		String json = autoGson.toJson(gson, child);
		
		try {
			FooBarChild foo = autoGson.fromJson(gson, json);
			String data = foo.getData().getData();
			assertTrue(data.equals("somedata"));
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			fail("Class not found or an issue with the original json");
		}
	}
	
	@Test
	public void testInnerCustomClassChild() {
		FooBar child = new FooBarChild();
		Gson gson = new Gson();
		AutoGson autoGson = new AutoGson.Builder().build();
		String json = autoGson.toJson(gson, child);
		
		try {
			FooBarChild foo = autoGson.fromJson(gson, json);
			FooData<String> fooData = foo.getData();
			assertTrue(fooData instanceof ExtraFooData);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			fail("Class not found or an issue with the original json");
		}
	}
	
	@Test
	public void testRefactoredClass() {
		Gson gson = new Gson();
		FooBar foo = new FooBar();
		AutoGson autoGson = new AutoGson.Builder().build();
		String json = autoGson.toJson(gson, foo);
		JsonObject obj = gson.fromJson(json, JsonObject.class);
		String className = obj.get(AutoGson.AUTO_GSON_CLASS).getAsString();
		String refactoredClassName = "refactored." + className;
		obj.remove(AutoGson.AUTO_GSON_CLASS);
		obj.addProperty(AutoGson.AUTO_GSON_CLASS, refactoredClassName);
		String newJson = obj.toString();
		AutoGson newAutoGson = new AutoGson
				.Builder()
				.addRefactoredClass(refactoredClassName, className)
				.build();
		FooBar newFoo = newAutoGson.fromJson(gson, newJson);
		assertTrue(obj.get(AutoGson.AUTO_GSON_CLASS).getAsString().equals(refactoredClassName));
		assertTrue(newFoo != null);
	}
	
	@Test
	public void testNoRefactoredClass() {
		Gson gson = new Gson();
		FooBar foo = new FooBar();
		AutoGson autoGson = new AutoGson.Builder().build();
		String json = autoGson.toJson(gson, foo);
		JsonObject obj = gson.fromJson(json, JsonObject.class);
		String className = obj.get(AutoGson.AUTO_GSON_CLASS).getAsString();
		String refactoredClassName = "refactored." + className;
		obj.remove(AutoGson.AUTO_GSON_CLASS);
		obj.addProperty(AutoGson.AUTO_GSON_CLASS, refactoredClassName);
		String newJson = obj.toString();
		AutoGson newAutoGson = new AutoGson
				.Builder()
				.build();
		FooBar newFoo = newAutoGson.fromJson(gson, newJson);
		assertTrue(newFoo == null);
	}
	
	@Test
	public void testDoesNotExistClass() {
		Gson gson = new Gson();
		FooBar foo = new FooBar();
		AutoGson autoGson = new AutoGson.Builder().build();
		String json = autoGson.toJson(gson, foo);
		JsonObject obj = gson.fromJson(json, JsonObject.class);
		String className = obj.get(AutoGson.AUTO_GSON_CLASS).getAsString();
		String refactoredClassName = "refactored." + className;
		String refactoredDoesNotExistClassName = refactoredClassName + "DoesNotExist";
		obj.remove(AutoGson.AUTO_GSON_CLASS);
		obj.addProperty(AutoGson.AUTO_GSON_CLASS, refactoredDoesNotExistClassName);
		String newJson = obj.toString();
		AutoGson newAutoGson = new AutoGson
				.Builder()
				.addRefactoredClass(refactoredClassName, refactoredDoesNotExistClassName)
				.build();
		FooBar newFoo = newAutoGson.fromJson(gson, newJson);
		assertTrue(newFoo == null);
	}
	
	@Test
	public void testSetClassLoader() {
		Gson gson = new Gson();
		FooBar orginalFoo = new FooBar();
		AutoGson autoGson = new AutoGson.Builder()
				.setClassLoader(this.getClass().getClassLoader())
				.build();
		String json = autoGson.toJson(gson, orginalFoo);
		
		try {
			FooBar fooBar = autoGson.fromJson(gson, json);
			String foo = fooBar.getFoo();
			assertTrue(foo.equals("bar"));
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
	}
}