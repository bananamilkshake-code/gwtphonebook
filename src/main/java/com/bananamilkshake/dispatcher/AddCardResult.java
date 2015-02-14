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

package com.bananamilkshake.dispatcher;

import com.bananamilkshake.shared.Card;
import net.customware.gwt.dispatch.shared.Result;

public class AddCardResult implements Result {
	private Card addedCard;
	
	AddCardResult() {
	}
	
	public AddCardResult(Card addedCard) {
		this.addedCard = addedCard;
	}

	/**
	 * @return cad that was added
	 */
	public Card getAddedCard() {
		return this.addedCard;
	}
}
