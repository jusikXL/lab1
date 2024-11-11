import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.IntBinaryOperator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DistinctClosedTest {
  Set set1;
  Set set2;

  IntBinaryOperator add;

  @BeforeEach
  public void setUp() {
    set1 = new Set();
    set2 = new Set();
    add = (a, b) -> a + b;
  }

  @Test
  public void testFalseBasic() {
    set1.insert(1);
    set1.insert(2);
    set1.insert(3);

    boolean result = set1.distinctClosed(add);

    assertFalse(result);
  }

  @Test
  public void testTrueBasic() {
    set1.insert(0);
    set1.insert(0);
    set1.insert(0);

    boolean result = set1.distinctClosed(add);

    assertTrue(result);
  }

  @Test
  public void emptySetTest() {
    boolean result = set1.distinctClosed(add);

    assertTrue(result);
  }

}