package com.forerunnergames.tools.net;

import java.net.InetSocketAddress;

public interface Remote
{
  int getConnectionId ();

  boolean has (final int connectionId);

  boolean hasAddress ();

  boolean has (final InetSocketAddress address);

  boolean is (final Remote remote);

  boolean isNot (final Remote remote);

  String getAddress ();

  @Override
  int hashCode ();

  @Override
  boolean equals (final Object o);

  @Override
  String toString ();
}
