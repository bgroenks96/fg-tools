// Copyright © 2011 - 2013 Forerunner Games
package com.forerunnergames.tools;

/**
 *
 * @author Aaron Mahan
 */
public class Translation2D
{
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
    ClassUtils.defaultConstructorNotSupported();
  }

  int translationX_;
  int translationY_;
}
