package com.forerunnergames.tools.common.assets;

import com.forerunnergames.tools.common.id.Id;

public interface Asset extends Comparable <Asset>
{
  public String getName();
  public Id getId();
  public boolean doesNotHave (final Id id);
  public boolean has (final String name);
  public boolean hasName (final String name);
  public boolean has (final Id id);
  public boolean is (final Asset asset);
  public boolean isNot (final Asset asset);
  @Override
  public int compareTo (final Asset asset);
  @Override
  public boolean equals (final Object obj);
  @Override
  public int hashCode();
  @Override
  public String toString();
}
