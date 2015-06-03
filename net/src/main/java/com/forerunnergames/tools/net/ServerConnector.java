package com.forerunnergames.tools.net;

import com.forerunnergames.tools.common.Result;

public interface ServerConnector
{
  Result <String> connect (final String address, final int tcpPort, final int timeoutMs, final int maxAttempts);

  boolean isConnected ();

  void disconnect ();
}
