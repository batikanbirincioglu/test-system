package org.batikan.test.system.controller;

import lombok.RequiredArgsConstructor;
import org.batikan.test.system.dto.TestResultDto;
import org.batikan.test.system.service.SolveTestService;
import org.batikan.test.system.dto.SolveTestDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SolveTestController {
    private final SolveTestService solveTestService;

    @PostMapping("/solve-test")
    public void solveTest(@RequestBody SolveTestDto solveTestDto) {
	solveTestService.solveTest(solveTestDto);
    }

    @GetMapping("/test-result/{studentId}/{testId}")
    public TestResultDto getTestResult(@PathVariable Long studentId, @PathVariable Long testId) {
	return solveTestService.getTestResult(studentId, testId);
    }
}
