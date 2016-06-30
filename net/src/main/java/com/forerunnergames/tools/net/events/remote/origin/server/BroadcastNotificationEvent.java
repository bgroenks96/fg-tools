package com.forerunnergames.tools.net.events.remote.origin.server;

/**
 * A notification event sent from server to all clients.
 *
 * This event is intended to be sent over the network.
 */
public interface BroadcastNotificationEvent extends ServerNotificationEvent, BroadcastEvent
{
}
