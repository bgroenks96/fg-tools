// Copyright © 2011 - 2013 Forerunner Games
package com.forerunnergames.tools;

/**
 *
 * @author Aaron Mahan
 */
public class Translation2D
{
  public Translation2D (Point2D point2d)
  {
    Arguments.checkIsNotNull (point2d, "point2d");

    point2d_ = point2d;
  }

  public Translation2D (int translationX, int translationY)
  {
    point2d_ = new Point2D (translationX, translationY);
  }

  public Point2D getPoint2D()
  {
    assert point2d_ != null;

    return point2d_;
  }

  public int getX()
  {
    assert point2d_ != null;

    return point2d_.getX();
  }

  public int getY()
  {
    assert point2d_ != null;

    return point2d_.getY();
  }

  @Override
  public String toString()
  {
    assert point2d_ != null;

    return getClass().getSimpleName() + ": " + point2d_;
  }

  private Translation2D()
  {
    ClassUtils.defaultConstructorNotSupported();
  }

  Point2D point2d_;
}
