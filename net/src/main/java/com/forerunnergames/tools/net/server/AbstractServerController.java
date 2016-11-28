/*
 * Copyright © 2011 - 2013 Aaron Mahan
 * Copyright © 2013 - 2016 Forerunner Games, LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.forerunnergames.tools.net.server;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.controllers.ControllerAdapter;
import com.forerunnergames.tools.net.server.remote.RemoteClient;
import com.forerunnergames.tools.net.server.remote.RemoteClientListener;

import com.google.common.collect.ImmutableSet;

public abstract class AbstractServerController extends ControllerAdapter implements ServerController
{
  private final Server server;
  private final int port;
  private final ImmutableSet <Class <?>> classesToRegisterForNetworkSerialization;

  protected AbstractServerController (final Server server,
                                      final int port,
                                      final ImmutableSet <Class <?>> classesToRegisterForNetworkSerialization)
  {
    Arguments.checkIsNotNull (server, "server");
    Arguments.checkIsNotNull (classesToRegisterForNetworkSerialization, "classesToRegisterForNetworkSerialization");
    Arguments.checkIsNotNegative (port, "port");
    Arguments.checkHasNoNullElements (classesToRegisterForNetworkSerialization,
                                      "classesToRegisterForNetworkSerialization");

    this.server = server;
    this.port = port;
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
  public boolean isConnected (final RemoteClient client)
  {
    Arguments.checkIsNotNull (client, "client");

    return server.isConnected (client);
  }

  @Override
  public void disconnect (final RemoteClient client)
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
  public void sendTo (final RemoteClient client, final Object object)
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
  public void sendToAllExcept (final RemoteClient client, final Object object)
  {
    Arguments.checkIsNotNull (client, "client");
    Arguments.checkIsNotNull (object, "object");

    server.sendToAllExcept (client, object);
  }

  protected abstract void onConnection (final RemoteClient client);

  protected abstract void onDisconnection (final RemoteClient client);

  protected abstract void onCommunication (final RemoteClient client, final Object object);

  private void registerClasses ()
  {
    for (final Class <?> classToRegister : classesToRegisterForNetworkSerialization)
    {
      server.register (classToRegister);
    }
  }

  private void addListener ()
  {
    server.add (new RemoteClientListener ()
    {
      @Override
      public void connected (final RemoteClient remote)
      {
        Arguments.checkIsNotNull (remote, "client");

        onConnection (remote);
      }

      @Override
      public void disconnected (final RemoteClient remote)
      {
        Arguments.checkIsNotNull (remote, "client");

        onDisconnection (remote);
      }

      @Override
      public void received (final RemoteClient sender, final Object object)
      {
        Arguments.checkIsNotNull (sender, "client");
        Arguments.checkIsNotNull (object, "object");

        onCommunication (sender, object);
      }
    });
  }

  private void startServer ()
  {
    server.start (port);
  }
}
