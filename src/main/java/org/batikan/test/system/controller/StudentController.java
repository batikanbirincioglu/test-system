package org.batikan.test.system.controller;

import lombok.RequiredArgsConstructor;
import org.batikan.test.system.service.StudentService;
import org.batikan.test.system.dto.StudentDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/all")
    public List<StudentDto> getAll() {
	return studentService.getAll();
    }

    @GetMapping("/{id}")
    public StudentDto getById(@PathVariable Long id) {
	return studentService.getById(id);
    }

    @PostMapping
    public StudentDto create(@RequestBody StudentDto studentDto) {
	return studentService.create(studentDto);
    }

    @PutMapping("/{id}")
    public StudentDto update(@PathVariable Long id, @RequestBody StudentDto studentDto) {
	return studentService.update(id, studentDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
	studentService.delete(id);
    }
}
