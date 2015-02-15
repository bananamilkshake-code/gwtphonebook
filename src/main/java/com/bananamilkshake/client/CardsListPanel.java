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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CardsListPanel extends Composite implements CardsListPresenter.Display {
	private final Messages messages = GWT.create(Messages.class);

	private final VerticalPanel cards = new VerticalPanel();
	private final Button backButton = new Button();
	
	public CardsListPanel() {
		Label label = new Label(messages.cardsListLabel());
		this.backButton.setText(messages.backButton());
		
		VerticalPanel panel = new VerticalPanel();
		panel.add(this.backButton);
		panel.add(label);
		panel.add(this.cards);
		
		initWidget(panel);
	}
	
	@Override
	public VerticalPanel getCards() {
		return this.cards;
	}
	
	@Override
	public HasClickHandlers getBackButton() {
		return this.backButton;
	}
	
	@Override
	public void startProcessing() {
	}

	@Override
	public void stopProcessing() {
	}
}
