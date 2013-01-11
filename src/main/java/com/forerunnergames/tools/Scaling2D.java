// Copyright © 2011 - 2012 Forerunner Games
package com.forerunnergames.tools;

/**
 *
 * @author Aaron Mahan
 */
public class Scaling2D
{
  // Begin public interface

  public Scaling2D (float scalingX, float scalingY)
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
    return String.format ("scaleX: %1$s, scaleY: %2$s", getX(), getY());
  }

  // End public interface

  // --------------------

  // Begin private interface

  private float x_;
  private float y_;

  // End private interface
}
