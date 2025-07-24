
# 💬 Spring Boot Messaging Application

![Java 17](https://img.shields.io/badge/Java-17-informational?style=flat&logo=java&logoColor=white&color=007396)
![Spring Boot 3.5.5](https://img.shields.io/badge/Spring%20Boot-3.5.5-informational?style=flat&logo=spring&logoColor=white&color=6DB33F)
![H2 Database](https://img.shields.io/badge/H2%20Database-In--Memory-blue?style=flat&logo=h2&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build%20Tool-C71A36?style=flat&logo=apache-maven&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-API%20Testing-orange?style=flat&logo=postman&logoColor=white)
![License MIT](https://img.shields.io/badge/License-MIT-green?style=flat&logo=github)

---

## ✨ Overview

This is a robust backend application built with Spring Boot that simulates a modern messaging platform. It goes beyond basic messaging, offering sophisticated friend management features like mutual friend calculations and analyzing the degree of separation between users using an efficient Breadth-First Search (BFS) algorithm.

Ready to connect the dots? 🤝

---

## 🚀 Features

- 👥 **User Management:** Create and manage user profiles with ease.
- 💌 **Friend Requests:** Send, accept, and reject friend requests.
- 👯 **Friendship Management:** Add and remove friends.
- 🤝 **Mutual Friends:** Discover common connections between users.
- 🌐 **Friendship Degree (BFS):** Calculate degrees of separation between users.
- 🔗 **Connection Tracking:** Manage friend requests and connections.

---

## 🛠️ Technologies Used

- **Java 17 ☕**
- **Spring Boot 3.5.5 🍃**
- **Spring Data JPA 💾**
- **H2 In-Memory Database 🗄️**
- **Maven 🏗️**
- **Postman 📬**

---

## 🏁 Getting Started

### 📋 Prerequisites

- Java 17 JDK
- Maven 3.8+
- Postman

### 🚀 Installation

Clone the repository:

```bash
git clone https://github.com/Rishabhjain1922/messaging-app.git
cd messaging-app
```

Build the project:

```bash
mvn clean install
```

Run the application:

```bash
mvn spring-boot:run
```

The application will start on [http://localhost:8080](http://localhost:8080) 🎉

---

## 🗄️ Database Schema

**Tables:**

- **Friends (Friendships):** Manages bidirectional friendships.
- **Connections (Friend Requests):** Tracks friend requests with status.

### H2 Console Access

- **URL:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **JDBC URL:** jdbc:h2:mem:testdb
- **User:** sa
- **Password:** password

---

## 🌐 API Documentation

Base URL: `http://localhost:8080/api`

### 👤 User Management

| Endpoint | Method | Description | Parameters |
|---|---|---|---|
| /users | POST | Create a new user | name (string) |

### 💌 Friend Requests

| Endpoint | Method | Description | Parameters |
|---|---|---|---|
| /connections/request | POST | Send friend request | senderId, receiverId (long) |
| /connections/{id}/accept | PUT | Accept friend request | id (long) |
| /connections/{id}/reject | PUT | Reject friend request | id (long) |

### 👯 Friendship Management

| Endpoint | Method | Description | Parameters |
|---|---|---|---|
| /friends/add | POST | Add friend | user1Id, user2Id (long) |
| /friends/remove | DELETE | Remove friend | user1Id, user2Id (long) |
| /friends/{userId} | GET | List user friends | userId (long) |
| /friends/mutual | GET | Mutual friends | user1Id, user2Id (long) |
| /friends/degree | GET | Degree of separation | user1Id, user2Id (long) |

---

## 🎯 Sample API Requests

### ➕ Create User

```http
POST /api/users?name=John
```

### 💌 Send Friend Request

```http
POST /api/connections/request?senderId=1&receiverId=2
```

### ✅ Accept Friend Request

```http
PUT /api/connections/1/accept
```

### 👥 Get Friends of User

```http
GET /api/friends/1
```

### 🤝 Calculate Mutual Friends

```http
GET /api/friends/mutual?user1Id=1&user2Id=3
```

### 🌐 Calculate Degree of Separation

```http
GET /api/friends/degree?user1Id=1&user2Id=5
```

---

## 🧪 Testing with Postman

Import the included Postman collection to test APIs in sequence.

---

## 🧠 Key Algorithms

### Degree of Separation (BFS)

```java
public int calculateDegreeOfSeparation(Long sourceId, Long targetId) {
    Queue<Long> queue = new LinkedList<>();
    Map<Long, Integer> distances = new HashMap<>();

    queue.add(sourceId);
    distances.put(sourceId, 1);

    while (!queue.isEmpty()) {
        Long current = queue.poll();
        if (current.equals(targetId)) return distances.get(current);

        for (User friend : getFriends(current)) {
            Long friendId = friend.getId();
            if (!distances.containsKey(friendId)) {
                distances.put(friendId, distances.get(current) + 1);
                queue.add(friendId);
            }
        }
    }
    throw new NoConnectionException("No connection between users");
}
```

---

## 🚫 Exception Handling

Handles:

- User not found
- Duplicate user/friendship
- Invalid requests
- Connection not found
- No path between users

Returns clear HTTP statuses and messages.

---

## 🤝 Contributing

1. Fork repo
2. Create branch `git checkout -b feature/your-feature`
3. Commit `git commit -am 'Add feature'`
4. Push `git push origin feature/your-feature`
5. Open Pull Request

---

