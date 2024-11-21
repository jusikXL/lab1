import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;

class NextIncompleteTest {
    static final int SIZE = 8; // Number of hours in the schedule
    static final int REQUIRED_NUMBER = 10; // Employees required per hour
    static WorkSchedule schedule; // Schedule instance for testing

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class FullyComplete {
        @BeforeAll
        void setUp() {
            schedule = new WorkSchedule(SIZE);  
            schedule.setRequiredNumber(REQUIRED_NUMBER, 0, SIZE - 1);
        }

        @BeforeEach
        void setUpSchedule() {
            for (int i = 0; i < REQUIRED_NUMBER; i++) {
                schedule.addWorkingPeriod(String.valueOf(i), 0, SIZE - 1);
            }
        }

        @ParameterizedTest(name = "Fully Complete: -1 is next incomplete for {0}.")
        @ValueSource(ints = { 0, SIZE / 2, SIZE - 1 })
        void test(int currentTime) {
            assertEquals(-1, schedule.nextIncomplete(currentTime));             
        }

        @AfterEach
        void assertScheduleUnchanged() {
            for (int i = 0; i < SIZE; i++) {
                WorkSchedule.Hour hour = schedule.readSchedule(i);
                assertEquals(REQUIRED_NUMBER, hour.requiredNumber);
                assertEquals(REQUIRED_NUMBER, hour.workingEmployees.length);
            }
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class FullyIncomplete {
        @BeforeAll
        void setUp() {
            schedule = new WorkSchedule(SIZE);  
            schedule.setRequiredNumber(REQUIRED_NUMBER, 0, SIZE - 1);
        }

        @ParameterizedTest(name = "Fully Incomplete: {0} is next incomplete for {0}.")
        @ValueSource(ints = { 0, SIZE / 2, SIZE - 1 })
        public void test(int currentTime) {
            assertEquals(currentTime, schedule.nextIncomplete(currentTime));
        }

        @AfterEach
        void assertScheduleUnchanged() {
            for (int i = 0; i < SIZE; i++) {
                WorkSchedule.Hour hour = schedule.readSchedule(i);
                assertEquals(10, hour.requiredNumber);
                assertEquals(0, hour.workingEmployees.length);
            }
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class SingleIncomplete {
        @BeforeEach
        void setUp() {
            schedule = new WorkSchedule(SIZE);  
            schedule.setRequiredNumber(REQUIRED_NUMBER, 0, SIZE - 1);
        }

        @ParameterizedTest(name = "Single {0} Incomplete: assert next incomplete for {1}.")
        @MethodSource
        public void test(int emptyHour, int currentTime) {
            for (int i = 0; i < REQUIRED_NUMBER; i++) {
                String employee = String.valueOf(i);
                schedule.addWorkingPeriod(employee, 0, emptyHour - 1);
                schedule.addWorkingPeriod(employee, emptyHour + 1, SIZE - 1);
            }

            ///////////////////////////////////////////////////////////////

            assertEquals(currentTime <= emptyHour ? emptyHour : -1, schedule.nextIncomplete(currentTime)); 

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

        Stream<Arguments> test() {
            return Stream.of(
                // emptyHour, currentTime
                
                Arguments.of(0, 0),
                Arguments.of(0, SIZE / 2),
                Arguments.of(0, SIZE - 1),

                Arguments.of(SIZE / 2, 0),
                Arguments.of(SIZE / 2, SIZE / 2 - 1),
                Arguments.of(SIZE / 2, SIZE / 2 + 1),
                Arguments.of(SIZE / 2, SIZE / 2),
                Arguments.of(SIZE / 2,  SIZE - 1),

                Arguments.of(SIZE - 1, 0),
                Arguments.of(SIZE - 1, SIZE / 2),
                Arguments.of(SIZE - 1, SIZE - 1)
            );
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class TwoIncomplete {
        @BeforeEach
        void setUp() {
            schedule = new WorkSchedule(SIZE);  
            schedule.setRequiredNumber(REQUIRED_NUMBER, 0, SIZE - 1);
        }

        @ParameterizedTest(name = "Two {0}, {1} Incomplete: assert next incomplete for {2}.")
        @MethodSource
        public void test(int firstEmptyHour, int secondEmptyHour, int currentTime) {
            schedule = new WorkSchedule(SIZE);
    
            schedule.setRequiredNumber(REQUIRED_NUMBER, 0, SIZE - 1);
    
            for (int i = 0; i < REQUIRED_NUMBER; i++) {
                String employee = String.valueOf(i);
                schedule.addWorkingPeriod(employee, 0, firstEmptyHour - 1);
                schedule.addWorkingPeriod(employee, firstEmptyHour + 1, secondEmptyHour - 1); // verify
                schedule.addWorkingPeriod(employee, secondEmptyHour + 1, SIZE - 1);
            }
    
            ///////////////////////////////////////////////////////////////
         
            assertEquals(nextIncomplete(firstEmptyHour, secondEmptyHour, currentTime), schedule.nextIncomplete(currentTime)); 
    
            // Schedule should be unchanged
            for (int i = 0; i < SIZE; i++) {
                WorkSchedule.Hour hour = schedule.readSchedule(i);
                if (i == firstEmptyHour || i == secondEmptyHour) {
                    assertEquals(REQUIRED_NUMBER, hour.requiredNumber);
                    assertEquals(0, hour.workingEmployees.length);
                } else {
                    assertEquals(REQUIRED_NUMBER, hour.requiredNumber);
                    assertEquals(REQUIRED_NUMBER, hour.workingEmployees.length);
                }
            }
        }
    
        Stream<Arguments> test() {
            return Stream.of(
                // firstEmptyHour, secondEmptyHour, currentTime
                
                // start and end
                Arguments.of(0, SIZE - 1, 0),       
                Arguments.of(0, SIZE - 1, SIZE / 2),
                Arguments.of(0, SIZE - 1, SIZE - 1),
    
                // middle and end
                Arguments.of(SIZE / 2, SIZE - 1, 0), 
                Arguments.of(SIZE / 2, SIZE - 1, SIZE / 2 - 1),
                Arguments.of(SIZE / 2, SIZE - 1, SIZE / 2),
                Arguments.of(SIZE / 2, SIZE - 1, SIZE / 2 + 1),
                Arguments.of(SIZE / 2, SIZE - 1, SIZE - 1),
    
                // start and middle
                Arguments.of(0, SIZE / 2, 0),       
                Arguments.of(0, SIZE / 2, SIZE / 2 - 1),
                Arguments.of(0, SIZE / 2, SIZE / 2),
                Arguments.of(0, SIZE / 2, SIZE / 2 + 1),
                Arguments.of(0, SIZE / 2, SIZE - 1),
    
    
                // two together in the middle
                Arguments.of(SIZE / 2 - 1, SIZE / 2, 0),       
                Arguments.of(SIZE / 2 - 1, SIZE / 2, SIZE / 2 - 2),
                Arguments.of(SIZE / 2 - 1, SIZE / 2, SIZE / 2 - 1),
                Arguments.of(SIZE / 2 - 1, SIZE / 2, SIZE / 2),
                Arguments.of(SIZE / 2 - 1, SIZE / 2, SIZE / 2 + 1),
                Arguments.of(SIZE / 2 - 1, SIZE / 2, SIZE - 1)
            );
        }

        int nextIncomplete(int firstEmptyHour, int secondEmptyHour, int currentTime) {
            if(currentTime <= firstEmptyHour) {
                return firstEmptyHour;
            } else if (currentTime <= secondEmptyHour) {
                return secondEmptyHour;
            } else {
                return -1;
            }
        }
    }    
}
