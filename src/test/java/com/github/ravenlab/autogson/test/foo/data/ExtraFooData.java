package com.github.ravenlab.autogson.test.foo.data;

public class ExtraFooData<T> extends FooData<T> {

	private T extraData;
	
	public ExtraFooData(T data, T extraData) {
		super(data);
	}

	public T getExtraData(){
		return this.extraData;
	}
}