import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import services.MonitorFolderService;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbTest extends TestBase {
    private static final String FILE = ".fb2";

    @BeforeClass(alwaysRun = true)
    public void start() {
        System.out.println("BEFORE CLASS EXECUTED");
        super.start();
        try {
            MonitorFolderService.monitorSourseDirForFile(FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterClass(alwaysRun = true)
    public void finish() {
        System.out.println("AFTER BASIC CLASS EXECUTED");
    }

    @AfterTest(alwaysRun = true)
    public void end() {
        System.out.println("AFTER ALL TEST END EXECUTED");
    }

    @Test
    public void verifyTableIsNotEmpty() {
        int bookId = 1;
        ResultSet resultSet = DbSelectService.selectFromDb("", bookId);
        Assert.assertNotNull(resultSet);
    }

    @Test
    public void verifyTotalWordCount() throws SQLException {
        int bookId = 1;
        int returnValue=0;
        String totalWordCount = "total_word_count";
        ResultSet resultSet = DbSelectService.selectFromDb(totalWordCount, bookId);
        while(resultSet.next()){
            returnValue = resultSet.getInt(1);
        }
        Assert.assertNotNull(returnValue);
    }
    @Test
    public void verifySentenceCount() throws SQLException {
        int bookId = 1;
        int returnValue=0;
        String totalWordCount = "sentence_count";
        ResultSet resultSet = DbSelectService.selectFromDb(totalWordCount, bookId);
        while(resultSet.next()){
            returnValue = resultSet.getInt(1);
        }
        Assert.assertNotNull(returnValue);
    }
    @Test
    public void verifywhitespaceCountCount() throws SQLException {
        int bookId = 1;
        int returnValue=0;
        String totalWordCount = "whitespace_count";
        ResultSet resultSet = DbSelectService.selectFromDb(totalWordCount, bookId);
        while(resultSet.next()){
            returnValue = resultSet.getInt(1);
        }
        Assert.assertNotNull(returnValue);
    }
}
