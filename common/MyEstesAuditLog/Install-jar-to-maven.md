## Maven Jar Installation Command

1) Run following command to make jar
```shell
mvn clean package
```
2) Run following command to install jar to local maven repository

```shell
mvn install:install-file -Dfile=target/MyEstesAuditLog-1.0.0.0.jar -DgroupId=com.estes.myestes.MyEstesAuditLog -DartifactId=MyEstesAuditLog -Dversion=1.0.0.0 -Dpackaging=jar -DgeneratePom=true
```
