// Copyright © 2011 - 2013 Forerunner Games
package com.forerunnergames.tools;

/**
 *
 * @author Aaron Mahan
 */
public class Delta
{
  public Delta (final int dx, final int dy)
  {
    dx_ = dx;
    dy_ = dy;
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
      Delta delta = (Delta) object;

      if (dx_ == delta.getDx() && dy_ == delta.getDy())
      {
        equals = true;
      }
    }

    return equals;
  }

  public int getDx()
  {
    return dx_;
  }

  public int getDy()
  {
    return dy_;
  }

  @Override
  public int hashCode()
  {
    int hash = 7;

    hash = 23 * hash + dx_;
    hash = 23 * hash + dy_;

    return hash;
  }

  private final int dx_;
  private final int dy_;
}
