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

import com.google.common.math.IntMath;

public final class Maths
{
  /**
   * Calculates the next higher multiple of the specified integer.
   *
   * For example, the next higher multiple of 4 from 10 is 12.
   *
   * @param n
   *          The number to obtain the next higher multiple of, must be >= 0.
   * @param multiple
   *          The desired multiple that n must be evenly divisible by, must be > 0.
   */
  public static int nextHigherMultiple (final int n, final int multiple)
  {
    Arguments.checkIsNotNegative (n, "n");
    Arguments.checkLowerInclusiveBound (multiple, 1, "multiple");

    return IntMath.checkedMultiply (((IntMath.checkedAdd (n, (multiple - 1))) / multiple), multiple);
  }

  private Maths ()
  {
    Classes.instantiationNotAllowed ();
  }
}
