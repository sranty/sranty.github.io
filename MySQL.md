# MySQL my.cnf


>> 授权远程访问 ``mysqld.cnf``

``` javascript

bind-address = 0.0.0.0

mysql > grant all privileges on *.* to root@'%' identified by 'mysqlpass' with grant option;

```

>> 解决运程连接速度慢的问题 ``mysqld.cnf``

``` console

skip-name-resolve

```