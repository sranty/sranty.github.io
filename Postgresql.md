# Ubuntu install postgresql 

```
sudo -u postgres psql
```

> 其中，sudo -u postgres 是使用postgres 用户登录的意思
> PostgreSQL数据默认会创建一个postgres的数据库用户作为数据库的管理员，密码是随机的
> postgres=# ALTER USER postgres WITH PASSWORD '123456'; 

> postgres=#为PostgreSQL下的命令提示符，--注意最后的分号；
## 退出PostgreSQL psql客户端
```
postgres=# \q
```

## 修改ubuntu操作系统的postgres用户的密码（密码要与数据库用户postgres的密码相同）

> 切换到root用户

```
su root
```

> 删除PostgreSQL用户密码

```
sudo passwd -d postgres
```

> passwd -d 是清空指定用户密码的意思
> 设置PostgreSQL系统用户的密码
```
sudo -u postgres passwd
```

> 按照提示，输入两次新密码
```
输入新的 UNIX 密码
重新输入新的 UNIX 密码
passwd：已成功更新密码
```
## 修改PostgresSQL数据库配置实现远程访问
```
vim /etc/postgresql/9.5/main/postgresql.conf
```
> 监听任何地址访问，修改连接权限

```
#listen_addresses = 'localhost' 改为 listen_addresses = '*'
```

> 启用密码验证
```
#password_encryption = on 改为 password_encryption = on


vi /etc/postgresql/9.4/main/pg_hba.conf

```

> 在pg_hba.conf文档末尾加上以下内容
```
host all all 0.0.0.0 0.0.0.0 md5
```

## 重启服务

```

/etc/init.d/postgresql restart
```

## 5432端口的防火墙设置

> 5432为postgreSQL默认的端口

```
iptables -A INPUT -p tcp -m state --state NEW -m tcp --dport 5432 -j ACCEPT

ufw allow 5432
````