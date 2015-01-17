package com.forerunnergames.tools.common.net;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.net.annotations.RequiredForNetworkSerialization;

public final class DefaultServerConfiguration implements ServerConfiguration
{
  private final String serverName;
  private final String serverAddress;
  private final int serverTcpPort;

  public DefaultServerConfiguration (final String serverName, final String serverAddress, final int serverTcpPort)
  {
    Arguments.checkIsNotNull (serverName, "serverName");
    Arguments.checkIsNotNull (serverAddress, "serverAddress");
    Arguments.checkIsNotNegative (serverTcpPort, "serverTcpPort");

    this.serverName = serverName;
    this.serverAddress = serverAddress;
    this.serverTcpPort = serverTcpPort;
  }

  @Override
  public String getServerAddress ()
  {
    return serverAddress;
  }

  @Override
  public String getServerName ()
  {
    return serverName;
  }

  @Override
  public int getServerTcpPort ()
  {
    return serverTcpPort;
  }

  @Override
  public String toString ()
  {
    return String.format ("%1$s: Server name: %2$s | Server address: %3$s | Server port: %4$s (TCP)",
                    getClass ().getSimpleName (), serverName, serverAddress, serverTcpPort);
  }

  @RequiredForNetworkSerialization
  private DefaultServerConfiguration ()
  {
    serverName = null;
    serverAddress = null;
    serverTcpPort = 0;
  }
}
