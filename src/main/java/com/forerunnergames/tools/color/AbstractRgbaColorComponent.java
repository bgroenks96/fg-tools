package com.forerunnergames.tools.color;

import com.forerunnergames.tools.Arguments;

public abstract class AbstractRgbaColorComponent implements RgbaColorComponent
{
  private final int value;

  protected AbstractRgbaColorComponent (final int value)
  {
    Arguments.checkLowerInclusiveBound (value, MIN_VALUE, "value");
    Arguments.checkUpperInclusiveBound (value, MAX_VALUE, "value");

    this.value = value;
  }

  @Override
  public final int getValue()
  {
    return value;
  }

  @Override
  public final boolean equals (final Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final AbstractRgbaColorComponent that = (AbstractRgbaColorComponent) o;

    return value == that.value;
  }

  @Override
  public final int hashCode()
  {
    return value;
  }
}
