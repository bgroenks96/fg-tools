package com.forerunnergames.tools.net;

import java.net.InetSocketAddress;

public interface Remote
{
  int getConnectionId ();

  boolean hasConnectionId (final int connectionId);

  boolean hasAddress ();

  boolean hasPort ();

  boolean hasAddressAndPort ();

  boolean hasPort (final int port);

  boolean has (final InetSocketAddress address);

  boolean is (final Remote remote);

  boolean isNot (final Remote remote);

  String getAddress ();

  int getPort ();

  @Override
  int hashCode ();

  @Override
  boolean equals (final Object o);

  @Override
  String toString ();
}
