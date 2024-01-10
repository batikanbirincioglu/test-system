package org.batikan.test.system.service;

import lombok.RequiredArgsConstructor;
import org.batikan.test.system.entity.Student;
import org.batikan.test.system.exception.BusinessError;
import org.batikan.test.system.exception.BusinessException;
import org.batikan.test.system.mapper.StudentMapper;
import org.batikan.test.system.dto.StudentDto;
import org.batikan.test.system.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentMapper studentMapper;

    private final StudentRepository studentRepository;

    @Transactional(readOnly = true)
    public List<StudentDto> getAll() {
	return studentRepository.findAll()
		.stream()
		.map(studentMapper::toDto)
		.toList();
    }

    @Transactional(readOnly = true)
    public StudentDto getById(Long id) {
	Student student = studentRepository.findById(id).get();
	return studentMapper.toDto(student);
    }

    @Transactional
    public StudentDto create(StudentDto dto) {
	Student student = studentMapper.toEntity(dto);
	student = studentRepository.save(student);
	return studentMapper.toDto(student);
    }

    @Transactional
    public StudentDto update(Long id, StudentDto dto) {
	Student student = studentRepository.findById(id)
		.orElseThrow(() -> new BusinessException(BusinessError.STUDENT_NOT_FOUND));

	student.setName(dto.getName());
	student.setSurname(dto.getSurname());
	student = studentRepository.save(student);
	return studentMapper.toDto(student);
    }

    @Transactional
    public void delete(Long id) {
	studentRepository.deleteById(id);
    }
}
