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
import com.bananamilkshake.ejb.Phones;
import com.bananamilkshake.shared.Card;
import com.bananamilkshake.shared.PhonesDispatchException;
import com.bananamilkshake.validation.Validators;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

public class EditCardHandler extends PhonesActionHandler<EditCard, EditCardResult> {	
	public EditCardHandler(Phones phones) {
		super(phones);
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
		Validators.validateData(action.getNewName(), action.getNewPhone());
		
		try {
			Card toEdit = this.phones.get(action.getId());
			
			synchronized (toEdit) {
				String oldName = toEdit.getName();
				String oldPhone = toEdit.getPhone();
				if (!toEdit.getName().equals(action.getEditingName()) || !toEdit.getPhone().equals(action.getEditingPhone())) {
					throw new PhonesDispatchException("Old data card was tried to be edited");
				}
				
				toEdit.setName(action.getNewName());
				toEdit.setPhone(action.getNewPhone());
				
				this.phones.edit(toEdit);
				
				return new EditCardResult(oldName, oldPhone, toEdit.getId(), action.getNewName(), action.getNewPhone());
			}
		} catch (Exception exception) {
			throw new PhonesDispatchException(exception);
		}
	}

	@Override
	public void rollback(EditCard action, EditCardResult result, ExecutionContext context) throws DispatchException {
		try {
			Card edited = this.phones.get(result.getId());
			
			synchronized (edited) {
				if (!edited.getName().equals(result.getSetName()) || !edited.getPhone().equals(result.getSetPhone())) {
					throw new PhonesDispatchException("Tried to rollback other changes");
				}
				
				edited.setName(result.getOldName());
				edited.setPhone(result.getOldPhone());
				
				this.phones.edit(edited);
			}
		} catch (Exception exception) {
			throw new PhonesDispatchException(exception);
		}
	}
}
