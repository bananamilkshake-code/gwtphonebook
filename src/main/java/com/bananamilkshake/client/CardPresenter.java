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

import com.bananamilkshake.dispatcher.GetCard;
import com.bananamilkshake.dispatcher.GetCardResult;
import com.bananamilkshake.shared.Card;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.RootPanel;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.Place;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

public class CardPresenter extends WidgetPresenter<CardPresenter.Display> {
	private final DispatchAsync dispatchAsync;
	
	public static final Place PLACE = new Place("card");
	
	public static final String PARAM_ID = "id";
	
	public interface Display extends WidgetDisplay{
		HasValue<String> getNameField();
		HasValue<String> getPhoneField();
		
		HasClickHandlers getEditButton();
		HasClickHandlers getRemoveButton();
	}

	public CardPresenter(CardPresenter.Display display, EventBus eventBus, final DispatchAsync dispatchAsync) {
		super(display, eventBus);
		this.dispatchAsync = dispatchAsync;
	}

	@Override
	protected void onBind() {
		this.display.getEditButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				CardPresenter.this.edit();
			}
		});
		
		this.display.getRemoveButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				CardPresenter.this.remove();
			}
		});
	}

	@Override
	protected void onUnbind() {
	}

	@Override
	public Place getPlace() {
		return PLACE;
	}

	@Override
	protected void onPlaceRequest(PlaceRequest request) {		
		String idVal = request.getParameter(PARAM_ID, null);
		if (idVal == null)
			Window.alert("Empty card id in request parametr");
		
		try {
			int cardId = Integer.valueOf(idVal);
			this.showCard(cardId);
		} catch (NumberFormatException exception) {
			Window.alert("Wrong card id format for \"" + idVal + "\" (id must be an integer)");
		}
	}

	@Override
	public void refreshDisplay() {
	}

	@Override
	public void revealDisplay() {
		RootPanel.get().clear();
		RootPanel.get().add(this.display.asWidget());
	}

	private void showCard(int cardId) {
		this.dispatchAsync.execute(new GetCard(cardId), new AsyncCallback<GetCardResult>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("There is no card with id");
			}

			@Override
			public void onSuccess(GetCardResult result) {
				Card card = result.getCard();
				
				CardPresenter.this.display.getNameField().setValue(card.getName());
				CardPresenter.this.display.getPhoneField().setValue(card.getPhone());
			}	
		});
	}

	private void edit() {
		String newName = this.display.getNameField().getValue();
		String newPhone = this.display.getPhoneField().getValue();
		
		if (!CardFieldsVerification.verifyCardFields(newName, newPhone))
			return;
	}

	private void remove() {
	}
}
