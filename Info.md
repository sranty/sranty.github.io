#


``` conf

k_s_service s 
k_s_usercard_level ul
k_s_pro_order po 
k_s_card_type ct 
k_s_residual_card rc 
k_s_payment_info p
k_s_change_repay_history h
k_s_refund r
k_s_refund_order s

k_s_member m
t_s_user_org uo
t_s_label_member
t_s_depart d
t_s_base_user b

```
# 服务操作

```
k_s_user_point  //用户
t_s_base_user   // 用户

k_s_detail_card  // 卡产品关联
k_s_card_type_card // 卡卡关联


k_s_service  // 卡项服务

k_s_detail_card


k_s_residual_card  // 会员购买的止

k_s_card_type   // 门店出售的卡
k_s_pro_order  // 销售订单







```







``` conf

[mysqld]
pid-file        = /var/run/mysqld/mysqld.pid
socket          = /var/run/mysqld/mysqld.sock
datadir         = /var/lib/mysql
log-error       = /var/log/mysql/error.log
log-bin         = /var/lib/mysql/mysql-bin
server-id       = 2020

# By default we only accept connections from localhost
bind-address    = 0.0.0.0
# Disabling symbolic-links is recommended to prevent assorted security risks
symbolic-links=0
innodb_thread_concurrency       = 8
innodb_io_capacity              = 200
innodb_log_files_in_group       = 1
innodb_log_file_size            = 1000m

back_log                = 500
max_connections         = 8000
max_user_connections    = 2000
table_open_cache        = 80000
thread_cache_size       = 512
wait_timeout            = 1800
interactive_timeout     = 1800
sync_binlog             = 1

key_buffer_size         = 512m
innodb_buffer_pool_size = 2048m
transaction_isolation = READ-COMMITTED
innodb_lock_wait_timeout = 600
lock_wait_timeout = 600
max_binlog_size   = 256M

query_cache_limit       = 1M
query_cache_size        = 128M
max_allowed_packet      = 1024M
thread_stack            = 256K



```