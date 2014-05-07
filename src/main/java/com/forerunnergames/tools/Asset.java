package com.forerunnergames.tools;

public interface Asset
{
  public String getName();
  public Id getId();
  public boolean has (final String name);
  public boolean has (final Id id);
  public boolean is (final Asset asset);
  public boolean isNot (final Asset asset);
  public int compareTo (final Asset asset);
  public boolean equals (final Object object);
  public int hashCode();
  public String toString();
}
