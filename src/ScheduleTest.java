import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.util.Arrays;


public class ScheduleTest {

    private static final int SIZE = 5; // Number of hours in the schedule
    private static final int REQUIRED_NUMBER = 10; // Employees required per hour
    private WorkSchedule schedule; // Schedule instance for testing

    @Test
    public void testCompleteSchedule() {
        schedule = new WorkSchedule(SIZE);

        schedule.setRequiredNumber(REQUIRED_NUMBER, 0, SIZE - 1);
        for (int i = 0; i < REQUIRED_NUMBER; i++) {
            schedule.addWorkingPeriod(String.valueOf(i), 0, SIZE - 1);
        }

        // assert set required
        for (int i = 0; i < SIZE; i++) {
            WorkSchedule.Hour hour = schedule.readSchedule(i);
            assertEquals(REQUIRED_NUMBER, hour.requiredNumber); 
            assertEquals(REQUIRED_NUMBER, hour.workingEmployees.length); 
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
    public void testSingleIncompleteHour() {
        Set set1 = new Set();
        set1.insert(1);
        set1.insert(2);
        set1.insert(5);
        System.out.println(Arrays.toString(set1.toArray()));

        final int EMPTY_HOUR = SIZE/2;
       

        System.out.println("START");
        System.out.println(EMPTY_HOUR);
        schedule = new WorkSchedule(SIZE);
        
        schedule.setRequiredNumber(REQUIRED_NUMBER, 0, SIZE - 1);
        for (int i = 0; i < REQUIRED_NUMBER; i++) {
            if(i != EMPTY_HOUR){
                schedule.addWorkingPeriod(String.valueOf(i), 0, SIZE - 1);
            }
        }

        // assert set required
        for (int i = 0; i < SIZE; i++) {
            WorkSchedule.Hour hour = schedule.readSchedule(i);

            System.out.println("HELLO");

            if(i != EMPTY_HOUR){
                
                assertEquals(REQUIRED_NUMBER, hour.requiredNumber); 
                assertEquals(REQUIRED_NUMBER, hour.workingEmployees.length); 
            } else {
                assertEquals(0, hour.requiredNumber); 
                assertEquals(0, hour.workingEmployees.length); 
            }
        }

        ///////////////////////////////////////////////////////////////

        assertEquals(2, schedule.nextIncomplete(0)); // Closest incomplete from start
        assertEquals(2, schedule.nextIncomplete(1)); // Closest incomplete after hour 1
        assertEquals(2, schedule.nextIncomplete(2)); // Closest incomplete at hour 2
        assertEquals(-1, schedule.nextIncomplete(3)); // No incomplete after hour 2

        // Ensure the schedule was not changed
        for (int i = 0; i < SIZE; i++) {
            WorkSchedule.Hour hour = schedule.readSchedule(i);
            if (i == 2) {
                assertEquals(REQUIRED_NUMBER, hour.requiredNumber);
                assertEquals(REQUIRED_NUMBER - 1, hour.workingEmployees.length);
            } else {
                assertEquals(REQUIRED_NUMBER, hour.requiredNumber);
                assertEquals(REQUIRED_NUMBER, hour.workingEmployees.length);
            }
        }
    }

}
