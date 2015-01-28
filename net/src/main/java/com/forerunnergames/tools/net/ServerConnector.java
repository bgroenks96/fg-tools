package com.forerunnergames.tools.net;

import com.forerunnergames.tools.common.Result;

public interface ServerConnector
{
  public Result <String> connect (final String address, final int tcpPort, final int timeoutMs, final int maxAttempts);

  public boolean isConnected ();

  public void disconnect ();
}
