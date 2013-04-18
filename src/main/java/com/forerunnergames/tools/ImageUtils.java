// Copyright © 2011 - 2012 Forerunner Games
package com.forerunnergames.tools;

import com.forerunnergames.tools.exceptions.ImageLoadingException;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import javax.imageio.ImageIO;

import lwjgl.BufferUtils;

/**
 *
 * @author Aaron Mahan
 */
public class ImageUtils 
{
  // Begin public interface

  /**
   * Gets the alpha component of the specified pixel from the specified
   * ByteBuffer.
   * 
   * @param imageDataRGBA The RGBA image data, must not be null.
   * 
   * @param x The x coordinate of the pixel to get the alpha component of.
   * @param y The y coordinate of the pixel to get the alpha component of.
   * 
   * @return The alpha component color value, in the range 0...255.
   */
  public static int getAlphaComponent (ImageDataRGBA imageDataRGBA, int x,
                                                                    int y)
  {
    Arguments.checkIsNotNull (imageDataRGBA, "imageDataRGBA");

    return getColorComponent (imageDataRGBA, x, y, ALPHA_COMPONENT_OFFSET);
  }

  /**
   * Gets the blue component of the specified pixel from the specified
   * ByteBuffer.
   * 
   * @param imageDataRGBA The RGBA image data, must not be null.
   * 
   * @param x The x coordinate of the pixel to get the blue component of.
   * @param y The y coordinate of the pixel to get the blue component of.
   * 
   * @return The blue component color value, in the range 0...255.
   */
  public static int getBlueComponent (ImageDataRGBA imageDataRGBA, int x,
                                                                   int y)
  {
    Arguments.checkIsNotNull (imageDataRGBA, "imageDataRGBA");

    return getColorComponent (imageDataRGBA, x, y, BLUE_COMPONENT_OFFSET);
  }

  /**
   * Gets the green component of the specified pixel from the specified
   * ByteBuffer.
   * 
   * @param imageDataRGBA The RGBA image data, must not be null.
   * 
   * @param x The x coordinate of the pixel to get the green component of.
   * @param y The y coordinate of the pixel to get the green component of.
   * 
   * @return The green component color value, in the range 0...255.
   */
  public static int getGreenComponent (ImageDataRGBA imageDataRGBA, int x,
                                                                    int y)
  {
    Arguments.checkIsNotNull (imageDataRGBA, "imageDataRGBA");

    return getColorComponent (imageDataRGBA, x, y, GREEN_COMPONENT_OFFSET);
  }

  /**
   * 
   * @param spriteIndex The index of the sprite in the sprite sheet, must be >=
   * 0.
   * @param spriteWidth The width of the sprite, must be > 0.
   * @param spriteHeight The height of the sprite, must be > 0.
   * @param spriteSheetImageData The sprite sheet, must not be null.
   * 
   * @return The sprite image data.
   * 
   * @throws ImageLoadingException If spriteIndex is out of bounds, if
   * spriteWidth > sprite sheet width, if spriteHeight > sprite sheet height.
   */
  public static ImageDataRGBA loadSpriteRGBA (int spriteIndex,
          Size2D spriteSize, ImageDataRGBA spriteSheetImageData)
          throws ImageLoadingException
  {
    Arguments.checkLowerInclusiveBound (spriteIndex, 0, "spriteIndex");
    Arguments.checkIsNotNull (spriteSize, "spriteSize");
    Arguments.checkIsNotNull (spriteSheetImageData, "spriteSheetImageData");

    int spriteWidth  = spriteSize.getWidth();
    int spriteHeight = spriteSize.getHeight();

    ByteBuffer spriteSheetBuffer = spriteSheetImageData.asByteBuffer();

    int bytesPerPixel         = 4;
    int spriteSheetWidthBytes = spriteSheetImageData.getWidth() * bytesPerPixel;
    int spriteSizeBytes       = spriteWidth * spriteHeight * bytesPerPixel;
    int spriteWidthBytes      = spriteWidth * bytesPerPixel;

    byte[] spriteData = new byte [spriteSizeBytes];

    spriteSheetBuffer.clear();

    for (int y = 0; y < spriteHeight; ++y)
    {
      int spriteDataOffset = y * spriteWidthBytes;

      try
      {
        int spriteSheetPositionBytes = 
                (spriteIndex * spriteWidthBytes) + (y * spriteSheetWidthBytes);

        spriteSheetBuffer.position (spriteSheetPositionBytes);

        spriteSheetBuffer.get (spriteData, spriteDataOffset, spriteWidthBytes);
      }
      catch (IllegalArgumentException e)
      {
        throw new ImageLoadingException ("Error loading sprite " + spriteIndex +
                " with width: "  + spriteWidth + " & height: " + spriteHeight +
                " from sprite sheet:\n" + spriteSheetImageData, e);
      }
      catch (IndexOutOfBoundsException e)
      {
        throw new ImageLoadingException ("Error loading sprite " + spriteIndex +
                " with width: "  + spriteWidth + " & height: " + spriteHeight +
                " from sprite sheet:\n" + spriteSheetImageData, e);
      }
      catch (BufferUnderflowException e)
      {
        throw new ImageLoadingException ("Error loading sprite " + spriteIndex +
                " with width: "  + spriteWidth + " & height: " + spriteHeight +
                " from sprite sheet:\n" + spriteSheetImageData, e);
      }
    }

    ByteBuffer spriteBuffer = BufferUtils.createByteBuffer (spriteSizeBytes);

    spriteBuffer.clear();
    spriteBuffer.put (spriteData);
    spriteBuffer.clear();

    return new ImageDataRGBA (spriteBuffer, spriteWidth, spriteHeight);
  }

  /**
   * Gets the red component of the specified pixel from the specified
   * ByteBuffer.
   * 
   * @param imageDataRGBA The RGBA image data, must not be null.
   * 
   * @param x The x coordinate of the pixel to get the red component of.
   * @param y The y coordinate of the pixel to get the red component of.
   * 
   * @return The red component color value, in the range 0...255.
   */
  public static int getRedComponent (ImageDataRGBA imageDataRGBA, int x, int y)
  {
    Arguments.checkIsNotNull (imageDataRGBA, "imageDataRGBA");

    return getColorComponent (imageDataRGBA, x, y, RED_COMPONENT_OFFSET);
  }

  /**
   * Loads ByteBuffer-backed RGBA image data from a bufferedImage.
   * 
   * @param bufferedImage The buffered image, must not be null.
   * 
   * @return ImageDataRGBA loaded with RGBA color data, backed by a byteBuffer.
   * 
   * @throws ImageLoadingException If there is an IO error while attempting to
   * load the image, or if the stream contains an unrecognized image format
   * (non-RGBA, etc)
   */
  public static ImageDataRGBA loadImageRGBA (BufferedImage bufferedImage)
          throws ImageLoadingException
  {
    Arguments.checkIsNotNull (bufferedImage, "bufferedImage");

    int imageWidth        = bufferedImage.getWidth();
    int imageHeight       = bufferedImage.getHeight();
    int bytesPerPixel     = 4;
    int imageBufferSize   = imageWidth * imageHeight * bytesPerPixel;
    ByteBuffer byteBuffer = BufferUtils.createByteBuffer (imageBufferSize);
    int offset            = 0;
    int startX            = 0;
    int startY            = 0;
    int scanSize          = imageWidth;
    int[] pixels          = new int [imageWidth * imageHeight];

    try
    {
      bufferedImage.getRGB (startX, startY, imageWidth, imageHeight, pixels,
                            offset, scanSize);


      for (int y = 0; y < imageHeight; ++y)
      {
        for (int x = 0; x < imageWidth; ++x)
        {
          int pixel =
                  pixels [offset + ((y - startY) * scanSize) + (x - startX)];

          byteBuffer.put ((byte) ((pixel >> 16) & 0xFF)); // Red
          byteBuffer.put ((byte) ((pixel >>  8) & 0xFF)); // Green
          byteBuffer.put ((byte) ((pixel)       & 0xFF)); // Blue
          byteBuffer.put ((byte) ((pixel >> 24) & 0xFF)); // Alpha
        }
      }

      byteBuffer.clear();
    }
    catch (ArrayIndexOutOfBoundsException e)
    {
      throw new ImageLoadingException ("Error loading image.", e);
    }
    catch (BufferOverflowException e)
    {
      throw new ImageLoadingException ("Error loading image.", e);
    }
    catch (IndexOutOfBoundsException e)
    {
      throw new ImageLoadingException ("Error loading image.", e);
    }
    catch (ReadOnlyBufferException e)
    {
      throw new ImageLoadingException ("Error loading image.", e);
    }

    return new ImageDataRGBA (byteBuffer, imageWidth, imageHeight);
  }


  /**
   * Loads ByteBuffer-backed RGBA image data from stream.
   * 
   * @param imageStream The image as a stream, must not be null, must be a
   * recognized image format (An ImageReader must exist for it), must be RGBA
   * format. The stream is guaranteed to be closed unless an exception is
   * thrown.
   * 
   * @return ImageDataRGBA loaded with RGBA color data, backed by a byteBuffer.
   * 
   * @throws ImageLoadingException If there is an IO error while attempting to
   * load the image, or if the stream contains an unrecognized image format
   * (non-RGBA, etc)
   */
  public static ImageDataRGBA loadImageRGBA (InputStream imageStream)
          throws ImageLoadingException
  {
    Arguments.checkIsNotNull (imageStream, "imageStream");

    BufferedImage bufferedImage;

    try
    {
      bufferedImage = ImageIO.read (imageStream);

      imageStream.close();

      if (bufferedImage == null)
      {
        throw new ImageLoadingException ("Unrecognized image format.");
      }
    }
    catch (IOException e)
    {
      throw new ImageLoadingException ("Error loading image.", e);
    }

    return loadImageRGBA (bufferedImage);
  }

  // End public interface

  // --------------------

  // Begin private interface

  private static final int ALPHA_COMPONENT_OFFSET = 3;
  private static final int BLUE_COMPONENT_OFFSET  = 2;
  private static final int GREEN_COMPONENT_OFFSET = 1;
  private static final int RED_COMPONENT_OFFSET   = 0;

  private static int getColorComponent (ImageDataRGBA imageDataRGBA, int x,
                                        int y, int colorComponentOffset)
  {
    assert colorComponentOffset >= 0;
    assert imageDataRGBA != null;

    int        bytesPerPixel = 4;
    int        scanSize      = imageDataRGBA.getWidth();
    ByteBuffer imageBuffer   = imageDataRGBA.asByteBuffer();

    int pixelIndex     = (x * bytesPerPixel) + (y * scanSize * bytesPerPixel);
    int colorComponentIndex = pixelIndex + colorComponentOffset;
    int colorComponent;

    try
    {
      colorComponent = (imageBuffer.get (colorComponentIndex)) & 0xFF;
    }
    catch (IndexOutOfBoundsException e)
    {
      throw new RuntimeException ("x / y coordinates do not specify a " +
                                  "valid pixel location in the image", e);
    }

    return colorComponent;
  }

  private ImageUtils()
  {
    ClassUtils.instantiationNotAllowed();
  }

  // End private interface
}