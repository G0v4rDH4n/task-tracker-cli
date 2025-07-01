# 📝 Task Tracker CLI

A lightweight and intuitive command‑line application to help you track and manage your day‑to‑day tasks made with **Spring Shell**. This project is designed by [roadmap.sh](https://roadmap.sh/). You can find the project [here](https://roadmap.sh/projects/task-tracker).

## 🚀 Features

- Add tasks (`todo`)
- Update task descriptions
- Mark tasks as **in-progress** or **done**
- Delete tasks
- List all tasks or filter by status
- Persistent storage in a JSON file

## 🎯 Prerequisites

- Java 21 or higher
- Maven (wrapper included) or your IDE’s build support
- Git (to clone the repository)

## 📥 Installation

Clone the repo:

```bash
git clone https://github.com/G0v4rDH4n/task-tracker-cli.git
cd task-tracker-cli
```

Build with Maven:

```bash
./mvnw clean install
```

This will generate a runnable JAR under `target/` (e.g., `task-tracker-cli.jar`).

---

## ▶️ Running the App

Execute it via:

```bash
java -jar target/task-tracker-1.0.0.jar
```
Or:

```bash
./task-tracker
```
---

## 🛠️ Commands

| Command                            | Description                                           |
|------------------------------------|-------------------------------------------------------|
| `add <description>`                | Add a new task                                        |
| `update <id> <description>`        | Update an existing task                               |
| `delete <id>`                      | Remove a task                                         |
| `mark-in-progress <id>`            | Set task status to **in-progress**                    |
| `mark-done <id>`                   | Set task status to **done**                           |
| `list`                             | Show all tasks                                        |
| `list todo \| in-progress \| done` | Show tasks filtered by status                         |
| `help`                             | Show usage information                                |
| `exit --erase true`                | Exits the app along with deleting the `tasks.json` file |

---

## 📁 Example Usage

```bash
# Run the JAR
java -jar target/task-tracker-1.0.0.jar
# Add tasks
add "Buy groceries"

# List all tasks
list

# Mark a task as in-progress
mark-in-progress 1

# Mark as done
mark-done 1

# Update a task
update 1 "Buy groceries & cook dinner"

# Delete a task
delete 1

# List by status
list todo
list in-progress
list done
```

---

## 🗂️ Project Structure

```
task-tracker-cli/
│
├── src/                 → Java source code
├── target/              → Built JARs (after Maven build)
├── tasks.json           → Your tasks data (auto-created in working dir)
├── pom.xml / mvnw       → Build configuration & Maven wrapper
├── task-tracker         → Shell Wrapper to run application
└── README.md            → You are here ↓
```

---

## 🤝 Contributing

Fork the repo, create a feature/bugfix branch, and submit a PR. Please adhere to the code style and include tests where possible.

---

Made with ❤️ by **G0v4rDH4n** • View the project on GitHub: https://github.com/G0v4rDH4n/task-tracker-cli
