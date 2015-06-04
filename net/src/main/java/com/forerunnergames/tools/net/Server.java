package com.forerunnergames.tools.net;

public interface Server extends ClientConnector, ClientCommunicator
{
  void add (final NetworkListener networkListener);

  void remove (final NetworkListener networkListener);

  void register (final Class <?> type);

  void start (final int tcpPort);

  void stop ();

  void shutDown ();
}
