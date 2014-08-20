package com.forerunnergames.tools.color;

public final class RgbaColor
{
  private final int color;
  private final RedRgbaColorComponent red;
  private final GreenRgbaColorComponent green;
  private final BlueRgbaColorComponent blue;
  private final AlphaRgbaColorComponent alpha;

  public RgbaColor (final int color)
  {
    this.color = color;

    red = new RedRgbaColorComponent ((color >> 24) & 0xFF);
    green = new GreenRgbaColorComponent ((color >> 16) & 0xFF);
    blue = new BlueRgbaColorComponent ((color >> 8) & 0xFF);
    alpha = new AlphaRgbaColorComponent (color & 0xFF);
  }

  public int getValue()
  {
    return color;
  }

  public RedRgbaColorComponent getRed()
  {
    return red;
  }

  public GreenRgbaColorComponent getGreen()
  {
    return green;
  }

  public BlueRgbaColorComponent getBlue()
  {
    return blue;
  }

  public AlphaRgbaColorComponent getAlpha()
  {
    return alpha;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final RgbaColor rgbaColor = (RgbaColor) o;

    return color == rgbaColor.color;
  }

  @Override
  public int hashCode()
  {
    return color;
  }
}
