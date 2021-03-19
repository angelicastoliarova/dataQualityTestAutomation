package services;

import dto.Book;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class MonitorFolderService {
    protected static final Logger LOGGER = LogManager.getLogger(MonitorFolderService.class);

    public static void monitorSourseDirForFile(String fileNamePrefix) throws IOException {
        Properties property = new Properties();
        FileInputStream fileProperty = new FileInputStream("src/main/resources/test.properties");
        property.load(fileProperty);
        Path sourceDir = Paths.get(property.getProperty("sourceDir"));
        Path dirForOtherFiles = Paths.get(property.getProperty("targetDir"));
        Files.walk(sourceDir).forEach(child -> {
            {
                try {
                    Files.walk(sourceDir)
                            .forEach(source -> {
                                try {
                                    if (source.getFileName().toString().endsWith(fileNamePrefix)) {
                                        System.out.println(source.getFileName().toString());
                                        parseFileWriteDataToDb(source.toString());
                                        LOGGER.info("Read file with prefix " + fileNamePrefix);
                                    } else
                                        Files.move(source, dirForOtherFiles.resolve(sourceDir.relativize(source)));
                                    LOGGER.info("Files except prefix " + fileNamePrefix+"mooved to"+dirForOtherFiles);
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

    protected static void parseFileWriteDataToDb(String fileName) throws IOException {

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

                String[] wordList = line.split("\\s+");

                countWord += wordList.length;
                whitespaceCount += countWord - 1;

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
        DbService.addData(book);

        LOGGER.info("Total word count = " + countWord);
        LOGGER.info("Total number of sentences = " + sentenceCount);
        LOGGER.info("Total number of characters = " + characterCount);
        LOGGER.info("Number of paragraphs = " + paragraphCount);
        LOGGER.info("Total number of whitespaces = " + whitespaceCount);

    }

    protected static void main(String[] args) throws IOException {
        monitorSourseDirForFile(".fb2");
    }
}
