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

public final class Size2D
{
  private final float width;
  private final float height;

  public Size2D ()
  {
    this (0.0f, 0.0f);
  }

  public Size2D (final float width, final float height)
  {
    Arguments.checkLowerInclusiveBound (width, 0.0f, "width");
    Arguments.checkLowerInclusiveBound (height, 0.0f, "height");

    this.width = width;
    this.height = height;
  }

  public Size2D (final Size2D size)
  {
    Arguments.checkIsNotNull (size, "size");

    width = size.getWidth ();
    height = size.getHeight ();
  }

  public float getWidth ()
  {
    return width;
  }

  public float getHeight ()
  {
    return height;
  }

  public double calculateArea ()
  {
    return width * height;
  }

  public boolean hasArea (final double area)
  {
    return Double.compare (calculateArea (), area) == 0;
  }

  public boolean hasWidth (final float width)
  {
    return Float.compare (this.width, width) == 0;
  }

  public boolean hasHeight (final float height)
  {
    return Float.compare (this.height, height) == 0;
  }

  public boolean is (final Size2D size)
  {
    Arguments.checkIsNotNull (size, "size");

    return equals (size);
  }

  public boolean is (final float width, final float height)
  {
    return Float.compare (this.width, width) == 0 && Float.compare (this.height, height) == 0;
  }

  public boolean isNot (final Size2D size)
  {
    return !is (size);
  }

  public boolean isNot (final float width, final float height)
  {
    return !is (width, height);
  }

  @Override
  public int hashCode ()
  {
    int result = (height != +0.0f ? Float.floatToIntBits (height) : 0);

    result = 31 * result + (width != +0.0f ? Float.floatToIntBits (width) : 0);

    return result;
  }

  @Override
  public boolean equals (final Object object)
  {
    if (this == object) return true;
    if (object == null || getClass () != object.getClass ()) return false;

    final Size2D size = (Size2D) object;

    return Float.compare (size.getWidth (), width) == 0 && Float.compare (size.getHeight (), height) == 0;
  }

  @Override
  public String toString ()
  {
    return String.format ("%1$s: width: %2$s | height: %3$s", getClass ().getSimpleName (), width, height);
  }
}
