package org.batikan.test.system.mapper;

import org.batikan.test.system.dto.QuestionDto;
import org.batikan.test.system.entity.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    public Question toEntity(QuestionDto dto);

    public QuestionDto toDto(Question entity);
}
