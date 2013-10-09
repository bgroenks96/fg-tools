// Copyright © 2011 - 2013 Forerunner Games. All rights reserved.
package com.forerunnergames.tools;

public class Texture
{
  public Texture (final int           id,
                  final Scaling2D     scaling,
                  final Translation2D translation,
                  final boolean       isVisible,
                  final int           layer,
                  final Size2D        size)
  {
    Arguments.checkIsNotNull (scaling,     "scaling");
    Arguments.checkIsNotNull (translation, "translation");
    Arguments.checkIsNotNull (size,        "size");

    this.id          = id;
    this.scaling     = scaling;
    this.translation = translation;
    this.isVisible   = isVisible;
    this.layer       = layer;
    this.size        = size;
  }

  @Override
  public int hashCode()
  {
    int hash = 5;
    hash = 41 * hash + id;

    return hash;
  }

  @Override
  public boolean equals (Object object)
  {
    if (this == object)
    {
      return true;
    }
    else if (object != null && object.getClass() == getClass())
    {
      final Texture texture = (Texture) object;

      return id == texture.getId();
    }
    else
    {
      return false;
    }
  }

  public int getId()
  {
    return id;
  }

  public Scaling2D getScaling()
  {
    return scaling;
  }

  public Translation2D getTranslation()
  {
    return translation;
  }

  public boolean isVisible()
  {
    return isVisible;
  }

  public void setVisible (boolean isVisible)
  {
    this.isVisible = isVisible;
  }

  public int getLayer()
  {
    return layer;
  }

  public Size2D getSize()
  {
    return size;
  }

  @Override
  public String toString()
  {
    return super.toString() + String.format (" | scaling: %1$s | translation: %2$s | isVisible: %3$s | layer: %4$s " +
                                             "| size: %5$s", scaling, translation, isVisible, layer, size);
  }

  private final int           id;
  private final Scaling2D     scaling;
  private final Translation2D translation;
  private boolean             isVisible;
  private final int           layer;
  private final Size2D        size;
}
