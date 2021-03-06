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

import com.bananamilkshake.dispatcher.ShowAll;
import com.bananamilkshake.dispatcher.CardsListResult;
import com.bananamilkshake.dispatcher.Search;
import com.bananamilkshake.shared.Card;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.List;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.Place;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;

public class CardsListPresenter extends BasePresenter<CardsListPresenter.Display> {
	public static final Place PLACE = new Place("list");
	
	public static final String PARAM_ALL = "all";
	public static final String PARAM_PATTERN = "pattern";
	
	public interface Display extends WidgetDisplay {
		HasClickHandlers getBackButton();
		VerticalPanel getCards();
	}
	
	public CardsListPresenter(Display display, EventBus eventBus, final DispatchAsync dispatchAsync) {
		super(display, eventBus, dispatchAsync);
	}
	
	@Override
	protected void onBind() {
		this.display.getBackButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				History.back();
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
		this.display.getCards().clear();
		
		String paramAllValue = request.getParameter(PARAM_ALL, Boolean.toString(true));
		if (Boolean.valueOf(paramAllValue)) {
			this.dispatchAsync.execute(new ShowAll(), new CardsListAsyncCallback());
		} else {
			String pattern = request.getParameter(PARAM_PATTERN, null);		
			try {
				this.dispatchAsync.execute(new Search(pattern), new CardsListAsyncCallback());
			} catch (RuntimeException exception) {
				this.printInfo("Wrong pattern to search");
			}
		}
	}

	@Override
	public void refreshDisplay() {
	}

	private void showCards(List<Card> cards) {		
		for (Card card : cards) {			
			PlaceRequest linkTo = CardPresenter.PLACE.requestWith(CardPresenter.PARAM_ID, String.valueOf(card.getId()));
			
			String cardStr = card.getName() + ": " + card.getPhone();
			
			Hyperlink cardLink = new Hyperlink(cardStr, linkTo.toString());
			
			this.display.getCards().add(cardLink);
		}
	}
	
	private class CardsListAsyncCallback implements AsyncCallback<CardsListResult> {
		@Override
		public void onFailure(Throwable exception) {
			CardsListPresenter.this.printInfo("Exception " + exception.getClass() + " on cards request: " + exception.getMessage());
		}

		@Override
		public void onSuccess(CardsListResult result) {
			CardsListPresenter.this.showCards(result.getCards());
		}
	}
}
