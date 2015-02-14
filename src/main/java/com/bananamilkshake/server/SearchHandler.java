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

import com.bananamilkshake.dispatcher.CardsListResult;
import com.bananamilkshake.dispatcher.Search;
import com.bananamilkshake.shared.Card;
import com.bananamilkshake.shared.FieldVerifier;
import java.util.ArrayList;
import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

public class SearchHandler implements ActionHandler<Search, CardsListResult> {
	@Override
	public Class<Search> getActionType() {
		return Search.class;
	}

	@Override
	public CardsListResult execute(Search action, ExecutionContext context) throws DispatchException {
		if (!FieldVerifier.isValidSearchPattern(action.getPattern())) {
			throw new SearchException(action.getPattern());
		}
		
		ArrayList<Card> cards = new ArrayList<>();
		cards.add(new Card(1, "searched_card1", "searched_phone1"));
		cards.add(new Card(2, "searched_card2", "searched_phone2"));
		cards.add(new Card(3, "searched_card3", "searched_phone3"));
		return new CardsListResult(cards);
	}

	@Override
	public void rollback(Search action, CardsListResult result, ExecutionContext context) throws DispatchException {
	}
	
	public class SearchException extends DispatchException {
		public SearchException(String pattern) {
			super("Wrong pattern \"" + pattern + "\"");
		}
	}
}
