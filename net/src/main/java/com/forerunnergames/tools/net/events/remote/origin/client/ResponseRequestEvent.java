package com.forerunnergames.tools.net.events.remote.origin.client;

import com.forerunnergames.tools.net.events.remote.ResponseEvent;

/**
 * A client is responding to a request from the server, while simultaneously requesting a confirmation of said response
 * from the server.
 */
public interface ResponseRequestEvent extends ClientRequestEvent, ResponseEvent
{
}