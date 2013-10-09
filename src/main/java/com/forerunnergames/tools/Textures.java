// Copyright © 2011 - 2013 Forerunner Games. All rights reserved.
package com.forerunnergames.tools;

import java.awt.image.BufferedImage;
import java.io.InputStream;

public class Textures
{
  public static Texture loadTexture (String        name,
                                     BufferedImage bufferedImage,
                                     ImageFormat   format,
                                     Scaling2D     scaling,
                                     Translation2D translation,
                                     boolean       isVisible,
                                     int           layer)
  {
    Arguments.checkIsNotNull (name,          "name");
    Arguments.checkIsNotNull (bufferedImage, "bufferedImage");
    Arguments.checkIsNotNull (format,        "format");
    Arguments.checkIsNotNull (scaling,       "scaling");
    Arguments.checkIsNotNull (translation,   "translation");

    Image image = Images.toImage (bufferedImage, format);

    return OpenGL.loadTexture (image, scaling, translation, isVisible, layer);
  }

  public static Texture loadTexture (String        name,
                                     InputStream   stream,
                                     ImageFormat   format,
                                     Scaling2D     scaling,
                                     Translation2D translation,
                                     boolean       isVisible,
                                     int           layer)
  {
    Arguments.checkIsNotNull (name,        "name");
    Arguments.checkIsNotNull (stream,      "stream");
    Arguments.checkIsNotNull (format,      "format");
    Arguments.checkIsNotNull (scaling,     "scaling");
    Arguments.checkIsNotNull (translation, "translation");

    Image image = Images.toImage (stream, format);

    return OpenGL.loadTexture (image, scaling, translation, isVisible, layer);
  }

  private Textures()
  {
    Classes.instantiationNotAllowed();
  }
}
