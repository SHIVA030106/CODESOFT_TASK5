import java.io.*;
import java.util.*;

class Student {
    private String name;
    private String rollNumber;
    private String grade;

    public Student(String name, String rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return rollNumber + "," + name + "," + grade;
    }
}

class StudentManagementSystem {
    private List<Student> students = new ArrayList<>();
    private static final String FILE_NAME = "students.txt";

    public StudentManagementSystem() {
        loadFromFile();
    }

    public void addStudent(Student student) {
        for (Student s : students) {
            if (s.getRollNumber().equalsIgnoreCase(student.getRollNumber())) {
                System.out.println("Error: Roll number already exists!");
                return;
            }
        }
        students.add(student);
        saveToFile();
        System.out.println("Student added successfully!");
    }

    public void removeStudent(String rollNumber) {
        boolean removed = students.removeIf(s -> s.getRollNumber().equalsIgnoreCase(rollNumber));
        if (removed) {
            saveToFile();
            System.out.println("Student removed successfully!");
        } else {
            System.out.println("Student not found!");
        }
    }

    public void editStudent(String rollNumber, String newName, String newGrade) {
        for (Student s : students) {
            if (s.getRollNumber().equalsIgnoreCase(rollNumber)) {
                s.setName(newName);
                s.setGrade(newGrade);
                saveToFile();
                System.out.println("Student updated successfully!");
                return;
            }
        }
        System.out.println("Student not found!");
    }

    public void searchStudent(String rollNumber) {
        for (Student s : students) {
            if (s.getRollNumber().equalsIgnoreCase(rollNumber)) {
                System.out.println("Found: Roll No: " + s.getRollNumber() +
                        ", Name: " + s.getName() + ", Grade: " + s.getGrade());
                return;
            }
        }
        System.out.println("Student not found!");
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found!");
            return;
        }
        System.out.println("\n=== Student List ===");
        for (Student s : students) {
            System.out.println("Roll No: " + s.getRollNumber() +
                    ", Name: " + s.getName() + ", Grade: " + s.getGrade());
        }
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) {
                writer.println(s.toString());
            }
        } catch (IOException e) {
            System.out.println("Error saving to file!");
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    students.add(new Student(data[1], data[0], data[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading from file!");
        }
    }
}

public class Management{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentManagementSystem sms = new StudentManagementSystem();
        int choice;

        do {
            System.out.println("\n=== STUDENT MANAGEMENT SYSTEM ===");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Edit Student");
            System.out.println("4. Search Student");
            System.out.println("5. Display All Students");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Enter a number: ");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Roll Number: ");
                    String roll = sc.nextLine().trim();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine().trim();
                    System.out.print("Enter Grade: ");
                    String grade = sc.nextLine().trim();

                    if (roll.isEmpty() || name.isEmpty() || grade.isEmpty()) {
                        System.out.println("Error: All fields are required!");
                    } else {
                        sms.addStudent(new Student(name, roll, grade));
                    }
                    break;
                case 2:
                    System.out.print("Enter Roll Number to remove: ");
                    sms.removeStudent(sc.nextLine().trim());
                    break;
                case 3:
                    System.out.print("Enter Roll Number to edit: ");
                    String editRoll = sc.nextLine().trim();
                    System.out.print("Enter New Name: ");
                    String newName = sc.nextLine().trim();
                    System.out.print("Enter New Grade: ");
                    String newGrade = sc.nextLine().trim();
                    sms.editStudent(editRoll, newName, newGrade);
                    break;
                case 4:
                    System.out.print("Enter Roll Number to search: ");
                    sms.searchStudent(sc.nextLine().trim());
                    break;
                case 5:
                    sms.displayAllStudents();
                    break;
                case 6:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 6);

        sc.close();
    }
}
