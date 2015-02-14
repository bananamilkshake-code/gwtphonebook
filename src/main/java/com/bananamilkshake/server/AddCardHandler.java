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

import com.bananamilkshake.dispatcher.AddCard;
import com.bananamilkshake.dispatcher.AddCardResult;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

public class AddCardHandler extends CardDataHandler<AddCard, AddCardResult> {
	@Override
	public Class<AddCard> getActionType() {
		return AddCard.class;
	}

	/**
	 * TODO: card creation
	 * @param action
	 * @param context
	 * @return
	 * @throws DispatchException 
	 */
	@Override
	public AddCardResult execute(AddCard action, ExecutionContext context) throws DispatchException {
		this.validateData(action.getName(), action.getPhone());
		
		return new AddCardResult(1);
	}

	@Override
	public void rollback(AddCard action, AddCardResult result, ExecutionContext context) throws DispatchException {
		// remove card by result.getAddedId();
	}
	
	public class AddCardException extends DispatchException {
		public AddCardException(String message) {
			super(message);
		}
	}
}
