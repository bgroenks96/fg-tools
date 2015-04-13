package com.forerunnergames.tools.common.geometry;

import com.forerunnergames.tools.common.Arguments;

public final class Delta2D
{
  private final float dx;
  private final float dy;

  public Delta2D (final float dx, final float dy)
  {
    this.dx = dx;
    this.dy = dy;
  }

  public Delta2D (final Delta2D delta)
  {
    Arguments.checkIsNotNull (delta, "delta");

    dx = delta.getDx ();
    dy = delta.getDy ();
  }

  public float getDx ()
  {
    return dx;
  }

  public float getDy ()
  {
    return dy;
  }

  public boolean hasDx (final float dx)
  {
    return Float.compare (this.dx, dx) == 0;
  }

  public boolean hasDy (final float dy)
  {
    return Float.compare (this.dy, dy) == 0;
  }

  public boolean is (final Delta2D delta)
  {
    Arguments.checkIsNotNull (delta, "delta");

    return equals (delta);
  }

  public boolean is (final float dx, final float dy)
  {
    return Float.compare (this.dx, dx) == 0 && Float.compare (this.dy, dy) == 0;
  }

  public boolean isNot (final Delta2D delta)
  {
    return !is (delta);
  }

  public boolean isNot (final float dx, final float dy)
  {
    return !is (dx, dy);
  }

  @Override
  public boolean equals (final Object object)
  {
    if (this == object) return true;
    if (object == null || getClass () != object.getClass ()) return false;

    final Delta2D delta = (Delta2D) object;

    return Float.compare (delta.getDx (), dx) == 0 && Float.compare (delta.getDy (), dy) == 0;
  }


  @Override
  public int hashCode ()
  {
    int result = (dx != +0.0f ? Float.floatToIntBits (dx) : 0);

    result = 31 * result + (dy != +0.0f ? Float.floatToIntBits (dy) : 0);

    return result;
  }

  @Override
  public String toString ()
  {
    return String.format ("%1$s: dx: %2$s | dy: %3$s", getClass ().getSimpleName (), dx, dy);
  }
}
