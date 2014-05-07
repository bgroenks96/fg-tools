package com.forerunnergames.tools.net;

import com.forerunnergames.tools.AbstractController;
import com.forerunnergames.tools.Arguments;

import java.io.IOException;

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

  public abstract void onConnection (final Remote client);
  public abstract void onDisconnection (final Remote client);
  public abstract void onCommunication (final Object object, final Remote client);

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
    getServer().register (type);
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

  public void sendToAll (final Object object)
  {
    Arguments.checkIsNotNull (object, "object");

    checkIsInitialized();

    if (! serverIsRunning())
    {
      return;
    }

    getServer().sendToAll (object);
  }

  public void sendToAllExcept (final Remote client, final Object object)
  {
    Arguments.checkIsNotNull (client, "client");
    Arguments.checkIsNotNull (object, "object");

    checkIsInitialized();

    if (! serverIsRunning())
    {
      return;
    }

    getServer().sendToAllExcept (client, object);
  }

  public void sendTo (final Remote client, final Object object)
  {
    Arguments.checkIsNotNull (client, "client");
    Arguments.checkIsNotNull (object, "object");

    checkIsInitialized();

    if (! serverIsRunning())
    {
      return;
    }

    getServer().sendTo (client, object);
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
    disconnectAll();
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

  private void disconnectAll()
  {
    getServer().disconnectAll();
  }

  private void stopServer()
  {
    getServer().stop();
  }

  public void disconnect (final Remote client)
  {
    Arguments.checkIsNotNull (client, "client");

    getServer().disconnect (client);
  }
}
