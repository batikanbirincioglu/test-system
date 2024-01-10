package org.batikan.test.system.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.batikan.test.system.Constants;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Converter
public class StudentChoicesConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> strings) {
	return strings.stream().collect(Collectors.joining(Constants.COMMA));
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
	return Arrays.stream(s.split(Pattern.quote(Constants.COMMA))).toList();
    }
}
