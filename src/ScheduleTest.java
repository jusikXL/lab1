import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


public class ScheduleTest {

    private static final int SIZE = 5; // Number of hours in the schedule
    private static final int REQUIRED_NUMBER = 10; // Employees required per hour
    private WorkSchedule schedule; // Schedule instance for testing

    @Test
    public void testFullyCompleteSchedule() {
        schedule = new WorkSchedule(SIZE);

        schedule.setRequiredNumber(REQUIRED_NUMBER, 0, SIZE - 1);
        for (int i = 0; i < REQUIRED_NUMBER; i++) {
            schedule.addWorkingPeriod(String.valueOf(i), 0, SIZE - 1);
        }

        ///////////////////////////////////////////////////////////////

        assertEquals(-1, schedule.nextIncomplete(0));
        assertEquals(-1, schedule.nextIncomplete(SIZE / 2)); // Check middle of range
        assertEquals(-1, schedule.nextIncomplete(SIZE - 1)); // Check last hour

        // Schedule was not changed
        for (int i = 0; i < SIZE; i++) {
            WorkSchedule.Hour hour = schedule.readSchedule(i);
            assertEquals(REQUIRED_NUMBER, hour.requiredNumber);
            assertEquals(REQUIRED_NUMBER, hour.workingEmployees.length);
        }
    }

    @Test
    public void testFullyIncompleteSchedule() {
        schedule = new WorkSchedule(SIZE);

        schedule.setRequiredNumber(REQUIRED_NUMBER, 0, SIZE - 1);

        for (int i = 0; i < SIZE; i++) {
            WorkSchedule.Hour hour = schedule.readSchedule(i);
            System.out.println(i + " " + hour.requiredNumber + " " + hour.workingEmployees.length);
        }

        ///////////////////////////////////////////////////////////////

        assertEquals(-1, schedule.nextIncomplete(0));
        assertEquals(-1, schedule.nextIncomplete(SIZE / 2)); // Check middle of range
        assertEquals(-1, schedule.nextIncomplete(SIZE - 1)); // Check last hour

        // Schedule was not changed
        for (int i = 0; i < SIZE; i++) {
            WorkSchedule.Hour hour = schedule.readSchedule(i);
            assertEquals(0, hour.requiredNumber);
            assertEquals(0, hour.workingEmployees.length);
        }
    }

    static Stream<Integer> emptyHourProvider() {
        return Stream.of(0, SIZE/2, SIZE - 1);
      }


    @ParameterizedTest
    @MethodSource("emptyHourProvider")
    public void testSingleIncompleteHour(Integer emptyHour) {
        schedule = new WorkSchedule(SIZE);

        schedule.setRequiredNumber(REQUIRED_NUMBER, 0, SIZE - 1);
        for (int i = 0; i < REQUIRED_NUMBER; i++) {
            String employee = String.valueOf(i);
            schedule.addWorkingPeriod(employee, 0, emptyHour - 1);
            schedule.addWorkingPeriod(employee, emptyHour + 1, SIZE - 1);
        }

        ///////////////////////////////////////////////////////////////

        for (int i = 0; i < SIZE; i++) {
            if (i <= emptyHour) {
                assertEquals(emptyHour.intValue(), schedule.nextIncomplete(i));
            } else {
                assertEquals(-1, schedule.nextIncomplete(i));
            }
        }

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

    
}
