package fi.tuni.prog3.sisu.view;

import fi.tuni.prog3.sisu.entity.Course;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class CoursePerformanceItem extends HBox {

    Course course;
    CheckBox isCompleted;
    Label courseName;
    Callback<Course, Course> onStatusChangeListener = null;

    public CoursePerformanceItem(Course course) {
        super();
        this.course = course;

        this.setPadding(new Insets(5));

        this.courseName = new Label(course.getName());
        this.courseName.setFont(new Font(16));

        this.isCompleted = new CheckBox();
        this.isCompleted.setScaleX(1.2);
        this.isCompleted.setScaleY(1.2);
        this.isCompleted.setPadding(new Insets(3));

        this.isCompleted.selectedProperty().setValue(course.isCompleted());

        this.isCompleted.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            System.out.format("Course %s isCompleted value changed to %s %n",course.getName(), newValue);
            course.setIsCompleted(newValue);
            onStatusChange(course);
        });

        this.getChildren().addAll(this.courseName, this.isCompleted);
    }

    public boolean hasCourseName(String courseName) {
        return this.course.getName().equals(courseName);
    }

    public void setOnStatusChangeListener(Callback<Course, Course> callback) {
        this.onStatusChangeListener = callback;
    }

    private void onStatusChange(Course course) {
        if(this.onStatusChangeListener != null) {
            this.course = this.onStatusChangeListener.call(course);
        }

    }
}
