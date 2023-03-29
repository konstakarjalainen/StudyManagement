package fi.tuni.prog3.sisu.entity;

public class Course {
    String courseCode;
    String courseName;
    boolean status = false; // Completed or not

    public void setCode(String newCode) {
        courseCode = newCode;
    }

    public String getCode() {
        return courseCode;
    }

    public void setName(String newName) {
        courseName = newName;
    }

    public String getName() {
        return courseName;
    }
    
    public boolean isCompleted() {
        return status;
    }

    public void setIsCompleted(boolean completed) {
        this.status = completed;
    }
    
    public void changeStatus(){
        this.status = !status;
    }
}
