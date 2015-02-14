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

package com.bananamilkshake.server;

import com.bananamilkshake.dispatcher.ShowAll;
import com.bananamilkshake.dispatcher.CardsListResult;
import com.bananamilkshake.shared.Card;
import java.util.ArrayList;
import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

public class ShowAllHandler implements ActionHandler<ShowAll, CardsListResult> {
	@Override
	public Class<ShowAll> getActionType() {
		return ShowAll.class;
	}

	@Override
	public CardsListResult execute(ShowAll action, ExecutionContext context) throws DispatchException {
		ArrayList<Card> cards = new ArrayList<>();
		cards.add(new Card(1, "card1", "phone1"));
		cards.add(new Card(2, "card2", "phone2"));
		cards.add(new Card(3, "card3", "phone3"));
		return new CardsListResult(cards);
	}

	@Override
	public void rollback(ShowAll action, CardsListResult result, ExecutionContext context) throws DispatchException {
	}
}
