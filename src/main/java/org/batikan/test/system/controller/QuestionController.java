package org.batikan.test.system.controller;

import lombok.RequiredArgsConstructor;
import org.batikan.test.system.dto.QuestionDto;
import org.batikan.test.system.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/all")
    public List<QuestionDto> getAll() {
	return questionService.getAll();
    }

    @GetMapping("/{id}")
    public QuestionDto getById(@PathVariable Long id) {
	return questionService.getById(id);
    }

    @PostMapping
    public QuestionDto create(@RequestBody QuestionDto dto) {
	return questionService.create(dto);
    }

    @PutMapping("/{id}")
    public QuestionDto update(@PathVariable Long id, @RequestBody QuestionDto dto) {
	return questionService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
	questionService.delete(id);
    }
}
