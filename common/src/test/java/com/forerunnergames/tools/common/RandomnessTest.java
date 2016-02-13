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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import static org.junit.Assert.assertThat;

import org.junit.Test;

public class RandomnessTest
{
  @Test (expected = IllegalArgumentException.class)
  public void testGetRandomIntegerWithExtremeUpperBoundFails ()
  {
    Randomness.getRandomIntegerFrom (0, Integer.MAX_VALUE);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetRandomIntegerWithExtremeLowerBoundFails ()
  {
    Randomness.getRandomIntegerFrom (Integer.MIN_VALUE, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetRandomIntegerWithNegativeLowerBoundFails ()
  {
    Randomness.getRandomIntegerFrom (-1, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetRandomIntegerWithReversedBoundsFails ()
  {
    Randomness.getRandomIntegerFrom (1, -1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetRandomIntegerWithReversedExtremeBoundsFails ()
  {
    Randomness.getRandomIntegerFrom (Integer.MAX_VALUE, Integer.MIN_VALUE);
  }

  @Test
  public void testGetRandomIntegerWithEqualZeroBoundsIsWithinRange ()
  {
    for (int tries = 0; tries < 10; ++tries)
    {
      final int randomInt = Randomness.getRandomIntegerFrom (0, 0);

      assertThat (randomInt, is (equalTo (0)));
    }
  }

  @Test
  public void testGetRandomIntegerWithEqualNearExtremeBoundsIsWithinRange ()
  {
    for (int tries = 0; tries < 10; ++tries)
    {
      final int randomInt = Randomness.getRandomIntegerFrom (Integer.MAX_VALUE - 1, Integer.MAX_VALUE - 1);

      assertThat (randomInt, is (equalTo (Integer.MAX_VALUE - 1)));
    }
  }

  @Test
  public void testGetRandomIntegerWithNearExtremeUpperBoundIsWithinRange ()
  {
    for (int tries = 0; tries < 10000; ++tries)
    {
      final int randomInt = Randomness.getRandomIntegerFrom (0, Integer.MAX_VALUE - 1);

      assertThat (randomInt, is (greaterThanOrEqualTo (0)));
      assertThat (randomInt, is (lessThanOrEqualTo (Integer.MAX_VALUE - 1)));
    }
  }

  @Test
  public void testGetRandomIntegerWithNearExtremeLowerAndUpperBoundsIsWithinRange ()
  {
    for (int tries = 0; tries < 100; ++tries)
    {
      final int randomInt = Randomness.getRandomIntegerFrom (Integer.MAX_VALUE - 5, Integer.MAX_VALUE - 1);

      assertThat (randomInt, is (greaterThanOrEqualTo (Integer.MAX_VALUE - 5)));
      assertThat (randomInt, is (lessThanOrEqualTo (Integer.MAX_VALUE - 1)));
    }
  }
}
