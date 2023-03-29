package fi.tuni.prog3.sisu.controller;

import fi.tuni.prog3.sisu.client.SisuClient;
import fi.tuni.prog3.sisu.entity.Student;
import fi.tuni.prog3.sisu.entity.Studies;
import fi.tuni.prog3.sisu.entity.Course;
import fi.tuni.prog3.sisu.entity.sisu.DegreeProgramme;
import fi.tuni.prog3.sisu.entity.sisu.SisuCourse;
import fi.tuni.prog3.sisu.entity.sisu.SisuModule;
import fi.tuni.prog3.sisu.entity.sisu.SisuResponse;
import fi.tuni.prog3.sisu.util.ModuleUtil;
import fi.tuni.prog3.sisu.util.Persistent;
import fi.tuni.prog3.sisu.util.SelectConverter;
import fi.tuni.prog3.sisu.view.CoursePerformanceItem;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.stage.Stage;

import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class MainViewController {

    Studies studies;

    SisuClient client = new SisuClient();

    @FXML
    TextField firstName, lastName, email, studentNumber, startingYear, graduationYear;
    
    @FXML
    Label errorMessage;

    @FXML
    ChoiceBox<DegreeProgramme> degreeProgrammeSelect;

    @FXML
    TreeView<SisuModule> coursesList;
    TreeItem<SisuModule> coursesListRootElement = new TreeItem<>();
    
    @FXML
    ListView<String> chosenCoursesList;
    
    @FXML
    public Button closeButton;

    @FXML
    VBox coursePerformanceView;

    @FXML
    Label coursesTotal, coursesComplete;
    
    ObservableList<String> chosenCoursesRoot = FXCollections.observableArrayList();

    Persistent<Studies> persistentStudies = new Persistent<>("saved_studies.txt", Studies.class);


    /**
     * Save student info.
     * @return true if inputs are valid, false if inputs are invalid
     * Shows error message if some input is invalid
     */
    public boolean onSave() {
        
        errorMessage.setText("");
        Student student = studies.getStudent();
        
        if (firstName.getText() == null || lastName.getText() == null) {
            errorMessage.setText("Nimi ei voi olla tyhjä");
            return false;
        }
        student.setFirstName(firstName.getText());
        student.setLastName(lastName.getText());
        
        if (email.getText() != null && student.setEmail(email.getText())){}
        else {
            errorMessage.setText("Virheellinen email: " + email.getText());
            return false;
        } 
        if (studentNumber.getText() != null && student.setStudentNumber(studentNumber.getText())){}
        else {
            errorMessage.setText("Opiskelijanumeron pitää olla vähintään 6 merkkiä pitkä");
            return false;
        }
        
        if (startingYear.getText() != null && student.setStartingYear(startingYear.getText())){}
        else {
            errorMessage.setText("Virheellinen aloitusvuosi: " + startingYear.getText());
            return false;
        } 
        if (graduationYear.getText() != null && student.setGraduationYear(graduationYear.getText())){}
        else {
            errorMessage.setText("Virheellinen valmistumisvuosi: " + graduationYear.getText());
            return false;
        }
        if (degreeProgrammeSelect.getValue() == null) {
            errorMessage.setText("Valitse tutkinto");
            return false;
        }
        student.setDegreeProgramme(degreeProgrammeSelect.getValue());
        
        persistentStudies.save(studies);
        
        // Set tree from new degreeProgramme
        this.coursesList.setCellFactory(treeViewCellFactory);
        var root = this.coursesListRootElement;
        this.coursesList.setRoot(root);
        
        // Set listView items
        this.chosenCoursesList.setCellFactory(listViewCellFactory);
        return true;
    }

    public void saveAndRefresh() {
        if(this.onSave()){
            this.refreshCourses();
        }
    }

    @FXML
    public void initialize() {

        // Load from disk
        this.studies = persistentStudies.load();
        if(this.studies == null) {
            Student student = new Student(); // placeholder
            this.studies = new Studies(student);
        }

        // Set loaded values to fields
        firstName.setText(this.studies.getStudent().getFirstName());
        lastName.setText(this.studies.getStudent().getLastName());
        email.setText(this.studies.getStudent().getEmail());
        studentNumber.setText(this.studies.getStudent().getStudentNumber());
        startingYear.setText(this.studies.getStudent().getStartingYear());
        graduationYear.setText(this.studies.getStudent().getGraduationYear());
        errorMessage.setText("");
        
        SisuResponse<DegreeProgramme> response = null;
        try {
            response = client.getDegreeProgrammes();
            initDegreeProgramme(response.getSearchResults());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        
        if (this.studies.getStudent().getDegreeProgramme()!= null){
            // Set tree from existing degreeProgramme
            this.coursesList.setCellFactory(treeViewCellFactory);
            var root = this.coursesListRootElement;
            this.coursesList.setRoot(root);
            this.chosenCoursesList.setCellFactory(listViewCellFactory);
            refreshCourses(); 
        }
        
        for(Course course : this.studies.courses()) {
            CoursePerformanceItem item = new CoursePerformanceItem(course);
            item.setOnStatusChangeListener(this::onCourseStatusChange);
            this.coursePerformanceView.getChildren().add(item);
        }

        this.coursesTotal.setText(String.valueOf(this.studies.courses().size()));
        this.coursesComplete.setText(String.valueOf(
                this.studies.courses().stream().filter((Course::isCompleted)).count()
        ));

        showCourses();
        
    }
    
    /**
     * Initialize degreeProgrammeSelect (ChoiceBox)
     * @param degreeProgrammeList values to set to selector
     */
    private void initDegreeProgramme(List<DegreeProgramme> degreeProgrammeList) {
        degreeProgrammeSelect.setConverter(SelectConverter.getDegreeConverter(degreeProgrammeList));
        degreeProgrammeSelect.setItems(FXCollections.observableList(degreeProgrammeList));
        if (this.studies.getStudent().getDegreeProgramme()!= null){
            for(DegreeProgramme degreeProgramme : degreeProgrammeSelect.getItems()) {
                if(degreeProgramme.getId().equalsIgnoreCase(this.studies.getStudent().getDegreeProgramme().getId())) {
                    degreeProgrammeSelect.setValue(degreeProgramme);
                    break;
                }
            }
        }
    }

    /**
     * Handler for Refresh courses button. Also refresh on app start.
     * Fetch info by selected degree programme, and then recursively fetch all related information in separate thread.
     */
    public void refreshCourses() {
        try {
            List<SisuModule> response = this.client.getModules(List.of(this.studies.getStudent().getDegreeProgramme().getGroupId()));
            SisuModule degreeModule = response.get(0);
            if(degreeModule == null) {
                System.out.println("Degree module not found");
                // @todo Alert here?
            } else {
                this.coursesListRootElement.getChildren().clear();
                this.coursesListRootElement.setValue(degreeModule);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        new Thread(new RefreshTask()).start();
    }

    /**
     * For threading. Calls refreshCourses method.
     */
    private class RefreshTask extends Task<Object> {
        @Override
        protected Object call() throws Exception {
            refreshCourses(coursesListRootElement);
            return null;
        }
    }

    /**
     * Recursively go through sisu modules, fetch info and set to TreeView.
     */
    public void refreshCourses(TreeItem<SisuModule> parent) {
        var ids = ModuleUtil.getChildModulesFromRules(parent.getValue().getRule());
        if(ids.isEmpty()) return;

        try {
            if(!ids.get("modules").isEmpty()) {
                List<SisuModule> response = this.client.getModules(ids.get("modules"));
                for(SisuModule mod : response) {
                    var item = new TreeItem<>(mod);
                    Platform.runLater(() -> {
                        parent.getChildren().add(item);
                    });
                    this.refreshCourses(item);
                }
            }
            if(!ids.get("courses").isEmpty()) {
                List<SisuCourse> response = this.client.getCourses(ids.get("courses"));
                for(SisuModule mod : response) {
                    var item = new TreeItem<>(mod);
                    Platform.runLater(() -> {
                        parent.getChildren().add(item);
                    });
                    this.refreshCourses(item);
                }
            }

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
    
    /**
     *  Called when succesfully added new course to student.courses and on app start
     */
    public void showCourses(){
        List<Course> courses = this.studies.courses();
        for (var course: courses){
            String courseName = course.getName();
            chosenCoursesRoot.add(courseName);
        }
        this.chosenCoursesList.setItems(chosenCoursesRoot);
        this.chosenCoursesList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /**
     * Called when double clicking treeview item
     * @param module Item clicked
     */
    private void handleTreeItemDoubleClick(SisuModule module) {

        // Special case for courses
        // @todo add courses to own list as seen on the picture in Plussa
        if(module.getType().equalsIgnoreCase("Course")) {
            SisuCourse course = (SisuCourse) module;
            System.out.println(course.getName() + " " + course.getCode());
            
            Course newCourse = new Course();
            newCourse.setCode(course.getCode());
            newCourse.setName(course.toString());
            
            if(this.studies.getStudent().addCourse(newCourse)){
                chosenCoursesRoot.add(newCourse.getName());

                CoursePerformanceItem item = new CoursePerformanceItem(newCourse);
                item.setOnStatusChangeListener(this::onCourseStatusChange);
                this.coursePerformanceView.getChildren().add(item);
            }
            this.coursesTotal.setText(String.valueOf(this.studies.courses().size()));
        }
    }

    /**
     * Factory for TreeCells.
     * Mainly for setting mouse click event.
     */
    private final Callback<TreeView<SisuModule>, TreeCell<SisuModule>> treeViewCellFactory = (module) -> {

        TreeCell<SisuModule> cell = new TreeCell<SisuModule>() {
            @Override
            public void updateItem(SisuModule item, boolean empty) {
                super.updateItem(item, empty) ;
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName().getFi());
                }
                setOnMouseClicked((evt) -> {
                    if(evt.getClickCount() == 2) {
                        handleTreeItemDoubleClick(item);
                    }
                });
            }
        };
        return cell;
    };
    
    
    /**
     * Called when double clicking listview item
     * @param course chosenCourse clicked
     */ 
    private void handleListItemDoubleClick(String course){
        this.chosenCoursesRoot.remove(course);
        this.studies.getStudent().removeCourse(course);

        Node node = this.coursePerformanceView
                .getChildren().stream()
                .filter((item) -> ((CoursePerformanceItem) item).hasCourseName(course))
                .findFirst().orElse(null);

        if(node != null) {
            this.coursePerformanceView.getChildren().remove(node);
        }
        this.coursesTotal.setText(String.valueOf(this.studies.courses().size()));
    }
    
        /**
     * Factory for ListCells.
     * Mainly for setting mouse click event.
     */
    private final Callback<ListView<String>, ListCell<String>> listViewCellFactory = (module) -> {

        ListCell<String> cell = new ListCell<String>() {
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty) ;
                if (empty || name == null) {
                    setText(null);
                } else {
                    setText(name);
                }
                setOnMouseClicked((evt) -> {
                    if(evt.getClickCount() == 2) {
                        handleListItemDoubleClick(name);
                    }
                });
            }
        };
        return cell;
    };

    public void handleCloseButtonAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public Course onCourseStatusChange(Course course) {

        this.coursesTotal.setText(String.valueOf(this.studies.courses().size()));
        this.coursesComplete.setText(String.valueOf(
                this.studies.courses().stream().filter((Course::isCompleted)).count()
        ));

        return course;
    }
}
