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
