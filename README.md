<!--
SPDX-FileCopyrightText: 2026 Antonio Seoane De Ois
SPDX-License-Identifier: EPL-2.0
-->


# Cortex – Backend (Java Spring Boot)

REST API built with **Spring Boot 3 + SQLite + Ollama (llama3.2)**.

## Tech Stack

| Layer | Technology |
|---|---|
| Framework | Spring Boot 3.2 |
| Language | Java 21 |
| Database | SQLite (via spring-boot-starter-jdbc) |
| AI | Ollama llama3.2 (local HTTP calls) |
| Build | Maven |

## Prerequisites

- Java 21+
- Maven 3.8+
- [Ollama](https://ollama.com) running locally with `llama3.2` pulled

```bash
# Install Ollama (Linux)
curl -fsSL https://ollama.com/install.sh | sh
ollama pull llama3.2
```

## Setup & Run

```bash
cd backend

# Build
mvn clean package -DskipTests

# Run
mvn spring-boot:run
```

The API will start on **http://localhost:8000**

## API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| `POST` | `/inbox` | Create inbox item `{type, rawContent, source?}` |
| `GET` | `/inbox/pending` | List all pending items |
| `DELETE` | `/inbox/{id}` | Discard an item |
| `POST` | `/process/{id}` | Process single item with AI |
| `POST` | `/process/batch/all` | Process all pending items |
| `GET` | `/brain` | All notes |
| `GET` | `/brain/search?q=` | Search notes (title, tags, content, summary) |

## Project Structure

```
src/main/java/com/cortex/
├── CortexApplication.java   ← Entry point
├── config/
│   └── CorsConfig.java            ← CORS (allow all origins)
├── model/
│   ├── InboxItem.java
│   └── Note.java
├── dto/
│   ├── InboxItemRequest.java
│   └── BatchProcessResponse.java
├── repository/
│   ├── InboxRepository.java       ← JdbcTemplate CRUD
│   └── NoteRepository.java        ← JdbcTemplate + LIKE search
├── service/
│   ├── AiService.java             ← Ollama HTTP client
│   ├── InboxService.java
│   └── ProcessService.java
└── controller/
    ├── InboxController.java
    ├── ProcessController.java
    └── BrainController.java
```

## Database

SQLite file: `cortex.db` (auto-created next to the jar on startup)

Tables:
- `inbox_items` – captured content with status lifecycle
- `notes` – AI-processed knowledge cards
