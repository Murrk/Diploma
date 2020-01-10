# AQA Diploma

## Дипломный проект профессии «Тестировщик»

[План автоматизации](artifacts/Plan.md)

### Инструкция по запуску

Перед запуском тестов необходимо установить [java 8](https://www.oracle.com/technetwork/java/javase/downloads/2133151)

Так же для запуска вам понадобится Docker  [Инструкция по установке Docker](https://github.com/netology-code/aqa-homeworks/blob/master/docker/installation.md)

* Склонировать репозиторий `git clone https://github.com/Murrk/Diploma.git`
* Перейти в директорию с проектом

* Для MySQL

Запуск контейнера:

`docker-compose -f docker-compose-mysql.yml up -d`

Запуск приложения:

`java -Dspring.datasource.url=jdbc:mysql://192.168.99.100:3306/app -jar aqa-shop.jar`

Остановка контейнера:

`docker-compose -f docker-compose-mysql.yml down`

* Для Postgres

Запуск контейнера:

`docker-compose -f docker-compose-postgres.yml up -d`

Запуск приложения:

`java -Dspring.datasource.url=jdbc:postgresql://192.168.99.100:5432/app -jar aqa-shop.jar`

Остановка контейнера:

`docker-compose -f docker-compose-postgres.yml down`

* Для запуска тестов:
* Выполнить команду `./gradlew test` (`./gradlew.bat test`, если запускается из windows)
* Отчет можно посмотреть в директории `build/reports/tests/test/`

* Для запуска тестов Allure выполнить команду:

MySQL: `gradlew -Ddb.url=jdbc:mysql://192.168.99.100:3306/app clean test allureReport`

Postgres: `gradlew -Ddb.url=jdbc:postgresql://192.168.99.100:5432/app clean test allureReport`

* Для просмотра результатов `gradlew allureServe`

[Отчётные документы по итогам тестирования](artifacts/Report.md)

[Отчётные документы по итогам автоматизации](artifacts/Summary.md)