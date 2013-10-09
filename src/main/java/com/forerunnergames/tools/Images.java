// Copyright © 2011 - 2013 Forerunner Games. All rights reserved.
package com.forerunnergames.tools;

import com.forerunnergames.tools.exceptions.ImageException;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.imageio.ImageIO;

public class Images
{
  /**
   * Gets the color of the pixel specified by a 2-dimensional coordinate in the image space of the specified image.
   *
   * @param image The image, must not be null.
   * @param pixelCoordinate The coordinate of the pixel to get the color of, must not be null, must specify a valid
   * pixel location in the image space of the specified image.
   *
   * @return The pixel color.
   */
  public static ComponentizedColor getPixelColor (Image image, Point2D pixelCoordinate)
  {
    Arguments.checkIsNotNull (image,           "image");
    Arguments.checkIsNotNull (pixelCoordinate, "pixelCoordinate");

    int        bytesPerPixel = image.getBytesPerPixel();
    int        pixelX        = pixelCoordinate.getX();
    int        pixelY        = pixelCoordinate.getY();
    int        scanSize      = image.getSize().getWidth();
    int        pixelIndex    = (pixelX * bytesPerPixel) + (pixelY * scanSize * bytesPerPixel);
    ByteBuffer imageBuffer   = image.asByteBuffer();
    byte       red           = getColorComponent (imageBuffer, pixelIndex, RED_COMPONENT_OFFSET);
    byte       green         = getColorComponent (imageBuffer, pixelIndex, GREEN_COMPONENT_OFFSET);
    byte       blue          = getColorComponent (imageBuffer, pixelIndex, BLUE_COMPONENT_OFFSET);
    byte       alpha         = getColorComponent (imageBuffer, pixelIndex, ALPHA_COMPONENT_OFFSET);

    return new ComponentizedColor (red, green, blue, alpha);
  }

  private static byte getColorComponent (ByteBuffer imageBuffer, int pixelIndex, int colorComponentOffset)
  {
    assert imageBuffer != null;

    return imageBuffer.get (pixelIndex + colorComponentOffset);
  }

  /**
   * Converts an InputStream to an Image. The caller is responsible for closing the InputStream.
   *
   * @param stream The image as an InputStream, must not be null.
   * @param format The image format, must not be null.
   *
   * @return The Image.
   */
  public static Image toImage (InputStream stream, ImageFormat format)
  {
    Arguments.checkIsNotNull (stream, "stream");
    Arguments.checkIsNotNull (format, "format");

    BufferedImage bufferedImage = toBufferedImage (stream);

    return toImage (bufferedImage, format);
  }

  private static BufferedImage toBufferedImage (InputStream stream)
  {
    try
    {
      assert stream != null;

      BufferedImage bufferedImage = ImageIO.read (stream);

      if (bufferedImage == null)
      {
        throw new ImageException ("Unrecognized image format.");
      }

      return bufferedImage;
    }
    catch (IOException e)
    {
      throw new ImageException ("Error reading image from stream.", e);
    }
  }

  /**
   * Converts a BufferedImage to an Image.
   *
   * @param bufferedImage The source bufferedImage, must not be null.
   * @param format The source bufferedImage format, must not be null.
   *
   * @return The Image.
   */
  public static Image toImage (BufferedImage bufferedImage, ImageFormat format)
  {
    Arguments.checkIsNotNull (bufferedImage, "bufferedImage");

    ByteBuffer buffer = toByteBuffer (bufferedImage, format);

    return toImage (buffer, new Size2D (bufferedImage.getWidth(), bufferedImage.getHeight()), format);
  }

  private static ByteBuffer toByteBuffer (BufferedImage image, ImageFormat format)
  {
    assert image  != null;
    assert format != null;

    int        imageWidth      = image.getWidth();
    int        imageHeight     = image.getHeight();
    int        imageBufferSize = imageWidth * imageHeight * format.getBytesPerPixel();
    int        offset          = 0;
    int        startX          = 0;
    int        startY          = 0;
    int        scanSize        = imageWidth;
    int[]      pixels          = new int [imageWidth * imageHeight];
    ByteBuffer buffer          = createBuffer (imageBufferSize);

    image.getRGB (startX, startY, imageWidth, imageHeight, pixels, offset, scanSize);

    for (int y = 0; y < imageHeight; ++y)
    {
      for (int x = 0; x < imageWidth; ++x)
      {
        int pixel = pixels [offset + ((y - startY) * scanSize) + (x - startX)];

        buffer.put ((byte) ((pixel >> 16) & 0xFF)); // Red
        buffer.put ((byte) ((pixel >>  8) & 0xFF)); // Green
        buffer.put ((byte) ((pixel)       & 0xFF)); // Blue
        buffer.put ((byte) ((pixel >> 24) & 0xFF)); // Alpha
      }
    }

    buffer.flip();

    return buffer;
  }

  private static Image toImage (ByteBuffer buffer, Size2D size, ImageFormat format)
  {
    assert buffer != null;
    assert size   != null;
    assert format != null;

    return new Image (buffer, size, format);
  }

  /**
   * Loads a specific sprite image from a sprite sheet image.
   *
   * @param spriteIndex The index of the sprite in the sprite sheet, must be >= 0.
   * @param spriteSize The size of the sprite, must not be null.
   * @param spriteSheetImage The sprite sheet image, must not be null.
   *
   * @return The sprite image.
   */
  public static Image loadSprite (int spriteIndex, Size2D spriteSize, Image spriteSheetImage)
  {
    Arguments.checkLowerInclusiveBound (spriteIndex, 0,   "spriteIndex");
    Arguments.checkIsNotNull           (spriteSize,       "spriteSize");
    Arguments.checkIsNotNull           (spriteSheetImage, "spriteSheetImage");

    byte[] rawSprite = loadRawSprite (spriteIndex, spriteSize, spriteSheetImage);
    ByteBuffer spriteBuffer = createBufferFrom (rawSprite);

    return toImage (spriteBuffer, spriteSize, spriteSheetImage.getFormat());
  }

  private static byte[] loadRawSprite (int spriteIndex, Size2D spriteSize, Image spriteSheetImage)
  {
    assert spriteIndex      >= 0;
    assert spriteSize       != null;
    assert spriteSheetImage != null;

    int        bytesPerPixel         = spriteSheetImage.getBytesPerPixel();
    int        spriteWidth           = spriteSize.getWidth();
    int        spriteHeight          = spriteSize.getHeight();
    int        spriteWidthBytes      = spriteWidth * bytesPerPixel;
    int        spriteSizeBytes       = spriteWidth * spriteHeight * bytesPerPixel;
    byte[]     rawSpriteImageData    = new byte [spriteSizeBytes];
    int        spriteSheetWidthBytes = spriteSheetImage.getSize().getWidth() * bytesPerPixel;
    ByteBuffer spriteSheetBuffer     = spriteSheetImage.asByteBuffer();

    spriteSheetBuffer.clear();

    for (int y = 0; y < spriteHeight; ++y)
    {
      int spriteImageDataOffset = y * spriteWidthBytes;
      int spriteSheetImagePositionBytes = (spriteIndex * spriteWidthBytes) + (y * spriteSheetWidthBytes);

      spriteSheetBuffer.position (spriteSheetImagePositionBytes);
      spriteSheetBuffer.get (rawSpriteImageData, spriteImageDataOffset, spriteWidthBytes);
    }

    return rawSpriteImageData;
  }

  private static ByteBuffer createBufferFrom (byte[] data)
  {
    assert data != null;

    ByteBuffer buffer = createBuffer (data.length);

    return loadBuffer (buffer, data);
  }

  private static ByteBuffer createBuffer (int capacityInBytes)
  {
    assert capacityInBytes >= 0;

		return ByteBuffer.allocateDirect (capacityInBytes).order (ByteOrder.nativeOrder());
  }

  private static ByteBuffer loadBuffer (ByteBuffer buffer, byte[] data)
  {
    assert buffer != null;
    assert data   != null;

    buffer.clear();
    buffer.put (data);
    buffer.flip();

    return buffer;
  }

  private Images()
  {
    Classes.instantiationNotAllowed();
  }

  private static final int RED_COMPONENT_OFFSET   = 0;
  private static final int GREEN_COMPONENT_OFFSET = 1;
  private static final int BLUE_COMPONENT_OFFSET  = 2;
  private static final int ALPHA_COMPONENT_OFFSET = 3;
}