// Copyright © 2011 - 2013 Forerunner Games. All rights reserved.
package com.forerunnergames.tools;

import java.nio.ByteBuffer;

public class Image
{
  public Image (final ByteBuffer buffer, final Size2D size, final ImageFormat format)
  {
    Arguments.checkIsNotNull (buffer, "buffer");
    Arguments.checkIsNotNull (size,   "size");
    Arguments.checkIsNotNull (format, "format");

    this.buffer = buffer;
    this.size   = size;
    this.format = format;
  }

  public ByteBuffer asByteBuffer()
  {
    return this.buffer;
  }

  public ComponentizedColor getColorAt (final Point2D point)
  {
    Arguments.checkIsNotNull (point, "point");

    return Images.getPixelColor (this, point);
  }

  public ImageFormat getFormat()
  {
    return this.format;
  }

  public long calculateSizeInBytes()
  {
    return calculateSizeInPixels() * getBytesPerPixel();
  }

  public long calculateSizeInPixels()
  {
    return this.size.calculateArea();
  }

  public int getBytesPerPixel()
  {
    return this.format.getBytesPerPixel();
  }

  public Size2D getSize()
  {
    return this.size;
  }

  @Override
  public String toString()
  {
    return String.format (getClass().getSimpleName() + ":\nSize: %1$6s", getSize());
  }

  private Image()
  {
    this.buffer = null;
    this.size   = null;
    this.format = null;

    Classes.defaultConstructorNotSupported();
  }

  private final ByteBuffer  buffer;
  private final Size2D      size;
  private final ImageFormat format;
}
