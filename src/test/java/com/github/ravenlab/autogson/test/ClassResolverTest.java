package com.github.ravenlab.autogson.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.github.ravenlab.AutoGson;
import com.github.ravenlab.resolver.ClassResolver;

public class ClassResolverTest {

	private ClassResolver resolver;
	
	@Before
	public void setup() {
		this.resolver = new ClassResolver(this.getClass().getClassLoader());
	}
	
	@Test
	public void testClassLoadNotNull() {
		Class<?> clazz = this.resolver.loadClass(AutoGson.class.getName());
		assertTrue(clazz != null);
	}
	
	@Test
	public void testClassNullString() {
		Class<?> clazz = this.resolver.loadClass(null);
		assertTrue(clazz == null);
	}
	
	@Test
	public void testClassLoadNull() {
		Class<?> clazz = this.resolver.loadClass("ThiScLaSsDoEsNoTEXIST");
		assertTrue(clazz == null);
	}
	
	@Test
	public void testClassExists() {
		assertTrue(this.resolver.classExists(AutoGson.class.getName()));
	}
	
	@Test
	public void testClassDoesNotExist() {
		assertFalse(this.resolver.classExists("ThiScLaSsDoEsNoTEXIST"));
	}
}