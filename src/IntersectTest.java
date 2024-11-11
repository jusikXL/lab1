import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntersectTest {
  Set set1;
  Set set2;

  @BeforeEach
  public void setUp() {
    set1 = new Set();
    set2 = new Set();

    set1.insert(1);
    set1.insert(2);
    set1.insert(3);

    set2.insert(1);
    set2.insert(1);
    set2.insert(3);
  }

  @Test
  public void testIntersectWithSelf() {
    set1.intersect(set1);
    assertArrayEquals(new int[] { 1, 2, 3 }, set1.toArray());
  }

  @Test
  public void testIntersectWithEmptySet() {
    Set emptySet = new Set();
    set1.intersect(emptySet);
    assertArrayEquals(new int[] {}, set1.toArray());
  }

  @Test
  public void testEmptySetIntersection() {
    Set emptySet = new Set();
    emptySet.intersect(set1);
    assertArrayEquals(new int[] {}, emptySet.toArray());
  }

  @Test
  public void testHigherElements() {
    set1.intersect(set2);

    assertArrayEquals(new int[] { 1, 3 }, set1.toArray());
  }

  @Test
  public void testSmallerElements() {
    set2.intersect(set1);

    assertArrayEquals(new int[] { 1, 3 }, set2.toArray());
  }

  @Test
  public void testAnotherIntersection() {
    set1 = new Set();
    set2 = new Set();

    set1.insert(1);
    set1.insert(2);
    set1.insert(3);
    set1.insert(4);

    set2.insert(1);
    set2.insert(4);
    set2.insert(1);
    set2.insert(1);

    set1.intersect(set2);

    assertArrayEquals(new int[] { 1, 4 }, set1.toArray());

  }

  @Test
  public void testOneDifferentSizeIntersection() {
    set1 = new Set();
    set2 = new Set();

    set1.insert(1);
    set1.insert(2);
    set1.insert(3);
    set1.insert(4);

    set2.insert(1);
    set2.insert(2);
    set2.insert(3);
    set2.insert(4);
    set2.insert(5);
    set2.insert(6);

    set1.intersect(set2);

    assertArrayEquals(new int[] { 1, 2, 3, 4 }, set1.toArray());

  }

  @Test
  public void testTwoDifferentSizeIntersection() {
    set1 = new Set();
    set2 = new Set();

    set1.insert(1);
    set1.insert(2);
    set1.insert(3);
    set1.insert(4);
    set1.insert(5);

    set2.insert(1);
    set2.insert(2);
    set2.insert(3);
    set2.insert(4);

    set1.intersect(set2);

    System.out.println(Arrays.toString(set1.toArray()));

    assertArrayEquals(new int[] { 1, 2, 3, 4 }, set1.toArray());

  }

}
