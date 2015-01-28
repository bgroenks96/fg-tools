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
