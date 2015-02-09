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
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PhoneBook implements EntryPoint {

	private final Messages messages = GWT.create(Messages.class);
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		Button add = new Button(messages.addButton(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("Add new phone");
			}
		});
		
		Button edit = new Button(messages.editButton(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("Edit");
			}
		});
		
		Button remove = new Button(messages.removeButton(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("Remove");
			}
		});
		
		Button showAll = new Button(messages.showAllButton(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("Show");
			}
		});
		
		Button search = new Button(messages.searchButton(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("Search");
			}
		});
		
		RootPanel.get("addButtonContainer").add(add);
		RootPanel.get("editButtonContainer").add(edit);
		RootPanel.get("removeButtonContainer").add(remove);
		RootPanel.get("showAllButtonContainer").add(showAll);
		RootPanel.get("searchButtonContainer").add(search);
	}
}
