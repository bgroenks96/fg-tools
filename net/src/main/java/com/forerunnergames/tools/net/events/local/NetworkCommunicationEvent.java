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

package com.forerunnergames.tools.net.events.local;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.Strings;
import com.forerunnergames.tools.net.Remote;
import com.forerunnergames.tools.net.events.remote.RemoteEvent;

/**
 * Clients & servers should use this event to notify all local listeners that communication was received over the
 * network.
 *
 * This event is not intended to be sent over the network. Rather, it wraps an event (message) that was already sent
 * over the network.
 */
public abstract class NetworkCommunicationEvent <T extends Remote, U extends RemoteEvent> extends NetworkEvent <T>
{
  private final U message;

  public NetworkCommunicationEvent (final T sender, final U message)
  {
    super (sender);

    Arguments.checkIsNotNull (message, "message");

    this.message = message;
  }

  public final T getSender ()
  {
    return getRemote ();
  }

  public final U getMessage ()
  {
    return message;
  }

  @Override
  public String toString ()
  {
    return Strings.format ("{}: Sender: [{}] | Message: [{}]", getClass ().getSimpleName (), getSender (), message);
  }
}
