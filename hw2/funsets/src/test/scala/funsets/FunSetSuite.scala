package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("test singletonSet") {
    val ss = FunSets.singletonSet(5)
    assert(ss(5) === true)
    assert(ss(10) === false)
  }

  test("test union") {
    val ss = FunSets.singletonSet(5)
    val ss2 = FunSets.singletonSet(10)
    val union = FunSets.union(ss, ss2)
    assert(union(5) === true)
    assert(union(10) === true)
    assert(union(15) === false)
  }

  test("test intersect") {
    val ss = FunSets.singletonSet(5)
    val ss2: FunSets.Set = (x) => x > 0
    val inter = FunSets.intersect(ss, ss)
    val inter2 = FunSets.intersect(ss, ss2)
    assert(inter(5) === true)
    assert(inter(10) === false)
    assert(inter2(5) === true)
    assert(inter2(-5) === false)
  }

  test("test diff") {
    val ss = FunSets.singletonSet(5)
    val ss2 = FunSets.singletonSet(10)
    val diff = FunSets.diff(ss, ss2)
    assert(diff(5) === true)
    assert(diff(10) === false)
  }

  test("test filter") {
    val ss = FunSets.singletonSet(5)
    val filt = FunSets.filter(ss, (p) => p > 0)
    val filt2 = FunSets.filter(ss, (p) => p < 0)
    assert(filt(5) === true)
    assert(filt2(5) === false)
  }

  test("test forall") {
    val positiveSet: FunSets.Set = x => x > 0
    val evenFilter: Int => Boolean = x => x % 2 == 0 
    val greaterFilter: Int => Boolean = x => x > -10
    val lessFilter: Int => Boolean = x => x < -10
    
    assert(FunSets.forall(positiveSet, evenFilter) === false)
    assert(FunSets.forall(positiveSet, greaterFilter) === true)
    assert(FunSets.forall(positiveSet, lessFilter) === false)
  }
  
  test("test exists") {
    val positiveSet: FunSets.Set = x => x > 0
    val evenFilter: Int => Boolean = x => x % 2 == 0 
    val greaterFilter: Int => Boolean = x => x > -10
    val lessFilter: Int => Boolean = x => x < -10
    
    assert(FunSets.exists(positiveSet, evenFilter) === true)
    assert(FunSets.exists(positiveSet, greaterFilter) === true)
    assert(FunSets.exists(positiveSet, lessFilter) === false)
  }

  /**
   * If original set contains 1, 2, 3, 4 and f(x) = 2*x
   * New set should be 2, 4, 6, 8
   */
  test("test map") {
    val smallSet: FunSets.Set = x => x >= 1 && x <= 4
    val f: Int => Int = x => 2*x
    val f2: Int => Int = x => x*x
    val mappedSet = FunSets.map(smallSet, f)
    val mappedSet2 = FunSets.map(smallSet, f2)

    assert(FunSets.contains(mappedSet, 1) === false)
    assert(FunSets.contains(mappedSet, 2) === true)
    assert(FunSets.contains(mappedSet, 3) === false)
    assert(FunSets.contains(mappedSet, 4) === true)
    assert(FunSets.contains(mappedSet, 6) === true)
    assert(FunSets.contains(mappedSet, 8) === true)

    assert(FunSets.contains(mappedSet2, 1) === true)
    assert(FunSets.contains(mappedSet2, 2) === false)
    assert(FunSets.contains(mappedSet2, 3) === false)
    assert(FunSets.contains(mappedSet2, 4) === true)
    assert(FunSets.contains(mappedSet2, 9) === true)
    assert(FunSets.contains(mappedSet2, 16) === true)
  }
  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }

  
  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }
  
  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   * 
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   * 
   *   val s1 = singletonSet(1)
   * 
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   * 
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   * 
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   * 
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {
    
    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3". 
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }
}
