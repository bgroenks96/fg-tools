package com.forerunnergames.tools.common;

public enum ImageQuality
{
  LOW,
  HIGH;

  public boolean is (final ImageQuality quality)
  {
    Arguments.checkIsNotNull (quality, "quality");

    return equals (quality);
  }
}
