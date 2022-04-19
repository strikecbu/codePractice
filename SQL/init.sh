# mysql
#創建並運行一個容器
$ docker run --name mysql -e MYSQL_ROOT_PASSWORD='p@ssw0rd' -p 3306:3306 -p 33060:33060 -d mysql
$ docker exec -it mysql mysql -uroot -p
 # 123456789 <-- (密碼) 
mysql > CREATE DATABASE myDatabase ;

# postgres
$ docker run --name postgresSQL --restart always -p 5432:5432 -e POSTGRES_PASSWORD='p@ssw0rd' -d postgres