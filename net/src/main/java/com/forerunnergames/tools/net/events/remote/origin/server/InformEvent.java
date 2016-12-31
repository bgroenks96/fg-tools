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

package com.forerunnergames.tools.net.events.remote.origin.server;

import com.forerunnergames.tools.net.events.remote.origin.client.InformRequestEvent;

/**
 * Represents pseudo-request events sent by the server to a client which may contain information that the client must
 * respond to, but has the freedom to respond with various types of client requests, instead of just a single type.
 * Should be answered by an {@link InformRequestEvent}, which the server should then answer with a {@link SuccessEvent}
 * or {@link DeniedEvent}, but NOT a {@link ResponseSuccessEvent} NOR a {@link ResponseDeniedEvent}.
 *
 * Note:
 *
 * To simplify understanding, imagine the server is telling a client, "I'm going to inform you of what's going on, and
 * you can decide what kind of request (out of several valid options) you'd like to make based on that information. I'll
 * then answer your request with either success or denial."
 */
public interface InformEvent extends ServerQuestionEvent
{
}
