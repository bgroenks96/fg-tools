package com.forerunnergames.tools.common.assets;

import com.forerunnergames.tools.common.id.Id;

public interface Asset extends Comparable <Asset>
{
  String getName ();

  Id getId ();

  boolean doesNotHave (final Id id);

  boolean has (final String name);

  boolean hasName (final String name);

  boolean has (final Id id);

  boolean is (final Asset asset);

  boolean isNot (final Asset asset);

  @Override
  int compareTo (final Asset asset);

  @Override
  int hashCode ();

  @Override
  boolean equals (final Object obj);

  @Override
  String toString ();
}
