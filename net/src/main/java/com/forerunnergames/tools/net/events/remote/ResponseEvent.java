package com.forerunnergames.tools.net.events.remote;

/**
 * A ResponseEvent can either be
 *
 * 1) A client responding to a request from the server, while simultaneously requesting confirmation of said response
 * from the server,
 *
 * or,
 *
 * 2) The server confirming the response with success or denial.
 *
 * This event is intended to be sent over the network.
 */
public interface ResponseEvent extends AnswerEvent
{
}
