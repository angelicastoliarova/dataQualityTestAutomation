import org.testng.annotations.BeforeClass;

public class TestBase {
    @BeforeClass(alwaysRun = true)
    public void start() {
        System.out.println("BEFORE BASE CLASS EXECUTED");
    }
}
