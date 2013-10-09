// Copyright © 2011 - 2013 Forerunner Games. All rights reserved.
package com.forerunnergames.tools;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;

public class OpenGL
{
  public static void clearScreen()
  {
    GL11.glClearColor (0.0f, 0.0f, 0.0f, 1.0f);
    GL11.glClear      (GL11.GL_COLOR_BUFFER_BIT);
  }

  public static void drawTexture (Texture texture)
  {
    Arguments.checkIsNotNull (texture, "texture");

    if (! texture.isVisible())
    {
      return;
    }

    GL11.glBindTexture (GL11.GL_TEXTURE_2D, texture.getId());
    GL11.glPushMatrix  ();
    GL11.glTranslatef  (texture.getTranslation().getX(), texture.getTranslation().getY(), 0.0f);
    GL11.glScalef      (texture.getScaling().getX(), texture.getScaling().getY(), 1.0f);
    GL11.glColor4f     (0.0f, 0.0f, 0.0f, 1.0f);
    GL11.glBegin       (GL11.GL_QUADS);
    GL11.glTexCoord2f  (0.0f, 0.0f);
    GL11.glVertex2i    (0, 0);
    GL11.glTexCoord2f  (1.0f, 0.0f);
    GL11.glVertex2i    (texture.getSize().getWidth(), 0);
    GL11.glTexCoord2f  (1.0f, 1.0f);
    GL11.glVertex2i    (texture.getSize().getWidth(), texture.getSize().getHeight());
    GL11.glTexCoord2f  (0.0f, 1.0f);
    GL11.glVertex2i    (0, texture.getSize().getHeight());
    GL11.glEnd         ();
    GL11.glPopMatrix   ();
  }

  public static Texture loadTexture (Image         image,
                                     Scaling2D     scaling,
                                     Translation2D translation,
                                     boolean       isVisible,
                                     int           layer)
  {
    Arguments.checkIsNotNull (image,       "image");
    Arguments.checkIsNotNull (scaling,     "scaling");
    Arguments.checkIsNotNull (translation, "translation");

    int textureId      = GL11.glGenTextures();
    int level          = 0;
    int internalFormat = GL11.GL_RGBA8;
    int imageHeight    = image.getSize().getHeight();
    int imageWidth     = image.getSize().getWidth();
    int border         = 0;
    int format         = getTextureFormat (image.getFormat());
    int type           = GL11.GL_UNSIGNED_BYTE;
    ByteBuffer pixels  = image.asByteBuffer();

    GL11.glBindTexture (GL11.GL_TEXTURE_2D, textureId);
    GL11.glEnable      (GL11.GL_TEXTURE_2D);

    if (GLContext.getCapabilities().OpenGL12)
    {
      GL11.glTexParameteri (GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
      GL11.glTexParameteri (GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
    }
    else
    {
      GL11.glTexParameteri (GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
      GL11.glTexParameteri (GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
    }

    GL11.glTexParameteri (GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
    GL11.glTexParameteri (GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

    if (GLContext.getCapabilities().OpenGL30)
    {
      GL11.glTexImage2D     (GL11.GL_TEXTURE_2D, level, internalFormat, imageWidth, imageHeight, border, format, type, pixels);
      GL11.glEnable         (GL11.GL_TEXTURE_2D);
      GL30.glGenerateMipmap (GL11.GL_TEXTURE_2D);
    }
    else if (GLContext.getCapabilities().OpenGL14)
    {
      GL11.glHint          (GL14.GL_GENERATE_MIPMAP_HINT, GL11.GL_NICEST);
      GL11.glTexParameteri (GL11.GL_TEXTURE_2D, GL14.GL_GENERATE_MIPMAP, GL11.GL_TRUE);
      GL11.glTexImage2D    (GL11.GL_TEXTURE_2D, level, internalFormat, imageWidth, imageHeight, border, format, type, pixels);
    }
    else
    {
      GLU.gluBuild2DMipmaps (GL11.GL_TEXTURE_2D, internalFormat, imageWidth, imageHeight, format, type, pixels);
    }

    return new Texture (textureId, scaling, translation, isVisible, layer, image.getSize());
  }

  private static int getTextureFormat (ImageFormat format)
  {
    assert format != null;

    switch (format)
    {
      case RGBA:
      {
        return GL11.GL_RGBA;
      }
      default:
      {
        throw new RuntimeException ("Unrecognized texture format: " + format + ".");
      }
    }
  }

  private OpenGL()
  {
    Classes.instantiationNotAllowed();
  }
}
