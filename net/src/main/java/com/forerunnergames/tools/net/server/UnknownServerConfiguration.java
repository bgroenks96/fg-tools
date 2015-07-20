package com.forerunnergames.tools.net.server;

public final class UnknownServerConfiguration implements ServerConfiguration
{
  private final String serverAddress = "";
  private final int serverTcpPort = -1;

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
}
