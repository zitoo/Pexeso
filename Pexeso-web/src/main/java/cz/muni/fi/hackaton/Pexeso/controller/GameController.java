/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.hackaton.Pexeso.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cz.muni.fi.hackaton.Pexeso.data.CardManager;
import cz.muni.fi.hackaton.Pexeso.model.Card;
import cz.muni.fi.hackaton.Pexeso.model.User;

/**
 *
 * @author Martin Zitnik
 */
@ViewScoped
@Named
public class GameController implements Serializable {

	@Inject
	private Logger log;
	private User user;
	private Calendar startTime = Calendar.getInstance();
	private Integer baconIndex;
	private Integer flippedCard;
	private Integer secondFlippedCard;
	private List<Card> cards;
	private Boolean isEnd = false;

	public Integer getFlippedCard() {
		return flippedCard;
	}

	public Integer getSecondFlippedCard() {
		return secondFlippedCard;
	}

	public String getPrefferedBacon() {
		if (secondFlippedCard != null) {
			return cards.get(secondFlippedCard).getBacons().get(baconIndex).getValue();
		}

		if (flippedCard != null) {
			return cards.get(flippedCard).getBacons().get(baconIndex).getValue();
		}

		return "";
	}

	public boolean isSelected(int index) {
		return (flippedCard != null && flippedCard.intValue() == index) || (secondFlippedCard != null && secondFlippedCard.intValue() == index);
	}

	public User getUser() {
		return user;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public Boolean getIsEnd() {
		return isEnd;
	}
	@Inject
	private CardManager cm;

	@PostConstruct
	private void loadCards() {
		List<Card> allCards = cm.getAll();
		Collections.shuffle(allCards);

		List<Card> lessCards = new ArrayList<Card>();

		for (int i = 0; i < 18; i++) {
			lessCards.add(allCards.get(i));
			lessCards.add(allCards.get(i));
		}

		Collections.shuffle(lessCards);

		cards = lessCards;
	}

	private void isEnd() {
		int counter = 0;
		for (Card c : cards) {
			if (c.getIsActive()) {
				counter++;
			} else {
			}
		}
		if (counter == 0) {
			isEnd = true;
		}
	}

	public void flipCard(int index) {
		if (flippedCard == null) {
			flippedCard = index;
			baconIndex = new Random().nextInt(cards.get(index).getBacons().size());
		} else {
			if (secondFlippedCard == null && index != flippedCard) {
				secondFlippedCard = index;
				baconIndex = new Random().nextInt(cards.get(index).getBacons().size());

			}
		}
	}

	public List<Card> getCards() {
		return cards;
	}
	
	public String getPicture(int index) {
		if((flippedCard != null && flippedCard.intValue() == index) || (secondFlippedCard!=null && secondFlippedCard.intValue() == index)) {
			return cards.get(index).getImage();
		} else {
			return "resources/img/rubkarty.jpg";
		}
	}

	public void setNullCards() {
		if (flippedCard!=null && secondFlippedCard!= null && cards.get(flippedCard).equals(cards.get(secondFlippedCard))) { // are the same
			
			cards.get(flippedCard).setIsActive(false);
			flippedCard = null;
			secondFlippedCard = null;
			
			isEnd();
			log.info("active set");


		} else if(flippedCard != null && secondFlippedCard != null) { // are not the same
			flippedCard = null;
			secondFlippedCard = null;
		}
	}
}
