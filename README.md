<div align="center">

```
████████╗ █████╗ ███████╗██╗  ██╗
╚══██╔══╝██╔══██╗██╔════╝██║ ██╔╝
   ██║   ███████║███████╗█████╔╝ 
   ██║   ██╔══██║╚════██║██╔═██╗ 
   ██║   ██║  ██║███████║██║  ██╗
   ╚═╝   ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝

██╗     ██╗███████╗████████╗
██║     ██║██╔════╝╚══██╔══╝
██║     ██║███████╗   ██║   
██║     ██║╚════██║   ██║   
███████╗██║███████║   ██║   
╚══════╝╚═╝╚══════╝   ╚═╝   

 ██████╗ ██████╗  ██████╗  █████╗ ███╗   ██╗██╗███████╗███████╗██████╗ 
██╔═══██╗██╔══██╗██╔════╝ ██╔══██╗████╗  ██║██║╚══███╔╝██╔════╝██╔══██╗
██║   ██║██████╔╝██║  ███╗███████║██╔██╗ ██║██║  ███╔╝ █████╗  ██████╔╝
██║   ██║██╔══██╗██║   ██║██╔══██║██║╚██╗██║██║ ███╔╝  ██╔══╝  ██╔══██╗
╚██████╔╝██║  ██║╚██████╔╝██║  ██║██║ ╚████║██║███████╗███████╗██║  ██║
 ╚═════╝ ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝╚══════╝╚══════╝╚═╝  ╚═╝
```

**Organizes tasks and to-do lists — add, update, and remove with ease.**

</div>

---

<div align="center">

```
╔══════════════════════════════════════════════════════════╗
║   add tasks  ·  set priorities  ·  track progress  ·     ║
║   update descriptions  ·  sort  ·  delete  ·  persist    ║
╚══════════════════════════════════════════════════════════╝
```

</div>

---

## 👤 Author

<table>
  <tr>
    <td><b>Name</b></td>
    <td>Abdurauf Abdulla</td>
  </tr>
  <tr>
    <td><b>Role</b></td>
    <td> COMSE25 Student</td>
  </tr>
</table>

---

## 📌 Description

**TaskListOrganizer** is a console-based task management application written in **Java**.
It helps users stay on top of their work by organizing tasks into a structured list — each with a title, description, priority level, and completion deadline.

Users can **add** new tasks, **update** descriptions and statuses, and **remove** tasks — all from a clean interactive terminal menu. Every task flows through a defined lifecycle (`NEW → IN_PROGRESS → DONE`), enforced by the **State design pattern**, which prevents invalid operations like editing a completed task.

All data is persisted to a **PostgreSQL** database via **JDBC**, with a switchable **JSON file** backend available as an alternative.

---

## 🎯 Objectives

```
┌─────────────────────────────────────────────────────────────────────┐
│  01 · MANAGE TASKS                                                  │
│       Provide a full CRUD experience — create tasks with rich       │
│       metadata, update their status and description, and delete     │
│       them — all through an intuitive console interface.            │
├─────────────────────────────────────────────────────────────────────┤
│  02 · ENFORCE TASK LIFECYCLE                                        │
│       Implement the State design pattern so that each task          │
│       transition (NEW → IN_PROGRESS → DONE) is validated at the     │
│       object level, making illegal operations impossible.           │
├─────────────────────────────────────────────────────────────────────┤
│  03 · PERSIST DATA RELIABLY                                         │
│       Integrate PostgreSQL via raw JDBC to store and retrieve       │
│       tasks across sessions, with a JSON fallback for portability.  │
├─────────────────────────────────────────────────────────────────────┤
│  04 · APPLY LAYERED ARCHITECTURE                                    │
│       Separate the application into model, service, storage, and    │
│       ui layers — each with a single, clear responsibility.         │
├─────────────────────────────────────────────────────────────────────┤
│  05 · VALIDATE ALL INPUT                                            │
│       Handle every edge case in user input — invalid dates, empty   │
│       fields, out-of-range menu choices — with clear error prompts. │
└─────────────────────────────────────────────────────────────────────┘
```

---

## ✨ Features

| # | Feature | Details |
|---|---|---|
| 01 | ➕ **Add Task** | Title, description, priority (`LOW` / `MEDIUM` / `HIGH`), due date |
| 02 | 📋 **View All Tasks** | Formatted table with overdue `*` markers |
| 03 | 🔄 **Change Status** | `NEW → IN_PROGRESS → DONE` with state-enforced rules |
| 04 | ✏️ **Edit Description** | Allowed only in `NEW` state |
| 05 | 🗑️ **Delete Task** | Allowed only in `NEW` state |
| 06 | 📊 **Sort Tasks** | By priority, creation date, or description |
| 07 | 🗄️ **PostgreSQL Backend** | Full JDBC persistence with ENUM type support |
| 08 | 📁 **JSON Backend** | File-based alternative, switchable in one line |

---

## 🔄 Task Lifecycle

```
                    ┌──────────────────────────────────────┐
                    │                                      │
          ┌─────────▼──────┐              ┌───────────────▼────┐
          │      NEW       │ ──────────►  │    IN_PROGRESS     │
          └────────────────┘              └────────────────────┘
               │                                    │
               │  ✅ edit description               │  ✅ move to DONE
               │  ✅ delete                         │  ❌ edit description
               │  ✅ move to IN_PROGRESS            │  ❌ delete
               │  ❌ move directly to DONE          │  ❌ revert to NEW
               │                                    │
               │                         ┌──────────▼──────────┐
               │                         │        DONE         │
               │                         └─────────────────────┘
               │                                    │
               │                                    │  ❌ any modification
               └────────────────────────────────────┘  ❌ deletion
```

---

## 🏗️ Project Structure

```
TaskListOrganizer/
│
├── src/
│   ├── Main.java                     ← Entry point & wiring
│   ├── App.java                      ← Menu loop & flow control
│   │
│   ├── model/
│   │   ├── Task.java                 ← Core domain entity
│   │   ├── Priority.java             ← Enum: LOW | MEDIUM | HIGH
│   │   ├── Status.java               ← Enum: NEW | IN_PROGRESS | DONE
│   │   ├── TaskState.java            ← State interface
│   │   ├── NewState.java             ← Behaviour when NEW
│   │   ├── InProgressState.java      ← Behaviour when IN_PROGRESS
│   │   └── DoneState.java            ← Behaviour when DONE
│   │
│   ├── service/
│   │   └── TaskManager.java          ← Business logic, list management
│   │
│   ├── storage/
│   │   ├── TaskStorage.java          ← Storage interface
│   │   ├── TaskPostgresStorage.java  ← PostgreSQL / JDBC implementation
│   │   ├── TaskJsonStorage.java      ← JSON file implementation
│   │   └── LocalDateAdapter.java     ← Gson LocalDate serializer
│   │
│   └── ui/
│       ├── Input.java                ← Validated console input
│       ├── Menu.java                 ← Menu printer
│       └── TaskPrinter.java          ← Table formatter
│
├── data/
│   └── tasks.json                    ← JSON storage file (if used)
│
├── schema.sql                        ← Database setup script
└── pom.xml                           ← Maven dependencies
```

---

## 🗄️ Database Schema

```sql
-- Custom ENUM types
CREATE TYPE priority_level AS ENUM ('LOW', 'MEDIUM', 'HIGH');
CREATE TYPE task_status    AS ENUM ('NEW', 'IN_PROGRESS', 'DONE');

-- Main table
CREATE TABLE tasks (
    id               SERIAL          PRIMARY KEY,
    title            VARCHAR(255)    NOT NULL,
    description      TEXT,
    completion_date  DATE,
    created_date     DATE            NOT NULL DEFAULT CURRENT_DATE,
    priority         priority_level  NOT NULL DEFAULT 'MEDIUM',
    status           task_status     NOT NULL DEFAULT 'NEW'
);
```

---

<div align="center">

```
  "A task well organized is a task half done."
```

*Built with Java, caffeine, and clean architecture ☕*

</div>
