import dto.Book;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Monitor {
    protected static final Logger LOGGER = LogManager.getLogger(Monitor.class);

    private static void findFile(String fileNamePrefix) throws IOException {
        Path currentDir = Paths.get("C:\\task4\\input");
        Path target = Paths.get("C:\\task4\\incorrect_input");
        Files.walk(currentDir).forEach(child -> {
            {
                try {
                    Files.walk(currentDir)
                            .forEach(source -> {
                                try {
                                    if (source.getFileName().toString().endsWith(fileNamePrefix)) {
                                        System.out.println(source.getFileName().toString());
                                        readFile(source.toString());
                                    } else
                                        Files.move(source, target.resolve(currentDir.relativize(source)));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void readFile(String fileName) throws IOException {

        File file = new File(fileName);
        FileInputStream fileStream = new FileInputStream(file);
        InputStreamReader input = new InputStreamReader(fileStream);
        BufferedReader reader = new BufferedReader(input);

        int countWord = 0;
        int sentenceCount = 0;
        int characterCount = 0;
        int paragraphCount = 1;
        int whitespaceCount = 0;

        while (true) {
            String line = reader.readLine();
            if (line == null) break;
            if (line.equals("")) {
                paragraphCount++;
            } else {
                characterCount += line.length();

                // \\s+ is the space delimiter in java
                String[] wordList = line.split("\\s+");

                countWord += wordList.length;
                whitespaceCount += countWord - 1;

                // [!?.:]+ is the sentence delimiter in java
                String[] sentenceList = line.split("[!?.:]+");

                sentenceCount += sentenceList.length;
            }
        }
        Book book = new Book()
                .setTotalWordCount(countWord)
                .setSentenceCount(sentenceCount)
                .setCharacterCount(characterCount)
                .setParagraphCount(paragraphCount)
                .setWhitespaceCount(whitespaceCount);
        services.DbService.addData(book, 1);

        LOGGER.info("Total word count = " + countWord);
        LOGGER.info("Total number of sentences = " + sentenceCount);
        LOGGER.info("Total number of characters = " + characterCount);
        LOGGER.info("Number of paragraphs = " + paragraphCount);
        LOGGER.info("Total number of whitespaces = " + whitespaceCount);

    }

    public static void main(String[] args) throws IOException {
        findFile(".fb2");
    }
}
