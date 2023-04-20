package com.sms.api;

import com.sms.api.model.entities.Programme;
import com.sms.api.model.entities.enums.Level;
import com.sms.api.repositories.ProgrammeRepository;
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
public class ProgrammeRepoTests {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ProgrammeRepository programmeRepo;

    @Test
    public void testCreateProgramme() {
        Programme programme = new Programme();
        programme.setName("Technical");
        programme.setLevel(Level.HIGH_SCHOOL);

        Programme savedProgramme = programmeRepo.save(programme);

        Programme existingProgramme = entityManager.find(Programme.class, savedProgramme.getId());

        assertThat(programme.getName()).isEqualTo(existingProgramme.getName());
    }
}
