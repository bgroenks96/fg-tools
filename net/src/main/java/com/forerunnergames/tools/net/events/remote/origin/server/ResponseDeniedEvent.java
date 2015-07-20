package com.forerunnergames.tools.net.events.remote.origin.server;

import com.forerunnergames.tools.net.events.remote.ResponseEvent;

/**
 * The server is sending a negative confirmation of the client's response to the server's original request.
 *
 * This event is intended to be sent over the network.
 */
public interface ResponseDeniedEvent <T> extends ServerEvent, ResponseEvent, DeniedEvent <T>
{
}
