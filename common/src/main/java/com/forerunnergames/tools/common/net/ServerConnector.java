package com.forerunnergames.tools.common.net;

import com.forerunnergames.tools.common.Result;

public interface ServerConnector
{
  public Result connect (final String address, final int tcpPort, final int timeoutMs, final int maxAttempts);
  public boolean isConnected();
  public void disconnect();
}
