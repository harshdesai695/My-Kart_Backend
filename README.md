# MyKart - E-Commerce Microservices Platform

MyKart is a robust, scalable e-commerce platform built using a **Microservices Architecture** with **Java Spring Boot** for the backend and **React.js** for the frontend. It features centralized configuration, service discovery, API gateway routing, JWT-based security, and caching mechanisms.

## 🏗 System Architecture

The application is decomposed into autonomous services communicating via REST APIs, managed by a central Gateway and Service Registry.

### **Tech Stack**
* **Backend:** Java 17, Spring Boot 3.3.x, Spring Cloud
* **Frontend:** React 18, CSS3, Context API
* **Databases:** MongoDB (Primary), Redis (Caching)
* **Infrastructure:** Spring Cloud Gateway, Netflix Eureka, Spring Cloud Config

---

## 🚀 Microservices Breakdown

### 1. **Service Discovery (Eureka Server)**
* **Path:** `com.myKart.eureka`
* **Port:** `8761`
* **Description:** Acts as the service registry. All other microservices register themselves here upon startup, allowing the Gateway to discover them dynamically without hardcoded URLs.

### 2. **API Gateway & Security**
* **Path:** `com.myKart.security`
* **Port:** `8080`
* **Description:** The single entry point for all client requests.
    * **Routing:** Routes requests to specific services (User, Product, etc.) using load balancing (`lb://`).
    * **Security:** Implements a Global `AuthenticationFilter`. It validates **JWT Bearer Tokens** for protected endpoints and passes the `X-User-Id` header to downstream services.
    * **CORS:** Centralized CORS configuration allowing requests from the frontend.

### 3. **Config Service**
* **Path:** `com.myKart.config`
* **Port:** `8001`
* **Description:** Centralized configuration server backed by MongoDB. It serves externalized `application.properties` to all other services at startup (e.g., database URLs, JWT secrets).

### 4. **User Service**
* **Path:** `com.myKart.user`
* **Port:** `8081`
* **Description:** Manages user accounts and profiles.
    * **Features:** User Registration, Login (JWT generation), Address Management.
    * **Database:** MongoDB collection `Users`, `UserAddress`.

### 5. **Seller Service**
* **Path:** `com.mykart.seller`
* **Port:** `8082`
* **Description:** Manages seller accounts and inventory ownership.
    * **Features:** Seller Onboarding, Login, Profile Management.
    * **Database:** MongoDB collection `Seller`.

### 6. **Product Service**
* **Path:** `com.myKart.product`
* **Port:** `8083`
* **Description:** Handles the product catalog.
    * **Features:** CRUD operations for products, searching by brand/category.
    * **Caching:** Implements **Redis** caching to optimize `getProduct` and `getProductByBrand` queries, reducing database load.
    * **Database:** MongoDB collection `Product`.

### 7. **User Activity Service**
* **Path:** `com.myKart.userActivity`
* **Port:** `8085`
* **Description:** Tracks user interactions.
    * **Features:** Manages Shopping Cart, Wishlist, and Order History.
    * **Database:** MongoDB collection `UserActivity`.

---

## 💻 Frontend (Client)

* **Path:** `MyKart-Frontend`
* **Port:** `3000`
* **Link:** `https://github.com/harshdesai695/MyKart-Frontend`
* **Description:** A responsive Single Page Application (SPA) built with React.
    * **Features:**
        * Product browsing and searching.
        * User & Seller Authentication (Login/Signup).
        * Shopping Cart & Wishlist management.
        * Profile management.
    * **Architecture:** Uses a centralized `api.js` controller with Axios interceptors to automatically inject JWT tokens and handle 401 Unauthorized redirects.

---

## 🛠️ Setup & Installation

### Prerequisites
1.  **Java 17 JDK** installed.
2.  **Maven** installed.
3.  **Node.js** & **npm** installed.
4.  **MongoDB** running (Local or Atlas).
5.  **Redis** running (Local or Cloud).

### Step 1: Infrastructure Setup
1.  **Database:** Ensure MongoDB is running. Update connection strings in the **Config Service** database or local properties if bypassing config.
2.  **Config Service:** Start the `com.myKart.config` application first (Port 8001).
3.  **Eureka Server:** Start `com.myKart.eureka` (Port 8761).

### Step 2: Start Microservices
Start the remaining services in any order (they will fetch config from port 8001 and register with 8761):
* `com.myKart.user`
* `com.mykart.seller`
* `com.myKart.product`
* `com.myKart.userActivity`

### Step 3: Start API Gateway
Start `com.myKart.security`. This will effectively "open the gates" on **port 8080**.

### Step 4: Start Frontend
Navigate to the frontend directory:
```bash
cd MyKart-Frontend
npm install
npm start
