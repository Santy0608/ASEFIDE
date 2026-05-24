# Enterprise Management System — ASEFIDE

> **Academic Project** — Database Languages Course · Universidad Fidélitas
> **Client:** Asociación Solidarista ASEFIDE (200+ active members)

A complete enterprise management system built for a solidarity association, featuring full financial logic, AI-powered role-aware chatbot, advanced Oracle database architecture, and a dual-portal experience for administrators and associates.

---

## 🗂️ Repositories

| Component | Repository |
|---|---|
| 🖥️ Frontend | *(link al repo frontend)* |
| ⚙️ Backend | *(link al repo backend)* |

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

📹 **Full System Demo** — *(coming soon)*

*(Attach screenshots here)*

---

## 📸 Screenshots

### Analytics Dashboard


### Loans Module


### Associate Portal


### Chatbot FIDE — ADMIN Mode


### Chatbot FIDE — ASSOCIATE Mode


### PDF Reports


### Database ERD

---

## 🛠️ Full Tech Stack

- **Backend:** Java, Spring Boot, Spring Security (JWT), Spring Data JPA, Spring Web, RESTful APIs
- **Frontend:** Angular, Chart.js, HTML, CSS
- **Database:** Oracle — stored procedures, functions, triggers, jobs, materialized views, sequences, views — 3NF normalization
- **AI:** External AI API, Prompt Engineering, token caching, role-based chatbot
- **PDF Generation:** iTextPDF
- **Security:** Spring Security JWT, role-based access control, logical deletion
