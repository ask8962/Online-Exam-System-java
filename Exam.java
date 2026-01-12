import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/**
 * ============================================================================
 * Exam.java - Core exam logic and student management
 * ============================================================================
 * 
 * PURPOSE:
 * This is the main service class that handles all exam-related operations:
 * - Managing students (registration, lookup)
 * - Conducting exams and calculating scores
 * - Maintaining rankings using BST
 * 
 * DATA STRUCTURES USED:
 * 
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ Data Structure │ Purpose │ Time Complexity │
 * ├─────────────────────────────────────────────────────────────────────────┤
 * │ Question[] (Array) │ Fixed exam questions │ O(1) access │
 * │ ArrayList<Student> │ Store all students │ O(1) add │
 * │ HashMap<Integer, Student> │ O(1) lookup by ID │ O(1) avg lookup │
 * │ RankTree (BST) │ Ranking system │ O(log n) insert │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * WHY BOTH ArrayList AND HashMap?
 * ─────────────────────────────────
 * - ArrayList: Maintains insertion order, allows sorted iteration
 * - HashMap: O(1) lookup by student ID
 * - Trade-off: Extra memory for O(1) lookup benefit
 * 
 * This is a common pattern in real systems where you need both:
 * 1. Ordered collection (for iteration/display)
 * 2. Fast lookup by key (for search/update)
 * 
 * INTERVIEW TIP:
 * This dual data structure approach is used in:
 * - LRU Cache (HashMap + Doubly Linked List)
 * - Database indexes (Primary key + Secondary indexes)
 * - Session management systems
 * 
 * @author Online Exam System
 * @version 1.0
 */
public class Exam {

    // ========================================================================
    // INSTANCE VARIABLES
    // ========================================================================

    /**
     * Array of questions for the exam.
     * 
     * WHY ARRAY (not ArrayList)?
     * - Questions are fixed and known at compile time
     * - No dynamic resizing needed
     * - O(1) random access for displaying questions
     * - Memory efficient (no ArrayList overhead)
     * 
     * Space Complexity: O(q) where q = number of questions
     */
    private Question[] questions;

    /**
     * List of all registered students.
     * 
     * WHY ArrayList?
     * - Dynamic size (students can register anytime)
     * - Maintains insertion order
     * - Supports sorting for binary search
     * - O(1) add at end (amortized)
     * 
     * Time Complexities:
     * - Add: O(1) amortized
     * - Get by index: O(1)
     * - Search (unsorted): O(n)
     * - Search (sorted, binary): O(log n)
     */
    private ArrayList<Student> students;

    /**
     * HashMap for O(1) student lookup by ID.
     * 
     * WHY HashMap?
     * - Login requires finding student by ID
     * - O(1) average lookup time
     * - O(1) average insert time
     * - Eliminates the need for O(n) linear search
     * 
     * Key: Student ID (Integer)
     * Value: Student object (reference)
     * 
     * INTERVIEW TIP:
     * HashMap uses hashing internally:
     * - hash(key) determines bucket
     * - Collision resolution via chaining or open addressing
     * - Load factor affects performance (default 0.75)
     * - Worst case O(n) when all keys hash to same bucket
     */
    private HashMap<Integer, Student> studentMap;

    /**
     * Binary Search Tree for ranking system.
     * 
     * WHY BST?
     * - O(log n) insert of new scores
     * - O(n) in-order traversal for sorted rankings
     * - Automatic sorting without explicit sort() call
     */
    /**
     * Queue for students waiting to take the exam.
     * 
     * WHY QUEUE?
     * - FIFO (First-In-First-Out) ensures fairness
     * - O(1) to add (offer) and remove (poll)
     * - LinkedList implementation provides standard Queue behavior
     */
    private Queue<Student> waitingList;

    private RankTree rankTree;

    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================

    /**
     * Initializes the Exam system with predefined DSA questions.
     * 
     * Time Complexity: O(q) where q = number of questions
     * Reason: Initializing the questions array.
     * 
     * Space Complexity: O(q) for questions storage
     */
    public Exam() {
        // Initialize data structures
        students = new ArrayList<>(); // O(1) - empty list
        studentMap = new HashMap<>(); // O(1) - empty map
        rankTree = new RankTree(); // O(1) - empty tree
        waitingList = new LinkedList<>(); // O(1) - empty queue

        // Initialize exam questions (DSA focused)
        initializeQuestions();
    }

    /**
     * Initializes the exam with DSA-focused questions.
     * 
     * Time Complexity: O(q) where q = number of questions
     * Reason: Creating and assigning each Question object.
     * 
     * WHY SEPARATE METHOD?
     * - Single Responsibility Principle
     * - Easier to modify questions without touching constructor
     * - Could be extended to load questions from file
     */
    private void initializeQuestions() {
        // Array of 5 DSA questions
        // Using array because size is fixed and known
        questions = new Question[5];

        // Question 1: Time Complexity
        questions[0] = new Question(
                "What is the time complexity of Binary Search?",
                new String[] {
                        "O(n)", // Option 1
                        "O(log n)", // Option 2 - CORRECT
                        "O(n²)", // Option 3
                        "O(1)" // Option 4
                },
                1 // Correct answer index (0-based)
        );

        // Question 2: Data Structures
        questions[1] = new Question(
                "Which data structure uses LIFO (Last In First Out)?",
                new String[] {
                        "Queue", // Option 1
                        "Stack", // Option 2 - CORRECT
                        "Array", // Option 3
                        "LinkedList" // Option 4
                },
                1 // Correct answer index
        );

        // Question 3: Sorting
        questions[2] = new Question(
                "What is the average time complexity of Quick Sort?",
                new String[] {
                        "O(n)", // Option 1
                        "O(n²)", // Option 2
                        "O(n log n)", // Option 3 - CORRECT
                        "O(log n)" // Option 4
                },
                2 // Correct answer index
        );

        // Question 4: Trees
        questions[3] = new Question(
                "In a Binary Search Tree, where are smaller elements stored?",
                new String[] {
                        "Right subtree", // Option 1
                        "Left subtree", // Option 2 - CORRECT
                        "Root node", // Option 3
                        "Randomly placed" // Option 4
                },
                1 // Correct answer index
        );

        // Question 5: HashMap
        questions[4] = new Question(
                "What is the average time complexity of HashMap lookup?",
                new String[] {
                        "O(n)", // Option 1
                        "O(log n)", // Option 2
                        "O(1)", // Option 3 - CORRECT
                        "O(n²)" // Option 4
                },
                2 // Correct answer index
        );
    }

    // ========================================================================
    // WAITING LIST (QUEUE) METHODS
    // ========================================================================

    /**
     * Adds a student to the waiting list queue.
     * 
     * Time Complexity: O(1)
     * Reason: LinkedList.offer() adds to tail in constant time.
     * 
     * @param student Student to add
     */
    public void addToWaitingList(Student student) {
        if (waitingList.contains(student)) {
            System.out.println("⚠ " + student.getName() + " is already in the waiting list.");
            return;
        }

        waitingList.offer(student); // Enqueue operation
        System.out.println("✓ " + student.getName() + " added to waiting list.");
        System.out.println("  Position in queue: " + waitingList.size());
    }

    /**
     * Processes the next student in the queue.
     * 
     * Time Complexity: O(1)
     * Reason: LinkedList.poll() removes head in constant time.
     * 
     * @return The next student, or null if queue is empty
     */
    public Student processNextInQueue() {
        if (waitingList.isEmpty()) {
            System.out.println("\nWaiting list is empty!");
            return null;
        }

        Student nextStudent = waitingList.poll(); // Dequeue operation
        System.out.println("\n✓ Processing next student: " + nextStudent.getName());
        return nextStudent;
    }

    /**
     * Displays current state of the waiting list.
     * 
     * Time Complexity: O(n) traversal
     */
    public void displayWaitingList() {
        if (waitingList.isEmpty()) {
            System.out.println("\nWaiting list is empty.");
            return;
        }

        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║                WAITING LIST (Queue - FIFO)            ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");

        int pos = 1;
        for (Student s : waitingList) {
            System.out.printf("║  %d. %-46s ║%n", pos++, s.getName());
        }

        System.out.println("╚══════════════════════════════════════════════════════╝");
    }

    // ========================================================================
    // STUDENT MANAGEMENT METHODS
    // ========================================================================

    /**
     * Registers a new student or returns existing student if ID exists.
     * 
     * Time Complexity: O(1) average
     * Reason:
     * - HashMap.containsKey() is O(1) average
     * - HashMap.put() is O(1) average
     * - ArrayList.add() is O(1) amortized
     * 
     * WHY CHECK EXISTING?
     * - Prevents duplicate registrations
     * - Allows returning students to "login" with same ID
     * - HashMap ignores duplicates but ArrayList doesn't
     * 
     * @param id   Student's unique identifier
     * @param name Student's name
     * @return The Student object (new or existing)
     */
    public Student registerOrLoginStudent(int id, String name) {
        // Check if student already exists using HashMap - O(1)
        if (studentMap.containsKey(id)) {
            System.out.println("\n✓ Welcome back, " + name + "!");
            return studentMap.get(id); // O(1) retrieval
        }

        // Create new student
        Student newStudent = new Student(id, name);

        // Add to ArrayList - O(1) amortized
        students.add(newStudent);

        // Add to HashMap for O(1) lookup - O(1) average
        studentMap.put(id, newStudent);

        System.out.println("\n✓ Registered successfully! Welcome, " + name + "!");
        return newStudent;
    }

    /**
     * Finds a student by ID using HashMap (O(1) lookup).
     * 
     * Time Complexity: O(1) average
     * Reason: HashMap uses hashing for direct bucket access.
     * 
     * WHY NOT SEARCH ArrayList?
     * - ArrayList search is O(n) - must check each element
     * - HashMap provides O(1) average lookup
     * - Trade-off: Extra memory for speed
     * 
     * @param id The student ID to search for
     * @return Student object if found, null otherwise
     */
    public Student findStudentById(int id) {
        // HashMap lookup - O(1) average time
        // Returns null if key doesn't exist
        return studentMap.get(id);
    }

    /**
     * Binary Search to find student by ID in sorted ArrayList.
     * First sorts the ArrayList, then performs binary search.
     * 
     * Time Complexity:
     * - Sorting: O(n log n) using Collections.sort (TimSort)
     * - Binary Search: O(log n)
     * - Total: O(n log n) for first call, O(log n) if pre-sorted
     * 
     * Space Complexity: O(log n) for recursion stack
     * 
     * WHY BINARY SEARCH?
     * - Demonstrates the algorithm (interview requirement)
     * - O(log n) is faster than O(n) linear search for large lists
     * - Requires sorted data (we sort first)
     * 
     * WHEN TO USE:
     * - Large datasets where sorting overhead is worth it
     * - Data that is already sorted or rarely changes
     * - When multiple searches will be performed
     * 
     * @param id The student ID to search for
     * @return Student object if found, null otherwise
     */
    public Student binarySearchStudentById(int id) {
        // First, sort the ArrayList by student ID
        // Collections.sort uses TimSort - O(n log n)
        // Student implements Comparable, comparing by ID
        Collections.sort(students);

        // Perform binary search
        // Time Complexity: O(log n)
        int index = binarySearchHelper(0, students.size() - 1, id);

        // Return student if found, null if not
        if (index != -1) {
            return students.get(index); // O(1)
        }
        return null;
    }

    /**
     * Recursive helper method for binary search.
     * 
     * Time Complexity: O(log n)
     * Reason: Each recursive call halves the search space.
     * 
     * ALGORITHM:
     * 1. Find middle element
     * 2. If middle is target → found!
     * 3. If target < middle → search left half
     * 4. If target > middle → search right half
     * 5. If left > right → not found
     * 
     * WHY RECURSIVE?
     * - Cleaner, more readable code
     * - Naturally divides the problem
     * - Iterative version is also valid (uses while loop)
     * 
     * @param left  Left boundary index
     * @param right Right boundary index
     * @param id    Student ID to search for
     * @return Index of student if found, -1 if not found
     */
    private int binarySearchHelper(int left, int right, int id) {
        // Base case: search space exhausted
        if (left > right) {
            return -1; // Not found
        }

        // Calculate middle index
        // Using (left + right) / 2 could overflow for large indices
        // This formula avoids overflow: left + (right - left) / 2
        int mid = left + (right - left) / 2;

        // Get student at middle index
        Student midStudent = students.get(mid); // O(1)

        if (midStudent.getId() == id) {
            // Found! Return the index
            return mid;
        } else if (midStudent.getId() > id) {
            // Target is in LEFT half (smaller IDs)
            return binarySearchHelper(left, mid - 1, id);
        } else {
            // Target is in RIGHT half (larger IDs)
            return binarySearchHelper(mid + 1, right, id);
        }
    }

    // ========================================================================
    // EXAM CONDUCTING METHODS
    // ========================================================================

    /**
     * Conducts the exam for a given student.
     * Presents all questions, collects answers, and calculates score.
     * 
     * Time Complexity: O(q) where q = number of questions
     * Reason: Iterates through all questions once.
     * 
     * Space Complexity: O(1)
     * Reason: Only using a score counter, no extra data structures.
     * 
     * WORKFLOW:
     * 1. Display exam instructions
     * 2. For each question:
     * a. Display question and options
     * b. Get student's answer
     * c. Check if correct using Question.isCorrect()
     * d. Accumulate score
     * 3. Store score in student's history (LinkedList)
     * 4. Add to ranking tree (BST)
     * 
     * @param student The student taking the exam
     * @param scanner Scanner for user input
     */
    public void conductExam(Student student, Scanner scanner) {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║           DSA ONLINE EXAMINATION STARTED             ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.println("║  Student: " + String.format("%-42s", student.getName()) + "║");
        System.out.println("║  Total Questions: 5                                  ║");
        System.out.println("║  Each Question: 20 marks                             ║");
        System.out.println("║  Total Marks: 100                                    ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        // Score counter
        int score = 0;

        // Iterate through all questions - O(q) loop
        for (int i = 0; i < questions.length; i++) {
            System.out.println("\n────────────────────────────────────────────────────────");
            System.out.println("Question " + (i + 1) + " of " + questions.length);
            System.out.println("────────────────────────────────────────────────────────");

            // Display the question - O(1) since 4 options always
            questions[i].displayQuestion();

            // Get student's answer
            System.out.print("\nYour answer (1-4): ");
            int answer = getValidAnswer(scanner);

            // Convert to 0-based index and check
            // Question.isCorrect() is O(1) - simple comparison
            if (questions[i].isCorrect(answer - 1)) {
                System.out.println("✓ Correct!");
                score += 20; // Each question is worth 20 marks
            } else {
                System.out.println("✗ Wrong! Correct answer was: " +
                        (questions[i].getCorrectOption() + 1));
            }
        }

        // Display final score
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║                  EXAM COMPLETED!                      ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.println("║  Your Score: " + score + "/100" +
                String.format("%39s", "║"));
        System.out.println("╚══════════════════════════════════════════════════════╝");

        // Update student's score
        student.setScore(score);

        // Add to attempt history - O(1) LinkedList addLast()
        student.addAttempt(score);

        // Add to ranking tree - O(log n) average BST insert
        rankTree.insert(score, student.getName());
    }

    /**
     * Gets a valid answer (1-4) from the user with input validation.
     * 
     * Time Complexity: O(k) where k = number of invalid inputs
     * Best case O(1) if first input is valid.
     * 
     * WHY INPUT VALIDATION?
     * - Prevents crashes from invalid input
     * - Better user experience
     * - Robust error handling
     * 
     * @param scanner Scanner for user input
     * @return Valid integer between 1 and 4
     */
    private int getValidAnswer(Scanner scanner) {
        int answer = -1;

        // Loop until valid input received
        while (answer < 1 || answer > 4) {
            try {
                answer = Integer.parseInt(scanner.nextLine().trim());
                if (answer < 1 || answer > 4) {
                    System.out.print("Please enter a number between 1 and 4: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input! Please enter a number (1-4): ");
            }
        }

        return answer;
    }

    // ========================================================================
    // DISPLAY / VIEW METHODS
    // ========================================================================

    /**
     * Displays complete attempt history for a student.
     * 
     * Time Complexity: O(k) where k = number of attempts
     * Reason: LinkedList traversal in student.displayAttemptHistory()
     * 
     * @param student The student whose history to display
     */
    public void displayAttemptHistory(Student student) {
        // Delegates to Student class - O(k) for k attempts
        student.displayAttemptHistory();
    }

    /**
     * Displays the ranking list using BST in-order traversal.
     * 
     * Time Complexity: O(n) where n = number of students with scores
     * Reason: Must visit every node in BST for complete ranking.
     */
    public void displayRankings() {
        // Delegates to RankTree - O(n) traversal
        rankTree.displayRankings();
    }

    /**
     * Displays all registered students.
     * 
     * Time Complexity: O(n) where n = number of students
     * Reason: Iterates through ArrayList once.
     */
    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("\nNo students registered yet.");
            return;
        }

        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║              ALL REGISTERED STUDENTS                  ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");

        // O(n) iteration through ArrayList
        for (Student s : students) {
            System.out.printf("║  ID: %-6d │ Name: %-20s │ Score: %-3d ║%n",
                    s.getId(), s.getName(), s.getScore());
        }

        System.out.println("╚══════════════════════════════════════════════════════╝");
        System.out.println("Total Students: " + students.size());
    }

    // ========================================================================
    // UTILITY METHODS
    // ========================================================================

    /**
     * Returns total number of registered students.
     * 
     * Time Complexity: O(1)
     * Reason: ArrayList.size() is cached.
     * 
     * @return Number of students
     */
    public int getStudentCount() {
        return students.size();
    }

    /**
     * Checks if any students are registered.
     * 
     * Time Complexity: O(1)
     * 
     * @return true if no students, false otherwise
     */
    public boolean hasNoStudents() {
        return students.isEmpty();
    }
}
