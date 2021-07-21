# spring-api-download-logs
Project web service download file by ssh and compress zip file.


# Configuration ENVIRONMENT.

## ENVIRONMENT VALUES:

| BASE_PATH | USER_HOST | PASS_HOST | PORT | SERVER_PORT |
| ------------- | ------------- |------------- |------------- |------------- |
| /path/directory | username | password | 22 | 9090 |

## ENVIRONMENT ECLIPSE

BASE_PATH=/path/directory

USER_HOST=username

PASS_HOST=password

PORT=22

SERVER_PORT=9090

## ENVIRONMENT JVM

| COMMAND |
| ------------- |
| -DBASE_PATH=/path/directory -DUSER_HOST=username -DPASS_HOST=password -DPORT=22 -DSERVER_PORT=9090|

##SWAGGER UI

http://localhost:9090/

![alt text](doc/img/rest-service.png)

