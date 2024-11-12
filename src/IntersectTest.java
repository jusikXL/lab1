import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntersectTest {
  Set set1, set2, set3;

  @BeforeEach
  public void setUp() {
    set1 = new Set();
    set2 = new Set();
    set3 = new Set();

    set1.insert(1);
    set1.insert(2);
    set1.insert(5);

    set2.insert(1);
    set2.insert(3);

    set3.insert(1);
    set3.insert(2);
    set3.insert(5);
  }

  @Test
  public void testIntersect() {
    // more elements
    set1.intersect(set2); // statement 2, 3, 4, 5, 6
    System.out.println(Arrays.toString(set1.toArray()));
    assertArrayEquals(new int[] { 1 }, set1.toArray());

    // with empty set
    set1.intersect(new Set()); // statement 1
    assertArrayEquals(new int[] {}, set1.toArray());

    // empty set
    new Set().intersect(set1); // statement 1
    assertArrayEquals(new int[] {}, set1.toArray());

    // less elements
    set2.intersect(set3);
  }
}
