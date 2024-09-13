# Angular-Spring Trello App

## Getting Started

### Prerequisites

- Java 21
- Maven
- Angular
- MySQL Server Database

**1. Clone the repository:**
```shell
git clone https://github.com/ssusnj/TrelloLikeApp
```

### Angular Frontend
**1. Navigate to the `frontend` directory:**
```shell
cd frontend
```

2. Install dependencies
```shell
npm install
```

### Spring Boot Backend

**1. Navigate to the `backend` directory:**
```shell
cd backend
```

**2. Build the app:**
```shell
mvn clean install
```

## MySQL Server Database
**1. Modify `application.yml`:**
```shell
spring:
  datasource:
    url: YOUR_DATABASE_CONNECTION_URL
    username: YOUR_DATABASE_USERNAME
    password: YOUR_DATABASE_PASSWORD

```
