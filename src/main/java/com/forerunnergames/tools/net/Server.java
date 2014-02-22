package com.forerunnergames.tools.net;

import java.io.IOException;

public interface Server
{
  public void addListener (final NetworkListener listener);
  public void closeAllConnections();
  public boolean isRunning();
  public void registerClass (final Class <?> type);
  public void removeListener (final NetworkListener listener);
  public void sendToClient (final int connectionId, final Object object);
  public void sendToAllClients (final Object object);
  public void sendToAllClientsExcept (final int connectionId, final Object object);
  public void shutDown();
  public void start (final int port) throws IOException;
  public void stop();
  public void update();
}
