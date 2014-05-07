package com.forerunnergames.tools.net;

import java.io.IOException;

public interface Server
{
  public void addListener (final NetworkListener listener);
  public void disconnectAll();
  public void disconnect (final Remote client);
  public boolean isRunning();
  public void register (final Class <?> type);
  public void remove (final NetworkListener listener);
  public void sendTo (final Remote client, final Object object);
  public void sendToAll (final Object object);
  public void sendToAllExcept (final Remote client, final Object object);
  public void shutDown();
  public void start (final int port) throws IOException;
  public void stop();
  public void update();
}
