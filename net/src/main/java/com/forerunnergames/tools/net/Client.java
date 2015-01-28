package com.forerunnergames.tools.net;

public interface Client extends ServerConnector, ServerCommunicator
{
  public void add (final NetworkListener listener);

  public void remove (final NetworkListener listener);

  public void register (final Class <?> type);

  public void start ();

  public void stop ();

  public void shutDown ();
}
