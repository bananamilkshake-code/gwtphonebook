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
import com.bananamilkshake.ejb.Phones;
import com.bananamilkshake.shared.PhonesDispatchException;
import com.bananamilkshake.shared.FieldVerifier;
import java.util.regex.Pattern;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

public class SearchHandler extends PhonesActionHandler<Search, CardsListResult> {
	public SearchHandler(Phones phones) {
		super(phones);
	}
	
	@Override
	public Class<Search> getActionType() {
		return Search.class;
	}

	@Override
	public CardsListResult execute(Search action, ExecutionContext context) throws DispatchException {
		if (!FieldVerifier.isValidSearchPattern(action.getPattern())) {
			throw new PhonesDispatchException("Invalid pattern \"" + action.getPattern() + "\"");
		}
		
		Pattern pattern = Pattern.compile(action.getPattern());
		return new CardsListResult(this.phones.search(pattern));
	}

	@Override
	public void rollback(Search action, CardsListResult result, ExecutionContext context) throws DispatchException {
	}
}
