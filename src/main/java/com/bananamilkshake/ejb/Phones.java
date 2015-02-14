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
package com.bananamilkshake.ejb;

import com.bananamilkshake.dispatcher.EditCardResult;
import com.bananamilkshake.shared.Card;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class Phones {
	private static final Logger LOG = Logger.getLogger(Phones.class.getName());
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Phones() {
	}
	
	@PostConstruct
	public void postConstruct() {
		LOG.info("Phones singleton contructed");
	}
	
	@Lock(LockType.WRITE)
	public Card add(String name, String phone) {
		Card newCard = new Card(name, phone);
		entityManager.persist(newCard);		
		return newCard;
	}

	@Lock(LockType.WRITE)
	public EditCardResult edit(int id, String newName, String newPhone) {
		Card card = this.entityManager.find(Card.class, id);
		
		EditCardResult result = new EditCardResult(card.getName(), card.getPhone(), card);

		card.setName(newName);
		card.setPhone(newPhone);
		this.entityManager.merge(card);
		
		return result;
	}

	@Lock(LockType.WRITE)
	public Card remove(int id) {
		Card card = this.entityManager.find(Card.class, id);
		this.entityManager.remove(card);
		return card;
	}
	
	@Lock(LockType.READ)
	public void restore(Card card) {
		this.entityManager.persist(card);
	}

	@Lock(LockType.READ)
	public Card get(int id) {
		return this.entityManager.find(Card.class, id);
	}
	
	@Lock(LockType.READ)
	public ArrayList<Card> getAll() {
		return new ArrayList<>();
	}

	@Lock(LockType.READ)
	public ArrayList<Card> search(Pattern pattern) {
		return new ArrayList<>();
	}
}
