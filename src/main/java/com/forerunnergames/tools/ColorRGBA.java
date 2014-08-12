package com.forerunnergames.tools;

public final class ColorRGBA
{
  private final int color;

  public ColorRGBA (final int color)
  {
    this.color = color;
  }

  public int getRed()
  {
    return (color >> 24) & 0xFF;
  }

  public int getGreen()
  {
    return (color >> 16) & 0xFF;
  }

  public int getBlue()
  {
    return (color >> 8) & 0xFF;
  }

  public int getAlpha()
  {
    return color & 0xFF;
  }

  @Override
  public boolean equals (Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final ColorRGBA colorRGBA = (ColorRGBA) o;

    return color == colorRGBA.color;
  }

  @Override
  public int hashCode()
  {
    return color;
  }
}
