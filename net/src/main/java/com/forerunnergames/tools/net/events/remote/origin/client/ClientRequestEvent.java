package com.forerunnergames.tools.net.events.remote.origin.client;

import com.forerunnergames.tools.net.events.remote.RequestEvent;

/**
 * The server is requesting something from a client.
 *
 * This event is intended to be sent over the network.
 */
public interface ClientRequestEvent extends ClientEvent, RequestEvent
{
}
