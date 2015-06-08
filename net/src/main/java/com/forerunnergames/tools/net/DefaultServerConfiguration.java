package com.forerunnergames.tools.net;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.net.annotations.RequiredForNetworkSerialization;

public final class DefaultServerConfiguration implements ServerConfiguration
{
  private final String serverAddress;
  private final int serverTcpPort;

  public DefaultServerConfiguration (final String serverAddress, final int serverTcpPort)
  {
    Arguments.checkIsNotNull (serverAddress, "serverAddress");
    Arguments.checkIsNotNegative (serverTcpPort, "serverTcpPort");

    this.serverAddress = serverAddress;
    this.serverTcpPort = serverTcpPort;
  }

  @Override
  public String getServerAddress ()
  {
    return serverAddress;
  }

  @Override
  public int getServerTcpPort ()
  {
    return serverTcpPort;
  }

  @Override
  public String toString ()
  {
    return String.format ("%1$s: Server address: %2$s | Server port: %3$s (TCP)", getClass ().getSimpleName (),
                          serverAddress, serverTcpPort);
  }

  @RequiredForNetworkSerialization
  private DefaultServerConfiguration ()
  {
    serverAddress = null;
    serverTcpPort = 0;
  }
}
