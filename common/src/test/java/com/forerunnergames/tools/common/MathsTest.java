/*
 * Copyright © 2011 - 2013 Aaron Mahan
 * Copyright © 2013 - 2016 Forerunner Games, LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.forerunnergames.tools.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MathsTest
{
  @Test
  public void testNextHigherMultiple ()
  {
    final int n = 10;
    final int multiple = 4;
    final int expected = 12;
    final int actual = Maths.nextHigherMultiple (n, multiple);

    assertEquals (expected, actual);
  }

  @Test
  public void testNextHigherMultiple2 ()
  {
    final int n = 9872343;
    final int multiple = 394;
    final int expected = 9872458;
    final int actual = Maths.nextHigherMultiple (n, multiple);

    assertEquals (expected, actual);
  }

  @Test
  public void testNextHigherMultipleOfZero ()
  {
    final int n = 0;
    final int multiple = 4;
    final int expected = 0;
    final int actual = Maths.nextHigherMultiple (n, multiple);

    assertEquals (expected, actual);
  }

  @Test
  public void testNextHigherMultipleOfMaxValueWhenMultipleIsOne ()
  {
    final int n = Integer.MAX_VALUE;
    final int multiple = 1;
    final int expected = Integer.MAX_VALUE;
    final int actual = Maths.nextHigherMultiple (n, multiple);

    assertEquals (expected, actual);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNegativeNThrowsException ()
  {
    Maths.nextHigherMultiple (-1, 2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testZeroMultipleThrowsException ()
  {
    Maths.nextHigherMultiple (0, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNegativeMultipleThrowsException ()
  {
    Maths.nextHigherMultiple (2, -1);
  }

  @Test (expected = ArithmeticException.class)
  public void testMaxValueNAndMaxValueMultipleThrowsException ()
  {
    Maths.nextHigherMultiple (Integer.MAX_VALUE, Integer.MAX_VALUE);
  }

  @Test (expected = ArithmeticException.class)
  public void testNextHigherMultipleIsGreaterThanMaxValueThrowsException ()
  {
    Maths.nextHigherMultiple (Integer.MAX_VALUE, 2);
  }
}
