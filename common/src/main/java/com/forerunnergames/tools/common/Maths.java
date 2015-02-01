package com.forerunnergames.tools.common;

import com.google.common.math.IntMath;

public final class Maths
{
  /**
   * Calculates the next higher multiple of the specified integer.
   *
   * For example, the next higher multiple of 4 from 10 is 12.
   *
   * @param n The number to obtain the next higher multiple of, must be >= 0.
   * @param multiple The desired multiple that n must be evenly divisible by, must be > 0.
   */
  public static int nextHigherMultiple (final int n, final int multiple)
  {
    Arguments.checkIsNotNegative (n, "n");
    Arguments.checkLowerInclusiveBound (multiple, 1, "multiple");

    return IntMath.checkedMultiply (((IntMath.checkedAdd (n, (multiple - 1))) / multiple), multiple);
  }

  private Maths()
  {
    Classes.instantiationNotAllowed ();
  }
}
