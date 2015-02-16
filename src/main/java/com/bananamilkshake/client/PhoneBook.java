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

import com.google.gwt.core.client.EntryPoint;
import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;
import net.customware.gwt.presenter.client.DefaultEventBus;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.Presenter;
import net.customware.gwt.presenter.client.place.PlaceManager;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.place.PlaceRequestEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PhoneBook implements EntryPoint {	
	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		EventBus eventBus = new DefaultEventBus();
		DispatchAsync dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());
		
		Presenter []presenters = new Presenter[] {
			new PhoneBookPresenter(new PhoneBookPanel(), eventBus, dispatchAsync),
			new CardsListPresenter(new CardsListPanel(), eventBus, dispatchAsync),
			new CardPresenter(new CardPanel(), eventBus, dispatchAsync)
		};
		
		for (Presenter presenter : presenters) {
			presenter.bind();
		}
		
		PlaceManager placeManager = new PlaceManager(eventBus);
		placeManager.fireCurrentPlace();
		
		eventBus.fireEvent(new PlaceRequestEvent(new PlaceRequest(PhoneBookPresenter.PLACE)));
	}
}
