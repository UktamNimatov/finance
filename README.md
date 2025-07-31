# Финансовый REST API

Приложение на Spring Boot с JWT-аутентификацией для финансового продукта.
---

## 🚀 Быстрый старт

### Вариант 1: Docker Compose (рекомендуется)

1. **Сборка и запуск стека:**
   ```bash
   docker compose up --build
   ```
   - Запустит PostgreSQL и Spring Boot приложение.
   - API будет доступно по адресу `http://localhost:8080`
   - База данных будет доступна на `localhost:5432` (пользователь: `postgres`, пароль: `postgres`, БД: `postgres`).
   - Схема базы и начальные данные подгружаются автоматически из `src/main/resources/sql/schema.sql` и `data.sql`.

2. **Остановка стека:**
   ```bash
   docker compose down
   ```
   - Остановит и удалит контейнеры. Данные сохранятся в Docker volume `db_data`, если не удалить его явно: `docker volume rm finance_db_data`.

### Вариант 2: Локальная разработка

1. **Запустите PostgreSQL** (убедитесь, что работает и доступен).
2. **Создайте схему базы и тестовые данные:**
   - См. раздел [Создание SQL-таблиц](#создание-sql-таблиц) ниже для команд.
   - Или выполните скрипты из `src/main/resources/sql/schema.sql` и `data.sql`.
3. **Запустите приложение:**
   ```bash
   ./mvnw spring-boot:run
   ```
4. **Доступ к API:**
   - `http://localhost:8080`

---

## 📝 Создание SQL-таблиц

Можно вручную создать необходимые таблицы в PostgreSQL следующими командами:

```sql
CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,
  roles VARCHAR(100) NOT NULL DEFAULT 'ROLE_USER',
  full_name VARCHAR(100) NOT NULL,
  balance NUMERIC(19,2) NOT NULL DEFAULT 0.00
);

CREATE TABLE transactions (
  id SERIAL PRIMARY KEY,
  user_id INT NOT NULL REFERENCES users(id),
  amount NUMERIC(19,2) NOT NULL,
  type VARCHAR(10) NOT NULL, -- CREDIT или DEBIT
  timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
  description VARCHAR(255)
);
```

Также можно использовать готовые файлы `src/main/resources/sql/schema.sql` и `data.sql` для создания схемы и заполнения начальными данными.

---

### Поток аутентификации

1. **Вход**: `POST /api/auth/login` с username и password
2. **Генерация токена**: создаётся и возвращается JWT
3. **Аутентификация запросов**: передаём токен в заголовке `Authorization: Bearer <token>`
4. **Валидация**: JWT-фильтр проверяет токен для каждого запроса

### Эндпоинты API

#### Аутентификация
- `POST /api/auth/login` — вход и получение JWT-токена

#### Пользователи (защищённые)
- `GET /api/users` — получить всех пользователей
- `GET /api/users/{id}` — получить пользователя по ID
- `POST /api/users` — создать нового пользователя
- `PUT /api/users/{id}` — обновить пользователя

#### Транзакции (защищённые)
- `GET /api/transactions` — получить все транзакции
- `GET /api/transactions/{id}` — получить транзакцию по ID
- `POST /api/transactions` — создать новую транзакцию

## Конфигурация

### JWT-конфигурация
```yaml
jwt:
  secret: y7K8s0Pq2X9Rv4HdU5Jm1Zt3Wq6Ne8B4
  expiration-ms: 3600000  # 1 час
```

### Конфигурация базы данных
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/postgres
    username: postgres
    password: postgres
```

### Переменные окружения для Docker
Следующие переменные можно переопределить в `docker-compose.yml`:

- `POSTGRES_DB`: имя базы данных (по умолчанию: finance)
- `POSTGRES_USER`: пользователь БД (по умолчанию: postgres)
- `POSTGRES_PASSWORD`: пароль БД (по умолчанию: postgres)
- `JWT_SECRET`: секрет для подписи JWT (по умолчанию: y7K8s0Pq2X9Rv4HdU5Jm1Zt3Wq6Ne8B4)
- `JWT_EXPIRATION_MS`: время жизни JWT в миллисекундах (по умолчанию: 3600000)

## Примеры использования

### Вход
```bash
curl -X POST http://localhost:8080/api/auth/login   -H "Content-Type: application/json"   -d '{"username":"boss","password":"testpass"}'
```

### Доступ к защищённому эндпоинту
```bash
curl -H "Authorization: Bearer <your-jwt-token>"   http://localhost:8080/api/users
```

## Тестирование

В базе присутствуют тестовые пользователи:
- Username: `testuser`, Password: `testpass`
- Username: `alice`, Password: (`password`)
- Username: `bob`, Password: (`password`)

## Зависимости

- Spring Boot 3.5.4
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT (jjwt)
- Lombok  
