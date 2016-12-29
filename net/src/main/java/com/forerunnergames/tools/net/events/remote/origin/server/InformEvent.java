/*
 * Copyright © 2016 Forerunner Games, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
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
