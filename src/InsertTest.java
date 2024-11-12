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
  public void testInsert() {
    // don't go into for loop
    set.insert(10); // statement 4
    assertArrayEquals(new int[] { 10 }, set.toArray(), "should insert into empty set");

    // go into for loop and if
    set.insert(5); // statement 1,2
    assertArrayEquals(new int[] { 5, 10 }, set.toArray(), "should insert smaller");

    // go into for loop and else if
    set.insert(5); // statement 3
    assertArrayEquals(new int[] { 5, 10 }, set.toArray(), "should not insert same element twice");

    // go into for loop and leave it
    set.insert(15); // statement 4
    assertArrayEquals(new int[] { 5, 10, 15 }, set.toArray());
  }
}
