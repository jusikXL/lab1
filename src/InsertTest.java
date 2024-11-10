import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class InsertTest {
  @Test
  void testInsert() {
    Set s = new Set();

    s.insert(10); // statement 4
    assertArrayEquals(new int[] { 10 }, s.toArray());

    s.insert(5); // statement 1, 2
    assertArrayEquals(new int[] { 5, 10 }, s.toArray());

    s.insert(5); // statement 3
    assertArrayEquals(new int[] { 5, 10 }, s.toArray());

    s.insert(15); // last branch
    assertArrayEquals(new int[] { 5, 10, 15 }, s.toArray());
  }
}
