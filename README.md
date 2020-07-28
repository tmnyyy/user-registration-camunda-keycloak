# Саморегистрация пользователя с ручным подтверждением
# Используемые техонологии
- Java 8
- Сamunda BPM 7.13.0
- Keycloak 11.0.0
## Общие настройки

http://localhost:8181 - camunda   
login: admin  
password: admin

http://localhost:8080/auth - keycloak  
login: admin  
password: admin  

## Сборка проекта
```
git clone https://github.com/tmnyyy/user-registration-camunda-keycloak.git
Переходим в каталог backend и выполняем следующие команды.
mvn package
Запуск и сборку лучше проводить из среды разработки.
java -jar backend-1.0.0-SNAPSHOT.jar

Собираем frontend
Переходим в каталог frontend и выполняем команды
npm install
ng serve для запуска dev сервера, по умолчанию будет запущен на http://localhost:4200/
```

## Модель
![alt text](doc/img/model.png)
##Проверка
При входе в приложение по адресу(http://localhost:4200/) требуется заполнить указанную информацию.
![alt text](doc/img/interface_1.png)
![alt text](doc/img/interface_2.png)
![alt text](doc/img/interface_3.png)
![alt text](doc/img/interface_4.png)
![alt text](doc/img/interface_5.png)
![alt text](doc/img/interface_6.png)
![alt text](doc/img/interface_7.png)
![alt text](doc/img/interface_8.png)

Как альтернатива можно отправить post запрос (например, через postman):

Пример:

Отправляем запрос на http://localhost:8181/engine-rest/process-definition/key/registration_user_process/start

```json
 {
      "variables": {
        "params" : {
            "value" : {"login": "Nikolay", "firstName": "Nikolay", "lastName": "Temnyakov","email": "nike@yandex.ru","password": "12345678"},
            "type": "String"
        }
      },
     "businessKey" : "myBusinessKey"
 }
```
![alt text](doc/img/interface_9.png)