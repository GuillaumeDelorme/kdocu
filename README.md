![KDocu Logo](.github/kdocu-logo.png)

**K**otlin utilities for **Docu**mentum DFC

### About the project

**KDocu** aims to make Documentum DFC more Kotlin friendly.

A small example :
```Kotlin
fun main(args: Array<String>) {
    getSession("myrepo", "myuser", "mypassword").use { session ->
        // DQL query execution
        execReadQuery(session, "select object_name from dm_server_config").use { collection ->
            while (collection.next()) {
                println(collection[DmServerConfig.objectName])
            }
        }
        
        // Object creation
        val myNewObject = session.newObject(DmSysobject.typeName)
            
        myNewObject[MyType.objectName] = "Kotlin is awesome"
        myNewObject[MyType.myInt] = 1337
        myNewObject[MyType.myRepeatingDouble] = listOf(1.0, 1.9, 0.2)
    
        myNewObject.save()
    }
}
```

### Sessions

Get a Documentum session and use it like an AutoCloseable class :
```Kotlin
getSession("myrepo", "myuser", "mypassword").use { session ->
    // Using the session...
}
// The session is closed automatically
```

### Queries and collections

Execute a DQL query and use the collection like an AutoCloseable class :
```Kotlin
execReadQuery(session, "select object_name from dm_server_config").use { collection ->
    while (collection.next()) {
        println(collection[DmServerConfig.objectName])
    }
}
// The collection is closed automatically
```

Available functions for executing queries are :
- `execQuery(session, query)` : Execute a DQL query
- `execReadQuery(session, query)` : Execute a read-only select DQL query. The query must not make any change during processing
- `execCachedQuery(session, query)` : Execute a read-only cached DQL query
- `execApplyQuery(session, query)` : Execute a DQL Query that invoke procedure. For example GET_PATH

### Documentum typed object operators

Indexed access operators has been defined for the type IDfTypedObject to simplify get and set operations, example :
```Kotlin
myObject[StringAttr("my_string")] = "str"
println(myObject[StringAttr("my_string")]) // str
```

Available attribute types are :
 - `BooleanAttr`
 - `IntAttr`
 - `StringAttr`
 - `IdAttr`
 - `TimeAttr`
 - `DoubleAttr`
 - `RepeatingBooleanAttr`
 - `RepeatingIntAttr`
 - `RepeatingStringAttr`
 - `RepeatingIdAttr`
 - `RepeatingTimeAttr`
 - `RepeatingDoubleAttr`

### Type aliases for repeating types

Type aliases has been created for repeating types :
- `RepeatingBoolean` : `List<Boolean>`
- `RepeatingInt` : `List<Int>`
- `RepeatingString` : `List<String>`
- `RepeatingId` : `List<IDfId>`
- `RepeatingTime` : `List<IDfTime>`
- `RepeatingDouble` : `List<Double>`

This can be used to simplify repeating manipulation, example :
```Kotlin
myObject[RepeatingStringAttr("my_repeating_string")] = listOf("str1", "str2")
println(myObject[RepeatingStringAttr("my_repeating_string")) // [str1, str2]
println(myObject[RepeatingStringAttr("my_repeating_string")].contains("str1")) // true
```

### Ensuring type safety

To ensure type safety and simplify your code, you can create objects to describes Documentum types :
```Kotlin
object MyType {
    val typeName = "my_type"
    
    val rObjectId = IdAttr("r_object_id")
    val objectName = StringAttr("object_name")
    val aStatus = StringAttr("a_status")
    val myInt = IntAttr("my_int")
    val myRepeatingDouble = RepeatingDoubleAttr("my_repeating_double")
}
    
fun main(args: Array<String>) {
    getSession("myrepo", "myuser", "mypassword").use { session ->
        val myNewObject = session.newObject(MyType.typeName)
    
        myNewObject[MyType.objectName] = "Kotlin is awesome"
        myNewObject[MyType.aStatus] = "Draft"
        myNewObject[MyType.myInt] = 42
        myNewObject[MyType.myRepeatingDouble] = listOf(1.0, 1.9, 0.2)
    
        myNewObject.save()
    }
}
```

### Building the project

To build the Gradle project, you need the DFC dependency that isn't available in Maven public repositories.
You can download Documentum REST Services SDK on the EMC support website. This package contains scripts to deploy Documentum libraries in your local Maven repository.

### License

KDocu is licensed under the Apache License 2.0.
