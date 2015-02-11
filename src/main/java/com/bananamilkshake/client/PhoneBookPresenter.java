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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.Place;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

public class PhoneBookPresenter extends WidgetPresenter<PhoneBookPresenter.Display> {
	private Place PLACE = new Place("main");

	public interface Display extends WidgetDisplay {
		public HasClickHandlers getAddButton();
		public HasValue<String> getNameText();
		public HasValue<String> getPhoneText();
		
		public HasClickHandlers getShowAllButton();
		
		public HasClickHandlers getSearchButton();
		public HasValue<String> getSearchPatternText();
	}
	
	public PhoneBookPresenter(Display display, EventBus eventBus) {
		super(display, eventBus);
	}
	
	@Override
	protected void onBind() {
		display.getAddButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				PhoneBookPresenter.this.add();
			}
		});
		
		display.getSearchButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				PhoneBookPresenter.this.search();
			}
		});
		
		display.getShowAllButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				PhoneBookPresenter.this.showAll();
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
	}
	
	private void add() {
		String name = display.getNameText().getValue();
		String phone = display.getPhoneText().getValue();
	}

	private void search() {
		String toSearch = display.getSearchPatternText().getValue();
	}

	private void showAll() {
	}
}
