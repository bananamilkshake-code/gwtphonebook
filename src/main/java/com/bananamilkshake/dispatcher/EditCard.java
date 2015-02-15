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

import net.customware.gwt.dispatch.shared.Action;

public class EditCard implements Action<EditCardResult> {
	private int id;
	
	private String editingName;
	private String editingPhone;
	
	private String newName;
	private String newPhone;

	EditCard() {
	}
	
	public EditCard(int id, String editingName, String editingPhone, String newName, String newPhone) {
		this.id = id;
		
		this.editingName = editingName;
		this.editingPhone = editingPhone;
		
		this.newName = newName;
		this.newPhone = newPhone;
	}

	/**
	 * @return Id of <code>Card</code> that is being tried to change.
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * @return Name that client tries to set.
	 */
	public String getNewName() {
		return this.newName;
	}
	
	/**
	 * @return Phone that client tries to set.
	 */
	public String getNewPhone() {
		return this.newPhone;
	}

	/** 
	 * @return Name that client was tried to change.
	 */
	public String getEditingName() {
		return editingName;
	}

	/**
	 * @return Phone that client was tried to change.
	 */
	public String getEditingPhone() {
		return editingPhone;
	}
}
