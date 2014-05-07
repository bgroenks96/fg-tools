package com.forerunnergames.tools.net;

import java.io.IOException;

public interface Client
{
  public void addListener (final NetworkListener listener);
  public void connectTo (final String host, final int tcpPort, final int timeoutMs) throws IOException;
  public void disconnect();
  public boolean isConnected();
  public boolean isRunning();
  public void register (final Class <?> type);
  public void remove (final NetworkListener listener);
  public void send (final Object object);
  public void shutDown();
  public void start();
  public void stop();
  public void update();
}
