import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.IntBinaryOperator;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class DistinctClosedTest {
  Set set;

  IntBinaryOperator add;
  IntBinaryOperator substract;

  @BeforeEach
  public void setUp() {
    set = new Set();

    add = (a, b) -> a + b;
    substract = (a, b) -> a - b;
  }

  static Stream<IntBinaryOperator> operationsProvider() {
    return Stream.of(
        (a, b) -> a + b,
        (a, b) -> a - b);
  }

  @ParameterizedTest
  @MethodSource("operationsProvider")
  public void testDistinctClosed(IntBinaryOperator operation) {
    // don't go into for loops
    assertTrue(set.distinctClosed(add)); // statement 1, 5

    // go into and out of only the i for loop
    set.insert(0);
    assertTrue(set.distinctClosed(add)); // statement 1, 5

    // go into and out of the i and j for loops
    set.insert(1);
    assertTrue(set.distinctClosed(add)); // statement 1, 2, 3, 5

    // go into the if
    set.insert(2);
    assertFalse(set.distinctClosed(add)); // statement 1, 2, 3, 4
  }
}