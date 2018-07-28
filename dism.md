


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


