package com.forerunnergames.tools.net;

import java.io.IOException;

public interface Client
{
  public void addListener (final NetworkListener listener);
  public void connect (final int timeoutMs, final String host, final int tcpPort) throws IOException;
  public void disconnect();
  public int getConnectionId();
  public boolean isConnected();
  public boolean isRunning();
  public void registerClass (final Class <?> type);
  public void removeListener (final NetworkListener listener);
  public void sendToServer (int connectionId, Object object);
  public void shutDown();
  public void start();
  public void stop();
  public void update();
}
