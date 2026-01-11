# 🎓 Online Exam System - Java DSA Project

A **console-based Online Examination System** built in Java, demonstrating core **Data Structures and Algorithms** concepts. This project is designed to be **interview-ready** with comprehensive comments explaining time complexity and design decisions.

---

## 📚 Data Structures Implemented

| Data Structure | Usage | Time Complexity |
|----------------|-------|-----------------|
| **Array** | Fixed exam questions | O(1) access |
| **ArrayList\<Student\>** | Dynamic student storage | O(1) amortized add |
| **HashMap\<Integer, Student\>** | O(1) student lookup by ID | O(1) average |
| **LinkedList\<Integer\>** | Attempt history per student | O(1) insertion |
| **Binary Search Tree (BST)** | Ranking system | O(log n) insert |
| **Binary Search** | Search student by ID | O(log n) |

---

## 🗂️ Project Structure

```
onlineexamdsa/
├── Question.java      # MCQ model (Array-based options)
├── Student.java       # Student model (LinkedList history)
├── RankNode.java      # BST node for rankings
├── RankTree.java      # BST implementation
├── Exam.java          # Core logic (ArrayList + HashMap)
├── Main.java          # Menu-driven console UI
└── README.md          # Documentation
```

---

## 🚀 How to Run

### Prerequisites
- Java JDK 8 or higher

### Compile
```bash
cd onlineexamdsa
javac *.java
```

### Run
```bash
java Main
```

---

## 📋 Features

### 1. Student Login/Registration
- Register with unique ID and name
- HashMap provides O(1) lookup for returning students

### 2. DSA Exam
- 5 Multiple Choice Questions on DSA topics
- Auto-scoring (20 marks per question)
- Score stored in LinkedList history

### 3. Attempt History
- View all previous exam attempts
- LinkedList maintains chronological order
- Calculate average score

### 4. Ranking System (BST)
- Rankings displayed using reverse in-order traversal
- Highest scores appear first
- O(log n) average insertion time

### 5. Binary Search
- Search students by ID in sorted ArrayList
- Demonstrates divide-and-conquer algorithm
- O(log n) search after O(n log n) sort

### 6. View All Students
- ArrayList iteration to display all registered students

---

## ⏱️ Time Complexity Summary

| Operation | Complexity | Reason |
|-----------|------------|--------|
| Student Login | O(1) | HashMap.get() |
| Add Attempt | O(1) | LinkedList.addLast() |
| BST Insert | O(log n) avg | Tree height traversal |
| Display Rankings | O(n) | In-order traversal |
| Binary Search | O(log n) | Halves search space |
| ArrayList Add | O(1) amortized | Dynamic array |

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────┐
│                      Main.java                          │
│                   (Console UI Layer)                    │
│              Menu display, user input only              │
└─────────────────────────┬───────────────────────────────┘
                          │ delegates to
                          ▼
┌─────────────────────────────────────────────────────────┐
│                      Exam.java                          │
│                  (Business Logic Layer)                 │
│     ArrayList + HashMap + RankTree + Binary Search      │
└─────────────────────────┬───────────────────────────────┘
                          │ uses
          ┌───────────────┼───────────────┐
          ▼               ▼               ▼
┌─────────────┐   ┌─────────────┐   ┌─────────────┐
│ Question[]  │   │  Student    │   │  RankTree   │
│   (Array)   │   │ (LinkedList)│   │   (BST)     │
└─────────────┘   └─────────────┘   └─────────────┘
```

---

## 💡 Design Principles

- **Single Responsibility**: Each class has one purpose
- **Separation of Concerns**: UI logic separated from business logic
- **No over-engineering**: Simple, clean, interview-ready code
- **Comprehensive Comments**: Every method explains time complexity

---

## 📝 Sample Output

```
╔═══════════════════════════════════════════════════════════════╗
║           Online Exam - DSA Examination System                ║
╠═══════════════════════════════════════════════════════════════╣
║  DATA STRUCTURES IMPLEMENTED:                                 ║
║  • Array           → Fixed exam questions                     ║
║  • ArrayList       → Dynamic student storage                  ║
║  • HashMap         → O(1) student lookup                      ║
║  • LinkedList      → Attempt history tracking                 ║
║  • Binary Search Tree → Ranking system                        ║
║  • Binary Search   → Search student by ID                     ║
╚═══════════════════════════════════════════════════════════════╝
```

---

## 👨‍💻 Author

Online Exam System - Java DSA Project

---

## 📄 License

This project is open source and available for educational purposes.
