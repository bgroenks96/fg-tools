package com.forerunnergames.tools.net.client;

import com.forerunnergames.tools.net.NetworkListener;
import com.forerunnergames.tools.net.server.ServerCommunicator;
import com.forerunnergames.tools.net.server.ServerConnector;

public interface Client extends ServerConnector, ServerCommunicator
{
  void add (final NetworkListener networkListener);

  void remove (final NetworkListener networkListener);

  void register (final Class <?> type);

  void start ();

  void stop ();

  void shutDown ();
}
