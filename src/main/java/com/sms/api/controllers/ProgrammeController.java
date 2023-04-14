package com.sms.api.controllers;

import com.sms.api.exceptions.ProgrammeNotFoundException;
import com.sms.api.model.dtos.ProgrammeDTO;
import com.sms.api.model.entities.Programme;
import com.sms.api.repositories.ProgrammeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/programmes")
@RequiredArgsConstructor
public class ProgrammeController {
    private final ProgrammeRepository programmeRepo;

    @GetMapping("")
    public ResponseEntity<?> getProgrammes() {
        return ResponseEntity.ok(programmeRepo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProgrammeById(@PathVariable(value = "id") String id) throws ProgrammeNotFoundException {
        return programmeRepo.findById(Long.parseLong(id))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ProgrammeNotFoundException("Programme not found with id: " + id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProgramme(@RequestBody ProgrammeDTO programmeDTO) {
        var programmeExists = programmeRepo.existsByName(programmeDTO.getName());

        if (programmeExists) {
            return ResponseEntity.badRequest().body("Programme already " + programmeDTO.getName() + " exists");
        }

        Programme programme = new Programme();

        BeanUtils.copyProperties(programmeDTO, programme);

        return ResponseEntity.ok(programmeRepo.save(programme));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProgramme(@PathVariable(value = "id") String id, @RequestBody ProgrammeDTO programmeDTO) throws ProgrammeNotFoundException {
        Programme programme = programmeRepo.findById(Long.parseLong(id)).orElseThrow(() -> new ProgrammeNotFoundException("Programme not found with id: " + id));

        BeanUtils.copyProperties(programmeDTO, programme);

        return ResponseEntity.ok(programmeRepo.save(programme));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProgramme(@PathVariable(value = "id") String id) throws ProgrammeNotFoundException {
        Programme programme = programmeRepo.findById(Long.parseLong(id)).orElseThrow(() -> new ProgrammeNotFoundException("Programme not found with id: " + id));

        programmeRepo.delete(programme);

        return ResponseEntity.ok("Programme deleted successfully");
    }
}
