import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.function.IntBinaryOperator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

  @Test
  public void testDistinctClosedAdd() {
    // don't go into for loops
    assertTrue(set.distinctClosed(add)); // statement 1, 5

    // go into and out of only the i for loop
    set.insert(0);
    assertTrue(set.distinctClosed(add)); // statement 1, 5

    // go into and out of only the i and j for loop
    set.insert(1);
    assertTrue(set.distinctClosed(add)); // statement 1, 2, 3, 5

    // go into i and j for loops and if
    set.insert(2);
    assertFalse(set.distinctClosed(add)); // statement 1, 2, 3, 4
  }

  @Test
  public void testDistinctClosedSubstract() {
    // don't go into for loops
    assertTrue(set.distinctClosed(substract)); // statement 1, 5

    // go into and out of only the i for loop
    set.insert(0);
    assertTrue(set.distinctClosed(substract)); // statement 1, 5

    // go into and out of only the i and j for loop
    set.insert(1);
    set.insert(-1);
    assertTrue(set.distinctClosed(add)); // statement 1, 2, 3, 5

    // go into i and j for loops and if
    set.insert(2);
    assertFalse(set.distinctClosed(add)); // statement 1, 2, 3, 4
  }
}