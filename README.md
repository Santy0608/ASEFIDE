# Enterprise Management System — ASEFIDE

> **Academic Project** — Database Languages Course · Universidad Fidélitas
> **Client:** Asociación Solidarista ASEFIDE (200+ active members)

A complete enterprise management system built for a solidarity association, featuring full financial logic, AI-powered role-aware chatbot, advanced Oracle database architecture, and a dual-portal experience for administrators and associates.

---

## 🗂️ Repositories

| Component | Repository |
|---|---|
| 🖥️ Frontend | https://github.com/Santy0608/Frontend-App-ASEFIDE
| ⚙️ Backend | https://github.com/Santy0608/Backend-App-ASEFIDE-

---

## 🧩 System Overview

| Layer | Stack |
|---|---|
| Backend | Java · Spring Boot · Spring Security JWT · Spring Data JPA |
| Frontend | Angular · Chart.js · HTML · CSS |
| Database | Oracle — stored procedures, functions, views, materialized views, triggers, sequences, jobs |
| AI | External AI API · Prompt Engineering · Token Caching |
| PDF Generation | iTextPDF |
| Security | Spring Security · JWT · Role-based access control |

---

## 👥 Roles

The system supports two distinct roles with separate portals and permissions:

- **ADMIN** — Full access to all modules, reports, analytics, and system configuration
- **ASSOCIATE** — Personal portal with access to their own transactions, contributions, loans, savings, and benefits

---

## 🏗️ Modules

### 👤 Users
- Full user management with role assignment (ADMIN / ASSOCIATE).
- Logical deletion managed through the **States** module.

### 🔘 States
- Centralized state management for logical deletion across all system entities.

### 🎉 Activities
- Management of association activities with full CRUD.

### 🎁 Benefits
- Benefits catalog with assignment and visibility per associate role.

### 🛠️ Services
- Association services management with full CRUD.

### 💰 Loans
- Complete loan management with full financial logic.
- Loan request, approval, and tracking per associate.

### 🏦 Savings Accounts
- Savings account management per associate.
- Balance tracking and contribution history.

### 💳 Transactions
- Full transaction management covering contributions, loan payments, and savings movements.
- Complete financial traceability per associate.

### 📊 Reports
- PDF report generation with date range filtering using iTextPDF.
- Available for admins to export financial summaries by period.

---

## 🤖 AI Chatbot — FIDE
- Role-aware AI chatbot scoped to the ASEFIDE domain.
- **ADMIN mode:** answers questions about system management, members, and operations.
- **ASSOCIATE mode:** answers questions about personal loans, savings, and benefits.
- Built with prompt engineering, token caching for cost control, and external AI API integration.
- Endpoints protected with Spring Security JWT.

---

## 📈 Analytics Dashboard
- Dynamic dashboard with Chart.js visualizations.
- Fed by Oracle views and materialized views for optimized query performance.
- Trend analysis across transactions, loans, and savings for administrative insight.

---

## 👤 Associate Portal
- Personal portal for associates to visualize:
  - Their transactions and contributions
  - Active and historical loans
  - Savings account balance and movements
  - Available benefits and services

---

## 🗄️ Database Architecture

**Oracle Database** with 3NF normalization, featuring:

- Stored procedures and functions consumed from Spring Boot via Spring Data JPA
- Triggers for automated business logic
- Scheduled jobs for periodic operations
- Materialized views for analytics performance
- Sequences for primary key generation
- Views for data abstraction and role-based visibility
- Logical deletion architecture managed via States module

<img width="2050" height="2288" alt="Diagrama Proyecto Final drawio (1)" src="https://github.com/user-attachments/assets/c8107bdc-4df0-480e-96b2-e1eea1de0c94" />

---

## 🏛️ System Architecture

<img width="1283" height="1181" alt="Diagrama de Arquitectura ASEFIDE drawio" src="https://github.com/user-attachments/assets/c72a5ddb-bee2-46f0-bfdd-7fb95cd37e74" />

---

## 🔒 Security

- Spring Security with JWT authentication
- Role-based endpoint protection (ADMIN / ASSOCIATE)
- Logical deletion across all modules — no hard deletes
- AI API key protected server-side, never exposed to client

---

## 🚀 Running the Project

### Prerequisites
- Java 17+
- Node.js 18+
- Oracle Database (local or cloud instance)
- Angular CLI

### Backend
```bash
git clone (link al repo backend)
cd asefide-backend
# Configure Oracle connection in application.properties
./mvnw spring-boot:run
```

### Frontend
```bash
git clone (link al repo frontend)
cd asefide-frontend
npm install
ng serve
```

---

## 📹 Demo

📹 **Full System Demonstration** https://www.youtube.com/watch?v=-QvlpeK8QYg


## 📸 Screenshots

### Login
<img width="1897" height="913" alt="ASEFIDE Login" src="https://github.com/user-attachments/assets/a08a845a-33b9-4785-b92a-c88b798e5777" />

### Home
<img width="1871" height="912" alt="ASEFIDE Home" src="https://github.com/user-attachments/assets/6fd89e24-73da-40bb-bc55-8d8503d565a1" />

### Analytics Dashboard
<img width="1891" height="911" alt="ASEFIDE Dashboard" src="https://github.com/user-attachments/assets/9223277b-79a5-4d67-b32e-2f4801e8738c" />

### Loans Module
<img width="1878" height="917" alt="ASEFIDE Loan" src="https://github.com/user-attachments/assets/a273a4c0-233b-4d0f-b870-ca6a42c08ee4" />

### Associate Portal
<img width="1902" height="917" alt="ASEFIDE Associate Portal" src="https://github.com/user-attachments/assets/135580f1-e6ac-4fe8-b7ff-df2f5ea21a04" />

### Chatbot FIDE — ADMIN Mode
<img width="363" height="552" alt="ASEFIDE Chatbot" src="https://github.com/user-attachments/assets/9fe017c5-6277-454e-86f6-958570532d49" />

### Chatbot FIDE — ASSOCIATE Mode
<img width="363" height="550" alt="ASEFIDE Chatbot Associate" src="https://github.com/user-attachments/assets/9482499c-2a60-4744-a012-63856cc29766" />

### PDF Reports
<img width="1393" height="865" alt="ASEFIDE Report PDF" src="https://github.com/user-attachments/assets/cb2a673b-eb65-4801-981e-42616eba7213" />

### USsers Module
<img width="1882" height="908" alt="ASEFIDE Users Module" src="https://github.com/user-attachments/assets/f0a9f365-d917-4248-9973-e11bb6786388" />

---

## 🛠️ Full Tech Stack

- **Backend:** Java, Spring Boot, Spring Security (JWT), Spring Data JPA, Spring Web, RESTful APIs
- **Frontend:** Angular, Chart.js, HTML, CSS
- **Database:** Oracle — stored procedures, functions, triggers, jobs, materialized views, sequences, views — 3NF normalization
- **AI:** External AI API, Prompt Engineering, token caching, role-based chatbot
- **PDF Generation:** iTextPDF
- **Security:** Spring Security JWT, role-based access control, logical deletion
