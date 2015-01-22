package com.forerunnergames.tools.common.geometry;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Classes;

public final class Geometry
{
  public static Point2D add (final Point2D point1, final Point2D point2)
  {
    Arguments.checkIsNotNull (point1, "point1");
    Arguments.checkIsNotNull (point2, "point2");

    return new Point2D (point1.getX () + point2.getX(), point1.getY () + point2.getY());
  }

  public static Point2D subtract (final Point2D point1, final Point2D point2)
  {
    Arguments.checkIsNotNull (point1, "point1");
    Arguments.checkIsNotNull (point2, "point2");

    return new Point2D (point1.getX () - point2.getX(), point1.getY () - point2.getY());
  }

  private Geometry()
  {
    Classes.instantiationNotAllowed ();
  }
}
