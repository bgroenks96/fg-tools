package com.forerunnergames.tools.net;

public interface Client extends ServerConnector, ServerCommunicator
{
  void add (final NetworkListener networkListener);

  void remove (final NetworkListener networkListener);

  void register (final Class <?> type);

  void start ();

  void stop ();

  void shutDown ();
}
