package com.forerunnergames.tools.net;

public interface Server extends ClientConnector, ClientCommunicator
{
  void add (final NetworkListener listener);

  void remove (final NetworkListener listener);

  void register (final Class<?> type);

  void start (final int tcpPort);

  void stop ();

  void shutDown ();
}
