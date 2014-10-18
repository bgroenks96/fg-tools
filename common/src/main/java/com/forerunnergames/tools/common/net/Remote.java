package com.forerunnergames.tools.common.net;

import java.net.InetSocketAddress;

public interface Remote
{
  public int getConnectionId();
  public boolean has (final int connectionId);
  public boolean hasAddress();
  public boolean has (final InetSocketAddress address);
  public boolean is (final Remote remote);
  public boolean isNot (final Remote remote);
  public String getAddress();
  @Override
  public boolean equals (final Object o);
  @Override
  public int hashCode();
  @Override
  public String toString();
}
