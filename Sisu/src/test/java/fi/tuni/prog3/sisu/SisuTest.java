/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package fi.tuni.prog3.sisu;

import fi.tuni.prog3.sisu.entity.Course;
import fi.tuni.prog3.sisu.entity.Student;
import fi.tuni.prog3.sisu.entity.Studies;
import fi.tuni.prog3.sisu.entity.sisu.DegreeProgramme;

import org.testfx.framework.junit.ApplicationTest;
import javafx.stage.Stage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class SisuTest extends ApplicationTest {

    
    @Override
    public void start(Stage stage) throws IOException {
        new Sisu().start(stage);
    }

    @Test
    @DisplayName("Testing student")
    public void testStudent() {
        Student tStudent = new Student();
        
        String firstName = "test";
        String lastName = "student";
        String email = "test.student@mail.com";
        String studentNumber = "123456";
        String startingYear = "2020";
        String graduationYear = "2025";
        DegreeProgramme degreeProgramme = new DegreeProgramme();
        
        tStudent.setFirstName(firstName);
        tStudent.setLastName(lastName);
        tStudent.setEmail(email);
        tStudent.setStudentNumber(studentNumber);
        tStudent.setStartingYear(startingYear);
        tStudent.setGraduationYear(graduationYear);
        tStudent.setDegreeProgramme(degreeProgramme);

        assertEquals(firstName, tStudent.getFirstName());
        assertEquals(lastName, tStudent.getLastName());
        assertEquals(email, tStudent.getEmail());
        assertEquals(studentNumber, tStudent.getStudentNumber());
        assertEquals(startingYear, tStudent.getStartingYear());
        assertEquals(graduationYear, tStudent.getGraduationYear());
        assertEquals(degreeProgramme, tStudent.getDegreeProgramme());
    }

    @Test
    @DisplayName("Testing course")
    public void testCource() {
        Course course = new Course();

        String courseCode = "123456";
        String courseName = "abcdefg";

        assertEquals(false, course.isCompleted());

        course.setCode(courseCode);
        course.setName(courseName);
        course.setIsCompleted(true);

        assertEquals(courseCode, course.getCode());
        assertEquals(courseName, course.getName());
        assertEquals(true, course.isCompleted());

        course.changeStatus();
        assertEquals(false, course.isCompleted());
        course.changeStatus();
        assertEquals(true, course.isCompleted());
    }

    @Test
    @DisplayName("Testing studies")
    public void testStudies() {
        Student tStudent = new Student();
        
        String firstName = "test";
        String lastName = "student";
        String email = "test.student@mail.com";
        String studentNumber = "123456";
        String startingYear = "2020";
        String graduationYear = "2025";
        DegreeProgramme degreeProgramme = new DegreeProgramme();
        
        tStudent.setFirstName(firstName);
        tStudent.setLastName(lastName);
        tStudent.setEmail(email);
        tStudent.setStudentNumber(studentNumber);
        tStudent.setStartingYear(startingYear);
        tStudent.setGraduationYear(graduationYear);
        tStudent.setDegreeProgramme(degreeProgramme);

        Course course = new Course();

        String courseCode = "123456";
        String courseName = "abcdefg";

        course.setCode(courseCode);
        course.setName(courseName);

        Studies studies = new Studies(tStudent);

        assertEquals(true, studies.courses().isEmpty());

        studies.getStudent().addCourse(course);

        assertEquals(false, studies.courses().isEmpty());

        studies.getStudent().removeCourse(courseName);

        assertEquals(true, studies.courses().isEmpty());
    }

    @Test
    @DisplayName("Testing degreeprogramme")
    public void testDegreeProgramme() {

        String id = "Programme_ID";
        String code = "Programme_code";
        String lang = "Language";
        String groupId = "Group ID";
        String name = "Programme Name";
        String nameMatch = "Name Match";
        String searchTagsMatch = "Search Tags";
        List<String> curriculumPeriodIds = new ArrayList<>();
        JsonObject credits = new JsonObject();

        DegreeProgramme degreeProgramme = new DegreeProgramme();

        degreeProgramme.setId(id);
        degreeProgramme.setCode(code);
        degreeProgramme.setLang(lang);
        degreeProgramme.setGroupId(groupId);
        degreeProgramme.setName(name);
        degreeProgramme.setNameMatch(nameMatch);
        degreeProgramme.setSearchTagsMatch(searchTagsMatch);
        degreeProgramme.setCurriculumPeriodIds(curriculumPeriodIds);
        degreeProgramme.setCredits(credits);

        assertEquals(id, degreeProgramme.getId());
        assertEquals(code, degreeProgramme.getCode());
        assertEquals(lang, degreeProgramme.getLang());
        assertEquals(groupId, degreeProgramme.getGroupId());
        assertEquals(name, degreeProgramme.getName());
        assertEquals(nameMatch, degreeProgramme.getNameMatch());
        assertEquals(searchTagsMatch, degreeProgramme.getSearchTagsMatch());
        assertEquals(curriculumPeriodIds, degreeProgramme.getCurriculumPeriodIds());
        assertEquals(credits, degreeProgramme.getCredits());
    }
}
