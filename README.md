![KDocu Logo](.github/kdocu-logo.png)

**K**otlin utilities for **Docu**mentum DFC

### About the project

**KDocu** aims to make Documentum DFC more Kotlin friendly

A small example :
```Kotlin
fun main(args: Array<String>) {
    getSession("myrepo", "myuser", "mypassword").use { session ->
        execReadQuery(session, "select object_name from dm_server_config").use { collection ->
            while (collection.next()) {
                println(collection.getString("object_name"))
            }
        }
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
        println(collection.getString("object_name"))
    }
}
// The collection is closed automatically
```

Available functions for executing queries are :
- `execQuery(session, query)` : Execute a DQL query
- `execReadQuery(session, query)` : Execute a read-only select DQL query. The query must not make any change during processing
- `execCachedQuery(session, query)` : Execute a read-only cached DQL query
- `execApplyQuery(session, query)` : Execute a DQL Query that invoke procedure. For example GET_PATH
