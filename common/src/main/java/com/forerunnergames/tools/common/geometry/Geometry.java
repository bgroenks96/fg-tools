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
import com.forerunnergames.tools.common.Classes;

public final class Geometry
{
  public static Scaling2D divide (final Size2D size1, final Size2D size2)
  {
    Arguments.checkIsNotNull (size1, "size1");
    Arguments.checkIsNotNull (size2, "size2");
    Arguments.checkLowerInclusiveBound (size2.getWidth (), 0, "size2.getWidth()");
    Arguments.checkLowerInclusiveBound (size2.getHeight (), 0, "size2.getHeight()");

    return new Scaling2D (size1.getWidth () / size2.getWidth (), size1.getHeight () / size2.getHeight ());
  }

  public static Point2D scale (final Point2D point, final Scaling2D scaling)
  {
    Arguments.checkIsNotNull (point, "point");
    Arguments.checkIsNotNull (scaling, "scaling");

    return new Point2D (point.getX () * scaling.getX (), point.getY () * scaling.getY ());
  }

  public static Translation2D scale (final Translation2D translation, final Scaling2D scaling)
  {
    Arguments.checkIsNotNull (translation, "translation");
    Arguments.checkIsNotNull (scaling, "scaling");

    return new Translation2D (translation.getX () * scaling.getX (), translation.getY () * scaling.getY ());
  }

  public static Size2D scale (final Size2D size, final Scaling2D scaling)
  {
    Arguments.checkIsNotNull (size, "size");
    Arguments.checkIsNotNull (scaling, "scaling");

    return new Size2D (size.getWidth () * scaling.getX (), size.getHeight () * scaling.getY ());
  }

  public static Point2D translate (final Point2D point, final Translation2D translation)
  {
    Arguments.checkIsNotNull (point, "point");
    Arguments.checkIsNotNull (translation, "translation");

    return new Point2D (point.getX () + translation.getX (), point.getY () + translation.getY ());
  }

  public static Point2D absoluteValue (final Point2D point)
  {
    Arguments.checkIsNotNull (point, "point");

    return new Point2D (Math.abs (point.getX ()), Math.abs (point.getY ()));
  }

  private Geometry ()
  {
    Classes.instantiationNotAllowed ();
  }
}
