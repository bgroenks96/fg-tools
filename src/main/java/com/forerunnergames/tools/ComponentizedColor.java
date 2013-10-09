// Copyright © 2011 - 2013 Forerunner Games. All rights reserved.
package com.forerunnergames.tools;

public class ComponentizedColor
{
  public ComponentizedColor (byte red, byte green, byte blue, byte alpha)
  {
    this.red   = red;
    this.green = green;
    this.blue  = blue;
    this.alpha = alpha;
  }

  public byte getRed()
  {
    return this.red;
  }

  public byte getGreen()
  {
    return this.green;
  }

  public byte getBlue()
  {
    return this.blue;
  }
  
  public byte getAlpha()
  {
    return this.alpha;
  }

  private byte red;
  private byte green;
  private byte blue;
  private byte alpha;
}
