Installation
------------
You mast to use Apach Tomcat as a servlet container. Install MySQL server and 
client on your server. To config MySQL connection you mast to download 
[mysql-connector](http://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-5.1.37.tar.gz)
 and put it in ``` $CATALINA_HOME/lib/ ``` folder.
Create 'private_disk' database and file table using code below 
```sql
CREATE DATABASE private_disk;

CREATE TABLE `private_disk`.`file` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `size` BIGINT UNSIGNED NOT NULL,
  `sha1` VARCHAR(255) NOT NULL UNIQUE,
  PRIMARY KEY (`id`));
```
In ```/webapp/META-INF/context.xml ``` you mast to change ```username="***" password="***"```
information for connection to MySQL DB. And ```url="jdbc:mysql://127.0.0.1:3306/private_disk"``` if MySQL
 server is on remoute server, not on your localhost.

Usage
----- 
Start Tomcat server and deploy *.war file on server, when open localhost:8080/ web page.
You can upload files by using form on page, ```download``` file by using appropriate button and ```delete```
file also with button. You can search files in folder by using full file name or some wildcards to search.

![](https://hsto.org/files/99c/d17/3b1/99cd173b1ee143a59463a0aee317c95d.jpg)
