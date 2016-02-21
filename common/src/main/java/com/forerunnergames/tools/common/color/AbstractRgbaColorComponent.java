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

package com.forerunnergames.tools.common.color;

import com.forerunnergames.tools.common.Arguments;

public abstract class AbstractRgbaColorComponent implements RgbaColorComponent
{
  private final int value;

  protected AbstractRgbaColorComponent (final int value)
  {
    Arguments.checkLowerInclusiveBound (value, MIN_VALUE, "value");
    Arguments.checkUpperInclusiveBound (value, MAX_VALUE, "value");

    this.value = value;
  }

  @Override
  public final int getValue ()
  {
    return value;
  }

  @Override
  public final int hashCode ()
  {
    return value;
  }

  @Override
  public final boolean equals (final Object o)
  {
    if (this == o) return true;
    if (o == null || getClass () != o.getClass ()) return false;

    final AbstractRgbaColorComponent that = (AbstractRgbaColorComponent) o;

    return value == that.value;
  }

  @Override
  public String toString ()
  {
    return String.format ("%1$s: Value: %2$s", getClass ().getSimpleName (), value);
  }
}
