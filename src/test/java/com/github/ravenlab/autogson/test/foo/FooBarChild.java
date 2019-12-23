package com.github.ravenlab.autogson.test.foo;

import com.github.ravenlab.autogson.test.foo.data.ExtraFooData;
import com.github.ravenlab.autogson.test.foo.data.FooData;

public class FooBarChild extends FooBar {

	private String fooBar;
	private ExtraFooData<String> fooData;
	public FooBarChild()
	{
		super();
		this.fooBar = "foobar";
		this.fooData = new ExtraFooData<>("somedata", "someotherdata");
	}

	public String getFooBar()
	{
		return this.fooBar;
	}
	
	public FooData<String> getData()
	{
		return this.fooData;
	}
}
