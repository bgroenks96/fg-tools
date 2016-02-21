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

public final class RgbaColor
{
  private final int color;
  private final RedRgbaColorComponent red;
  private final GreenRgbaColorComponent green;
  private final BlueRgbaColorComponent blue;
  private final AlphaRgbaColorComponent alpha;

  public RgbaColor (final int color)
  {
    this.color = color;

    red = new RedRgbaColorComponent ((color >> 24) & 0xFF);
    green = new GreenRgbaColorComponent ((color >> 16) & 0xFF);
    blue = new BlueRgbaColorComponent ((color >> 8) & 0xFF);
    alpha = new AlphaRgbaColorComponent (color & 0xFF);
  }

  public int getValue ()
  {
    return color;
  }

  public RedRgbaColorComponent getRed ()
  {
    return red;
  }

  public GreenRgbaColorComponent getGreen ()
  {
    return green;
  }

  public BlueRgbaColorComponent getBlue ()
  {
    return blue;
  }

  public AlphaRgbaColorComponent getAlpha ()
  {
    return alpha;
  }

  @Override
  public int hashCode ()
  {
    return color;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (this == o) return true;
    if (o == null || getClass () != o.getClass ()) return false;

    final RgbaColor rgbaColor = (RgbaColor) o;

    return color == rgbaColor.color;
  }

  @Override
  public String toString ()
  {
    return String.format ("%1$s: Color: %2$s | Red: %3$s | Green: %4$s | Blue: %5$s | Alpha: %6$s",
                          getClass ().getSimpleName (), color, red, green, blue, alpha);
  }
}
