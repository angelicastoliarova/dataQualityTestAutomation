package dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class Book {
    private String totalWordCount;
    private String sentenceCount;
    private String characterCount;
    private String paragraphCount;
    private String whitespaceCount;
}
