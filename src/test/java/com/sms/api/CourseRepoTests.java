package com.sms.api;

import com.sms.api.model.entities.Course;
import com.sms.api.model.entities.Grade;
import com.sms.api.model.entities.Programme;
import com.sms.api.repositories.CourseRepository;
import com.sms.api.repositories.GradeRepository;
import com.sms.api.repositories.ProgrammeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.HashSet;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CourseRepoTests {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ProgrammeRepository programmeRepository;
    @Autowired
    private GradeRepository gradeRepository;

    @Test
    public void testCreateCourse() throws Exception {
        Course course = new Course();
        course.setName("Mathematics");

        var programme = programmeRepository.findByName("Technical").orElseThrow();

        course.setProgramme(programme);
//        course.setGrades({"Form 1"});
        Set<Grade> grades = new HashSet<>();
        grades.add(gradeRepository.findByName("Form 1").orElseThrow());
        course.setGrades(grades);

        Course savedCourse = courseRepository.save(course);

        System.out.println(savedCourse);

        Course existingCourse = entityManager.find(Course.class, savedCourse.getId());

        Assertions.assertThat(course.getName()).isEqualTo(existingCourse.getName());
    }
}
