package com.forerunnergames.tools.common.color;

public interface RgbaColorComponent
{
  int MIN_VALUE = 0;
  int MAX_VALUE = 255;

  int getValue ();

  int hashCode ();

  boolean equals (final Object o);
}
