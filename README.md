# Курсовой проект по дисциплине "Программная инженерия" 
# Интернет-магазин (WebShop)

# Задание
Создайте сайт интернет магазина.  
На сайте должно быть 2 роли, покупатель и администратор. 
Покупатель может заходить в свой личный кабинет, корзин и страницу товаров. 
На странице товаров он может добавлять товары в корзину, производить фильтрацию товаров и открывать страницу товара, что бы посмотреть более подробную информацию о нем. 
Если пользователь покинул сайт при следующем заходе на него его корзина должна быть в том-же состоянии, что и в прошлой сессии 
Администратор может создавать новыые товары, редактировать и удалять их.
Реализуйте данный сайт используя java-spring,js, и любую удобную для вас сбд.

Данный проект представляет собой веб-приложение интернет-магазина с разграничением ролей покупателя и администратора.

# Функциональность включает:

- просмотр каталога товаров;
- страницу подробной информации о товаре;
- корзину покупателя с сохранением состояния;
- оформление и просмотр заказов;
- административную панель для управления товарами.

# Используемые технологии:
Backend

- Java 23
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- Hibernate
  
Frontend

- Thymeleaf
- HTML5 / CSS3
- Bootstrap 5
- JavaScript

База данных

- MySQL

# Структура проекта

CourseProjectV2.1 (webshop)
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.example.webshop
│   │   │       ├── config
│   │   │       ├── controller
│   │   │       ├── entity
│   │   │       ├── repository
│   │   │       ├── service
│   │   │       └── WebshopApplication.java
│   │   └── resources
│   │       ├── static
│   │       │   ├── css
│   │       │   ├── js
│   │       │   └── images
│   │       ├── templates
│   │       │   ├── auth
│   │       │   ├── products
│   │       │   ├── cart
│   │       │   ├── orders
│   │       │   ├── favorites
│   │       │   ├── profile
│   │       │   └── admin
│   │       │       └── products
│   │       └── application.properties
│   └── test
├── target
├── pom.xml
├── .gitignore
└── README.md

# Структура БД

webshop
│
├── users
│   ├── id            (BIGINT, PK)
│   ├── username      (VARCHAR)
│   ├── password      (VARCHAR)
│   └── role          (VARCHAR)   -- ROLE_USER / ROLE_ADMIN
│
├── product
│   ├── id            (BIGINT, PK)
│   ├── name          (VARCHAR)
│   ├── description   (TEXT)
│   ├── price         (DECIMAL)
│   ├── category      (VARCHAR)
│   └── image_url     (VARCHAR)
│
├── cart
│   ├── id            (BIGINT, PK)
│   └── user_id       (BIGINT, FK → users.id)
│
├── cart_item
│   ├── id            (BIGINT, PK)
│   ├── cart_id       (BIGINT, FK → cart.id)
│   ├── product_id    (BIGINT, FK → product.id)
│   └── quantity      (INT)
│
├── favorites
│   ├── id            (BIGINT, PK)
│   ├── user_id       (BIGINT, FK → users.id)
│   └── product_id    (BIGINT, FK → product.id)
│
├── orders
│   ├── id            (BIGINT, PK)
│   ├── user_id       (BIGINT, FK → users.id)
│   ├── created_at    (TIMESTAMP)
│   └── status        (VARCHAR)
│
└── order_item
    ├── id            (BIGINT, PK)
    ├── order_id      (BIGINT, FK → orders.id)
    ├── product_id    (BIGINT, FK → product.id)
    ├── quantity      (INT)
    └── price         (DECIMAL)
# Пояснение к таблицам

users — хранение данных пользователей и их ролей
product — каталог товаров интернет-магазина
cart — корзина пользователя (одна корзина на пользователя)
cart_item — товары и их количество в корзине
favorites — список избранных товаров пользователя
orders — оформленные заказы
order_item — состав заказа (товары, цена и количество на момент покупки)

# Связи сущностей в БД

users
 ├── 1 : 1 ── cart
 ├── 1 : N ── orders
 ├── 1 : N ── favorites

cart
 └── 1 : N ── cart_item

orders
 └── 1 : N ── order_item

product
 ├── 1 : N ── cart_item
 ├── 1 : N ── order_item
 └── 1 : N ── favorites

 
