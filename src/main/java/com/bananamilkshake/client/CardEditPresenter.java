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

package com.bananamilkshake.client;

import com.bananamilkshake.dispatcher.EditCard;
import com.bananamilkshake.dispatcher.EditCardResult;
import com.bananamilkshake.dispatcher.RemoveCard;
import com.bananamilkshake.dispatcher.RemoveCardResult;
import com.bananamilkshake.shared.Card;
import com.bananamilkshake.shared.FieldVerifier;
import com.google.gwt.user.client.rpc.AsyncCallback;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;

public abstract class CardEditPresenter <D extends WidgetDisplay> extends BasePresenter<D> {
	protected String currentCardName;
	protected String currentCardPhone;
	
	public CardEditPresenter(D display, EventBus eventBus, DispatchAsync dispatchAsync) {
		super(display, eventBus, dispatchAsync);
	}
	
	protected abstract void onEditCardSuccess();
	
	protected abstract void onEditCardFailure(Throwable exception);
	
	protected abstract void onRemoveCardSuccess();
	
	protected abstract void onRemoveCardFailure(Throwable exception);
	
	protected abstract void onCardLoaded(Card card);
	
	protected void updateCurrentCardValues(String name, String phone) {
		this.currentCardName = name;
		this.currentCardPhone = phone;
	}
	
	protected void editCard(int id, String newName, String newPhone) {
		if (!this.verifyCardFields(newName, newPhone))
			return;
		
		this.dispatchAsync.execute(new EditCard(id, this.currentCardName, this.currentCardPhone, newName, newPhone), new AsyncCallback<EditCardResult>() {
			@Override
			public void onFailure(Throwable exception) {
				CardEditPresenter.this.onEditCardFailure(exception);
			}

			@Override
			public void onSuccess(EditCardResult result) {
				CardEditPresenter.this.onEditCardSuccess();
			}
		});
	}
	
	protected void removeCard(int id) {
		this.dispatchAsync.execute(new RemoveCard(id), new AsyncCallback<RemoveCardResult>() {
			@Override
			public void onFailure(Throwable caught) {
				CardEditPresenter.this.onRemoveCardFailure(caught);
			}

			@Override
			public void onSuccess(RemoveCardResult result) {
				CardEditPresenter.this.onRemoveCardSuccess();
			}
		});
	}
	
	/**
	 * Checks that name and phone are valid.
	 * 
	 * If name or phone are invalid shows Window.alert with message.
	 * 
	 * @param name name to check
	 * @param phone phone to check
	 * @return true if all fields are valid
	 */
	public boolean verifyCardFields(String name, String phone) {
		if (!FieldVerifier.isValidName(name)) {
			this.printInfo("Name " + name + " is invalid (must not be empty)");
			return false;
		}
		
		if (!FieldVerifier.isValidPhone(phone)) {
			this.printInfo("Phone " + phone + " is invalid. It must be 11 digits without any other characters");
			return false;
		}
		
		return true;
	}
	
	public int convertId(String idVal) {
		if (idVal == null || idVal.isEmpty()) {
			this.printInfo("Id is empty");
		}
		
		return Integer.valueOf(idVal);
	}
}
