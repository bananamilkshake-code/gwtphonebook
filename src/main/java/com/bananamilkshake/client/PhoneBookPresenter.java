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

import com.bananamilkshake.dispatcher.AddCard;
import com.bananamilkshake.dispatcher.AddCardResult;
import com.bananamilkshake.shared.FieldVerifier;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.RootPanel;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.Place;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.place.PlaceRequestEvent;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;

public class PhoneBookPresenter extends CardEditPresenter<PhoneBookPresenter.Display> {
	private static final Place PLACE = new Place("");
	
	public interface Display extends WidgetDisplay {
		public HasClickHandlers getAddButton();
		public HasValue<String> getNameText();
		public HasValue<String> getPhoneText();
		
		public HasClickHandlers getShowAllButton();
		
		public HasClickHandlers getSearchButton();
		public HasValue<String> getSearchPatternText();
		
		public HasClickHandlers getEditButton();
		public HasValue<String> getEditIdText();
		public HasValue<String> getEditNameText();
		public HasValue<String> getEditPhoneText();
		
		public HasClickHandlers getRemoveButton();
		public HasValue<String> getRemoveIdText();
	}
	
	public PhoneBookPresenter(Display display, EventBus eventBus, final DispatchAsync dispatchAsync) {
		super(display, eventBus, dispatchAsync);
	}

	@Override
	protected void onEditCardSuccess() {
		RootPanel.get().add(new HTML("Card successfully edited"));
	}

	@Override
	protected void onEditCardFailure(Throwable exception) {
		RootPanel.get().add(new HTML("Exception on editing card: " +exception.getMessage()));
	}

	@Override
	protected void onRemoveCardSuccess() {
		Window.alert("Card removed");
	}

	@Override
	protected void onRemoveCardFailure(Throwable exception) {
		RootPanel.get().add(new HTML("Exception on removing card: " + exception.getMessage()));
	}
	
	@Override
	protected void onBind() {
		this.display.getAddButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				PhoneBookPresenter.this.add();
			}
		});
		
		this.display.getSearchButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				PhoneBookPresenter.this.search();
			}
		});
		
		this.display.getShowAllButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				PhoneBookPresenter.this.showAll();
			}
		});
		
		this.display.getEditButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				PhoneBookPresenter.this.edit();
			}
		});
		
		display.getRemoveButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				PhoneBookPresenter.this.remove();
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
	}

	@Override
	public void refreshDisplay() {
	}

	@Override
	public void revealDisplay() {
		RootPanel.get().clear();
		RootPanel.get().add(this.display.asWidget());
	}
	
	private void add() {
		String name = display.getNameText().getValue();
		String phone = display.getPhoneText().getValue();
		
		if (!CardFieldsVerification.verifyCardFields(name, phone))
			return;
		
		this.dispatchAsync.execute(new AddCard(name, phone), new AsyncCallback<AddCardResult>() {
			@Override
			public void onFailure(Throwable exception) {
				Window.alert("Error on adding card: " + exception.getMessage());
			}

			@Override
			public void onSuccess(AddCardResult result) {
				Window.alert("Card added. New id is " + result.getAddedCard().getId());
			}
		});
	}

	private void search() {
		String toSearch = display.getSearchPatternText().getValue();
		if (!FieldVerifier.isValidSearchPattern(toSearch)) {
			Window.alert("Invalid search pattern. Must be a regular expression.");
			return;
		}
		
		this.eventBus.fireEvent(new PlaceRequestEvent(new PlaceRequest(CardsListPresenter.PLACE)
				.with(CardsListPresenter.PARAM_ALL, "false")
				.with(CardsListPresenter.PARAM_PATTERN, toSearch)));
	}

	private void showAll() {
		this.eventBus.fireEvent(new PlaceRequestEvent(new PlaceRequest(CardsListPresenter.PLACE)
				.with(CardsListPresenter.PARAM_ALL, "true")));
	}

	private void edit() {
		String idVal = this.display.getEditIdText().getValue();
		String newName = this.display.getEditNameText().getValue();
		String newPhone = this.display.getEditPhoneText().getValue();
		
		int id = CardFieldsVerification.convertId(idVal);
		
		this.editCard(id, newName, newPhone);
	}

	private void remove() {
		String idVal = this.display.getRemoveIdText().getValue();
		int id = CardFieldsVerification.convertId(idVal);
		
		this.removeCard(id);
	}
}
