// Copyright © 2011 - 2013 Forerunner Games. All rights reserved.
package com.forerunnergames.tools;

public class Delta
{
  public Delta (final int dx, final int dy)
  {
    this.dx = dx;
    this.dy = dy;
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
      Delta delta = (Delta) object;

      if (dx == delta.getDx() && dy == delta.getDy())
      {
        equals = true;
      }
    }

    return equals;
  }

  public int getDx()
  {
    return dx;
  }

  public int getDy()
  {
    return dy;
  }

  @Override
  public int hashCode()
  {
    int hash = 7;

    hash = 23 * hash + dx;
    hash = 23 * hash + dy;

    return hash;
  }

  private final int dx;
  private final int dy;
}
