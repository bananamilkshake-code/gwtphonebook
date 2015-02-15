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

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

public abstract class BasePresenter <D extends WidgetDisplay> extends WidgetPresenter<D> {
	private static final String PRESENTER_ELEMENT_ID = "presenter";
	private static final String ACTIONS_LOGGER_ELEMENT_ID = "actions_logger";
	
	private final VerticalPanel actions_logger;
	
	protected final DispatchAsync dispatchAsync;
	
	public BasePresenter(D display, EventBus eventBus, DispatchAsync dispatchAsync) {
		super(display, eventBus);
		this.dispatchAsync = dispatchAsync;
		
		this.actions_logger = new VerticalPanel();
	}
	
	protected abstract void placeRequested(PlaceRequest request);
	
	@Override
	public void revealDisplay() {
		this.actions_logger.clear();
		
		RootPanel.get(PRESENTER_ELEMENT_ID).clear();
		RootPanel.get(PRESENTER_ELEMENT_ID).add(this.display.asWidget());
		
		RootPanel.get(ACTIONS_LOGGER_ELEMENT_ID).clear();
		RootPanel.get(ACTIONS_LOGGER_ELEMENT_ID).add(this.actions_logger.asWidget());
	}
	
	protected void printInfo(String message) {
		this.actions_logger.add(new HTML(message));
	}
	
	@Override
	protected void onPlaceRequest(PlaceRequest request) {
		History.newItem(request.toString());
		this.placeRequested(request);
	}
}
