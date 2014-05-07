package com.forerunnergames.tools;

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
  public boolean has (final String name)
  {
    Arguments.checkIsNotNull (name, "name");

    return getName().equals (name);
  }

  @Override
  public boolean has (final Id id)
  {
    Arguments.checkIsNotNull (id, "id");

    return id.equals (id);
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

    if (o == null || getClass() != o.getClass()) return false;

    final AbstractAsset that = (AbstractAsset) o;

    return id.equals (that.id);
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

  // Required for network serialization
  protected AbstractAsset()
  {
    name = null;
    id = null;
  }
}