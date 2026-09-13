# Web Smart Storage

**Web Smart Storage** is the web/API rebirth of [**Qt SmartStorage**](https://pergersoft.hu/) — a mature, battle-tested invoicing application, now reborn as a modern, self-hosted Spring Boot backend with a REST API and a NAV Online Invoice integration built in from day one.

If you're looking for a Hungarian **NAV Online Invoice compatible** invoicing system that's easy to run yourself — on a NAS, a small server, or in the cloud — without vendor lock-in or a monthly subscription, this is for you.

## Why Qt SmartStorage / Web Smart Storage?

- ✅ **NAV Online Invoice compatible** out of the box
- ✅ **Simple to deploy** — runs without a dedicated SQL server if you don't need one
- ✅ **Works offline** — you can still issue invoices without an internet connection
- 🧾 E-invoice (e-számla) issuing
- 🧾 Fast, straightforward invoice creation
- 📄 Save invoices as PDF
- 📧 Send invoices by e-mail
- 📧 Send payment reminders by e-mail (with the invoice PDF attached)
- 🌍 Multiple built-in invoice templates (multi-currency, multi-language)
- 🧾 Proforma invoices and payment request documents
- 🖼️ Your own company logo on every invoice
- 🔁 Recurring service invoicing, based on invoice templates

### One computer, or a whole network?

Qt SmartStorage works equally well for a single user on a single machine or a whole office on a shared network — the setup is essentially the same either way. Moving from standalone to a full SQL-backed, multi-user setup doesn't require reinstalling the application. Choosing a real SQL server (PostgreSQL, MySQL, or SQLite) buys you:

- Multiple people invoicing at the same time
- A shared database even on a single machine — your data isn't buried in one user's home folder
- The ability to let other systems connect to the same database

### Learn more & get started

- 🌐 Product site: **[pergersoft.hu](https://pergersoft.hu/)**
- 📖 [Qt SmartStorage setup guide](https://pergersoft.hu/readme/qt4smartstorage)
- 📖 [Installing MySQL / PostgreSQL](https://pergersoft.hu/readme/qt4smartstoragesql)
- 📖 [Installing MSSQL Server (Windows)](https://pergersoft.hu/readme/ss20-windows-install-mssql)

---

## About this repository

This repository contains the **backend API** for Web Smart Storage: a multi-module Maven project built with **Spring Boot** and **hexagonal architecture**, supporting **PostgreSQL**, **MySQL**, and **SQLite**. A separate Angular + Ionic PWA frontend will consume this API.

## Quick start

```bash
# Compile all modules
mvn -q -pl domain,data,infra,api-service -am compile

# Run the full test suite
mvn -q test

# Run the app (H2 in-memory database by default)
mvn -q -pl api-service spring-boot:run

# Run against a specific database profile
mvn -q -pl api-service spring-boot:run -Dspring-boot.run.profiles=dev-sqlite
mvn -q -pl api-service spring-boot:run -Dspring-boot.run.profiles=dev-pgsql
mvn -q -pl api-service spring-boot:run -Dspring-boot.run.profiles=dev-mysql
```

For architecture details, module layout, coding standards, and the AI-assisted development workflow used in this repo, see [`.github/copilot-instructions.md`](.github/copilot-instructions.md).

---

**Maintainer**: [Perger Attila](https://pergersoft.hu/) — aperger/web-smart-storage
