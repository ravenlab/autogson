package com.github.ravenlab.autogson.test.foo.data;

public class FooData<T> {

	private T data;
	public FooData(T data)
	{
		this.data = data;
	}
	
	public T getData()
	{
		return this.data;
	}
}