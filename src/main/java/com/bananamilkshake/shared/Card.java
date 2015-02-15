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

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Card is used to keep name and phone.
 */
@Entity
public class Card implements Comparable<Card>, Serializable {
	@Id
	@GeneratedValue
	protected int id;
	
	@Column(unique = true)
	@NotNull
	private String name;
	
	@Column(unique = true)
	@NotNull
	private String phone;

	public Card() {
	}
	
	public Card(String name, String phone) {
		this.name = name;
		this.phone = phone;
	}

	/**
	 * Get card id.
	 * @return card id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Get name that is written in card.
	 * @return name value
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Setting name to new value
	 * @param newName 
	 */
	public void setName(final String newName) {
		this.name = newName;
	}
	
	/**
	 * Get phone that is written in card.
	 * @return phone value
	 */
	public String getPhone() {
		return this.phone;
	}
	
	/**
	 * Setting phone to new value.
	 * @param newPhone 
	 */
	public void setPhone(final String newPhone) {
		this.phone = newPhone;
	}

	/**
	 * @param other card to compare with
	 * @return 0 objects are equal, 1 cards are not equal
	 */
	@Override
	public int compareTo(Card other) {
		if (this.name.compareTo(other.name) == 0 || this.phone.compareTo(other.phone) == 0) {
			return 0;
		}
		
		return 1;
	}
}
