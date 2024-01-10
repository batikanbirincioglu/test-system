package org.batikan.test.system.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.batikan.test.system.Constants;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Converter
public class ChoicesConverter implements AttributeConverter<Map<Character, String>, String> {

    @Override
    public String convertToDatabaseColumn(
	    Map<Character, String> map) {
	return map.entrySet()
		.stream()
		.map(entry -> String.format("%s%s%s",
			entry.getKey(),
			Constants.CHOICES_DELIMITER_1, entry.getValue()))
		.collect(Collectors.joining(Constants.CHOICES_DELIMITER_2));
    }

    @Override
    public Map<Character, String> convertToEntityAttribute(String s) {
	return Arrays
		.stream(s.split(Pattern.quote(Constants.CHOICES_DELIMITER_2)))
		.map(choiceAndContent -> {
		    String[] choiceAndContentArray = choiceAndContent.split(Pattern.quote(Constants.CHOICES_DELIMITER_1));
		    return new AbstractMap.SimpleEntry<>(choiceAndContentArray[0].toCharArray()[0], choiceAndContentArray[1]);
		}).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }
}
