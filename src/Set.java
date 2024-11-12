import java.util.*;
import java.util.function.IntBinaryOperator;

public class Set {
  private ArrayList<Integer> a;

  public Set() {
    a = new ArrayList<Integer>();
  }

  public int[] toArray() {
    int[] ia = new int[a.size()];
    for (int i = 0; i < ia.length; i++) {
      ia[i] = a.get(i);
    }
    return ia;
  }

  public void insert(int x) {
    for (int i = 0; i < a.size(); i++) {
      if (a.get(i) > x) {
        a.add(i, x); // statement 1
        return; // statement 2
      } else {
        if (a.get(i) == x) {
          return; // statement 3
        }
      }
    }
    a.add(x); // statement 4
  }

  public boolean member(int x) {
    for (int i = 0; i < a.size(); i++) {
      if (a.get(i) > x) {
        return false; // statement 1
      } else {
        if (a.get(i) == x) {
          return true; // statement 2
        }
      }
    }
    return false; // statement 3
  }

  public void intersect(Set s) {
    if (a.size() == 0 || s.a.size() == 0) {
      a = new ArrayList<Integer>(); // statement 1
      return;
    }

    ArrayList<Integer> intersection = new ArrayList<>(); // statement 2

    for (int i = 0, j = 0; i < a.size() && j < s.a.size();) {
      if (a.get(i).equals(s.a.get(j))) {
        intersection.add(a.get(i)); // statement 3
        i++;
        j++;
      } else if (a.get(i) < s.a.get(j)) {
        i++; // statement 4
      } else {
        j++; // statement 5
      }
    }

    a = intersection; // statement 6
  }

  // Try with:
  // (a, b) -> a + b;
  // (a, b) -> a - b;
  public boolean distinctClosed(IntBinaryOperator f) {
    int vi, vj; // statement 1
    for (int i = 0; i < a.size(); i++) {
      for (int j = i + 1 /* consider only distinct pairs */; j < a.size(); j++) {
        vi = a.get(i); // statement 2
        vj = a.get(j); // statement 3
        if (!(member(f.applyAsInt(vi, vj)))) {
          return false; // statement 4
        }
      }
    }
    return true; // statement 5
  }
}
