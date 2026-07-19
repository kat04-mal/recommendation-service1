#  Recommendation Service

## Описание проекта

**Recommendation Service** — backend-приложение для формирования персональных рекомендаций банковских продуктов пользователям.

Сервис анализирует данные пользователей, банковских продуктов и транзакций и формирует рекомендации на основе набора правил.

В приложении реализованы:

- получение рекомендаций через REST API;
- статические правила рекомендаций, реализованные в коде;
- динамические правила рекомендаций, хранящиеся в PostgreSQL;
- управление правилами через REST API;
- статистика срабатывания правил;
- кеширование результатов рекомендаций;
- Telegram-бот для получения рекомендаций пользователями;
- management API для управления приложением.


---

# Технологический стек

## Backend

- Java 17
- Spring Boot 3.5.3
- Spring Web
- Spring Data JPA
- Hibernate ORM
- Lombok
- Maven


## Базы данных

В проекте используются две базы данных.


### H2 Database

Используется для хранения пользовательских данных и транзакционной информации.


Основные данные:

- пользователи;
- банковские продукты;
- транзакции.


Конфигурация:

```
jdbc:h2:file:./transaction
```


---

### PostgreSQL

Используется для хранения динамических правил рекомендаций и статистики их выполнения.


Основные таблицы:

- `recommendation_rule`
- `rule_query`
- `rule_stats`


---

## Миграции базы данных

Для управления структурой PostgreSQL используется Liquibase.


Файлы миграций:

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

# Архитектура приложения

Приложение построено по многослойной архитектуре:


```
Controller Layer

        |

Service Layer

        |

Rules Layer

        |

Repository Layer

        |

Database Layer
```


Основные компоненты:


## Controllers

```
RecommendationController
RuleController
RuleStatsController
ManagementController
```


## Services

```
RecommendationService
RuleService
RuleStatsService
```


## Rules

### Статические правила

Правила реализованы непосредственно в коде:

```
TopSavingRule

SimpleCreditRule

Invest500Rule
```


### Динамические правила

Правила хранятся в PostgreSQL и управляются через REST API.


---

# Функциональные возможности


## Получение рекомендаций


Endpoint:


```
GET /recommendation/{userId}
```


Параметр:

```
userId — UUID пользователя
```


Пример ответа:


```json
{
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "recommendations": [
    {
      "name": "Топ накопление",
      "id": "1",
      "text": "Описание продукта"
    }
  ]
}
```


---

# Управление динамическими правилами


## Создание правила


```
POST /rule
```


Тело запроса содержит данные правила в формате JSON.


---

## Получение всех правил


```
GET /rule
```


---

## Удаление правила


```
DELETE /rule/{id}
```


Ответ:

```
204 No Content
```


---

# Статистика правил


Получение статистики срабатывания:


```
GET /rule/stats
```


Пример ответа:


```json
{
  "stats": [
    {
      "rule_id": 1,
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


После выполнения происходит очистка кеша рекомендаций.


---

## Информация о приложении


```
GET /management/info
```


Пример ответа:


```json
{
  "name": "recommendation-service1",
  "version": "0.0.1-SNAPSHOT"
}
```


---

# Telegram Bot


В проект интегрирован Telegram-бот для получения рекомендаций.


Поддерживаемая команда:


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


Если пользователь не найден:


```
Пользователь не найден
```


---

# Требования для запуска


Необходимо установить:


- Java 17+
- Maven
- PostgreSQL


Проверка Java:


```bash
java -version
```


Проверка Maven:


```bash
mvn -version
```


---

# Конфигурация приложения


Основной файл:


```
src/main/resources/application.properties
```


Основные параметры:


```properties
server.port=8080


spring.datasource.url=jdbc:h2:file:./transaction;ACCESS_MODE_DATA=r

spring.datasource.driver-class-name=org.h2.Driver


rule.datasource.url=jdbc:postgresql://localhost:5432/recommendation_rules

rule.datasource.username=postgres

rule.datasource.password=password

rule.datasource.driver-class-name=org.postgresql.Driver


telegram.bot.name=starbank_recommendation_bot

telegram.bot.token=<telegram_token>
```


---

# Сборка приложения


## Windows


```bash
.\mvnw.cmd clean package
```


## Linux / MacOS


```bash
./mvnw clean package
```


После успешной сборки создаётся:


```
target/recommendation-service1-0.0.1-SNAPSHOT.jar
```


---

# Запуск приложения


```bash
java -jar target/recommendation-service1-0.0.1-SNAPSHOT.jar
```


После запуска приложение доступно:


```
http://localhost:8080
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


```bash
.\mvnw.cmd test
```


Linux / MacOS:


```bash
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
- REST контроллеры;
- статистика правил;
- Telegram Bot.


---

# Swagger OpenAPI


Документация REST API доступна после запуска:


```
http://localhost:8080/swagger-ui/index.html
```


---

# Документация проекта


Подробная документация находится в Wiki проекта.


Доступные страницы:


- [Home](https://github.com/kat04-mal/recommendation-service1/wiki)

- [User Story и нефункциональные требования](https://github.com/kat04-mal/recommendation-service1/wiki/User-Story-и-НФ-требования)

- [Requirements Tracking](https://github.com/kat04-mal/recommendation-service1/wiki/Requirements-Tracking)

- [Architecture](https://github.com/kat04-mal/recommendation-service1/wiki/Architecture)

- [REST API](https://github.com/kat04-mal/recommendation-service1/wiki/REST-API)

- [Deployment](https://github.com/kat04-mal/recommendation-service1/wiki/Deployment)


---

# Добавление новых рекомендаций


## Статическое правило


Для добавления нового статического правила:


1. Создать новый класс правила.
2. Реализовать необходимый интерфейс.
3. Добавить тесты.


---

## Динамическое правило


Для добавления нового динамического правила:


1. Создать правило через REST API.
2. Правило сохранится в PostgreSQL.
3. Оно будет использоваться сервисом рекомендаций.


---

# Автор проекта


Малеева Екатерина Александровна

