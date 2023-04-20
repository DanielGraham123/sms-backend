package com.sms.api;

import com.sms.api.model.entities.Course;
import com.sms.api.model.entities.Teacher;
import com.sms.api.model.entities.enums.Role;
import com.sms.api.repositories.CourseRepository;
import com.sms.api.repositories.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@Rollback(false)
public class TeacherRepoTests {
    @Autowired
    private TeacherRepository teacherRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CourseRepository courseRepo;


    @Test
    public void testCreateTeacher() {
        Teacher teacher = new Teacher();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        teacher.setFirstName("John");
        teacher.setLastName("Doe");
        teacher.setEmail("johndoe@gmail.com");
        teacher.setAddress("1234 Main Street");
        teacher.setPhoneNumber("1234567890");
        teacher.setPassword(passwordEncoder.encode("123456"));
        teacher.setUserName(teacher.getFirstName() + " " + teacher.getLastName());

        Set<Role> roles = new HashSet<>();
        roles.add(Role.TEACHER);

        teacher.setRole(roles);

        Optional<Course> course = courseRepo.findById(2L);
        System.out.println("course = " + course);
        teacher.setCourse(course.get());

        Teacher savedTeacher = teacherRepo.save(teacher);

        Teacher existingTeacher = entityManager.find(Teacher.class, savedTeacher.getId());

        assertThat(teacher.getFirstName()).isEqualTo(existingTeacher.getFirstName());
    }
}
