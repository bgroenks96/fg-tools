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

public final class Delta2D
{
  private final float dx;
  private final float dy;

  public Delta2D (final float dx, final float dy)
  {
    this.dx = dx;
    this.dy = dy;
  }

  public Delta2D (final Delta2D delta)
  {
    Arguments.checkIsNotNull (delta, "delta");

    dx = delta.getDx ();
    dy = delta.getDy ();
  }

  public float getDx ()
  {
    return dx;
  }

  public float getDy ()
  {
    return dy;
  }

  public boolean hasDx (final float dx)
  {
    return Float.compare (this.dx, dx) == 0;
  }

  public boolean hasDy (final float dy)
  {
    return Float.compare (this.dy, dy) == 0;
  }

  public boolean is (final Delta2D delta)
  {
    Arguments.checkIsNotNull (delta, "delta");

    return equals (delta);
  }

  public boolean is (final float dx, final float dy)
  {
    return Float.compare (this.dx, dx) == 0 && Float.compare (this.dy, dy) == 0;
  }

  public boolean isNot (final Delta2D delta)
  {
    return !is (delta);
  }

  public boolean isNot (final float dx, final float dy)
  {
    return !is (dx, dy);
  }

  @Override
  public int hashCode ()
  {
    int result = (dx != +0.0f ? Float.floatToIntBits (dx) : 0);

    result = 31 * result + (dy != +0.0f ? Float.floatToIntBits (dy) : 0);

    return result;
  }

  @Override
  public boolean equals (final Object object)
  {
    if (this == object) return true;
    if (object == null || getClass () != object.getClass ()) return false;

    final Delta2D delta = (Delta2D) object;

    return Float.compare (delta.getDx (), dx) == 0 && Float.compare (delta.getDy (), dy) == 0;
  }

  @Override
  public String toString ()
  {
    return String.format ("%1$s: dx: %2$s | dy: %3$s", getClass ().getSimpleName (), dx, dy);
  }
}
