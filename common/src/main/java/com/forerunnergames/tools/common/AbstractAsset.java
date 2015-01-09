package com.forerunnergames.tools.common;

import com.forerunnergames.tools.common.id.Id;
import com.forerunnergames.tools.common.net.annotations.RequiredForNetworkSerialization;

public abstract class AbstractAsset implements Asset, Comparable <Asset>
{
  private final String name;
  private final Id id;

  public AbstractAsset (final Id id)
  {
    this ("", id);
  }

  public AbstractAsset (final String name, final Id id)
  {
    Arguments.checkIsNotNull (name, "name");
    Arguments.checkIsNotNull (id, "id");

    this.name = name;
    this.id = id;
  }

  @Override
  public String getName()
  {
    return name;
  }

  @Override
  public Id getId()
  {
    return id;
  }

  @Override
  public boolean doesNotHave (final Id id)
  {
    return ! has (id);
  }

  @Override
  public boolean has (final String name)
  {
    Arguments.checkIsNotNull (name, "name");

    return getName().equals (name);
  }

  @Override
  public boolean hasName (final String name)
  {
    return has (name);
  }

  @Override
  public boolean has (final Id id)
  {
    Arguments.checkIsNotNull (id, "id");

    return id.equals (id);
  }

  @Override
  public boolean is (final Asset asset)
  {
    Arguments.checkIsNotNull (asset, "asset");

    return this.equals (asset);
  }

  @Override
  public boolean isNot (final Asset asset)
  {
    return ! is (asset);
  }

  @Override
  public int compareTo (final Asset asset)
  {
    Arguments.checkIsNotNull (asset, "asset");

    return getId().compareTo (asset.getId());
  }

  @Override
  public boolean equals (final Object o)
  {
    if (this == o) return true;
    if (! (o instanceof AbstractAsset)) return false;

    final AbstractAsset that = (AbstractAsset) o;

    if (! id.equals (that.id)) return false;

    return true;
  }

  @Override
  public int hashCode()
  {
    return id.hashCode();
  }

  @Override
  public String toString()
  {
    return String.format ("Name: %1$s | Id: %2$s", name, id);
  }

  @RequiredForNetworkSerialization
  protected AbstractAsset()
  {
    name = null;
    id = null;
  }
}