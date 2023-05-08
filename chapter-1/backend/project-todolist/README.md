# multi-module-java
multi-module-java

# project structor
```
.
├── README.md
├── build.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
├── src
│   └── main
│       └── java
│           └── com
│               └── multi
│                   └── Main.java
├── todolist-api-server
│   ├── Dockerfile
│   ├── README.md
│   ├── build
│   │   ├── bootJarMainClassName
│   │   ├── classes
│   │   │   └── java
│   │   │       └── main
│   │   │           └── com
│   │   │               └── todolist
│   │   │                   ├── TodoListMain.class
│   │   │                   └── controller
│   │   │                       └── TodoListController.class
│   │   ├── generated
│   │   │   └── sources
│   │   │       ├── annotationProcessor
│   │   │       │   └── java
│   │   │       │       └── main
│   │   │       └── headers
│   │   │           └── java
│   │   │               └── main
│   │   ├── libs
│   │   │   └── todolist-api-server-1.0-SNAPSHOT.jar
│   │   └── tmp
│   │       ├── bootJar
│   │       │   └── MANIFEST.MF
│   │       └── compileJava
│   │           └── previous-compilation-data.bin
│   ├── build.gradle
│   ├── src
│   │   └── main
│   │       └── java
│   │           └── com
│   │               └── todolist
│   │                   ├── TodoListMain.java
│   │                   └── controller
│   │                       └── TodoListController.java
│   └── task-dev-spring-an2.json
├── todolist-batch-server
│   ├── Dockerfile
│   ├── build
│   │   ├── bootJarMainClassName
│   │   ├── classes
│   │   │   └── java
│   │   │       └── main
│   │   │           └── com
│   │   │               └── todolist
│   │   │                   ├── BatchMain$1.class
│   │   │                   ├── BatchMain.class
│   │   │                   ├── impl
│   │   │                   │   └── TodoListRepository.class
│   │   │                   ├── imple
│   │   │                   │   └── service
│   │   │                   │       └── AdminService.class
│   │   │                   └── job
│   │   │                       ├── AdminItemProcessor.class
│   │   │                       ├── BatchConfiguration.class
│   │   │                       └── ScheduledTask.class
│   │   ├── generated
│   │   │   └── sources
│   │   │       ├── annotationProcessor
│   │   │       │   └── java
│   │   │       │       └── main
│   │   │       └── headers
│   │   │           └── java
│   │   │               └── main
│   │   ├── libs
│   │   │   └── multi-batch-server-1.0-SNAPSHOT.jar
│   │   ├── resources
│   │   │   └── main
│   │   │       ├── application-prd.yaml
│   │   │       ├── application.yaml
│   │   │       └── sql
│   │   │           ├── admin.csv
│   │   │           └── admin.sql
│   │   └── tmp
│   │       ├── bootJar
│   │       │   └── MANIFEST.MF
│   │       └── compileJava
│   │           └── previous-compilation-data.bin
│   ├── build.gradle
│   └── src
│       └── main
│           ├── java
│           │   └── com
│           │       └── todolist
│           │           ├── BatchMain.java
│           │           ├── core
│           │           │   └── schema-postsql.sql
│           │           ├── impl
│           │           │   └── TodoListRepository.java
│           │           ├── imple
│           │           │   └── service
│           │           │       └── TodoListService.java
│           │           ├── initdata
│           │           └── job
│           │               ├── BatchConfiguration.java
│           │               ├── ScheduledTask.java
│           │               └── TodoListItemProcessor.java
│           └── resources
│               ├── application-prd.yaml
│               ├── application.yaml
│               └── sql
│                   ├── admin.csv
│                   └── admin.sql
└── todolist-core
    ├── build
    │   ├── bootJarMainClassName
    │   ├── classes
    │   │   └── java
    │   │       └── main
    │   │           └── com
    │   │               └── todolist
    │   │                   ├── Main.class
    │   │                   └── domain
    │   │                       └── todolist
    │   │                           ├── TodoList$TodoListBuilder.class
    │   │                           └── TodoList.class
    │   ├── generated
    │   │   └── sources
    │   │       ├── annotationProcessor
    │   │       │   └── java
    │   │       │       └── main
    │   │       └── headers
    │   │           └── java
    │   │               └── main
    │   ├── libs
    │   │   └── todolist-core-1.0-SNAPSHOT-plain.jar
    │   └── tmp
    │       ├── compileJava
    │       │   └── previous-compilation-data.bin
    │       └── jar
    │           └── MANIFEST.MF
    ├── build.gradle
    └── src
        └── main
            └── java
                └── com
                    └── todolist
                        ├── Main.java
                        └── domain
                            └── todolist
                                └── TodoList.java
```