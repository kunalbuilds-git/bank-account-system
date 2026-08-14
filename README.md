# Java Banking System

> A console-based banking application built in Java to simulate real-world banking operations while applying Object-Oriented Programming, clean architecture, and software engineering principles.

![Java](https://img.shields.io/badge/Java-100%25-orange)
![OOP](https://img.shields.io/badge/OOP-Inheritance%20%7C%20Polymorphism-blue)
![Collections](https://img.shields.io/badge/Collections-ArrayList-success)
![File%20Handling](https://img.shields.io/badge/File%20Handling-Java%20IO-yellow)
![Authentication](https://img.shields.io/badge/Authentication-PIN%20Login-blueviolet)
![Architecture](https://img.shields.io/badge/Architecture-Clean%20Code-green)
![Status](https://img.shields.io/badge/Status-Actively%20Maintained-brightgreen)

---

# About

This project started as a simple Java OOP exercise and has gradually evolved into a feature-rich console banking application with clean architecture.

Instead of creating many disconnected beginner projects, I chose to continuously improve one application by integrating every new Java concept I learn while maintaining professional code quality.

**Phase 1 (Foundation):** Core OOP concepts, account management, and file handling.

**Phase 2 (Refactoring):** Transformed monolithic code into clean, modular architecture with separation of concerns and dependency injection.

**Phase 3 (Enhancement):** Input validation, error handling, and feature improvements.

---

# Project Evolution

- ✅ Java Fundamentals
- ✅ Object-Oriented Programming (Inheritance, Polymorphism, Interfaces)
- ✅ Collections Framework (ArrayList)
- ✅ File Handling & Persistent Storage
- ✅ Authentication & Session Management
- ✅ Transaction History & Monthly Statements
- ✅ Clean Architecture & Refactoring
- ✅ Input Validation & Error Handling
- 🔄 Interest Calculation & Subclass Features
- 📋 Unit Testing (Upcoming)

---

# Features

## Account Management
- ✅ Create New Account
- ✅ Display All Accounts
- ✅ Delete Account
- ✅ Edit Account Details
- ✅ Search Account by Name
- ✅ Automatic Account Number Generation
- ✅ Input Validation (PIN, Name, Balance)

## Banking Operations
- ✅ Secure PIN-based Login
- ✅ Deposit Money
- ✅ Withdraw Money
- ✅ Transfer Money (between accounts)
- ✅ Transaction History
- ✅ Monthly Bank Statement

## Data Persistence
- ✅ Save Accounts to File
- ✅ Load Accounts from File
- ✅ Transaction Recording

## Authentication & Security
- ✅ PIN-based Authentication
- ✅ Session Validation
- ✅ Self-transfer Prevention
- ✅ Logged-in User Operations

---

# Object-Oriented Programming Concepts

- Classes & Objects
- Encapsulation (public/private/protected)
- Inheritance (CurrentAccount, SavingsAccount extend Account)
- Polymorphism (Method Overriding)
- Interfaces (Transaction interface)
- Constructor Chaining
- Abstraction
- Static Variables

---

# Software Engineering Practices

- **Modular Architecture** (Separation of concerns)
- **Dependency Injection** (Managers receive dependencies via constructor)
- **DRY Principle** (No code duplication)
- **Clean Code** (Meaningful names, single responsibility)
- **Input Validation** (PIN, name, balance verification)
- **Exception Handling** (Try-catch, custom error messages)
- **File Operations** (Try-with-resources for safe I/O)

---

# Project Structure

```
BANK-ACCOUNT-SYSTEM/
│
├── Bank/
│   ├── managers/                    # Business Logic Layer
│   │   ├── AccountManager.java      # CRUD operations
│   │   ├── AccountFileManager.java  # File I/O operations
│   │   ├── LoginManager.java        # Authentication
│   │   └── LoggedInUserMenu.java    # Logged-in user features
│   │
│   ├── model/                       # Domain Model Layer
│   │   ├── Account.java             # Base account class
│   │   ├── CurrentAccount.java      # Overdraft account type
│   │   ├── SavingsAccount.java      # Interest-bearing account type
│   │   └── Transaction.java         # Interface for deposit/withdraw
│   │
│   └── BankMenu.java                # Entry Point (Orchestration)
│
├── accounts.txt                      # Persistent account storage
├── README.md
└── .gitignore
```

---

# Architecture Overview

This project follows **Clean Architecture** principles:

**Layer 1: Domain Model (Bank/model/)**
- Pure business logic
- No dependencies on other layers
- Contains: Account, CurrentAccount, SavingsAccount, Transaction interface

**Layer 2: Managers (Bank/managers/)**
- Service layer implementing use cases
- Depends on domain model
- Contains: AccountManager, AccountFileManager, LoginManager, LoggedInUserMenu
- Uses dependency injection for loose coupling

**Layer 3: Entry Point (Bank/BankMenu.java)**
- Orchestrates all managers
- Handles user interaction and control flow
- No business logic

**Benefits:**
- ✅ Easy to test (no hard dependencies)
- ✅ Easy to extend (add new features without breaking existing code)
- ✅ Easy to maintain (clear responsibility for each class)
- ✅ Easy to refactor (low coupling)

---

# Current Status

## Implemented ✅
- [x] Complete account management (CRUD)
- [x] Secure authentication system
- [x] File persistence
- [x] Transaction tracking
- [x] Input validation
- [x] Clean modular architecture
- [x] Error handling
- [x] Logged-in user menu
- [x] Money transfers

## In Progress 🔄
- [ ] Interest calculation for Savings Accounts
- [ ] Overdraft features for Current Accounts

## Upcoming 📋
- [ ] PIN re-authentication for sensitive operations
- [ ] Banking dashboard with statistics
- [ ] Unit testing (JUnit)
- [ ] Database integration (JDBC)
- [ ] Spring Boot REST API
- [ ] GUI version (JavaFX)

---

# Technologies & Concepts

| Category | Technologies |
|----------|--------------|
| **Language** | Java 8+ |
| **Collections** | ArrayList, String manipulation |
| **File I/O** | BufferedReader, BufferedWriter, FileReader, FileWriter |
| **Concepts** | OOP, Inheritance, Polymorphism, Interfaces, Static Variables |
| **Patterns** | Dependency Injection, MVC-inspired Architecture |
| **Tools** | Git, GitHub, VS Code |

---

# Key Learning Outcomes

Through building and continuously refactoring this project, I learned:

1. **Design Patterns** — How to structure large applications
2. **Clean Code** — Writing maintainable and readable code
3. **OOP Mastery** — Real-world application of OOP principles
4. **Refactoring** — Improving existing code without breaking functionality
5. **Architecture** — Building scalable systems with clear separation of concerns
6. **Collections Framework** — Effective use of Java collections
7. **File I/O** — Persistent storage and data management
8. **Git Workflow** — Incremental commits and version control

---

# What Makes This Project Special

- ⭐ **Continuous Improvement** — Not a one-time project, but an evolving codebase
- ⭐ **Clean Architecture** — Refactored to follow professional standards
- ⭐ **Real-world Features** — Authentication, persistence, transactions
- ⭐ **Learning Journey** — Shows progression from basic to advanced concepts
- ⭐ **Production-ready Code** — No compilation errors, professional structure

---

# Highlights

✨ Modular, maintainable codebase  
✨ Clean architecture with dependency injection  
✨ Secure PIN-based authentication  
✨ Persistent file storage  
✨ Automatic account number generation  
✨ Transaction tracking & monthly statements  
✨ Comprehensive input validation  
✨ Object-oriented design throughout  

---

# How to Run

```bash
# Clone the repository
git clone https://github.com/yourusername/bank-account-system.git
cd bank-account-system

# Compile
javac Bank/model/*.java Bank/managers/*.java Bank/BankMenu.java

# Run
java Bank.BankMenu
```

---

# Development Journey

This repository is actively maintained as part of my **#100DaysOfCode** challenge and ongoing Java learning journey.

Each commit represents:
- A new feature implemented
- A refactoring improvement
- A concept mastered
- A step forward in software engineering understanding

---

# Future Roadmap

**Q1 2025:**
- Interest calculation for savings accounts
- Overdraft limits for current accounts
- Enhanced transaction filtering

**Q2 2025:**
- Unit testing with JUnit
- JDBC database integration
- Admin dashboard features

**Q3 2025:**
- Spring Boot REST API
- JavaFX GUI version
- Mobile app consideration

---

# Code Quality Standards

This project maintains:
- ✅ Zero compilation errors
- ✅ Consistent naming conventions
- ✅ Comprehensive error handling
- ✅ Professional JavaDoc comments (in progress)
- ✅ DRY principle (no code duplication)
- ✅ Single Responsibility Principle
- ✅ Dependency injection pattern

---

# Feedback & Contributions

Suggestions, ideas, and code reviews are always welcome!

If you find areas to improve or have ideas for features, please:
- Open an Issue
- Start a Discussion
- Submit a Pull Request

Every piece of feedback helps me grow as a developer.

---

# Support

If you found this project interesting or educational, consider giving it a ⭐!

It motivates me to continue improving the codebase and sharing my learning journey.

---

# License

This project is open source and available for learning purposes.

---

**Happy Coding! 🚀**

*Last Updated: August 14, 2026*  
*Actively Maintained as part of #100DaysOfCode Challenge*