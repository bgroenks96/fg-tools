package com.forerunnergames.tools.net.events.remote.origin.server;

import com.forerunnergames.tools.net.events.remote.DirectEvent;

/**
 * The server is answering a RequestEvent from a client with a positive confirmation and is sent to only the client who
 * made the request.
 *
 * This event is intended to be sent over the network.
 */
public interface DirectSuccessEvent extends DirectEvent, SuccessEvent
{
}
