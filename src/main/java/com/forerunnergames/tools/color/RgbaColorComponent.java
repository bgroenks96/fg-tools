package com.forerunnergames.tools.color;

public interface RgbaColorComponent
{
  public int getValue();
  public boolean equals (final Object o);
  public int hashCode();

  public static final int MIN_VALUE = 0;
  public static final int MAX_VALUE = 255;
}
