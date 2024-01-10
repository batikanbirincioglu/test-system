package org.batikan.test.system.controller;

import lombok.RequiredArgsConstructor;
import org.batikan.test.system.service.TestService;
import org.batikan.test.system.dto.CreateOrUpdateTestDto;
import org.batikan.test.system.dto.TestDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tests")
public class TestController {
    private final TestService testService;

    @GetMapping("/all")
    public List<TestDto> getAll() {
	return testService.getAll();
    }

    @GetMapping("/{id}")
    public TestDto getById(@PathVariable Long id) {
	return testService.getById(id);
    }

    @PostMapping
    public TestDto create(@RequestBody CreateOrUpdateTestDto dto) {
	return testService.create(dto);
    }

    @PutMapping("/{id}")
    public TestDto update(@PathVariable Long id, @RequestBody CreateOrUpdateTestDto dto) {
	return testService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
	testService.delete(id);
    }
}
