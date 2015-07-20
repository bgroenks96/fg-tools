package com.forerunnergames.tools.net.events.remote.origin.server;

import com.forerunnergames.tools.net.events.remote.AnswerEvent;

/**
 * The server is answering a RequestEvent from a client with a negative confirmation.
 *
 * This event is intended to be sent over the network.
 */
public interface DeniedEvent <T> extends AnswerEvent
{
  T getReason ();
}
