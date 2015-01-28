package com.forerunnergames.tools.net;

public interface Server extends ClientConnector, ClientCommunicator
{
  public void add (final NetworkListener listener);
  public void remove (final NetworkListener listener);
  public void register (final Class <?> type);
  public void start (final int tcpPort);
  public void stop();
  public void shutDown();
}
