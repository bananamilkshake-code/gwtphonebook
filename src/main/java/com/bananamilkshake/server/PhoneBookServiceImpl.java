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
import com.bananamilkshake.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import static com.google.gwt.safehtml.shared.SafeHtmlUtils.htmlEscape;

/**
 * The server side implementation of the RPC service.
 */
public class PhoneBookServiceImpl extends RemoteServiceServlet implements PhoneBookService {

	public int add(String name, String phone) throws IllegalArgumentException {
		name = htmlEscape(name);
		phone = htmlEscape(phone);
		
		if (!FieldVerifier.isValidName(name)) {
			throw new IllegalArgumentException("Name +\"" + name
				+ " is not valid (must be not null or empty)");
		}
		
		return 0;
	}

	public void showAll() {
		
	}
	
	public int search(String namePartToSearch) throws IllegalArgumentException {
		namePartToSearch = htmlEscape(namePartToSearch);
		
		return 0;
	}

	public void edit(int id, String newName, String newPhone) throws IllegalArgumentException {
		newName = htmlEscape(newName);
		newPhone = htmlEscape(newPhone);
		
		if (!FieldVerifier.isValidName(newName)) {
			throw new IllegalArgumentException("Name +\"" + newName
				+ " is not valid (must be not null or empty)");
		}
	}
	
	public void remove(int id) {
		
	}
}
