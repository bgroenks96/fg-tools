package com.forerunnergames.tools.common.geometry;

import com.forerunnergames.tools.common.Arguments;

public final class Point2D
{
  private int x;
  private int y;

  public Point2D()
  {
    x = 0;
    y = 0;
  }

  public Point2D (final int x, final int y)
  {
    this.x = x;
    this.y = y;
  }

  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }

  public Point2D setX (final int x)
  {
    this.x = x;

    return this;
  }

  public Point2D setY (final int y)
  {
    this.y = y;

    return this;
  }

  public Point2D set (final int x, final int y)
  {
    this.x = x;
    this.y = y;

    return this;
  }

  public Point2D set (final Point2D point)
  {
    Arguments.checkIsNotNull (point, "point");

    this.x = point.getX();
    this.y = point.getY();

    return this;
  }

  public Point2D add (final Point2D point)
  {
    return new Point2D (x + point.getX(), y + point.getY());
  }

  public Point2D subtract (final Point2D point)
  {
    return new Point2D (x - point.getX(), y - point.getY());
  }

  @Override
  public boolean equals (final Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final Point2D point2D = (Point2D) o;

    if (x != point2D.x) return false;
    if (y != point2D.y) return false;

    return true;
  }

  @Override
  public int hashCode()
  {
    int result = x;
    result = 31 * result + y;
    return result;
  }

  @Override
  public String toString()
  {
    return String.format ("%1$s: X: %2$s | Y: %3$s", getClass().getSimpleName(), x, y);
  }
}
