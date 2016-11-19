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

public final class Scaling2D
{
  public static final Scaling2D NONE = new Scaling2D (1.0f, 1.0f);
  private final float x;
  private final float y;

  public Scaling2D (final float x, final float y)
  {
    this.x = x;
    this.y = y;
  }

  public Scaling2D (final Scaling2D scaling)
  {
    Arguments.checkIsNotNull (scaling, "scaling");

    x = scaling.getX ();
    y = scaling.getY ();
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

    final Scaling2D scaling = (Scaling2D) object;

    return Float.compare (scaling.getX (), x) == 0 && Float.compare (scaling.getY (), y) == 0;
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

  public boolean is (final Scaling2D scaling)
  {
    Arguments.checkIsNotNull (scaling, "scaling");

    return equals (scaling);
  }

  public boolean is (final float x, final float y)
  {
    return Float.compare (this.x, x) == 0 && Float.compare (this.y, y) == 0;
  }

  public boolean isNot (final Scaling2D scaling)
  {
    return !is (scaling);
  }

  public boolean isNot (final float x, final float y)
  {
    return !is (x, y);
  }
}
