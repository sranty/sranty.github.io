 
 ```
 sudo ufw allow 80 允许外部访问80端口
 sudo ufw delete allow 80 禁止外部访问80 端口
 sudo ufw allow from 192.168.1.1 允许此IP访问所有的本机端口
 sudo ufw deny smtp 禁止外部访问smtp服务
 sudo ufw delete allow smtp 删除上面建立的某条规则
 sudo ufw deny proto tcp from 10.0.0.0/8 to 192.168.0.1 port 22 要拒绝所有的TCP流量从10.0.0.0/8 到192.168.0.1地址的22端口
 可以允许所有RFC1918网络（局域网/无线局域网的）访问这个主机（/8,/16,/12是一种网络分级）：
 sudo ufw allow from 10.0.0.0/8
 sudo ufw allow from 172.16.0.0/12
 sudo ufw allow from 192.168.0.0/16
```