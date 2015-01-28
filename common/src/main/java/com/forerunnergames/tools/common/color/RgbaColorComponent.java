package com.forerunnergames.tools.common.color;

public interface RgbaColorComponent
{
  public static final int MIN_VALUE = 0;
  public static final int MAX_VALUE = 255;

  public int getValue ();

  public int hashCode ();

  public boolean equals (final Object o);
}
