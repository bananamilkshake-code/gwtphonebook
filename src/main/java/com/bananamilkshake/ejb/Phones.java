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

import com.bananamilkshake.shared.Card;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
public class Phones {
	private static final Logger LOG = Logger.getLogger(Phones.class.getName());
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Phones() {
	}
	
	/**
	 * Creating new instance of <code>Card</code> with name and phone.
	 * 
	 * @param name
	 * @param phone
	 * @return created card
	 * @throws Exception
	 */
	public Card add(String name, String phone) throws Exception {		
		Card newCard = new Card(name, phone);
		this.entityManager.persist(newCard);
		return newCard;
	}

	/**
	 * Updates card.
	 * 
	 * @param card to update
	 * @throws java.lang.Exception
	 */
	public void edit(Card card) throws Exception {
		this.entityManager.merge(card);
	}

	/**
	 * Remove card with specified id.
	 * @param id card to delete
	 * @return Card instance that was removed.
	 */
	public Card remove(int id) {
		Card card = this.get(id);
		
		this.entityManager.remove(card);
		return card;
	}
	
	/**
	 * Restore removed card.
	 * @param card 
	 */
	public void restore(Card card) {
		this.entityManager.persist(card);
	}

	/**
	 * Retrieving card with id.
	 * @param id
	 * @return requested card
	 */
	public Card get(int id) {
		return this.entityManager.find(Card.class, id);
	}
	
	/**
	 * Selecting all cards existing.
	 * @return List of cards
	 */
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
