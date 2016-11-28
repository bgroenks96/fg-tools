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

package com.forerunnergames.tools.net.events;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Classes;
import com.forerunnergames.tools.net.Remote;
import com.forerunnergames.tools.net.client.remote.RemoteServer;
import com.forerunnergames.tools.net.events.local.ClientCommunicationEvent;
import com.forerunnergames.tools.net.events.local.ClientConnectionEvent;
import com.forerunnergames.tools.net.events.local.ClientDisconnectionEvent;
import com.forerunnergames.tools.net.events.local.NetworkCommunicationEvent;
import com.forerunnergames.tools.net.events.local.ServerCommunicationEvent;
import com.forerunnergames.tools.net.events.local.ServerConnectionEvent;
import com.forerunnergames.tools.net.events.local.ServerDisconnectionEvent;
import com.forerunnergames.tools.net.events.remote.RemoteEvent;
import com.forerunnergames.tools.net.server.remote.RemoteClient;

public final class EventFluency
{
  public static RemoteClient clientFrom (final ClientCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getSender ();
  }

  public static RemoteClient clientFrom (final ClientConnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getRemote ();
  }

  public static RemoteClient clientFrom (final ClientDisconnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getRemote ();
  }

  public static <T extends Remote, U extends RemoteEvent> U messageFrom (final NetworkCommunicationEvent <T, U> event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getMessage ();
  }

  public static RemoteServer serverFrom (final ServerCommunicationEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getSender ();
  }

  public static RemoteServer serverFrom (final ServerConnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getRemote ();
  }

  public static RemoteServer serverFrom (final ServerDisconnectionEvent event)
  {
    Arguments.checkIsNotNull (event, "event");

    return event.getRemote ();
  }

  private EventFluency ()
  {
    Classes.instantiationNotAllowed ();
  }
}
