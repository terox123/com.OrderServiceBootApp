В ближайшее время будет обновлен!
Описание:
Это Spring Boot приложение для управления заказами, пользователями и продуктами. Оно предоставляет как веб-интерфейс (Thymeleaf), так и REST API для CRUD-операций с пользователями (Customer), заказами (Order) и продуктами (Product). Приложение использует JWT для аутентификации, Redis для кэширования, Kafka для асинхронных сообщений и PostgreSQL для базы данных. Поддерживает пагинацию, валидацию и роли пользователей. Полностью контейнеризовано с использованием Docker и Docker Compose.

Функции:
Аутентификация и авторизация: Регистрация/логин пользователей с JWT-токенами и ролями (например, USER, ADMIN).
Управление пользователями: Создание, чтение, обновление, удаление пользователей с пагинацией и сортировкой по дате рождения.
Управление заказами: Создание заказов с привязкой к пользователям и продуктам, проверка наличия товаров.
Управление продуктами: CRUD для продуктов с контролем количества.
Кэширование: Redis для кэширования пользователей.
Асинхронные сообщения: Kafka для логирования операций (создание, обновление, удаление).
Веб-интерфейс: Страницы для просмотра и управления данными (Thymeleaf).
REST API: Эндпоинты для программного доступа.
Валидация: Проверка данных с использованием Bean Validation.
Требования
Java 17 или выше (для сборки)
Maven 3.6+
Docker и Docker Compose (для запуска всех сервисов)
Установка и запуск
Клонируйте репозиторий:


Copy code:
git clone https://github.com/terox123/com.OrderServiceBootApp.git
cd com.OrderServiceBootApp
Соберите JAR-файл:



./mvnw clean package -DskipTests
Запустите все сервисы с помощью Docker Compose (включая Kafka, Zookeeper, PostgreSQL, Redis и само приложение):



docker-compose up --build
Приложение будет доступно по адресу: http://localhost:8080
PostgreSQL: localhost:5432 (DB: postgres, User: postgres, Password: Bilut2006b12)
Redis: localhost:6379
Kafka: localhost:9092 (Zookeeper: localhost:2181)
Docker Compose автоматически настроит все зависимости. Если нужно запустить только сервисы без приложения, используйте docker-compose up zookeeper kafka postgres redis.

Ручной запуск (без Docker)
Установите и запустите PostgreSQL, Redis и Kafka локально.
Обновите src/main/resources/application.properties с вашими настройками (по умолчанию настроено на localhost).
Соберите и запустите:


./mvnw spring-boot:run
Конфигурация
Основные настройки в src/main/resources/application.properties:

База данных: PostgreSQL (драйвер: org.postgresql.Driver, URL: jdbc:postgresql://localhost:5432/postgres, пользователь: postgres, пароль: Bilut2006b12).
JPA/Hibernate: Диалект PostgreSQL, показ SQL-запросов.
Redis: Хост: localhost, порт: 6379, таймаут: 9000ms.
Kafka: Bootstrap-серверы: localhost:9092, группа потребителей: my-group, сериализаторы строк.
JWT: Секрет: secret-json (из переменной secret_value_jwt).
Для продакшена настройте переменные окружения или внешние конфигурационные файлы.

API Эндпоинты
Веб-интерфейс (Thymeleaf)
GET / - Главная страница.
GET /auth/login - Страница логина.
GET /auth/registration - Страница регистрации.
POST /auth/registration - Регистрация пользователя.
GET /customers - Список пользователей (с пагинацией и сортировкой: ?sort=old или ?sort=young).
GET /customers/{id} - Просмотр пользователя и его заказов.
GET /customers/new - Форма создания пользователя.
POST /customers - Создание пользователя.
GET /customers/{id}/edit - Форма редактирования пользователя.
POST /customers/{id} - Обновление пользователя.
GET /customers/{id}/delete - Подтверждение удаления пользователя.
POST /customers/{id}/delete - Удаление пользователя.
GET /orders - Список заказов.
GET /orders/{id} - Просмотр заказа.
GET /orders/new - Форма создания заказа (выбор пользователя и продуктов).
POST /orders - Создание заказа.
GET /orders/delete/{id} - Удаление заказа.
GET /products - Список продуктов.
GET /products/{id} - Просмотр продукта.
GET /products/new - Форма создания продукта.
POST /products - Создание продукта.
GET /products/edit/{id} - Форма редактирования продукта.
POST /products/update/{id} - Обновление продукта.
GET /products/delete/{id} - Удаление продукта.
REST API
GET /api/customers - Получить всех пользователей (с пагинацией).
GET /api/customers/{id} - Получить пользователя по ID.
POST /api/customers - Создать пользователя.
POST /api/customers/{id} - Обновить пользователя.
POST /api/customers/{id}/delete - Удалить пользователя.
GET /api/products - Получить все продукты.
GET /api/products/{id} - Получить продукт по ID.
POST /api/products - Создать продукт.
POST /api/products/{id}/delete - Удалить продукт.
Все REST-эндпоинты требуют JWT-аутентификации (заголовок Authorization: Bearer <token>). Kafka отправляет сообщения о каждой операции.
