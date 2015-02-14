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

package com.bananamilkshake.shared;

import com.google.gwt.regexp.shared.RegExp;

/**
 * <p>
 * FieldVerifier validates that the name the user enters is valid.
 * </p>
 * <p>
 * This class is in the <code>shared</code> packing because we use it in both
 * the client code and on the server. On the client, we verify that the name is
 * valid before sending an RPC request so the user doesn't have to wait for a
 * network round trip to get feedback. On the server, we verify that the name is
 * correct to ensure that the input is correct regardless of where the RPC
 * originates.
 * </p>
 * <p>
 * When creating a class that is used on both the client and the server, be sure
 * that all code is translatable and does not use native JavaScript. Code that
 * is note translatable (such as code that interacts with a database or the file
 * system) cannot be compiled into client side JavaScript. Code that uses native
 * JavaScript (such as Widgets) cannot be run on the server.
 * </p>
 */
public class FieldVerifier {

	private static final RegExp PHONE_PATTERN;
	
	static {
		PHONE_PATTERN = RegExp.compile("(\\d{11})");
	}
	
	/**
	 * Verifies that the specified name is valid for our service.
	 *
	 * Check if name is valid. Name must not be null or empty.
	 *
	 * @param name the name to validate
	 * @return true if valid, false if invalid
	 */
	public static boolean isValidName(String name) {
		return (name != null && !name.isEmpty());
	}
	
	/**
	 * Verifies that the specified phone is valid for our service.
	 * 
	 * Phone must be string of eleven digits without any other symbols.
	 * 
	 * @param phone
	 * @return true if valid, false if invalid
	 */
	public static boolean isValidPhone(String phone) {
		return (phone != null && PHONE_PATTERN.test(phone));
	}

	public static boolean isValidSearchPattern(String toSearch) {
		if (toSearch == null)
			return false;
		
		try {
			RegExp.compile(toSearch);
		} catch (RuntimeException exception) {
			return false;
		}
		
		return true;
	}
}
