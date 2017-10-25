# Install git on Ubuntu Server

## 1 Get start

### You can do this by running the following command:

``` code
sudo apt-get update -y
sudo apt-get upgrade -y
```

## 2  Install Required Packages
>  First, you will need to install some required packages including nginx, git, nano and fcgiwrap to your system. You can install all of them by running the following command:

```
sudo apt-get install nginx git nano fcgiwrap apache2-utils -y
```
>  Once all the required packages are installed, you will need to create a directory for Git repository. You can do this by running the following command:
```
sudo mkdir /var/www/html/git
```
### Next, give proper permission to the Git directory:
```
sudo chown -R www-data:www-data /var/www/html/git
```

> Once you are done, you can proceed to configure Nginx web server.

## 3 Configure Nginx
> First, you will need to configure Nginx to pass on Git traffic to Git. You can do this by editing Nginx default configuration file:
sudo nano /etc/nginx/sites-available/default

### Change the file as shown below:

> Default server configuration
```
server {
        listen 80 default_server;
        listen [::]:80 default_server;

        root /var/www/html/git;
        # Add index.php to the list if you are using PHP
        index index.html index.htm index.nginx-debian.html;

        server_name _;

        location / {
                # First attempt to serve request as file, then
                # as directory, then fall back to displaying a 404.
                try_files $uri $uri/ =404;
        }

        location ~ (/.*) {
            client_max_body_size 0; # Git pushes can be massive, just to make sure nginx doesn't suddenly cut the connection add this.
            auth_basic "Git Login"; # Whatever text will do.
            auth_basic_user_file "/var/www/html/git/htpasswd";
            include /etc/nginx/fastcgi_params; # Include the default fastcgi configs
            fastcgi_param SCRIPT_FILENAME /usr/lib/git-core/git-http-backend; # Tells fastcgi to pass the request to the git http backend executable
            fastcgi_param GIT_HTTP_EXPORT_ALL "";
            fastcgi_param GIT_PROJECT_ROOT /var/www/html/git; # /var/www/git is the location of all of your git repositories.
            fastcgi_param REMOTE_USER $remote_user;
            fastcgi_param PATH_INFO $1; # Takes the capture group from our location directive and gives git that.
            fastcgi_pass  unix:/var/run/fcgiwrap.socket; # Pass the request to fastcgi
        }

}
``` 
> Save and close the file when you are finished. Then test Nginx for any configuration error with the following command:
```
sudo nginx -t
``` 

> If everything is fine, you should see the following output:
```
nginx: the configuration file /etc/nginx/nginx.conf syntax is ok
nginx: configuration file /etc/nginx/nginx.conf test is successful
``` 
> Next, you will need to create a user account of which you will need to use to browse of commit to the repository. You can create user with name hitesh by using the htpasswd utility:
``` 
sudo htpasswd -c /var/www/html/git/htpasswd hitesh
``` 

> Finally, restart Nginx to apply all the changes with the following command:
``` 
sudo systemctl restart nginx
``` 

> You can check the status of the Nginx server with the following command:
``` 
sudo systemctl status nginx 
```

> You should see the following output:
``` 
?? nginx.service - A high performance web server and a reverse proxy server
   Loaded: loaded (/lib/systemd/system/nginx.service; enabled; vendor preset: enabled)
   Active: active (running) since Tue 2017-06-20 23:00:11 IST; 51min ago
  Process: 12415 ExecStop=/sbin/start-stop-daemon --quiet --stop --retry QUIT/5 --pidfile /run/nginx.pid (code=exited, status=0/SUCCESS)
  Process: 7616 ExecReload=/usr/sbin/nginx -g daemon on; master_process on; -s reload (code=exited, status=0/SUCCESS)
  Process: 12423 ExecStart=/usr/sbin/nginx -g daemon on; master_process on; (code=exited, status=0/SUCCESS)
  Process: 12419 ExecStartPre=/usr/sbin/nginx -t -q -g daemon on; master_process on; (code=exited, status=0/SUCCESS)
 Main PID: 12427 (nginx)
   CGroup: /system.slice/nginx.service
           ??????12427 nginx: master process /usr/sbin/nginx -g daemon on; master_process on
           ??????12431 nginx: worker process                           

Jun 20 23:00:11 localhost systemd[1]: Stopped A high performance web server and a reverse proxy server.
Jun 20 23:00:11 localhost systemd[1]: Starting A high performance web server and a reverse proxy server...
Jun 20 23:00:11 localhost systemd[1]: nginx.service: Failed to read PID from file /run/nginx.pid: Invalid argument
Jun 20 23:00:11 localhost systemd[1]: Started A high performance web server and a reverse proxy server.
``` 

## 4 Create Git Repository
> Once everything is configured properly, it's time to create Git repository.
You can create repository with name repo.git with the following command:
``` 
cd /var/www/html/git
sudo mkdir hitesh.gitt
sudo git --bare initt
sudo git update-server-info
sudo chown -R www-data.www-data .
sudo chmod -R 777 .
``` 

> Next, you will need to allow http service through UFW firewall. By default UFW is disabled on your system, so you need to enable it first. You can enable it with the following command:
```
sudo ufw enable
``` 
> Once UFW firewall is enabled, you can allow HTTP service by running the following command:
``` 
sudo ufw allow http
``` 

> You can now check the status of UFW firewall by running the following command:
``` 
sudo ufw status
``` 

> Ok that's it for the server side configuration. You can now move on to the client side to test Git.



## **5 Test Git on Client Machine**
> Before starting, you will need to install git on the client system. You can install it with the following command:
```
sudo apt-get install git -y
``` 

> First, create a local repository with the following command:
```
sudo mkdir ~/testproject
``` 

> Next, change the directory to testproject and initiate the new remote repository with the following command:
``` 
cd ~/testproject
git init
git remote add origin http://hitesh@192.168.15.189/hitesh.git
```

> Next, create some files and directory with the following command:
```
mkdir test1 test2 test3
echo "This is my first repository" > test1/repo1
echo "This is my second repository" > test2/repo2
echo "This is my third repository" > test3/repo3
``` 

> Next, run the following command to add all the files and directories to the repository:
``` 
git add .
git commit -a -m "Add files and directoires"
``` 

> You should see the following output:
```
[master 002fac9] Add files and directoires
 3 files changed, 3 insertions(+)
 create mode 100644 repo1
 create mode 100644 repo2
 create mode 100644 repo3
 ``` 
> Next, push all the files and directories to the Git server with the following command:
```
git push origin master
``` 

> You should see the following output:
``` 
Password for 'http://hitesh@192.168.15.189': 
Counting objects: 6, done.
Delta compression using up to 4 threads.
Compressing objects: 100% (2/2), done.
Writing objects: 100% (5/5), 422 bytes | 0 bytes/s, done.
Total 5 (delta 0), reused 0 (delta 0)
To http://hitesh@192.168.15.189/hitesh.git
   68f1270..002fac9  master -> master
```
> Now, your all files and directories have been committed to your Git server.
Your Git repository creation process is now completed. You can now easily clone your repository in future. You can clone your repository using the following command on remote system:
```
git clone hitesh@192.168.15.189:/var/www/html/git/hitesh.git
```
> You should see the following output:
``` 
Cloning into 'hitesh'...
hitesh@192.168.15.189's password: 
remote: Counting objects: 8, done.
remote: Compressing objects: 100% (3/3), done.
Receiving objects: 100% (8/8), 598 bytes | 0 bytes/s, done.
remote: Total 8 (delta 0), reused 0 (delta 0)
Checking connectivity... done.
``` 
> Now, change the directory to the cloned repository with the following command:
``` 
cd hitesh
tree

You should see the following output:
.
|-- test1
|   `-- repo1
|-- test2
|   `-- repo2
`-- test3
    `-- repo3

3 directories, 3 files
``` 

