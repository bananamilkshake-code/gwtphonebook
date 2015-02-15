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
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PhoneBookPanel extends Composite implements PhoneBookPresenter.Display {
	private final Messages messages = GWT.create(Messages.class);
	
	private final TabPanel tabPanel = new TabPanel();
	
	private final Button addButton = new Button();
	private final TextBox nameText = new TextBox();
	private final TextBox phoneText = new TextBox();
	
	private final Button searchButton = new Button();
	private final TextBox searchPatternText = new TextBox();
	
	private final Button showAllButton = new Button();
	
	private final Button editButton = new Button();
	private final Button loadButton = new Button();
	private final TextBox editIdText = new TextBox();
	private final TextBox editNameText = new TextBox();
	private final TextBox editPhoneText = new TextBox();
	
	private final Button removeButton = new Button();
	private final TextBox removeIdText = new TextBox();
	
	public enum Tabs {
		ADD("add") {
			@Override
			public void createTabFor(PhoneBookPanel panel) {
				panel.createAddTab();
			}
		},
		SHOW_ALL("show_all") {
			@Override
			public void createTabFor(PhoneBookPanel panel) {
				panel.createShowAllTab();
			}
		},
		SEARCH("search") {
			@Override
			public void createTabFor(PhoneBookPanel panel) {
				panel.createSearchTab();
			}
		},
		EDIT("edit") {
			@Override
			public void createTabFor(PhoneBookPanel panel) {
				panel.createEditTab();
			}
		},
		REMOVE("remove") {
			@Override
			public void createTabFor(PhoneBookPanel panel) {
				panel.createRemoveTab();
			}
		};
		
		public final String actionName;
		
		Tabs(String actionName) {
			this.actionName = actionName;
		}
		
		public abstract void createTabFor(PhoneBookPanel panel);
	}
	
	public PhoneBookPanel() {
		this.addButton.setText(messages.addButton());
		this.searchButton.setText(messages.searchButton());
		this.showAllButton.setText(messages.showAllButton());
		this.editButton.setText(messages.editButton());
		this.removeButton.setText(messages.removeButton());
		this.loadButton.setText(messages.loadButton());
		
		initWidget(this.tabPanel);
		
		for (Tabs tab : Tabs.values()) {
			tab.createTabFor(this);
		}
		
		this.tabPanel.selectTab(0);
	}

	@Override
	public TabPanel getTabPanel() {
		return this.tabPanel;
	}

	@Override
	public HasClickHandlers getAddButton() {
		return this.addButton;
	}

	@Override
	public void afterAdd() {
		this.nameText.setText("");
		this.phoneText.setText("");
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
	public HasClickHandlers getEditButton() {
		return this.editButton;
	}

	@Override
	public HasValue<String> getEditIdText() {
		return this.editIdText;
	}

	@Override
	public HasClickHandlers getLoadButton() {
		return this.loadButton;
	}

	@Override
	public HasClickHandlers getRemoveButton() {
		return this.removeButton;
	}

	@Override
	public HasValue<String> getRemoveIdText() {
		return this.removeIdText;
	}

	@Override
	public HasValue<String> getEditNameText() {
		return this.editNameText;
	}

	@Override
	public HasValue<String> getEditPhoneText() {
		return this.editPhoneText;
	}

	@Override
	public void prepareEdit() {
		this.editButton.setEnabled(true);
		this.editNameText.setEnabled(true);
		this.editPhoneText.setEnabled(true);
	}

	@Override
	public void blockEdit() {
		this.editNameText.setText("");
		this.editPhoneText.setText("");
		
		this.editButton.setEnabled(false);
		this.editNameText.setEnabled(false);
		this.editPhoneText.setEnabled(false);
	}
	
	@Override
	public void startProcessing() {
	}

	@Override
	public void stopProcessing() {
	}
	
	private void createAddTab() {		
		VerticalPanel panel = new VerticalPanel();
		
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
		VerticalPanel panel = new VerticalPanel();
		
		Label showAllLabel = new Label(messages.showAllLabel());
		panel.add(showAllLabel);
		panel.add(this.showAllButton);
		
		tabPanel.add(panel, messages.showAllTabLabel());
	}

	private void createSearchTab() {
		VerticalPanel panel = new VerticalPanel();
		
		Label searchLabel = new Label(messages.searchLabel());
		
		panel.add(searchLabel);
		panel.add(this.searchPatternText);
		panel.add(this.searchButton);
		
		this.tabPanel.add(panel, messages.searchTabLabel());
	}

	private void createEditTab() {
		VerticalPanel panel = new VerticalPanel();
		
		Label idLabel = new Label(messages.idLabel());
		Label editNameLabel = new Label(messages.editNameLabel());
		Label editPhoneLabel = new Label(messages.editPhoneLabel());
		
		panel.add(idLabel);
		panel.add(this.editIdText);
		panel.add(this.loadButton);
		panel.add(editNameLabel);
		panel.add(this.editNameText);
		panel.add(editPhoneLabel);
		panel.add(this.editPhoneText);
		
		panel.add(this.editButton);
		
		this.tabPanel.add(panel, messages.editTabLabel());
		
		this.blockEdit();
	}

	private void createRemoveTab() {
		VerticalPanel panel = new VerticalPanel();
		
		Label idLabel = new Label(messages.idLabel());
		
		panel.add(idLabel);
		panel.add(this.removeIdText);
		
		panel.add(this.removeButton);
		
		this.tabPanel.add(panel, messages.removeTabLabel());
	}
}
