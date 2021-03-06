# План
   
## Перечень автоматизируемых сценариев 
```
Валидными данными будем считать:
Номер карты: 
  Карта 1 (Активная "APPROVED") : 4444 4444 4444 4441
  Карта 2 (Не активная "DECLINED") : 4444 4444 4444 4442
Месяц: две цифры, 01 до 12
Год: две цифры, от 21 до 25
Владелец: два слова латиницей, могут содержать дефисы
Cvc/Cvv: три цифры
```
  - **Позитивные** 
    - Карта 1 (Активная)
      - Обычная покупка
        1. Открываем сервис
        1. Выбираем покупку за наличные
        1. Заполняем все поля валидными данными
        1. Нажимаем кнопку подтверждения
        1. Получаем всплывающее сообщение в правом верхнем углу текущей страницы, которое содержит текст "Успешно"
      - Покупка в кредит 
        1. Открываем сервис
        1. Выбираем покупку в кредит
        1. Заполняем все поля валидными данными
        1. Нажимаем кнопку подтверждения
        1. Получаем всплывающее сообщение в правом верхнем углу текущей страницы, которое содержит текст "Успешно"  
     
    - Карта 2 (Не активная)  
      - Обычная покупка 
        1. Открываем сервис
        1. Выбираем покупку за наличные
        1. Заполняем все поля валидными данными
        1. Нажимаем кнопку подтверждения
        1. Получаем всплывающее сообщение в правом верхнем углу текущей страницы, которое содержит текст "Ошибка"
      - Покупка в кредит 
        1. Открываем сервис
        1. Выбираем покупку в кредит
        1. Заполняем все поля валидными данными
        1. Нажимаем кнопку подтверждения
        1. Получаем всплывающее сообщение в правом верхнем углу текущей страницы, которое содержит текст "Ошибка"
  
  - **Негативные** 
    - Карта 1 (Активная)
      - **Поле "Номер карты"**   
        - Пустое поле
          - Обычная покупка
             1. Открываем сервис
             1. Выбираем покупку за наличные
             1. Заполняем все поля валидными данными, кроме поля "Номер карты"
             2. Поле "Номер карты" оставляем пустым
             3. Нажимаем кнопку подтверждения
             4. Получаем ошибку "Неверный формат" у поля "Номер карты" 
          - Покупка в кредит 
             1. Открываем сервис
             2. Выбираем покупку в кредит
             3. Заполняем все поля валидными данными, кроме поля "Номер карты"
             4. Поле "Номер карты" оставляем пустым
             5. Нажимаем кнопку подтверждения
             6. Получаем ошибку "Неверный формат" у поля "Номер карты" 
          
      - **Поле "Месяц"**
        - Пустое поле
          - Обычная покупка
             1. Открываем сервис
             1. Выбираем покупку за наличные
             1. Заполняем все поля валидными данными, кроме поля "Месяц"
             2. Поле "Месяц" оставляем пустым
             3. Нажимаем кнопку подтверждения
             4. Получаем ошибку "Неверный формат" у поля "Месяц" 
          - Покупка в кредит 
             1. Открываем сервис
             2. Выбираем покупку в кредит
             3. Заполняем все поля валидными данными, кроме поля "Месяц"
             4. Поле "Месяц" оставляем пустым
             5. Нажимаем кнопку подтверждения
             6. Получаем ошибку "Неверный формат" у поля "Месяц" 
        - В поле указали месяц, который не существует 
           - Обычная покупка
               1. Открываем сервис
               1. Выбираем покупку за наличные
               1. Заполняем все поля валидными данными, кроме поля "Месяц"
               2. В поле "Месяц" вписываем "00"
               3. Нажимаем кнопку подтверждения
               4. Получаем ошибку "Неверно указан срок действия карты" у поля "Месяц" 
            - Покупка в кредит 
               1. Открываем сервис
               2. Выбираем покупку в кредит
               3. Заполняем все поля валидными данными, кроме поля "Месяц"
               4. В поле "Месяц" вписываем "00"
               5. Нажимаем кнопку подтверждения
               6. Получаем ошибку "Неверно указан срок действия карты" у поля "Месяц"    
      - **Поле "Год"**
        - Пустое поле
          - Обычная покупка
             1. Открываем сервис
             1. Выбираем покупку за наличные
             1. Заполняем все поля валидными данными, кроме поля "Год"
             2. Поле "Год" оставляем пустым
             3. Нажимаем кнопку подтверждения
             4. Получаем ошибку "Неверный формат" у поля "Год" 
          - Покупка в кредит 
             1. Открываем сервис
             2. Выбираем покупку в кредит
             3. Заполняем все поля валидными данными, кроме поля "Год"
             4. Поле "Год" оставляем пустым
             5. Нажимаем кнопку подтверждения
             6. Получаем ошибку "Неверный формат" у поля "Год"
        - В поле указали год, который не существует 
           - Обычная покупка
               1. Открываем сервис
               1. Выбираем покупку за наличные
               1. Заполняем все поля валидными данными, кроме поля "Год"
               2. В поле "Год" вписываем "88"
               3. Нажимаем кнопку подтверждения
               4. Получаем ошибку "Неверно указан срок действия карты" у поля "Год"
            - Покупка в кредит 
               1. Открываем сервис
               2. Выбираем покупку в кредит
               3. Заполняем все поля валидными данными, кроме поля "Год"
               4. В поле "Год" вписываем "88"
               5. Нажимаем кнопку подтверждения
               6. Получаем ошибку "Неверно указан срок действия карты" у поля "Год"
        - В поле указали истекший год 
           - Обычная покупка
               1. Открываем сервис
               1. Выбираем покупку за наличные
               1. Заполняем все поля валидными данными, кроме поля "Год"
               2. В поле "Год" вписываем "19"
               3. Нажимаем кнопку подтверждения
               4. Получаем ошибку "Истёк срок действия карты" у поля "Год"
            - Покупка в кредит 
               1. Открываем сервис
               2. Выбираем покупку в кредит
               3. Заполняем все поля валидными данными, кроме поля "Год"
               4. В поле "Год" вписываем "19"
               5. Нажимаем кнопку подтверждения
               6. Получаем ошибку "Истёк срок действия карты" у поля "Год"        
      - **Поле "Владелец"**
        - Пустое поле
          - Обычная покупка
             1. Открываем сервис
             1. Выбираем покупку за наличные
             1. Заполняем все поля валидными данными, кроме поля "Владелец"
             2. Поле "Владелец" оставляем пустым
             3. Нажимаем кнопку подтверждения
             4. Получаем ошибку "Поле обязательно для заполнения" у поля "Владелец"
          - Покупка в кредит 
             1. Открываем сервис
             2. Выбираем покупку в кредит
             3. Заполняем все поля валидными данными, кроме поля "Владелец"
             4. Поле "Владелец" оставляем пустым
             5. Нажимаем кнопку подтверждения
             6. Получаем ошибку "Поле обязательно для заполнения" у поля "Владелец"
        - Поле "Владелец" заполнено кириллицей
           - Обычная покупка
               1. Открываем сервис
               1. Выбираем покупку за наличные
               1. Заполняем все поля валидными данными, кроме поля "Владелец"
               2. В поле "Владелец" вписываем имя кириллицей
               3. Нажимаем кнопку подтверждения
               4. Получаем ошибку "Неверный формат" у поля "Владелец"
            - Покупка в кредит 
               1. Открываем сервис
               2. Выбираем покупку в кредит
               3. Заполняем все поля валидными данными, кроме поля "Владелец"
               4. В поле "Владелец" вписываем имя кириллицей
               5. Нажимаем кнопку подтверждения
               6. Получаем ошибку "Неверный формат" у поля "Владелец"
        - Поле "Владелец" заполнено цифрами
           - Обычная покупка
               1. Открываем сервис
               1. Выбираем покупку за наличные
               1. Заполняем все поля валидными данными, кроме поля "Владелец"
               2. В поле "Владелец" вписываем цифры
               3. Нажимаем кнопку подтверждения
               4. Получаем ошибку "Неверный формат" у поля "Владелец"
            - Покупка в кредит 
               1. Открываем сервис
               2. Выбираем покупку в кредит
               3. Заполняем все поля валидными данными, кроме поля "Владелец"
               4. В поле "Владелец" вписываем цифры
               5. Нажимаем кнопку подтверждения
               6. Получаем ошибку "Неверный формат" у поля "Владелец"         
      - **Поле "Cvc/Cvv"**
        - Пустое поле
          - Обычная покупка
             1. Открываем сервис
             1. Выбираем покупку за наличные
             1. Заполняем все поля валидными данными, кроме поля "Cvc/Cvv"
             2. Поле "Cvc/Cvv" оставляем пустым
             3. Нажимаем кнопку подтверждения
             4. Получаем ошибку "Неверный формат" у поля "Cvc/Cvv"
          - Покупка в кредит 
             1. Открываем сервис
             2. Выбираем покупку в кредит
             3. Заполняем все поля валидными данными, кроме поля "Cvc/Cvv"
             4. Поле "Cvc/Cvv" оставляем пустым
             5. Нажимаем кнопку подтверждения
             6. Получаем ошибку "Неверный формат" у поля "Cvc/Cvv"
        - Поле "Cvc/Cvv" заполнено буквами
           - Обычная покупка
               1. Открываем сервис
               1. Выбираем покупку за наличные
               1. Заполняем все поля валидными данными, кроме поля "Cvc/Cvv"
               2. В поле "Cvc/Cvv" вписываем буквы
               3. Нажимаем кнопку подтверждения
               4. Получаем ошибку "Неверный формат" у поля "Cvc/Cvv"
            - Покупка в кредит 
               1. Открываем сервис
               2. Выбираем покупку в кредит
               3. Заполняем все поля валидными данными, кроме поля "Cvc/Cvv"
               4. В поле "Cvc/Cvv" вписываем буквы
               5. Нажимаем кнопку подтверждения
               6. Получаем ошибку "Неверный формат" у поля "Cvc/Cvv"
    
    - Неизвестная карта 
      - Обычная покупка
        1. Открываем сервис
        2. Выбираем покупку за наличные
        3. Заполняем все поля валидными данными, кроме поля "Номер карты"
        4. Поле "номер карты" заполняем случайным набором цифр
        5. Нажимаем кнопку подтверждения
        6. Получаем всплывающее сообщение в правом верхнем углу текущей страницы, которое содержит текст "Ошибка"
      - Покупка в кредит 
        1. Открываем сервис
        2. Выбираем покупку в кредит
        3. Заполняем все поля валидными данными, кроме поля "Номер карты"
        4. Поле "номер карты" заполняем случайным набором цифр
        5. Нажимаем кнопку подтверждения
        6. Получаем всплывающее сообщение в правом верхнем углу текущей страницы, которое содержит текст "Ошибка"
  
  - **Проверка взаимодействия с БД**
    - Карта 1 (Активная)
      - Обычная покупка
        1. Открываем сервис
        1. Выбираем покупку за наличные
        1. Заполняем все поля валидными данными
        1. Нажимаем кнопку подтверждения
        1. Проверяем, что статус новой записи в БД, в таблице payment_entity соответствует статусу карты "APPROVED"
      - Покупка в кредит 
        1. Открываем сервис
        1. Выбираем покупку в кредит
        1. Заполняем все поля валидными данными
        1. Нажимаем кнопку подтверждения
        1. Проверяем, что статус новой записи в БД, в таблице credit_request_entity соответствует статусу карты "APPROVED"
    
    - Карта 2 (Не активная)
       - Обычная покупка
         1. Открываем сервис
         1. Выбираем покупку за наличные
         1. Заполняем все поля валидными данными
         1. Нажимаем кнопку подтверждения
         1. Проверяем, что статус новой записи в БД, в таблице payment_entity соответствует статусу карты "DECLINED"
       - Покупка в кредит 
         1. Открываем сервис
         1. Выбираем покупку в кредит
         1. Заполняем все поля валидными данными
         1. Нажимаем кнопку подтверждения
         1. Проверяем, что статус новой записи в БД, в таблице credit_request_entity соответствует статусу карты "DECLINED"
  
## Перечень используемых инструментов 
   
  Данные инструменты выбраны потому-что ими научили пользоваться на курсе.
     
  * **Java 8** — язык написания авто-тестов, используется в компании, чей продукт мы тестируем, будем придерживаться стандартов компании;
  * **JUnit** — платформа для написания авто-тестов и их запуска;
  * **Selenium Webdriver** - самый популярный фреймворк для автоматизации тестирования, с оберткой Selenide, 
  упрощает написание тестов и сокращает количество кода.
  * **Gradle** — система управления зависимостями, значительно облегчает подключение зависимостей и облегчает работу;
  * **Allure** — фреймворк, помогает создать визуально наглядную картину результатов выполнения тестов;
  * **Git и GitHub** — самый распространенный способ для хранения кода, в том числе авто-тестов, используется в компании, чей продукт мы тестируем. С ним будет удобнее взаимодействовать с командой;
  * **Docker** — платформа контейнерной виртуализации, использует процессы и утилиты, которые помогут запустить наше приложение для тестирования в связке с базами данных, без лишних установок и настроек дополнительного софта.
   
## Перечень и описание возможных рисков при автоматизации 

 * Проблемы с настройкой БД и сложные запросы к БД; 
 * Увеличение временных затрат на написание автотестов из-за особенностей строения исходного кода сервиса, не адаптированного под автотестирование;
 * Не исправность предоставленных для тестирования jar-ов;
 * Некорректное ТЗ, несовпадение фактического функционала с тем, что заявлен. 
  
## Интервальная оценка с учётом рисков 

 * Подготовка окружения, настройка подключения к БД, - 14 дней;
 * Написание автотестов, написание page objects, генерация/очистка данных - 14 дней;
 * Подготовка отчетных документов, написание отчета 3 дня.
 
## План сдачи работ 

  1. С 23-25 декабря - планирование автоматизации тестирования;
  1. С 26 декабря по 26 января - настройка окружения, написание и отладка автотестов, тестирование;
  1. C 27 по 30 января - подготовка отчетных документов.