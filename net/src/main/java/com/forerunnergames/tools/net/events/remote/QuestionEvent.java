package com.forerunnergames.tools.net.events.remote;

/**
 * Asks a question, expecting an answer as an AnswerEvent.
 *
 * It can either be a client asking a question of the server, or vice versa.
 *
 * This event is intended to be sent over the network.
 */
public interface QuestionEvent extends RemoteEvent
{
}
