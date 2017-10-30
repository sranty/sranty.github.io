
# Install Git With Source 

## Install Dependencies

``` java
apt-get install update -y
apt-get update -y
apt-get upgrade  

apt-get install build-essential libssl-dev libcurl4-gnutls-dev libexpat1-dev gettext unzip
apt-get install autoconf asciidoc
```

## Get git latest version

```
wget https://github.com/git/archives/git-master.zip

```
> or clone with git old version

```
git clone https://github.com/git/git.git --depth=1

```

## compile git source

```
# cd git
# make configure
# ./configure --prefix=/usr
# make all doc
# make install install-doc install-html
# 

```

# Install Nginx Git Server

>  install nginx 

```
# apt-get install nginx
```
> install 

```
# apt-get install fcgiwrap spawn-fcgi
```

# Install fcgiwrap with source

```
 apt-get  install pkg-config
```


```
apt-get install libcgi-session-perl

locate CGI.pm
```



