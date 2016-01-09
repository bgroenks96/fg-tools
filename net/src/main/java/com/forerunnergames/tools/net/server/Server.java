package com.forerunnergames.tools.net.server;

import com.forerunnergames.tools.net.NetworkListener;
import com.forerunnergames.tools.net.client.ClientCommunicator;
import com.forerunnergames.tools.net.client.ClientConnector;

import java.net.InetSocketAddress;

public interface Server extends ClientConnector, ClientCommunicator
{
  void add (final NetworkListener networkListener);

  void remove (final NetworkListener networkListener);

  void register (final Class <?> type);

  void start (final int tcpPort);

  void start (final InetSocketAddress addressWithTcpPort);

  void stop ();

  void shutDown ();
}
