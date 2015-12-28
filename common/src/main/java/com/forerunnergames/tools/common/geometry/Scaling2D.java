package com.forerunnergames.tools.common.geometry;

import com.forerunnergames.tools.common.Arguments;

public final class Scaling2D
{
  public static final Scaling2D NONE = new Scaling2D (1.0f, 1.0f);
  private final float x;
  private final float y;

  public Scaling2D (final float x, final float y)
  {
    this.x = x;
    this.y = y;
  }

  public Scaling2D (final Scaling2D scaling)
  {
    Arguments.checkIsNotNull (scaling, "scaling");

    x = scaling.getX ();
    y = scaling.getY ();
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

  public boolean is (final Scaling2D scaling)
  {
    Arguments.checkIsNotNull (scaling, "scaling");

    return equals (scaling);
  }

  public boolean is (final float x, final float y)
  {
    return Float.compare (this.x, x) == 0 && Float.compare (this.y, y) == 0;
  }

  public boolean isNot (final Scaling2D scaling)
  {
    return !is (scaling);
  }

  public boolean isNot (final float x, final float y)
  {
    return !is (x, y);
  }

  @Override
  public int hashCode ()
  {
    int result = (x != +0.0f ? Float.floatToIntBits (x) : 0);

    result = 31 * result + (y != +0.0f ? Float.floatToIntBits (y) : 0);

    return result;
  }

  @Override
  public boolean equals (final Object object)
  {
    if (this == object) return true;
    if (object == null || getClass () != object.getClass ()) return false;

    final Scaling2D scaling = (Scaling2D) object;

    return Float.compare (scaling.getX (), x) == 0 && Float.compare (scaling.getY (), y) == 0;
  }

  @Override
  public String toString ()
  {
    return String.format ("%1$s: x: %2$s | y: %3$s", getClass ().getSimpleName (), x, y);
  }
}
