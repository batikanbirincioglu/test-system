package org.batikan.test.system.mapper;

import org.batikan.test.system.dto.StudentDto;
import org.batikan.test.system.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    public Student toEntity(StudentDto dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "dto.name")
    public Student toEntity(Long id, StudentDto dto);

    public StudentDto toDto(Student entity);
}
