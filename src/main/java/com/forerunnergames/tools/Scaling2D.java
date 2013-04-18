// Copyright © 2011 - 2013 Forerunner Games
package com.forerunnergames.tools;

/**
 *
 * @author Aaron Mahan
 */
public class Scaling2D
{
  public Scaling2D (final float scalingX, final float scalingY)
  {
    x_ = scalingX;
    y_ = scalingY;
  }

  public float getX()
  {
    return x_;
  }

  public float getY()
  {
    return y_;
  }

  @Override
  public String toString()
  {
    return String.format (getClass().getSimpleName() + ": (x: %1$6s, y: %2$6s)",
                          getX(), getY());
  }

  private float x_;
  private float y_;
}
