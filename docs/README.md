# Malt Chatbot User Guide

## Introduction
Malt is a chatbot designed to help users manage tasks efficiently. It supports various types of tasks, including todos, deadlines, and events, allowing users to organize their schedules seamlessly. Malt provides simple commands to add, mark, delete, and list tasks, among other features.
It's named after the malteasers chocolate! :D

## Features

### 1. Adding a ToDo Task
Adds a simple task to your list.

**Usage:**
```
todo <task_description>
```
**Example:**
```
todo Read a book
```
**Expected Output:**
```
________________________________________
Adding this task:
[T][ ] Read a book
________________________________________
Now you have 1 task in the list.
```

### 2. Adding a Deadline Task
Adds a task with a specific due date.

**Usage:**
```
deadline <task_description> /by <due_date>
```
**Example:**
```
deadline Submit assignment /by 2025-03-28
```
**Expected Output:**
```
________________________________________
Adding this task:
[D][ ] Submit assignment (by: Mar 28 2025)
________________________________________
Now you have 2 tasks in the list. Get working :(
```

### 3. Adding an Event Task
Adds an event with a start and end time.

**Usage:**
```
event <event_description> /from <start_time> /to <end_time>
```
**Example:**
```
event Team meeting /from Monday 2pm /to 4pm
```
**Expected Output:**
```
________________________________________
Adding this task:
 [E][ ] Team meeting (from: Monday 2pm to: 4pm)
 ________________________________________
Now you have 3 tasks in the list.
```

### 4. Listing Tasks
Displays all tasks in your list.

**Usage:**
```
list
```
**Expected Output:**
```
________________________________________
1. [T][ ] Read a book
2. [D][ ] Submit assignment (by: Mar 28 2025)
3. [E][ ] Team meeting (from: Monday 2pm to: 4pm)
________________________________________
```

### 5. Marking a Task as Done
Marks a task as completed.

**Usage:**
```
mark <task_number>
```
**Example:**
```
mark 1
```
**Expected Output:**
```
________________________________________
Perfect, marking this task as done now:
  [T][X] Read a book
________________________________________
```

### 6. Unmarking a Task
Marks a task as not completed.

**Usage:**
```
unmark <task_number>
```
**Example:**
```
unmark 1
```
**Expected Output:**
```
________________________________________
OK, I've unmarked this task:
  [T][ ] Read a book
________________________________________
```

### 7. Deleting a Task
Removes a task from the list.

**Usage:**
```
delete <task_number>
```
**Example:**
```
delete 2
```
**Expected Output:**
```
________________________________________
Noted. I've removed this task:
  [D][ ] Submit assignment (by: 2024-02-28)
________________________________________
Now you have 2 tasks in the list. Get working ;(
```

### 8. Finding Tasks
Searches for tasks containing a keyword.

**Usage:**
```
find <keyword>
```
**Example:**
```
find meeting
```
**Expected Output:**
```
________________________________________
Here are the matching tasks in your list:
3. [E][ ] Team meeting (from: Monday 2pm to: 4pm)
________________________________________
```

### 9. Clearing All Tasks
Removes all tasks from the list.

**Usage:**
```
clear
```
**Expected Output:**
```
________________________________________
All tasks have been cleared!
________________________________________
```

### 10. Exiting the Program
Ends the chatbot session.

**Usage:**
```
bye
```
**Expected Output:**
```
________________________________________
Goodbye! Hope to see you again soon!
________________________________________
```


## Getting Started

1. **Download the latest release**  
   Get the latest version from [this link](https://github.com/GaaneshT/ip/releases).

2. **Run the application**  
   Open a terminal or command prompt and navigate to the folder where you downloaded `malt.jar`. Then run the following command:

   ```sh
   java -jar malt.jar
   ```

