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

import com.bananamilkshake.dispatcher.RemoveCard;
import com.bananamilkshake.dispatcher.RemoveCardResult;
import com.bananamilkshake.ejb.Phones;
import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

public class RemoveCardHandler implements ActionHandler<RemoveCard, RemoveCardResult> {
	private Phones phones;
	
	public RemoveCardHandler(Phones phones) {
		this.phones = phones;
	}
	
	@Override
	public Class<RemoveCard> getActionType() {
		return RemoveCard.class;
	}

	@Override
	public RemoveCardResult execute(RemoveCard action, ExecutionContext context) throws DispatchException {
		return new RemoveCardResult(this.phones.remove(action.getToRemove()));
	}

	@Override
	public void rollback(RemoveCard action, RemoveCardResult result, ExecutionContext context) throws DispatchException {
		this.phones.restore(result.getRemovedCard());
	}

}
