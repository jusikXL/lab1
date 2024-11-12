import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberTest {
  Set set;

  @BeforeEach
  public void setUp() {
    set = new Set();
  }

  @Test
  public void testMember() {
    // don't go into for loop
    assertFalse(set.member(123), "should be false for empty set"); // statement 3

    set.insert(5);
    // go into for loop and leave it
    assertFalse(set.member(10), "should return false for larger element"); // statement 3

    // go into for loop and if
    assertFalse(set.member(3), "should return false for smaller element"); // statement 1

    // go into for loop and else if
    assertTrue(set.member(5), "should be true for existing element"); // statement 2
  }
}
