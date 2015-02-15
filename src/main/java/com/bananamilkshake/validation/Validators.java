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

package com.bananamilkshake.validation;

import com.bananamilkshake.shared.FieldVerifier;
import com.bananamilkshake.shared.PhonesDispatchException;

public class Validators {
	public static void validateData(String name, String phone) throws PhonesDispatchException {
		if (!FieldVerifier.isValidName(name)) {
			throw new PhonesDispatchException("Name \"" + name + "\" is invalid");
		}
		
		if (!FieldVerifier.isValidPhone(phone)) {
			throw new PhonesDispatchException("Phone \"" + phone+ "\" is invalid");
		}
	}
}
