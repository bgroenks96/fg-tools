package com.forerunnergames.tools.net;

public interface Client extends ServerConnector, ServerCommunicator
{
  public void addListener (final NetworkListener listener);
  public boolean isRunning();
  public void register (final Class <?> type);
  public void remove (final NetworkListener listener);
  public void shutDown();
  public void start();
  public void stop();
  public void update();
}
