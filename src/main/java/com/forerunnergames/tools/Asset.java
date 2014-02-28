// Copyright © 2011 - 2013 Forerunner Games. All rights reserved.
package com.forerunnergames.tools;

import com.forerunnergames.tools.net.AssetPacket;

public class Asset implements Comparable <Asset>, AssetPacket
{
  public Asset (final int id)
  {
    this ("", id);
  }

  public Asset (final String name, final int id)
  {
    Arguments.checkIsNotNull (name, "name");

    this.name = name;
    this.id   = id;
  }

  @Override
  public int compareTo (Asset asset)
  {
    Arguments.checkIsNotNull (asset, "asset");

    return getId() - asset.getId();
  }

  @Override
  public boolean equals (Object object)
  {
    if (this == object)
    {
      return true;
    }

    boolean equals = false;

    if (object != null && object.getClass() == getClass())
    {
      Asset asset = (Asset) object;

      if (getId() == asset.getId())
      {
        equals = true;
      }
    }

    return equals;
  }

  @Override
  public int getId()
  {
    return id;
  }

  @Override
  public String getName()
  {
    return name;
  }

  @Override
  public int hashCode()
  {
    int hash = 3;

    hash = 83 * hash + getId();

    return hash;
  }

  public boolean hasId (int id)
  {
    return getId() == id;
  }

  public boolean hasName (String name)
  {
    Arguments.checkIsNotNull (name, "name");

    return getName().equals (name);
  }

  @Override
  public String toString()
  {
    return String.format ("%1$s: Name: %2$s | Id: %3$s", getClass().getSimpleName(), getName(), getId());
  }

  // Required for network serialization
  protected Asset()
  {
    name = null;
    id = 0;
  }

  private final String name;
  private final int    id;
}