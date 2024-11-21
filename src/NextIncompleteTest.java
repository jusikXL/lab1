import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

@DisplayName("Next Incomplete")
public class NextIncompleteTest {
    private static final int SIZE = 5; // Number of hours in the schedule
    private static final int REQUIRED_NUMBER = 10; // Employees required per hour
    private WorkSchedule schedule; // Schedule instance for testing

    @ParameterizedTest(name = "Fully Complete: -1 is next incomplete for {0}.")
    @ValueSource(ints = { 0, SIZE / 2, SIZE - 1 })
    public void testFullyCompleteSchedule(int currentTime) {
        schedule = new WorkSchedule(SIZE);  

        schedule.setRequiredNumber(REQUIRED_NUMBER, 0, SIZE - 1);
        for (int i = 0; i < REQUIRED_NUMBER; i++) {
            schedule.addWorkingPeriod(String.valueOf(i), 0, SIZE - 1);
        }

        ///////////////////////////////////////////////////////////////

        assertEquals(-1, schedule.nextIncomplete(currentTime)); 

        // Schedule was not changed
        for (int i = 0; i < SIZE; i++) {
            WorkSchedule.Hour hour = schedule.readSchedule(i);
            assertEquals(REQUIRED_NUMBER, hour.requiredNumber);
            assertEquals(REQUIRED_NUMBER, hour.workingEmployees.length);
        }
    }

    @ParameterizedTest(name = "Fully Incomplete: {0} is next incomplete for {0}.")
    @ValueSource(ints = { 0, SIZE / 2, SIZE - 1 })
    public void testFullyIncompleteSchedule(int currentTime) {
        schedule = new WorkSchedule(SIZE);

        schedule.setRequiredNumber(REQUIRED_NUMBER, 0, SIZE - 1);

        for (int i = 0; i < SIZE; i++) {
            WorkSchedule.Hour hour = schedule.readSchedule(i);
            System.out.println(i + " " + hour.requiredNumber + " " + hour.workingEmployees.length);
        }

        ///////////////////////////////////////////////////////////////

        assertEquals(currentTime, schedule.nextIncomplete(currentTime)); 
        
        // Schedule was not changed
        for (int i = 0; i < SIZE; i++) {
            WorkSchedule.Hour hour = schedule.readSchedule(i);
            assertEquals(10, hour.requiredNumber);
            assertEquals(0, hour.workingEmployees.length);
        }
    }


    @ParameterizedTest(name = "Single {0} Incomplete: {2} is next incomplete for {1}.")
    @MethodSource
    public void testSingleIncompleteHour(int emptyHour, int currentTime, int expectedNextIncomplete) {
        schedule = new WorkSchedule(SIZE);

        schedule.setRequiredNumber(REQUIRED_NUMBER, 0, SIZE - 1);
        for (int i = 0; i < REQUIRED_NUMBER; i++) {
            String employee = String.valueOf(i);
            schedule.addWorkingPeriod(employee, 0, emptyHour - 1);
            schedule.addWorkingPeriod(employee, emptyHour + 1, SIZE - 1);
        }

        ///////////////////////////////////////////////////////////////

        assertEquals(expectedNextIncomplete, schedule.nextIncomplete(currentTime)); 

        // Schedule was not changed
        for (int i = 0; i < SIZE; i++) {
            WorkSchedule.Hour hour = schedule.readSchedule(i);
            if (i == emptyHour) {
                assertEquals(REQUIRED_NUMBER, hour.requiredNumber);
                assertEquals(0, hour.workingEmployees.length);
            } else {
                assertEquals(REQUIRED_NUMBER, hour.requiredNumber);
                assertEquals(REQUIRED_NUMBER, hour.workingEmployees.length);
            }
        }
    }

    private static int expectedNextIncomplete(int emptyHour, int currentTime) {
        return currentTime <= emptyHour ? emptyHour : -1;
    }

    private static Stream<Arguments> testSingleIncompleteHour() {
        return Stream.of(
            // emptyHour, currentTime, expectedNextIncomplete
            
            Arguments.of(0, 0, expectedNextIncomplete(0, 0)),
            Arguments.of(0, SIZE / 2, expectedNextIncomplete(0, SIZE / 2)),
            Arguments.of(0, SIZE - 1, expectedNextIncomplete(0, SIZE - 1)),

            Arguments.of(SIZE / 2, 0, expectedNextIncomplete(SIZE / 2, 0)),
            Arguments.of(SIZE / 2, SIZE / 2 - 1, expectedNextIncomplete(SIZE / 2, SIZE / 2 - 1)),
            Arguments.of(SIZE / 2,  SIZE - 1, expectedNextIncomplete(SIZE / 2,  SIZE - 1)),

            Arguments.of(SIZE - 1, 0, expectedNextIncomplete(SIZE - 1, 0)),
            Arguments.of(SIZE - 1, SIZE / 2, expectedNextIncomplete(SIZE - 1, SIZE / 2)),
            Arguments.of(SIZE - 1, SIZE - 1, expectedNextIncomplete(SIZE - 1, SIZE - 1))
        );
    }
    
}
