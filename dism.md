


``` shell
>  dism /mount-wim /wimfile:"E:\Win7\sources\install.wim" /mountdir:D:\W7 /index:4

> dism /Get-ImageInfo /ImageFile:E:\Win7\sources\install.wim
```

## Lua Redis Script

``` lua
local cursor = 0
local fields = {}
local ids = {}
local key = 'product:name'
local value = '*' .. ARGV[1] .. '*'

repeat
    local result = redis.call('HSCAN', key, cursor, 'MATCH', value)
    cursor = tonumber(result[1])
    fields = result[2]
    for i, id in ipairs(fields) do
        if i % 2 == 0 then
            ids[#ids + 1] = id
        end
    end
until cursor == 0
return ids
```


``` lua

 local havg =  redis.call('HGET','store.20180810.avg:1000001','19')  
 local davg =  redis.call('GET','store.daily.avg:1000001:20180810')  
 local tavg =  redis.call('GET','store.total.avg:1000001')  
 local horders =  redis.call('HGET','store.20180810.orders:1000001','19')  
 local dorders =  redis.call('GET','store.daily.orders:1000001:20180810')  
 local torders =  redis.call('GET','store.total.orders:1000001')  
 local hrate =  redis.call('HGET','store.20180810.rate:1000001','19')  
 local drate =  redis.call('GET','store.daily.rate:1000001:20180810')  
 local trate =  redis.call('GET','store.total.rate:1000001')  
 local hsales =  redis.call('HGET','store.20180810.sales:1000001','19')  
 local dsales =  redis.call('GET','store.daily.sales:1000001:20180810')  
 local tsales =  redis.call('GET','store.total.sales:1000001')  
 local hturnovers =  redis.call('HGET','store.20180810.turnovers:1000001','19')  
 local dturnovers =  redis.call('GET','store.daily.turnovers:1000001:20180810')  
 local tturnovers =  redis.call('GET','store.total.turnovers:1000001')  
 local hvisitors =  redis.call('HGET','store.20180810.visitors:1000001','19')  
 local dvisitors =  redis.call('GET','store.daily.visitors:1000001:20180810')  
 local tvisitors =  redis.call('GET','store.total.visitors:1000001') 




```


