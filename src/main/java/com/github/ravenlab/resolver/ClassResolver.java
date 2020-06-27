package com.github.ravenlab.resolver;

public class ClassResolver {

	private ClassLoader loader;
	
	public ClassResolver(ClassLoader loader) {
		this.loader = loader;
	}
	
	public Class<?> loadClass(String className) {
		if(className == null) {
			return null;
		}
		try {
			return this.loader.loadClass(className);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	public boolean classExists(String className) {
		return this.loadClass(className) != null;
	}
}