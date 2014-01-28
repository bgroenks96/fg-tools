// Copyright © 2011 - 2013 Forerunner Games. All rights reserved.
package com.forerunnergames.tools;

public class Point2D
{
  public Point2D (final int x, final int y)
  {
    x_ = x;
    y_ = y;
  }

  @Override
  public boolean equals (Object object)
  {
    if (this == object)
    {
      return true;
    }

    boolean equals = false;

    if (object != null && object.getClass() == getClass())
    {
      Point2D point = (Point2D) object;

      if (x_ == point.getX() && y_ == point.getY())
      {
        equals = true;
      }
    }

    return equals;
  }

  public int getX()
  {
    return x_;
  }

  public int getY()
  {
    return y_;
  }

  @Override
  public int hashCode()
  {
    int hash = 7;

    hash = 47 * hash + x_;
    hash = 47 * hash + y_;

    return hash;
  }

  @Override
  public String toString()
  {
    return String.format (getClass().getSimpleName() + ": (x: %1$6s, y: %2$6s)", getX(), getY());
  }

  private final int x_;
  private final int y_;
}
