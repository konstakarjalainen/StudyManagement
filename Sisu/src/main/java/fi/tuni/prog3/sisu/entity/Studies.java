package fi.tuni.prog3.sisu.entity;

import java.util.List;

public class Studies {

    Student student;

    public Studies(Student student) {
        this.student = student;
    }

    public Studies() {
        this.student = new Student();
    }

    public List<Course> courses() {
        return this.student.courses;
    }
    
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
