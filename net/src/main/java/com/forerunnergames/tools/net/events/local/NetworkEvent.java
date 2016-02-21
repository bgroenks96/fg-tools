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
import com.forerunnergames.tools.net.Remote;

/**
 * Clients & servers should use this event as a base class for notifying all local listeners that a network-related
 * event occurred. It contains information about the remote end of the network connection.
 *
 * This event is not intended to be sent over the network.
 */
public abstract class NetworkEvent implements LocalEvent
{
  private final Remote remote;

  public NetworkEvent (final Remote remote)
  {
    Arguments.checkIsNotNull (remote, "remote");

    this.remote = remote;
  }

  public Remote getRemote ()
  {
    return remote;
  }

  @Override
  public String toString ()
  {
    return String.format ("%1$s: Remote : %2$s", getClass ().getSimpleName (), remote);
  }
}
