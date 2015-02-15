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
import java.util.List;
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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
	
	/**
	 * Creating new instance of <code>Card</code> with name and phone.
	 * 
	 * @param name
	 * @param phone
	 * @return created card
	 * @throws Exception
	 */
	@Lock(LockType.WRITE)
	public Card add(String name, String phone) throws Exception {		
		Card newCard = new Card(name, phone);
		this.entityManager.persist(newCard);
		return newCard;
	}

	/**
	 * Set card with id <code>id</code> name as <code>newName</code> and phone
	 * as <code>newPhone</code>.
	 * 
	 * @param id card to edit
	 * @param newName
	 * @param newPhone
	 * @return EditCardResult object, that contains information about editing
	 * @throws IllegalArgumentException if there is no card with id <code>id</code>
	 */
	@Lock(LockType.WRITE)
	public EditCardResult edit(int id, String newName, String newPhone) throws IllegalArgumentException {
		Card card = this.get(id);
		
		EditCardResult result = new EditCardResult(card.getName(), card.getPhone(), card);

		card.setName(newName);
		card.setPhone(newPhone);
		this.entityManager.merge(card);
		
		return result;
	}

	/**
	 * Remove card with specified id.
	 * @param id card to delete
	 * @return Card instance that was removed.
	 */
	@Lock(LockType.WRITE)
	public Card remove(int id) {
		Card card = this.get(id);
		
		this.entityManager.remove(card);
		return card;
	}
	
	/**
	 * Restore removed card.
	 * @param card 
	 */
	@Lock(LockType.READ)
	public void restore(Card card) {
		this.entityManager.persist(card);
	}

	/**
	 * Retrieving card with id.
	 * @param id
	 * @return requested card
	 */
	@Lock(LockType.READ)
	public Card get(int id) {
		Card card = this.entityManager.find(Card.class, id);
		if (card == null) {
			throw new IllegalArgumentException("There is no card with id " + id);
		}
		return card;
	}
	
	/**
	 * Selecting all cards existing.
	 * @return List of cards
	 */
	@Lock(LockType.READ)
	public ArrayList<Card> getAll() {
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Card> criteriaQuery = criteriaBuilder.createQuery(Card.class);
		Root<Card> cardRoot = criteriaQuery.from(Card.class);
		criteriaQuery.select(cardRoot);
		
		return this.select(criteriaQuery);
	}

	/**
	 * Selecting cards which name matches pattern <code>pattern</code>.
	 * @param pattern name pattern
	 * @return List of cards
	 */
	@Lock(LockType.READ)
	public ArrayList<Card> search(Pattern pattern) {
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Card> criteriaQuery = criteriaBuilder.createQuery(Card.class);
		
		Root<Card> cardRoot = criteriaQuery.from(Card.class);
		
		Expression<String> nameParam = cardRoot.get("name");
		Predicate patternLike = criteriaBuilder.like(nameParam, pattern.toString());
		
		criteriaQuery.where(patternLike);
		
		return this.select(criteriaQuery);
	}
	
	private ArrayList<Card> select(CriteriaQuery<Card> criteria) {
		List<Card> selected = this.entityManager.createQuery(criteria).getResultList();
		
		ArrayList<Card> cards = new ArrayList<>();
		if (selected == null) {
			LOG.info("Selected cards are null");
			return cards;
		}
		
		for (Card card : selected) {
			cards.add(card);
		}
		
		return cards;
	}
}
