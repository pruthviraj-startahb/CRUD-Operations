# 💻 JDBC CRUD Operations Project (Java + MySQL)

## 📌 Project Overview

This is a **console-based Java application** that performs **CRUD (Create, Read, Update, Delete)** operations using **JDBC with MySQL database**.

The project demonstrates:

* Database connectivity using JDBC
* Use of `PreparedStatement` and `Statement`
* Transaction management (`commit` & `rollback`)
* Batch processing (`addBatch`, `executeBatch`)

---

## 🚀 Features

* ➕ Insert single record
* ➕ Insert multiple records (Batch Processing)
* 📄 Display all records
* ✏️ Update records (single or all fields)
* ❌ Delete records
* 🔁 Transaction handling (commit & rollback)

---

## 🛠️ Technologies Used

* Java
* JDBC API
* MySQL Database

---

## 🗂️ Database Structure

**Database Name:** `startAhb`
**Table Name:** `dyp`

```sql
CREATE TABLE dyp (
    roll INT PRIMARY KEY,
    fname VARCHAR(50),
    mname VARCHAR(50),
    lname VARCHAR(50)
);
```

---

## ⚙️ How to Run

1. Install MySQL and create database/table
2. Add MySQL Connector JAR to project
3. Update DB credentials in code:

```java
String url="jdbc:mysql://localhost:3306/startAhb";
String username="root";
String password="root";
```

4. Compile & run:

```bash
javac Main.java
java Main
```

---

## 📋 Menu Options

```text
1.Insert Record
2.Delete Record
3.Update Record
4.Display all Records
5.Insert Multiple Records
6.Exit
```

---

## 🖥️ Sample Output

### ▶️ Insert Record

```text
Enter Roll Number: 1
Enter First Name: Pruthviraj
Enter Middle Name: A
Enter Last Name: Patil

Record inserted successfully
```

---

### ▶️ Display Records

```text
Roll Number | First Name | Middle Name | Last Name
---------------------------------------------------
1 | Pruthviraj | A | Patil
```

---

### ▶️ Update Record

```text
Enter Roll Number to Update: 1
What do you want to update?
1. First Name
2. Middle Name
3. Last Name
4. All Fields

Enter New First Name: Raj

Record Updated Successfully
```

---

### ▶️ Delete Record

```text
Enter Roll Number to Delete: 1
Record Deleted Successfully
```

---

### ▶️ Batch Insert

```text
How many records do you want to insert: 2

Enter details for record 1
Roll: 2
First Name: Amit
Middle Name: B
Last Name: Sharma

Enter details for record 2
Roll: 3
First Name: Ravi
Middle Name: C
Last Name: Kumar

2 rows inserted successfully.
```

---

## ⚠️ Exception Handling

* Duplicate Entry Handling
* SQL Errors with rollback

---

## 🧠 Key Concepts Used

* JDBC Driver Loading
* Connection Management
* PreparedStatement vs Statement
* Transaction Control
* Batch Processing

---

## 📌 Conclusion

This project provides a strong foundation in **database operations using Java JDBC**, covering both basic and advanced concepts like **transactions and batch processing**.

---

## 👨‍💻 Author

**Pruthviraj Patil**

---
