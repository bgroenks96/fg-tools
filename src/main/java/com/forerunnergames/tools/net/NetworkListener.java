package com.forerunnergames.tools.net;

import java.net.InetSocketAddress;

public interface NetworkListener
{
  public void connected (final int connectionId, final InetSocketAddress remoteAddress);
  public void disconnected (final int connectionId, final InetSocketAddress remoteAddress);
  public void received (final Object remoteObject, final int connectionId, final InetSocketAddress remoteAddress);
}
