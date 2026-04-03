# 🔐 VPN Backend (Spring Boot)

A **production-ready VPN backend service** built using **Spring Boot**, designed to manage WireGuard VPN sessions, server allocation, and secure client provisioning.

This backend acts as the core control layer between the Android VPN app and WireGuard infrastructure.

---

## 🚀 Features

* 🌐 VPN server management (multi-location support)
* 🔐 Secure session lifecycle handling
* ⚡ Fast connect / disconnect APIs
* 📡 Dynamic WireGuard configuration generation
* 🧠 Smart server validation (online/offline status)
* 🔄 Automatic session cleanup
* 🧾 Stateless API design
* 🛡️ Privacy-first architecture (no traffic logging)
* 🔌 Ready for real WireGuard provisioning integration

---

## 🧱 Tech Stack

**Language**

* Java (JDK 17)

**Framework**

* Spring Boot 3

**Architecture**

* Layered Architecture (Controller → Service → Repository)

**Database**

* MySQL 
* JPA (Hibernate)

**Other Tools**

* Lombok
* Spring Validation
* Spring Data JPA

---

## 📂 Project Structure

```bash
com.securevpn.backend
│
├── config          # App configuration
├── controller      # REST APIs (VpnController)
├── dto             # Request/Response models
├── entity          # Database entities
├── enums           # Enum definitions
├── exception       # Custom exceptions
├── loader          # Initial data loaders (optional)
├── repository      # JPA repositories
├── service         # Business logic
├── util            # Utility classes
│
└── VpnBackendApplication.java
```

---

## 🔌 API Flow

### 🔹 Connect API

**Flow:**

1. Validate selected VPN server
2. Check server status (ONLINE / OFFLINE)
3. Close existing active session (if any)
4. Create new session (GuestSession)
5. Generate session token
6. Allocate tunnel IP (10.8.0.x/32)
7. Provision WireGuard peer (via service)
8. Return configuration to Android app

---

### 🔹 Disconnect API

**Flow:**

1. Identify session via:
   * sessionToken OR
   * sessionId OR
   * deviceId
2. Validate ownership
3. Mark session as DISCONNECTED
4. Record end time
5. Revoke WireGuard peer (future implementation)

---

## 📡 WireGuard Config Response

Backend returns:

```json
{
  "sessionToken": "...",
  "assignedIp": "10.8.0.2/32",
  "server": { ... },
  "wireGuard": {
    "serverPublicKey": "...",
    "clientPrivateKey": "...",
    "endpointHost": "...",
    "endpointPort": 51820,
    "clientAddress": "10.8.0.2/32",
    "dnsServers": ["1.1.1.1", "8.8.8.8"],
    "allowedIps": ["0.0.0.0/0", "::/0"],
    "persistentKeepalive": 25
  }
}
```

---

## 🧩 Key Components

### 🔹 VpnController

* Handles REST API requests
* Connect / Disconnect endpoints

### 🔹 VpnServiceImpl

* Core business logic
* Session lifecycle management
* Server validation
* Delegates provisioning

### 🔹 WireGuardProvisioningService

* Abstraction layer for VPN node interaction
* Responsible for:

  * Peer creation
  * Key management
  * IP allocation

### 🔹 GuestSession

* Stores minimal session metadata
* No user traffic or logs stored

### 🔹 VpnServer

* Represents VPN server nodes
* Includes:

  * host
  * port
  * status
  * location info

---

## 🗄️ Database Entities

### GuestSession

* id
* deviceId
* sessionToken
* serverId
* status (CONNECTED / DISCONNECTED)
* startedAt
* endedAt

---

### VpnServer

* id
* country
* city
* host
* port
* status
* ping
* isFree / isRecommended

---

## 🔐 Security & Privacy

* ❌ No browsing history stored
* ❌ No DNS logs stored
* ❌ No traffic inspection
* ✅ Minimal session metadata only
* ✅ Stateless token-based design

---

## ⚙️ Configuration

### application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/vpn_db
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 🧪 Current Status

✔ Backend API fully functional
✔ Connect / Disconnect flow implemented
✔ Session management working
✔ Server management implemented
✔ Placeholder provisioning layer added

🚧 Real WireGuard provisioning (peer creation) in progress

---

## 📌 Future Improvements

* 🔐 Real WireGuard peer provisioning (SSH / Node API)
* 🌍 Multi-server load balancing
* 📊 Server health monitoring
* 🔁 Auto failover system
* 🔐 Authentication (JWT / OAuth)
* 💳 Subscription system
* 📈 Analytics & monitoring dashboard

---

## 🏗️ System Architecture

```text
Android App
      ↓
Spring Boot Backend
      ↓
WireGuard VPN Server (Linux VPS)
      ↓
Internet
```

---

## 👨‍💻 Developer

**Deepak Kumar**

Android & Backend Developer

---

## 📄 License

This project is for educational and development purposes.
Production deployment requires secure infrastructure and compliance practices.

---

## ⭐ Support

If you find this project useful, give it a ⭐ on GitHub!
