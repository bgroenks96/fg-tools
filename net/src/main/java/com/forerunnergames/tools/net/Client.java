package com.forerunnergames.tools.net;

public interface Client extends ServerConnector, ServerCommunicator
{
  void add (final NetworkListener listener);

  void remove (final NetworkListener listener);

  void register (final Class<?> type);

  void start ();

  void stop ();

  void shutDown ();
}
