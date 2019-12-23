# AQA Diploma

## Дипломный проект профессии «Тестировщик»

### Инструкция по запуску(пока не актуальна)

Перед запуском тестов необходимо установить [java 8](https://www.oracle.com/technetwork/java/javase/downloads/2133151)

Так же для запуска вам понадобится Docker  [Инструкция по установке Docker](https://github.com/netology-code/aqa-homeworks/blob/master/docker/installation.md)

* Склонировать репозиторий `git clone https://github.com/Murrk/Diploma.git`
* Перейти в директорию с проектом
* Запустить контейнер `docker-compose up -d`
* Перейти в директорию artifacts
* Запустить jar целевого сервиса набрав в консоли команду `java -jar aqa-shop.jar`
* Выполнить команду `./gradlew test` (`./gradlew.bat test`, если запускается из windows)
* Отчет можно посмотреть в директории `build/reports/tests/test/`
