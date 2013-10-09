package objsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TweetSetSuite extends FunSuite {
  trait TestSets {
    val set1 = new Empty
    val set2 = set1.incl(new Tweet("a", "a body", 20))
    val set3 = set2.incl(new Tweet("b", "b body", 20))
    val c = new Tweet("c", "c body", 7)
    val d = new Tweet("d", "d body", 9)
    val set4c = set3.incl(c)
    val set4d = set3.incl(d)
    val set5 = set4c.incl(d)

    val x1 = new Tweet("x1", "x1 body", 12)
    val x2 = new Tweet("x2", "x2 body", 11)
    val x3 = new Tweet("x3", "x3 body", 12)
    val x4 = new Tweet("x4", "x4 body", 13)
    val x6 = new Tweet("x6", "x6 body", 18)
    val x7 = new Tweet("x7", "x7 body", 12)
    val x8 = new Tweet("x8", "x8 body", 10)
    val set6 : TweetSet = set5.incl(x1).incl(x2).incl(x3).incl(x4).incl(x6).incl(x7).incl(x8)
    val x5 = new Tweet("x5", "x5 body", 7) // not in set6. useful for testing absence.
  }

  def asSet(tweets: TweetSet): Set[Tweet] = {
    var res = Set[Tweet]()
    tweets.foreach(res += _)
    res
  }

  def size(set: TweetSet): Int = asSet(set).size

  test("filter: on empty set") {
    new TestSets {
      assert(size(set1.filter(tw => tw.user == "a")) === 0)
    }
  }

  test("filter: a on set5") {
    new TestSets {
      assert(size(set5.filter(tw => tw.user == "a")) === 1)
    }
  }

  test("filter: 20 on set5") {
    new TestSets {
      assert(size(set5.filter(tw => tw.retweets == 20)) === 2)
    }
  }

  test("filter: 12 on set6") {
    new TestSets {
      assert(size(set6.filter(tw => tw.retweets == 12)) === 3)
    }
  }

  test("filter: 0 on set6") {
    new TestSets {
      assert(size(set6.filter(tw => tw.retweets == 1122)) === 0)
    }
  }

  test("union: set4c and set4d") {
    new TestSets {
      assert(size(set4c.union(set4d)) === 4)
    }
  }

  test("union: with empty set (1)") {
    new TestSets {
      assert(size(set5.union(set1)) === 4)
    }
  }

  test("union: with empty set (2)") {
    new TestSets {
      assert(size(set1.union(set5)) === 4)
    }
  }

  test("most retweets") {
    new TestSets {
      assert(set5.mostRetweeted.retweets === 20)
    }
  }

  test("descending: set5") {
    new TestSets {
      val trends = set5.descendingByRetweet
      assert(!trends.isEmpty)
      assert(trends.head.user == "a" || trends.head.user == "b")
    }
  }

}
