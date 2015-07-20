package com.forerunnergames.tools.net.client;

public final class UnknownClientConfiguration implements ClientConfiguration
{
  private final String clientAddress = "";
  private final int clientTcpPort = -1;

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
}
