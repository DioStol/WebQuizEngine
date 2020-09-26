package engine.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import engine.model.Completion;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dionysios Stolis 9/26/2020 <dstolis@gmail.com>
 */
public class ListSerializer implements AttributeConverter<List<Completion>, String> {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(List<Completion> list) {
        try {
            return objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    @Override
    public List<Completion> convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, List.class);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
