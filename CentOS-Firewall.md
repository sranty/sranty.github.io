# CentOS Firewall CMD LIST

``` console

>   firewall-cmd --zone=public --list-ports  #查看所有开放端口

>   firewall-cmd --zone=public --add-port=6379/tcp --permanent  #添加开放端口

>   firewall-cmd --reload #重启生效

>   