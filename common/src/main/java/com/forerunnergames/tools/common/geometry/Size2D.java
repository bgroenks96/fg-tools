// Copyright © 2011 - 2013 Forerunner Games. All rights reserved.
package com.forerunnergames.tools.common.geometry;

import com.forerunnergames.tools.common.Arguments;

public class Size2D
{
  public Size2D (final int width, final int height)
  {
    Arguments.checkLowerInclusiveBound (width, 0, "width");
    Arguments.checkLowerInclusiveBound (height, 0, "height");

    this.width  = width;
    this.height = height;
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

      if (this.width == size2D.getWidth() && this.height == size2D.getHeight())
      {
        equals = true;
      }
    }

    return equals;
  }

  public long calculateArea()
  {
    return (long) this.width * this.height;
  }

  public int getHeight()
  {
    return this.height;
  }

  public int getWidth()
  {
    return this.width;
  }

  @Override
  public int hashCode()
  {
    int hash = 3;

    hash = 97 * hash + this.height;
    hash = 97 * hash + this.width;

    return hash;
  }

  @Override
  public String toString()
  {
    return String.format (getClass().getSimpleName() + ": (w: %1$6s x h: %2$6s)", getWidth(), getHeight());
  }

  private final int height;
  private final int width;
}
