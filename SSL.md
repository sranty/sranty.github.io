
# SSL keystore pem cer 


```

> keytool -genkeypair -alias metonvip -keyalg RSA -dname "CN=rajind,OU=dev,O=bft,L=mt,C=Srilanka" -keystore metonvip.jks -keypass keypassword -storepass storepassword

> keytool -importkeystore -srckeystore metonvip.jks -srcstorepass storepassword -srckeypass keypassword -srcalias metonvip -destalias metonvip -destkeystore metonvip.p12 -deststoretype PKCS12 -deststorepass dspassword -destkeypass dkpassword

> openssl pkcs12 -in metonvip.p12 -nodes -nocerts -out private_key.pem
> openssl pkcs12 -in metonvip.p12 -nokeys -out cert.pem





```


```

>  keytool -importkeystore -srckeystore meton.jks -destkeystore meton.p12 -srcstoretype jks -deststoretype pkcs12

新入新密码
。。。

源密码


```