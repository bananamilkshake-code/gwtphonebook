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
import com.google.gwt.user.client.ui.TextBox;

public class CardPanel extends Composite implements CardPresenter.Display {
	private final Messages messages = GWT.create(Messages.class);

	private final FlowPanel panel = new FlowPanel();
	
	private final TextBox nameField = new TextBox();
	private final TextBox phoneField = new TextBox();
	
	private final Button editButton = new Button();
	private final Button removeButton = new Button();
	
	public CardPanel() {
		Label nameLabel = new Label(messages.nameLabel());
		Label phoneLabel = new Label(messages.phoneLabel());
		
		this.editButton.setText(messages.editButton());
		this.removeButton.setText(messages.removeButton());
		
		initWidget(this.panel);
		this.panel.add(nameLabel);
		this.panel.add(this.nameField);
		this.panel.add(phoneLabel);
		this.panel.add(this.phoneField);
		this.panel.add(this.editButton);
		this.panel.add(this.removeButton);
	}
	
	@Override
	public HasValue<String> getNameField() {
		return this.nameField;
	}

	@Override
	public HasValue<String> getPhoneField() {
		return this.phoneField;
	}

	@Override
	public HasClickHandlers getEditButton() {
		return this.editButton;
	}

	@Override
	public HasClickHandlers getRemoveButton() {
		return this.removeButton;
	}

	@Override
	public void startProcessing() {
	}

	@Override
	public void stopProcessing() {
	}
}
