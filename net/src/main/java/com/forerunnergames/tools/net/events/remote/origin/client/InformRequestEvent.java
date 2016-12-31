/*
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

package com.forerunnergames.tools.net.events.remote.origin.client;

import com.forerunnergames.tools.net.events.remote.origin.server.DeniedEvent;
import com.forerunnergames.tools.net.events.remote.origin.server.InformEvent;
import com.forerunnergames.tools.net.events.remote.origin.server.SuccessEvent;

/**
 * Represents request events sent by a client to the server after successfully joining the game as a player, that are
 * answers to {@link InformEvent}. They are also questions that should be answered by the server with a
 * {@link SuccessEvent} or {@link DeniedEvent}.
 *
 * @see {@link com.forerunnergames.tools.net.events.remote.origin.server.InformEvent} for a more detailed explanation.
 */
public interface InformRequestEvent <T extends InformEvent> extends ClientRequestEvent, ClientAnswerEvent <T>
{
}
