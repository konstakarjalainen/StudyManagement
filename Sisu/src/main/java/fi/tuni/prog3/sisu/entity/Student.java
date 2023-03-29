package fi.tuni.prog3.sisu.entity;

import fi.tuni.prog3.sisu.entity.sisu.DegreeProgramme;

import java.util.ArrayList;
import java.util.Calendar;  

public class Student {
    String firstName;
    String lastName;
    String email;
    String studentNumber;
    String startingYear;
    String graduationYear;
    DegreeProgramme degreeProgramme;

    ArrayList<Course> courses;
    
    public Student() {
        courses = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean setEmail(String email) {
        if (email.contains("@") && !email.contains(" ")) {
            this.email = email;
            return true;
        }else return false;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public boolean setStudentNumber(String studentNumber) {
        if (studentNumber.length()>=6){
            this.studentNumber = studentNumber;
            return true;
        }
        else return false;
    }
    
    public String getStartingYear(){
        return startingYear;
    }
    
    // Returns true if starting year is current year +-20 years and returns false if not.
    public boolean setStartingYear(String startingYear){
        try {
            int year = Integer.parseInt(startingYear);
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            if (year >= currentYear-20 && year <= currentYear+20){
                this.startingYear = startingYear;
                return true;
            }
            else {
                return false;
            }
        }
        catch(NumberFormatException e){
            return false;
        }
        
    }
    
    public String getGraduationYear(){
        return graduationYear;
    }
    
    // Returns true if the graduation year is between starting year + 1 --- starting year + 20
    public boolean setGraduationYear(String graduationYear){
        try {
            int year = Integer.parseInt(graduationYear);
            int startYear = Integer.parseInt(this.startingYear);
            if (year > startYear && year <= startYear + 20){
                this.graduationYear = graduationYear;
                return true;
            }
            else {
                return false;
            }
        }
        catch(NumberFormatException e){
            return false;
        }
    }

    public DegreeProgramme getDegreeProgramme() {
        return degreeProgramme;
    }

    public void setDegreeProgramme(DegreeProgramme degreeProgramme) {
        this.degreeProgramme = degreeProgramme;
    }

    // Returns true if course was added.
    // Returns false if student already has the course.
    public boolean addCourse(Course course) {
        
        if (courses.stream().anyMatch(c -> c.getName().equals(course.getName()))) {
            return false;
        }
        else {
            courses.add(course);
            return true;
        }
    }
    
    public void removeCourse(String name) {
        for (var course:courses){
            if (name.equalsIgnoreCase(course.getName())){
                courses.remove(course);
                break;
            } 
        }
    }
}
