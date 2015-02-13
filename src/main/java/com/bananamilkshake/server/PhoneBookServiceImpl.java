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

import com.bananamilkshake.client.PhoneBookService;
import com.bananamilkshake.shared.Card;
import com.bananamilkshake.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import static com.google.gwt.safehtml.shared.SafeHtmlUtils.htmlEscape;
import java.util.ArrayList;

/**
 * The server side implementation of the RPC service.
 */
public class PhoneBookServiceImpl extends RemoteServiceServlet implements PhoneBookService {

	@Override
	public int add(String name, String phone) throws IllegalArgumentException {
		name = htmlEscape(name);
		phone = htmlEscape(phone);
		
		if (!FieldVerifier.isValidName(name)) {
			throw new IllegalArgumentException("Name +\"" + name + " is not valid (must be not null or empty)");
		}
		
		return 0;
	}

	@Override
	public ArrayList<Card> showAll() {
		ArrayList<Card> res = new ArrayList<>();
		res.add(new Card(1, "card1", "phone1"));
		res.add(new Card(2, "card2", "phone2"));
		res.add(new Card(3, "card3", "phone3"));
		return res;
	}
	
	@Override
	public ArrayList<Card> search(String namePartToSearch) throws IllegalArgumentException {
		namePartToSearch = htmlEscape(namePartToSearch);
		
		ArrayList<Card> res = new ArrayList<>();
		res.add(new Card(1, "searched_card1", "searched_phone1"));
		res.add(new Card(2, "searched_card2", "searched_phone2"));
		res.add(new Card(3, "searched_card3", "searched_phone3"));
		return res;
	}

	@Override
	public void edit(int id, String newName, String newPhone) throws IllegalArgumentException {
		newName = htmlEscape(newName);
		newPhone = htmlEscape(newPhone);
		
		if (!FieldVerifier.isValidName(newName)) {
			throw new IllegalArgumentException("Name +\"" + newName + " is not valid (must be not null or empty)");
		}
	}
	
	@Override
	public void remove(int id) {
	}

	@Override
	public Card get(int id) {
		return new Card(id, "name", "55588899");
	}
}
