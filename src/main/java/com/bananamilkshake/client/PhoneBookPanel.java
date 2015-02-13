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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;

public class PhoneBookPanel extends Composite implements PhoneBookPresenter.Display {
	private final Messages messages = GWT.create(Messages.class);
	
	private final TabPanel tabPanel = new TabPanel();
	
	private final Button addButton = new Button();
	private final TextBox nameText = new TextBox();
	private final TextBox phoneText = new TextBox();
	
	private final Button searchButton = new Button();
	private final TextBox searchPatternText = new TextBox();
	
	private final Button showAllButton = new Button();
	
	private enum Tabs {
		ADD ("add"),
		SHOW_ALL ("show_all"),
		SEARCH ("search");
		
		final String asString;
		
		Tabs(final String asString) {
			this.asString = asString;
		}
	};
	
	public PhoneBookPanel() {
		this.addButton.setText(messages.addButton());
		this.searchButton.setText(messages.searchButton());
		this.showAllButton.setText(messages.showAllButton());
		
		initWidget(this.tabPanel);
		this.createAddTab();
		this.createShowAllTab();
		this.createSearchTab();
		
		this.tabPanel.selectTab(0);
	}

	@Override
	public HasClickHandlers getAddButton() {
		return this.addButton;
	}

	@Override
	public HasValue<String> getNameText() {
		return this.nameText;
	}

	@Override
	public HasValue<String> getPhoneText() {
		return this.phoneText;
	}

	@Override
	public HasClickHandlers getShowAllButton() {
		return this.showAllButton;
	}

	@Override
	public HasClickHandlers getSearchButton() {
		return this.searchButton;
	}

	@Override
	public HasValue<String> getSearchPatternText() {
		return this.searchPatternText;
	}
	
	@Override
	public void startProcessing() {
	}

	@Override
	public void stopProcessing() {
	}
	
	private void createAddTab() {
		FlowPanel panel = new FlowPanel();
		
		Label nameLabel = new Label(messages.nameLabel());
		Label phoneLabel = new Label(messages.phoneLabel());
		
		panel.add(nameLabel);
		panel.add(this.nameText);
		panel.add(phoneLabel);
		panel.add(this.phoneText);
		panel.add(this.addButton);
		
		this.tabPanel.add(panel, messages.addTabLabel());
	}

	private void createShowAllTab() {
		FlowPanel panel = new FlowPanel();
		
		Label showAllLabel = new Label(messages.showAllLabel());
		panel.add(showAllLabel);
		panel.add(this.showAllButton);
		
		tabPanel.add(panel, messages.showAllTabLabel());
	}

	private void createSearchTab() {
		FlowPanel panel = new FlowPanel();
		
		Label searchLabel = new Label(messages.searchLabel());
		
		panel.add(searchLabel);
		panel.add(this.searchPatternText);
		panel.add(this.searchButton);
		
		this.tabPanel.add(panel, messages.searchTabLabel());
	}
}
