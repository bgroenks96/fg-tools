package com.forerunnergames.tools.net.events.remote.origin.client;

import com.forerunnergames.tools.net.events.remote.RequestEvent;

/**
 * A client is requesting something from the server.
 *
 * This event is intended to be sent over the network.
 */
public interface ClientRequestEvent extends ClientEvent, RequestEvent
{
}
