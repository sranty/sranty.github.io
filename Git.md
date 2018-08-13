
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

# Resolve http  client intended to send too large chunked body
# 413 Request Entity Too Large

> nginx 
```
client_max_body_size 100m
```

> git 

```
git config --global http.postBuffer 157286400
```

# Auth Failed git project dir permisson

```
git pull origin master --allow-unrelated-histories
``` 


# Init Git And add remote

``` shell
$ > git init
Initialized empty Git repository in D:/demo/demo/.git/
$ > git remote add origin http://git.ku-link.win/cvs-android
$ > git pull
remote: Counting objects: 3, done.
remote: Total 3 (delta 0), reused 0 (delta 0)
Unpacking objects: 100% (3/3), done.
From http://git.ku-link.win/cvs-android
 * [new branch]      master     -> origin/master
There is no tracking information for the current branch.
Please specify which branch you want to merge with.
See git-pull(1) for details.

    git pull <remote> <branch>

If you wish to set tracking information for this branch you can do so with:

    git branch --set-upstream-to=origin/<branch> master

$ > git pull origin master
From http://git.ku-link.win/cvs-android
 * branch            master     -> FETCH_HEAD
$ > git branch --set-upstream-to=origin/master
Branch 'master' set up to track remote branch 'master' from 'origin'.
$ > git pull
Already up to date.
$ > git status
On branch master
Your branch is up to date with 'origin/master'.


```
