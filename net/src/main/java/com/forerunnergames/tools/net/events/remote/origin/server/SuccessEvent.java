package com.forerunnergames.tools.net.events.remote.origin.server;

import com.forerunnergames.tools.net.events.remote.AnswerEvent;

/**
 * The server is answering a RequestEvent from a client with a positive confirmation.
 *
 * This event is intended to be sent over the network.
 */
public interface SuccessEvent extends ServerEvent, AnswerEvent
{
}
