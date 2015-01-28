package com.forerunnergames.tools.net;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.controllers.ControllerAdapter;

import com.google.common.collect.ImmutableSet;

public abstract class AbstractServerController extends ControllerAdapter implements ServerController
{
  private final Server server;
  private final int tcpPort;
  private final ImmutableSet <Class <?>> classesToRegisterForNetworkSerialization;

  protected AbstractServerController (final Server server,
                                      final int tcpPort,
                                      final ImmutableSet <Class <?>> classesToRegisterForNetworkSerialization)
  {
    Arguments.checkIsNotNull (server, "server");
    Arguments.checkIsNotNull (classesToRegisterForNetworkSerialization, "classesToRegisterForNetworkSerialization");
    Arguments.checkIsNotNegative (tcpPort, "tcpPort");
    Arguments.checkHasNoNullElements (classesToRegisterForNetworkSerialization,
                    "classesToRegisterForNetworkSerialization");

    this.server = server;
    this.tcpPort = tcpPort;
    this.classesToRegisterForNetworkSerialization = classesToRegisterForNetworkSerialization;
  }

  @Override
  public void initialize ()
  {
    registerClasses ();
    addListener ();
    startServer ();
  }

  @Override
  public void shutDown ()
  {
    server.shutDown ();
  }

  @Override
  public boolean isConnected (final Remote client)
  {
    Arguments.checkIsNotNull (client, "client");

    return server.isConnected (client);
  }

  @Override
  public void disconnect (final Remote client)
  {
    Arguments.checkIsNotNull (client, "client");

    server.disconnect (client);
  }

  @Override
  public void disconnectAll ()
  {
    server.disconnectAll ();
  }

  @Override
  public void sendTo (final Remote client, final Object object)
  {
    Arguments.checkIsNotNull (client, "client");
    Arguments.checkIsNotNull (object, "object");

    server.sendTo (client, object);
  }

  @Override
  public void sendToAll (final Object object)
  {
    Arguments.checkIsNotNull (object, "object");

    server.sendToAll (object);
  }

  @Override
  public void sendToAllExcept (final Remote client, final Object object)
  {
    Arguments.checkIsNotNull (client, "client");
    Arguments.checkIsNotNull (object, "object");

    server.sendToAllExcept (client, object);
  }

  protected abstract void onConnection (final Remote client);

  protected abstract void onDisconnection (final Remote client);

  protected abstract void onCommunication (final Object object, final Remote client);

  private void registerClasses ()
  {
    for (final Class <?> classToRegister : classesToRegisterForNetworkSerialization)
    {
      server.register (classToRegister);
    }
  }

  private void addListener ()
  {
    server.add (new NetworkListener ()
    {
      @Override
      public void connected (final Remote client)
      {
        Arguments.checkIsNotNull (client, "client");

        onConnection (client);
      }

      @Override
      public void disconnected (final Remote client)
      {
        Arguments.checkIsNotNull (client, "client");

        onDisconnection (client);
      }

      @Override
      public void received (final Object object, final Remote client)
      {
        Arguments.checkIsNotNull (object, "object");
        Arguments.checkIsNotNull (client, "client");

        onCommunication (object, client);
      }
    });
  }

  private void startServer ()
  {
    server.start (tcpPort);
  }
}
