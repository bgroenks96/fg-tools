package com.forerunnergames.tools.net;

import java.io.IOException;

public interface Server extends ClientConnector, ClientCommunicator
{
  public void addListener (final NetworkListener listener);
  public boolean isRunning();
  public void register (final Class <?> type);
  public void remove (final NetworkListener listener);
  public void shutDown();
  public void start (final int port) throws IOException;
  public void stop();
  public void update();
}
