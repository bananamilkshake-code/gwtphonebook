/*
 * Copyright (C) 2015 Liza Lukicheva
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package com.bananamilkshake.server;

import com.bananamilkshake.ejb.Phones;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import net.customware.gwt.dispatch.client.standard.StandardDispatchService;
import net.customware.gwt.dispatch.server.DefaultActionHandlerRegistry;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.server.InstanceActionHandlerRegistry;
import net.customware.gwt.dispatch.server.SimpleDispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.Result;

/**
 * The server side implementation of the RPC service.
 */
public class PhoneBookService extends RemoteServiceServlet implements StandardDispatchService {
	private static final Logger LOG = Logger.getLogger(PhoneBookService.class.getName());

	private static Phones phones = null;
	
	static {
		try {
			Context context = new InitialContext();
			phones = (Phones) context.lookup("java:module/Phones");
		} catch (NamingException exception) {
			LOG.log(Level.WARNING, "Error on Phones EJB retrieving", exception);
		}
	}
	
	private final Dispatch dispatch;
	
	public PhoneBookService() {
		InstanceActionHandlerRegistry registry = new DefaultActionHandlerRegistry();
		registry.addHandler(new AddCardHandler(phones));
		registry.addHandler(new EditCardHandler(phones));
		registry.addHandler(new GetCardHandler(phones));
		registry.addHandler(new RemoveCardHandler(phones));
		registry.addHandler(new SearchHandler(phones));
		registry.addHandler(new ShowAllHandler(phones));
		this.dispatch = new SimpleDispatch(registry);
		
		LOG.info("PhoneBookService created");
	}

	@Override
	public Result execute(Action<?> action) throws DispatchException {
		LOG.info("New action " + action.getClass() + " received");
		
		try {
			return this.dispatch.execute(action);
		} catch (RuntimeException exception) {
			LOG.log(Level.WARNING, "Exception while executing " + action.getClass().getName() + ": "
				+ exception.getMessage(), exception);
			throw exception;
		}
	}
}
