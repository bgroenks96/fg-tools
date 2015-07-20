package com.forerunnergames.tools.net.client;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.net.annotations.RequiredForNetworkSerialization;

public final class DefaultClientConfiguration implements ClientConfiguration
{
  private final String clientAddress;
  private final int clientTcpPort;

  public DefaultClientConfiguration (final String clientAddress, final int clientTcpPort)
  {
    Arguments.checkIsNotNull (clientAddress, "clientAddress");
    Arguments.checkIsNotNegative (clientTcpPort, "clientTcpPort");

    this.clientAddress = clientAddress;
    this.clientTcpPort = clientTcpPort;
  }

  @Override
  public String getClientAddress ()
  {
    return clientAddress;
  }

  @Override
  public int getClientTcpPort ()
  {
    return clientTcpPort;
  }

  @Override
  public String toString ()
  {
    return String.format ("%1$s: Client address: %2$s | Client port: %3$s (TCP)", getClass ().getSimpleName (),
                          clientAddress, clientTcpPort);
  }

  @RequiredForNetworkSerialization
  private DefaultClientConfiguration ()
  {
    clientAddress = null;
    clientTcpPort = 0;
  }
}
