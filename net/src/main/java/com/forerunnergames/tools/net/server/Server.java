package com.forerunnergames.tools.net.server;

import com.forerunnergames.tools.net.NetworkListener;
import com.forerunnergames.tools.net.client.ClientCommunicator;
import com.forerunnergames.tools.net.client.ClientConnector;

public interface Server extends ClientConnector, ClientCommunicator
{
  void add (final NetworkListener networkListener);

  void remove (final NetworkListener networkListener);

  void register (final Class <?> type);

  void start (final int tcpPort);

  void stop ();

  void shutDown ();
}
