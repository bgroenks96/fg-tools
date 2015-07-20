package com.forerunnergames.tools.net.client;

import com.forerunnergames.tools.common.controllers.Controller;
import com.forerunnergames.tools.net.server.ServerCommunicator;
import com.forerunnergames.tools.net.server.ServerConnector;

public interface ClientController extends Controller, ServerConnector, ServerCommunicator
{
}
