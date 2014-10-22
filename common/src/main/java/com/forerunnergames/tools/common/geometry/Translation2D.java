// Copyright © 2011 - 2013 Forerunner Games. All rights reserved.
package com.forerunnergames.tools.common.geometry;

import com.forerunnergames.tools.common.Classes;

public class Translation2D
{
  public static final Translation2D NONE = new Translation2D (0, 0);

  public Translation2D (int translationX, int translationY)
  {
    translationX_ = translationX;
    translationY_ = translationY;
  }

  public int getX()
  {
    return translationX_;
  }

  public int getY()
  {
    return translationY_;
  }

  @Override
  public String toString()
  {
    return String.format (getClass().getSimpleName() + ": (%1$6s, %2$62)",
                          translationX_, translationY_);
  }

  private Translation2D()
  {
    Classes.defaultConstructorNotSupported();
  }

  private int translationX_;
  private int translationY_;
}
