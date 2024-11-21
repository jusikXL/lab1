import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;


class SetRequiredNumber {
    static final int SIZE = 8; // Number of hours in the schedule
    static final int REQUIRED_NUMBER = 10; // Employees required per hour
    static WorkSchedule schedule; // Schedule instance for testing

    @BeforeEach
    void setUp() {
        schedule = new WorkSchedule(SIZE);  
        
        schedule.setRequiredNumber(REQUIRED_NUMBER, 0, SIZE - 1);
        for (int i = 0; i < REQUIRED_NUMBER; i++) {
            schedule.addWorkingPeriod(String.valueOf(i), 0, SIZE - 1);
        }
    }

    @ParameterizedTest(name = "Setting from {0} to {1} with {2} employees.")
    @MethodSource("provider")
    void test(int startTime, int endTime, int nemployee) {
        try {
            schedule.setRequiredNumber(nemployee, startTime, endTime); // should do nothing
        } catch (Exception e) {}

        for (int i = 0; i < SIZE; i++) {
            WorkSchedule.Hour hour = schedule.readSchedule(i);

            if(startTime > endTime || startTime >= SIZE || endTime >= SIZE) {
                assertEquals(REQUIRED_NUMBER, hour.requiredNumber);
                assertEquals(REQUIRED_NUMBER, hour.workingEmployees.length);
                // unchanged
            } else if(i >= startTime && i <= endTime) {
                // changed
                assertEquals(nemployee, hour.requiredNumber);
                assertEquals(nemployee > REQUIRED_NUMBER ? REQUIRED_NUMBER : nemployee, hour.workingEmployees.length);
            } else {
                // rest unchanged
                assertEquals(REQUIRED_NUMBER, hour.requiredNumber);
                assertEquals(REQUIRED_NUMBER, hour.workingEmployees.length);
            }
        }
    }

    // TODO: refactor with https://junit.org/junit5/docs/current/user-guide/
    static Stream<Arguments> provider() {
        return Stream.of(
            // startTime, endTime, nemployee
            Arguments.of(SIZE - 1, SIZE / 2, REQUIRED_NUMBER), // start > end
            Arguments.of(SIZE, SIZE / 2, REQUIRED_NUMBER), // start >= size
            Arguments.of(SIZE / 2, SIZE, REQUIRED_NUMBER), // end >= size

            Arguments.of(1, SIZE - 2, REQUIRED_NUMBER), // inside
            Arguments.of(0, SIZE / 2, REQUIRED_NUMBER), // start to middle
            Arguments.of(SIZE / 2, SIZE - 1, REQUIRED_NUMBER), // middle to end
            Arguments.of(0, SIZE - 1, REQUIRED_NUMBER), // start to end

            Arguments.of(SIZE / 2, SIZE / 2, REQUIRED_NUMBER), // start = end

            //

            Arguments.of(SIZE - 1, SIZE / 2, REQUIRED_NUMBER / 2), // start > end
            Arguments.of(SIZE, SIZE / 2, REQUIRED_NUMBER / 2), // start >= size
            Arguments.of(SIZE / 2, SIZE, REQUIRED_NUMBER / 2), // end >= size

            Arguments.of(1, SIZE - 2, REQUIRED_NUMBER / 2), // inside
            Arguments.of(0, SIZE / 2, REQUIRED_NUMBER / 2), // start to middle
            Arguments.of(SIZE / 2, SIZE - 1, REQUIRED_NUMBER / 2), // middle to end
            Arguments.of(0, SIZE - 1, REQUIRED_NUMBER / 2), // start to end

            Arguments.of(SIZE / 2, SIZE / 2, REQUIRED_NUMBER / 2), // start = end

            //

            Arguments.of(SIZE - 1, SIZE / 2, REQUIRED_NUMBER * 2), // start > end
            Arguments.of(SIZE, SIZE / 2, REQUIRED_NUMBER * 2), // start >= size
            Arguments.of(SIZE / 2, SIZE, REQUIRED_NUMBER * 2), // end >= size

            Arguments.of(1, SIZE - 2, REQUIRED_NUMBER * 2), // inside
            Arguments.of(0, SIZE / 2, REQUIRED_NUMBER * 2), // start to middle
            Arguments.of(SIZE / 2, SIZE - 1, REQUIRED_NUMBER * 2), // middle to end
            Arguments.of(0, SIZE - 1, REQUIRED_NUMBER * 2), // start to end

            Arguments.of(SIZE / 2, SIZE / 2, REQUIRED_NUMBER * 2) // start = end
        );
    }
}
