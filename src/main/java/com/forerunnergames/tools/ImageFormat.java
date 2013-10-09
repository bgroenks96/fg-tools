// Copyright © 2011 - 2013 Forerunner Games. All rights reserved.
package com.forerunnergames.tools;

public enum ImageFormat
{
  RGBA (4);

  public int getBytesPerPixel()
  {
    return this.bytesPerPixel;
  }

  private ImageFormat (final int bytesPerPixel)
  {
    this.bytesPerPixel = bytesPerPixel;
  }

  private final int bytesPerPixel;
}
