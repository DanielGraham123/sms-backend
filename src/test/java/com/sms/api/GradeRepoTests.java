package com.sms.api;

import com.sms.api.model.entities.Grade;
import com.sms.api.model.entities.enums.Level;
import com.sms.api.repositories.GradeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class GradeRepoTests {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private GradeRepository gradeRepo;

    @Test
    public void testCreateGrade() {
        Grade grade = new Grade();
        grade.setName("Form 1");
        grade.setLevel(Level.HIGH_SCHOOL);

        Grade savedGrade = gradeRepo.save(grade);

        Grade existingGrade = entityManager.find(Grade.class, savedGrade.getId());

        assertThat(grade.getName()).isEqualTo(existingGrade.getName());
    }
}
