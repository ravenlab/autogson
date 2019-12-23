# autogson

[![Build Status](https://travis-ci.org/ravenlab/autogson.svg?branch=master)](https://travis-ci.org/ravenlab/autogson)
[![codecov](https://codecov.io/gh/ravenlab/autogson/branch/master/graph/badge.svg)](https://codecov.io/gh/ravenlab/autogson)
[![license](https://img.shields.io/badge/license-MIT-blue)](LICENSE)

A utility for automatic conversion to and from json using [gson.](https://github.com/google/gson)

## Using

You will need [gson](https://github.com/google/gson) in your class path as autogson does not include it.

```java
//Create a gson instance

//To get json
String json = AutoGson.toJson(gson, object);
	
//From json
FooBar bar = AutoGson.fromJson(gson, json);
```

## How

When creating a json string a seperate field in the json with the class's name is created.

When deserializing from json the new field is used to find the class via reflection to create your new object.

## Caveats

Since the class name is stored if the class is missing or refactored to a new class path autogson will not be able to deserialize your object.