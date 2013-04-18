// Copyright © 2011 - 2013 Forerunner Games
package com.forerunnergames.tools;

/**
 *
 * @author Aaron Mahan
 */
public class Size2D
{
  public Size2D (final int width, final int height)
  {
    Arguments.checkLowerInclusiveBound (width,  0, "width");
    Arguments.checkLowerInclusiveBound (height, 0, "height");

    width_  = width;
    height_ = height;
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
      Size2D size2D = (Size2D) object;

      if (width_ == size2D.getWidth() && height_ == size2D.getHeight())
      {
        equals = true;
      }
    }

    return equals;
  }

  public int getHeight()
  {
    return height_;
  }

  public int getWidth()
  {
    return width_;
  }

  @Override
  public int hashCode()
  {

    int hash = 3;

    hash = 97 * hash + height_;
    hash = 97 * hash + width_;

    return hash;
  }

  @Override
  public String toString()
  {
    return String.format (getClass().getSimpleName() +
            ": (w: %1$6s x h: %2$6s)", getWidth(), getHeight());
  }

  private final int height_;
  private final int width_;
}
