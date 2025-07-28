# Astana IT Opportunity 🌐

Astana IT Opportunity - это дипломный проект, представляющий собой веб-платформу, где студенты могут искать IT-вакансии, стажировки и волонтёрские возможности в Казахстане.

##  О проекте
Цель проекта - помочь студентам и выпускникам Astana IT University находить релевантные возможности в одном месте. Платформа предоставляет:
- Поиск по ключевым словам и фильтрам (тип, город, компания)
- Авторизацию и роли (студент, админ)
- CRUD для возможностей
- Надёжное хранение данных в PostgreSQL

## Стек технологий
- Java
- Spring Boot
- PostgreSQL
- Docker
- Gradle

## Архитектура
- Многослойная структура (Controller – Service – Repository)
- DTO + маппинг
- Exception Handling
- JWT-аутентификация
- Ролевой доступ через Spring Security

##  Как запустить
```bash
git clone https://github.com/username/astana-it-opportunity.git
cd astana-it-opportunity
docker-compose up
./gradlew bootrun         
