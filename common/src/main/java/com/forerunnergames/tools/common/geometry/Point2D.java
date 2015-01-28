package com.forerunnergames.tools.common.geometry;

import com.forerunnergames.tools.common.Arguments;

public final class Point2D
{
  private final float x;
  private final float y;

  public Point2D (final float x, final float y)
  {
    this.x = x;
    this.y = y;
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
    return String.format ("%1$s: x: %2$s | y: %3$s", getClass ().getSimpleName (), x, y);
  }
}
