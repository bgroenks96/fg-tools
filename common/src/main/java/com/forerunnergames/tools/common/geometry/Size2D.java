package com.forerunnergames.tools.common.geometry;

import com.forerunnergames.tools.common.Arguments;

public final class Size2D
{
  private final float width;
  private final float height;

  public Size2D (final float width, final float height)
  {
    Arguments.checkLowerInclusiveBound (width, 0.0f, "width");
    Arguments.checkLowerInclusiveBound (height, 0.0f, "height");

    this.width = width;
    this.height = height;
  }

  public float getWidth ()
  {
    return width;
  }

  public float getHeight ()
  {
    return height;
  }

  public double calculateArea ()
  {
    return width * height;
  }

  public boolean hasArea (final double area)
  {
    return Double.compare (calculateArea (), area) == 0;
  }

  public boolean hasWidth (final float width)
  {
    return Float.compare (this.width, width) == 0;
  }

  public boolean hasHeight (final float height)
  {
    return Float.compare (this.height, height) == 0;
  }

  public boolean is (final Size2D size)
  {
    Arguments.checkIsNotNull (size, "size");

    return equals (size);
  }

  public boolean is (final float width, final float height)
  {
    return Float.compare (this.width, width) == 0 && Float.compare (this.height, height) == 0;
  }

  public boolean isNot (final Size2D size)
  {
    return ! is (size);
  }

  public boolean isNot (final float width, final float height)
  {
    return ! is (width, height);
  }

  @Override
  public int hashCode ()
  {
    int result = (height != +0.0f ? Float.floatToIntBits (height) : 0);

    result = 31 * result + (width != +0.0f ? Float.floatToIntBits (width) : 0);

    return result;
  }

  @Override
  public boolean equals (final Object object)
  {
    if (this == object) return true;
    if (object == null || getClass () != object.getClass ()) return false;

    final Size2D size = (Size2D) object;

    return Float.compare (size.getWidth (), width) == 0 && Float.compare (size.getHeight (), height) == 0;
  }

  @Override
  public String toString ()
  {
    return String.format ("%1$s: width: %2$s | height: %3$s", getClass ().getSimpleName (), width, height);
  }
}
