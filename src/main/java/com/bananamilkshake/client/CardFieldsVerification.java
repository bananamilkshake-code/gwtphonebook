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

import com.bananamilkshake.shared.FieldVerifier;
import com.google.gwt.user.client.Window;

public class CardFieldsVerification {
	
	/**
	 * Checks that name and phone are valid.
	 * 
	 * If name or phone are invalid shows Window.alert with message.
	 * 
	 * @param name name to check
	 * @param phone phone to check
	 * @return true if all fields are valid
	 */
	public static boolean verifyCardFields(String name, String phone) {
		if (!FieldVerifier.isValidName(name)) {
			Window.alert("Name " + name + " is invalid (must not be empty)");
			return false;
		}
		
		if (!FieldVerifier.isValidPhone(phone)) {
			Window.alert("Phone " + phone + " is invalid. It must be 11 digits without any other characters");
			return false;
		}
		
		return true;
	}
	
	public static int convertId(String idVal) {
		if (idVal == null || idVal.isEmpty()) {
			Window.alert("Id is empty");
		}
		
		return Integer.valueOf(idVal);
	}
}
