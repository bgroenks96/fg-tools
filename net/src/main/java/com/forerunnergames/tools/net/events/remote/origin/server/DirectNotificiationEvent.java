package com.forerunnergames.tools.net.events.remote.origin.server;

import com.forerunnergames.tools.net.events.remote.DirectEvent;

/**
 * A notification event sent from server to a single client.
 *
 * This event is intended to be sent over the network.
 */
public interface DirectNotificiationEvent extends ServerNotificationEvent, DirectEvent
{
}
