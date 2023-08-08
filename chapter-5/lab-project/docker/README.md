# mysql container build


## preparation
```
export MYSQL_ROOT_PASSWORD=root1234
```

## docker build
```
docker build --tag mysql .
```

## docker run
```
docker run -p 3306:3306 mysql:latest -d

>> console log...
230608 13:31:01 [Warning] Using unique option prefix key_buffer instead of key_buffer_size is deprecated and will be removed in a future release. Please use the full name instead.
230608 13:31:01 [Note] Ignoring --secure-file-priv value as server is running with --bootstrap.
230608 13:31:01 [Note] /usr/sbin/mysqld (mysqld 5.5.62-0ubuntu0.14.04.1) starting as process 30 ...
230608 13:31:01 [Warning] Using unique option prefix key_buffer instead of key_buffer_size is deprecated and will be removed in a future release. Please use the full name instead.
230608 13:31:01 [Note] Ignoring --secure-file-priv value as server is running with --bootstrap.
230608 13:31:01 [Note] /usr/sbin/mysqld (mysqld 5.5.62-0ubuntu0.14.04.1) starting as process 36 ...
230608 13:31:01 [Warning] Using unique option prefix key_buffer instead of key_buffer_size is deprecated and will be removed in a future release. Please use the full name instead.
230608 13:31:01 [Note] Ignoring --secure-file-priv value as server is running with --bootstrap.
230608 13:31:01 [Note] mysqld (mysqld 5.5.62-0ubuntu0.14.04.1) starting as process 41 ...
230608 13:31:03 [Warning] Using unique option prefix key_buffer instead of key_buffer_size is deprecated and will be removed in a future release. Please use the full name instead.
230608 13:31:03 [Note] mysqld (mysqld 5.5.62-0ubuntu0.14.04.1) starting as process 61 ...

```


