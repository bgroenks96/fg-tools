// Copyright © 2011 - 2013 Forerunner Games. All rights reserved.
package com.forerunnergames.tools.geometry;

public class Scaling2D
{
  public static final Scaling2D NONE = new Scaling2D (1.0f, 1.0f);

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
