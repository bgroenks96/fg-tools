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

package com.forerunnergames.tools.common.geometry;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Strings;

public final class Point2D
{
  private final float x;
  private final float y;

  public Point2D (final float x, final float y)
  {
    this.x = x;
    this.y = y;
  }

  public Point2D (final Point2D point)
  {
    Arguments.checkIsNotNull (point, "point");

    x = point.getX ();
    y = point.getY ();
  }

  @Override
  public int hashCode ()
  {
    int result = (x != +0.0f ? Float.floatToIntBits (x) : 0);

    result = 31 * result + (y != +0.0f ? Float.floatToIntBits (y) : 0);

    return result;
  }

  @Override
  public boolean equals (final Object object)
  {
    if (this == object) return true;
    if (object == null || getClass () != object.getClass ()) return false;

    final Point2D point = (Point2D) object;

    return Float.compare (point.getX (), x) == 0 && Float.compare (point.getY (), y) == 0;
  }

  @Override
  public String toString ()
  {
    return Strings.format ("{}: x: {} | y: {}", getClass ().getSimpleName (), x, y);
  }

  public float getX ()
  {
    return x;
  }

  public float getY ()
  {
    return y;
  }

  public boolean hasX (final float x)
  {
    return Float.compare (this.x, x) == 0;
  }

  public boolean hasY (final float y)
  {
    return Float.compare (this.y, y) == 0;
  }

  public boolean is (final Point2D point)
  {
    Arguments.checkIsNotNull (point, "point");

    return equals (point);
  }

  public boolean is (final float x, final float y)
  {
    return Float.compare (this.x, x) == 0 && Float.compare (this.y, y) == 0;
  }

  public boolean isNot (final Point2D point)
  {
    return !is (point);
  }

  public boolean isNot (final float x, final float y)
  {
    return !is (x, y);
  }
}
