import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InsertTest {
  Set set;

  @BeforeEach
  public void setUp() {
    set = new Set();
  }

  @Test
  public void testInsertIntoEmptySet() {
    set.insert(5); // statement 4
    assertArrayEquals(new int[] { 5 }, set.toArray());
  }

  @Test
  public void testInsertSmallerElement() {
    set.insert(10); // statement 4
    set.insert(5); // statement 1,2
    assertArrayEquals(new int[] { 5, 10 }, set.toArray());
  }

  @Test
  public void testInsertLargerElement() {
    set.insert(5); // statement 4
    set.insert(10); // statement 4
    assertArrayEquals(new int[] { 5, 10 }, set.toArray());
  }

  @Test
  public void testInsertDuplicateElement() {
    set.insert(5); // statement 4
    set.insert(5); // statement 3
    assertArrayEquals(new int[] { 5 }, set.toArray());
  }
}
