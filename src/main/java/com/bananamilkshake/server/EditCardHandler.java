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

import com.bananamilkshake.dispatcher.EditCard;
import com.bananamilkshake.dispatcher.EditCardResult;
import com.bananamilkshake.shared.Card;
import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

public class EditCardHandler implements ActionHandler<EditCard, EditCardResult> {	
	public EditCardHandler() {
	}
	
	@Override
	public Class<EditCard> getActionType() {
		return EditCard.class;
	}

	/**
	 * @todo Need to get card, synchrony on it, retrieve its current name and phone, 
	 * change name and phone, paste new card, old values in result
	 * @param action
	 * @param context
	 * @return
	 * @throws DispatchException 
	 */
	@Override
	public EditCardResult execute(EditCard action, ExecutionContext context) throws DispatchException {
		Card card = new Card(1, "a", "b");
		
		synchronized (card) {
			String oldName = card.getName();
			String oldPhone = card.getPhone();
			
			card.setName(action.getNewName());
			card.setPhone(action.getNewPhone());
			
			return new EditCardResult(oldName, oldPhone, card);
		}
	}

	@Override
	public void rollback(EditCard action, EditCardResult result, ExecutionContext context) throws DispatchException {
		Card card = result.getCurrent();
		
		synchronized (card) {
			card.setName(result.getOldName());
			card.setPhone(result.getOldPhone());
		}
	}
}