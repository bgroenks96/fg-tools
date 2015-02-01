package com.forerunnergames.tools.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MathsTest
{
  @Test
  public void testNextHigherMultiple()
  {
    final int n = 10;
    final int multiple = 4;
    final int expected = 12;
    final int actual = Maths.nextHigherMultiple (n, multiple);

    assertEquals (expected, actual);
  }

  @Test
  public void testNextHigherMultiple2()
  {
    final int n = 9872343;
    final int multiple = 394;
    final int expected = 9872458;
    final int actual = Maths.nextHigherMultiple (n, multiple);

    assertEquals (expected, actual);
  }

  @Test
  public void testNextHigherMultipleOfZero()
  {
    final int n = 0;
    final int multiple = 4;
    final int expected = 0;
    final int actual = Maths.nextHigherMultiple (n, multiple);

    assertEquals (expected, actual);
  }

  @Test
  public void testNextHigherMultipleOfMaxValueWhenMultipleIsOne()
  {
    final int n = Integer.MAX_VALUE;
    final int multiple = 1;
    final int expected = Integer.MAX_VALUE;
    final int actual = Maths.nextHigherMultiple (n, multiple);

    assertEquals (expected, actual);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNegativeNThrowsException()
  {
    Maths.nextHigherMultiple (-1, 2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testZeroMultipleThrowsException()
  {
    Maths.nextHigherMultiple (0, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNegativeMultipleThrowsException()
  {
    Maths.nextHigherMultiple (2, -1);
  }

  @Test (expected = ArithmeticException.class)
  public void testMaxValueNAndMaxValueMultipleThrowsException()
  {
    Maths.nextHigherMultiple (Integer.MAX_VALUE, Integer.MAX_VALUE);
  }

  @Test (expected = ArithmeticException.class)
  public void testNextHigherMultipleIsGreaterThanMaxValueThrowsException()
  {
    Maths.nextHigherMultiple (Integer.MAX_VALUE, 2);
  }
}
