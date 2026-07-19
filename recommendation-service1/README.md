# StarBank Recommendation Service

## Описание проекта

**StarBank Recommendation Service** — это backend-приложение для формирования персональных рекомендаций банковских продуктов пользователям.

Сервис анализирует данные пользователей и на основании набора правил формирует список подходящих продуктов.

В системе реализованы:

- статические правила рекомендаций, встроенные в код приложения;
- динамические правила рекомендаций, хранящиеся в PostgreSQL;
- REST API для получения рекомендаций и управления правилами;
- Telegram-бот для получения персональных рекомендаций;
- статистика срабатывания правил;
- кеширование результатов рекомендаций.


---

# Технологический стек

## Backend

- Java 17
- Spring Boot 3.5.3
- Spring Web
- Spring Data JPA
- Hibernate ORM
- Lombok


## Базы данных

В проекте используются две базы данных.


### H2 Database

H2 используется для хранения пользовательских данных и транзакционной информации.

Основные таблицы:

- users
- products
- transactions
- spatial_ref_sys


### PostgreSQL

PostgreSQL используется для хранения динамических правил рекомендаций.

Основные таблицы:

- recommendation_rule
- rule_query
- rule_stats


## Миграции базы данных

Для управления структурой базы данных используется:

- Liquibase


Файлы миграций находятся:

```
src/main/resources/db/changelog
```


Используемые миграции:

```
001-create-rule-tables.yaml

002-change-arguments-to-jsonb.yaml

003-create-rule-stats.yaml

004-create-user-table.yaml
```


---

# Функциональные возможности


## Получение рекомендаций

Пользователь может получить персональные рекомендации банковских продуктов.


При формировании рекомендаций используются:


### Статические правила

Правила реализованы непосредственно в коде приложения.

Примеры:

- TopSavingRule
- SimpleCreditRule
- Invest500Rule


### Динамические правила

Правила хранятся в PostgreSQL и могут изменяться менеджером без изменения кода.


---

# Telegram Bot


В проект интегрирован Telegram-бот.


Пользователь может получить рекомендации командой:


```
/recommend username
```


Пример ответа:

```
Здравствуйте Иван Иванов

Новые продукты для вас:

- Топ накопление
- Простой кредит
```


Если пользователь отправляет неизвестную команду, бот показывает справку.


---

# Управление системой


Для внешних систем реализованы management endpoints.


Поддерживается:


## Очистка кеша


```
POST /management/clear-caches
```


После выполнения происходит очистка кеша рекомендаций.


## Информация о приложении


```
GET /management/info
```


Возвращает:

- название приложения;
- версию.


---

# Архитектура приложения


Основные компоненты:


```
                         Пользователь
                              |
                              |
                 REST API / Telegram Bot
                              |
                              v


+------------------------------------------------+

              Recommendation Service


+------------------------------------------------+

 Controller Layer

 - RecommendationController
 - ManagementController
 - RuleController


 Service Layer

 - RecommendationService
 - RuleService
 - RuleStatsService
 - UserService


 Rules

 - Static Rules
     - TopSavingRule
     - SimpleCreditRule
     - Invest500Rule


 - Dynamic Rules
     - DynamicRuleService


 Repository Layer


 Entity Layer


+------------------------------------------------+

              |                       |

              v                       v


          H2 Database            PostgreSQL


       users                    recommendation_rule

       products                 rule_query

       transactions             rule_stats


```


---

# Алгоритм формирования рекомендаций


1. Пользователь отправляет запрос.

2. RecommendationService получает идентификатор пользователя.

3. Проверяется наличие результата в кеше.

4. Если результат найден:

```
возвращается кешированный список рекомендаций
```


5. Если результата нет:

Выполняются:


- статические правила;
- динамические правила.


6. Подходящие рекомендации объединяются.

7. Результат сохраняется в кеш.

8. Пользователь получает ответ.


---

# Структура проекта


```
src/main/java/ru/starbank/recommendation_service1


├── controller

│
├── service

│
├── entity

│
├── repository

│
├── dto

│
├── mapper

│
├── rules

│   ├── static rules
│   └── dynamic rules

│
├── telegram

│
└── config

```


---

# REST API


## Recommendations


Получение рекомендаций пользователя:


```
GET /recommendations/{userId}
```


Ответ:

```json
[
  {
    "id": 1,
    "productId": "uuid",
    "productName": "Топ накопление",
    "productText": "Описание продукта"
  }
]
```


---

# Rule API


## Создание правила


```
POST /rule
```


## Получение списка правил


```
GET /rule
```


## Удаление правила


```
DELETE /rule/{id}
```


---

# Rule Statistics API


Получение статистики срабатывания правил:


```
GET /rule/stats
```


Пример ответа:


```json
{
  "stats": [
    {
      "ruleId": 1,
      "count": 10
    }
  ]
}
```


---

# Management API


## Очистка кеша


```
POST /management/clear-caches
```


## Информация о приложении


```
GET /management/info
```


---

# Конфигурация приложения


Основные параметры находятся в:


```
src/main/resources/application.properties
```


Пример:


```properties
server.port=8080


spring.datasource.url=jdbc:h2:file:./transaction

spring.datasource.driver-class-name=org.h2.Driver


rule.datasource.url=jdbc:postgresql://localhost:5432/recommendation_rules

rule.datasource.username=postgres

rule.datasource.password=password


telegram.bot.name=starbank_recommendation_bot

telegram.bot.token=token
```


---

# Переменные окружения


Для запуска в production рекомендуется использовать:


```
SERVER_PORT


SPRING_DATASOURCE_URL

SPRING_DATASOURCE_USERNAME

SPRING_DATASOURCE_PASSWORD


RULE_DATASOURCE_URL

RULE_DATASOURCE_USERNAME

RULE_DATASOURCE_PASSWORD


TELEGRAM_BOT_NAME

TELEGRAM_BOT_TOKEN
```


---

# Требования для запуска


Необходимо установить:


- Java 17+
- Maven
- PostgreSQL


Проверка Java:


```
java -version
```


---

# Сборка проекта


Windows:


```
.\mvnw.cmd clean package
```


Linux / Mac:


```
./mvnw clean package
```


После успешной сборки создаётся файл:


```
target/recommendation-service1-0.0.1-SNAPSHOT.jar
```


---

# Запуск приложения


Команда:


```
java -jar target/recommendation-service1-0.0.1-SNAPSHOT.jar
```


---

# Запуск через IntelliJ IDEA


1. Открыть проект.

2. Настроить JDK 17.

3. Запустить класс:


```
RecommendationService1Application
```


---

# Тестирование


Запуск всех тестов:


Windows:


```
.\mvnw.cmd test
```


Linux:


```
./mvnw test
```


В проекте используются:


- JUnit 5
- Mockito
- Spring Boot Test


Проверяются:


- статические правила;
- динамические правила;
- сервис рекомендаций;
- Telegram сервис;
- статистика правил;
- REST контроллеры.


---

# Swagger API Documentation


После запуска приложения документация доступна:


```
http://localhost:8080/swagger-ui/index.html
```


---

# Разработка новых рекомендаций


Для добавления новой рекомендации:


## Статическое правило


Создать новый класс в:


```
rules
```


Реализовать интерфейс правила.


Добавить тест.


---

## Динамическое правило


Создать правило через REST API.


Правило будет сохранено в PostgreSQL.


---

# Документация проекта


Дополнительная документация находится в Wiki:


- описание требований;
- User Stories;
- диаграмма вариантов использования;
- архитектурная диаграмма;
- Activity Diagram;
- инструкция развёртывания.


---

# Автор проекта
Малеева Екатерина Александровна


