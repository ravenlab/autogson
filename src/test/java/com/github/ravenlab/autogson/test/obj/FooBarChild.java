package com.github.ravenlab.autogson.test.obj;

public class FooBarChild extends FooBar {

	private String fooBar;
	public FooBarChild()
	{
		super();
		this.fooBar = "foobar";
	}

	public String getFooBar()
	{
		return this.fooBar;
	}
}
