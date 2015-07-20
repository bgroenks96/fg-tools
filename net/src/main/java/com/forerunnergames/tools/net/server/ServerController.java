package com.forerunnergames.tools.net.server;

import com.forerunnergames.tools.common.controllers.Controller;
import com.forerunnergames.tools.net.client.ClientCommunicator;
import com.forerunnergames.tools.net.client.ClientConnector;

public interface ServerController extends Controller, ClientConnector, ClientCommunicator
{
}
