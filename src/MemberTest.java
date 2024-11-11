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
  public void testMemberExistingElement() {
    set.insert(5);
    assertTrue(set.member(5)); // statement 2
  }

  @Test
  public void testMemberSmallerElement() {
    set.insert(5);
    assertFalse(set.member(3)); // statement 1
  }

  @Test
  public void testMemberLargerElement() {
    set.insert(5);
    assertFalse(set.member(10)); // statement 3
  }

  @Test
  public void testMemberNonExistentMiddleElement() {
    set.insert(3);
    set.insert(7);
    assertFalse(set.member(5)); // statement 1
  }
}
