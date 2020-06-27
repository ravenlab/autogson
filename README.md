# autogson

![build](https://github.com/ravenlab/autogson/workflows/build/badge.svg)
[![codecov](https://codecov.io/gh/ravenlab/autogson/branch/master/graph/badge.svg)](https://codecov.io/gh/ravenlab/autogson)
[![](https://jitpack.io/v/ravenlab/autogson.svg)](https://jitpack.io/#ravenlab/autogson)
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

### Gradle

``` groovy
repositories {
	maven { url 'https://jitpack.io' }
}

compile 'com.github.ravenlab:autogson:2.0.0'
```

### Maven

``` xml
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>

<dependency>
	<groupId>com.github.ravenlab</groupId>
	<artifactId>autogson</artifactId>
	<version>2.0.0</version>
</dependency>
```

## How

When creating a json string a seperate field in the json with the class's name is created.

When deserializing from json the new field is used to find the class via reflection to create your new object.

## Caveats

Since the class name is stored if the class is missing or refactored to a new class path autogson will not be able to deserialize your object.