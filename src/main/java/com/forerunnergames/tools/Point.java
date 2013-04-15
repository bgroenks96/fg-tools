// Copyright © 2011 - 2012 Forerunner Games
package com.forerunnergames.tools;

/**
 *
 * @author Aaron Mahan
 */
public class Point
{
  // Begin public interface

  public Point (final int x, final int y)
  {
    x_ = x;
    y_ = y;
  }

  @Override
  public boolean equals (Object object)
  {
    boolean equals = false;

    if (this == object)
    {
      equals = true;
    }
    else if (object != null && object.getClass() == getClass())
    {
      Point point = (Point) object;

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
    return String.format (getClass().getSimpleName() + ": (%1$6s, %2$6s)",
                          getX(), getY());
  }

  // End public interface

  // --------------------

  // Begin private interface

  private final int x_;
  private final int y_;

  // End private interface
}
