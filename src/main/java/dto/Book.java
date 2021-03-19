package dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class Book {
    private int bookId;
    private int totalWordCount;
    private int sentenceCount;
    private int characterCount;
    private int paragraphCount;
    private int whitespaceCount;
}
