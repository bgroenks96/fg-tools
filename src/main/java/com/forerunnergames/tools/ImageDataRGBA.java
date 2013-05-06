// Copyright © 2011 - 2013 Forerunner Games
package com.forerunnergames.tools;

import java.nio.ByteBuffer;

/**
 *
 * @author Aaron Mahan
 */
public class ImageDataRGBA
{
  // Begin public interface

  public ImageDataRGBA (ByteBuffer imageBufferRGBA, int imageWidth,
                                                    int imageHeight)
  {
    Arguments.checkIsNotNull           (imageBufferRGBA, "imageBufferRGBA");
    Arguments.checkLowerExclusiveBound (imageWidth,  0,  "imageWidth");
    Arguments.checkLowerExclusiveBound (imageHeight, 0,  "imageHeight");
    
    imageBufferRGBA_ = imageBufferRGBA;
    width_           = imageWidth;
    height_          = imageHeight;
  }

  public ByteBuffer asByteBuffer()
  {
    assert imageBufferRGBA_ != null;

    return imageBufferRGBA_;
  }

  public int getAlphaAt (int x, int y)
  {
    assert imageBufferRGBA_ != null;

    return ImageUtils.getAlphaComponent (this, x, y);
  }

  public int getAlphaAt (Point2D point)
  {
    Arguments.checkIsNotNull (point, "point");

    return getAlphaAt (point.getX(), point.getY());
  }

  public int getBlueAt (int x, int y)
  {
    return ImageUtils.getBlueComponent (this, x, y);
  }

  public int getBlueAt (Point2D point)
  {
    Arguments.checkIsNotNull (point, "point");

    return getBlueAt (point.getX(), point.getY());
  }

  public int getBytesPerPixel()
  {
    return BYTES_PER_PIXEL_RGBA_;
  }

  public int getGreenAt (int x, int y)
  {
    assert imageBufferRGBA_ != null;

    return ImageUtils.getGreenComponent (this, x, y);
  }

  public int getGreenAt (Point2D point)
  {
    Arguments.checkIsNotNull (point, "point");

    return getGreenAt (point.getX(), point.getY());
  }

  public int getHeight()
  {
    return height_;
  }

  public int getRedAt (int x, int y)
  {
    assert imageBufferRGBA_ != null;

    return ImageUtils.getRedComponent (this, x, y);
  }

  public int getRedAt (Point2D point)
  {
    Arguments.checkIsNotNull (point, "point");

    return getRedAt (point.getX(), point.getY());
  }

  public int getWidth()
  {
    return width_;
  }

  public long sizeInBytes()
  {
    return sizeInPixels() * BYTES_PER_PIXEL_RGBA_;
  }

  public long sizeInPixels()
  {
    return (long) width_ * height_;
  }

  @Override
  public String toString()
  {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append (String.format (getClass().getSimpleName() +
            ":\nWidth: %1$6s " + "Height: %2$6s\n", width_, height_));

    for (int x = 0; x < width_; ++x)
    {
      for (int y = 0; y < height_; ++y)
      {
        stringBuilder.append ("rgba at [")
                     .append (x)
                     .append (", ")
                     .append (y)
                     .append("]: ")
                     .append (getRedAt (x, y))
                     .append (", ")
                     .append (getGreenAt (x, y))
                     .append (", ")
                     .append (getBlueAt (x, y))
                     .append (", ")
                     .append (getAlphaAt (x, y))
                     .append ("\n");
      }
    }

    return stringBuilder.toString();
  }

  // End public interface

  // --------------------

  // Begin private interface

  private ImageDataRGBA()
  {
    ClassUtils.defaultConstructorNotSupported();
  }

  private ByteBuffer imageBufferRGBA_;
  private int        height_;
  private int        width_;

  private final int BYTES_PER_PIXEL_RGBA_ = 4;

  // End private interface
}
