# Card Transfer Project

## Описание

Card Transfer — учебный проект на Java, реализующий сервис для перевода денег между картами с веб-интерфейсом на React.\
Для запуска и тестирования используется Docker Compose.\
Интеграционные тесты написаны с помощью Testcontainers (JUnit 5).

---

## Архитектура проекта

Проект построен по принципам классического разделения на frontend и backend:

- **Backend** (Spring Boot):
  - REST API для перевода денег между картами.
  - Реализация бизнес-логики, валидации данных и обработки ошибок.
  - Поддержка интеграционных тестов с Testcontainers.
- **Frontend** (React):
  - Современный SPA-интерфейс для ввода данных карт и суммы перевода.
  - Взаимодействие с backend через REST API.
- **Docker Compose**:
  - Позволяет запускать backend и frontend как отдельные сервисы.
  - Гарантирует изолированную среду для разработки, тестирования и интеграционных тестов.
- **Testcontainers + JUnit**:
  - Интеграционные тесты запускаются с помощью Testcontainers, автоматически поднимают окружение через Docker Compose.
  - Проверяется корректная работа всей системы "end-to-end", включая фронт.

### Схема взаимодействия

```
[Пользователь] <---> [Frontend (React)] <--REST--> [Backend (Spring Boot)]
                                         (оба в Docker)
```

---

## Стек технологий

- **Backend:** Java 21, Spring Boot
- **Frontend:** React
- **Docker:** Backend и frontend в отдельных контейнерах
- **Testcontainers:** JUnit 5, Docker Compose integration
- **Прочее:** npm, node.js, curl, HttpURLConnection

---

## Как собрать и запустить

### 1. Сборка backend

```bash
./mvnw clean package
# или
mvn clean package
```

В результате появится jar-файл в папке `target/`.

---

### 2. Сборка и запуск через Docker Compose

```bash
docker-compose build --no-cache
docker-compose up -d
```

- Backend будет доступен на [http://localhost:5500](http://localhost:5500)
- Frontend — на [http://localhost:3000](http://localhost:3000)

---

### 3. Остановка и удаление контейнеров

```bash
docker-compose down -v
```

---

## Интеграционные тесты

- Для тестов используется Testcontainers с Docker Compose.
- Проверяется работоспособность backend и frontend в изолированных контейнерах.
- HTTP-запросы выполняются с помощью чистого Java HttpURLConnection для максимальной совместимости (без RestTemplate/Spring).
- Интеграционные тесты должны запускаться **после** сборки jar.

**Запуск тестов:**

```bash
./mvnw test
# или
mvn test
*Опционально*
./mvnw verify -Pintegration-tests
```


---

## Автор

- Дмитрий Рубцов, 2025

---


