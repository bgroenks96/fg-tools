package com.forerunnergames.tools.net;

import com.forerunnergames.tools.AbstractController;
import com.forerunnergames.tools.Arguments;

import java.io.IOException;
import java.net.InetSocketAddress;

public abstract class AbstractServerController extends AbstractController <Server>
{
  private final int tcpPort;
  private final Class[] classesToRegister;

  public AbstractServerController (final Server server, final int tcpPort, final Class... classesToRegisterForNetworkSerialization)
  {
    super (server);

    Arguments.checkIsNotNull (classesToRegisterForNetworkSerialization, "classesToRegisterForNetworkSerialization");
    Arguments.checkHasNoNullElements (classesToRegisterForNetworkSerialization, "classesToRegisterForNetworkSerialization");

    this.tcpPort = tcpPort;
    classesToRegister = classesToRegisterForNetworkSerialization;
  }

  public abstract void onClientConnection (final int connectionId, final InetSocketAddress address);
  public abstract void onClientDisconnection (final int connectionId, final InetSocketAddress address);
  public abstract void onClientCommunication (final Object remoteObject, final int connectionId, final InetSocketAddress address);

  @Override
  public void initialize()
  {
    checkIsNotInitialized();

    registerClasses();
    addListener();
    startServer();

    setInitialized (true);
  }

  private void registerClasses()
  {
    for (Class classToRegister : classesToRegister)
    {
      registerClass (classToRegister);
    }
  }

  private void registerClass (final Class <?> type)
  {
    getServer().registerClass (type);
  }

  private Server getServer()
  {
    return getControllee();
  }

  private void startServer()
  {
    try
    {
      getServer().start (tcpPort);
    }
    catch (IOException e)
    {
      throw new RuntimeException ("Could not start server on tcp port: " + tcpPort + ".", e);
    }
  }

  private void addListener()
  {
    getServer().addListener (new NetworkListener()
    {
      @Override
      public void connected (final int connectionId, final InetSocketAddress remoteAddress)
      {
        onClientConnection (connectionId, remoteAddress);
      }

      @Override
      public void disconnected (final int connectionId, InetSocketAddress remoteAddress)
      {
        onClientDisconnection (connectionId, remoteAddress);
      }

      @Override
      public void received (final Object remoteObject, final int connectionId, final InetSocketAddress remoteAddress)
      {
        Arguments.checkIsNotNull (remoteObject, "object");

        onClientCommunication (remoteObject, connectionId, remoteAddress);
      }
    });
  }

  public void sendToAllClients (final Object object)
  {
    Arguments.checkIsNotNull (object, "object");

    checkIsInitialized();

    if (! serverIsRunning())
    {
      return;
    }

    getServer().sendToAllClients (object);
  }

  public void sendToAllClientsExcept (final int connectionId, final Object object)
  {
    Arguments.checkIsNotNull (object, "object");

    checkIsInitialized();

    if (! serverIsRunning())
    {
      return;
    }

    getServer().sendToAllClientsExcept (connectionId, object);
  }

  public void sendToClient (final int connectionId, final Object object)
  {
    Arguments.checkIsNotNull (object, "object");

    checkIsInitialized();

    if (! serverIsRunning())
    {
      return;
    }

    getServer().sendToClient (connectionId, object);
  }

  @Override
  public boolean shouldShutDown()
  {
    return false;
  }

  @Override
  public void shutDown()
  {
    if (! serverIsRunning())
    {
      return;
    }

    shutDownServer();
  }

  public boolean serverIsRunning()
  {
    return getServer().isRunning();
  }

  private void shutDownServer()
  {
    closeAllConnections();
    stopServer();
  }

  @Override
  public void update()
  {
    if (! isInitialized())
    {
      return;
    }

    updateServer();
  }

  private void updateServer()
  {
    getServer().update();
  }

  private void closeAllConnections()
  {
    getServer().closeAllConnections();
  }

  private void stopServer()
  {
    getServer().stop();
  }
}
