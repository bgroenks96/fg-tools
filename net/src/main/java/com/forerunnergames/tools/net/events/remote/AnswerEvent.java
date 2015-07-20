package com.forerunnergames.tools.net.events.remote;

/**
 * Answers a question that was posed as a QuestionEvent.
 *
 * It can either be a client answering a server's question, or vice versa.
 *
 * This event is intended to be sent over the network.
 */
public interface AnswerEvent extends RemoteEvent
{
}
