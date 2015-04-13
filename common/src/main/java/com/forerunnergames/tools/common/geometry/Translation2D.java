package com.forerunnergames.tools.common.geometry;

import com.forerunnergames.tools.common.Arguments;

public final class Translation2D
{
  public static final Translation2D NONE = new Translation2D (0, 0);
  private final float x;
  private final float y;

  public Translation2D (final float x, final float y)
  {
    this.x = x;
    this.y = y;
  }

  public Translation2D (final Translation2D translation)
  {
    Arguments.checkIsNotNull (translation, "translation");

    x = translation.getX ();
    y = translation.getY ();
  }

  public float getX ()
  {
    return x;
  }

  public float getY ()
  {
    return y;
  }

  public boolean hasX (final float x)
  {
    return Float.compare (this.x, x) == 0;
  }

  public boolean hasY (final float y)
  {
    return Float.compare (this.y, y) == 0;
  }

  public boolean is (final Translation2D translation)
  {
    Arguments.checkIsNotNull (translation, "translation");

    return equals (translation);
  }

  public boolean is (final float x, final float y)
  {
    return Float.compare (this.x, x) == 0 && Float.compare (this.y, y) == 0;
  }

  public boolean isNot (final Translation2D translation)
  {
    return !is (translation);
  }

  public boolean isNot (final float x, final float y)
  {
    return !is (x, y);
  }

  @Override
  public boolean equals (final Object object)
  {
    if (this == object) return true;
    if (object == null || getClass () != object.getClass ()) return false;

    final Translation2D translation = (Translation2D) object;

    return Float.compare (translation.getX (), x) == 0 && Float.compare (translation.getY (), y) == 0;
  }

  @Override
  public int hashCode ()
  {
    int result = (x != +0.0f ? Float.floatToIntBits (x) : 0);

    result = 31 * result + (y != +0.0f ? Float.floatToIntBits (y) : 0);

    return result;
  }

  @Override
  public String toString ()
  {
    return String.format ("%1$s: x: %2$s | y: %3$s", getClass ().getSimpleName (), x, y);
  }
}
