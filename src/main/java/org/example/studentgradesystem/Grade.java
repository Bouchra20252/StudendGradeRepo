package org.example.studentgradesystem;

public class Grade {
    private int id;
    private String studentName;
    private String subject;
    private double mark;

    public Grade(int id, String studentName, String subject, double mark) {
        this.id = id;
        this.studentName = studentName;
        this.subject = subject;
        this.mark = mark;
    }

    // Getters
    public int getId() { return id; }
    public String getStudentName() { return studentName; }
    public String getSubject() { return subject; }
    public double getMark() { return mark; }
}