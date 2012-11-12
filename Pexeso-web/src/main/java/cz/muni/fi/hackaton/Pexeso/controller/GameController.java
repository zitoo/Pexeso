/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.hackaton.Pexeso.controller;

import cz.muni.fi.hackaton.Pexeso.data.CardManager;
import cz.muni.fi.hackaton.Pexeso.model.Card;
import cz.muni.fi.hackaton.Pexeso.model.User;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Martin Zitnik
 */

@ViewScoped
@Named
public class GameController {
    private User user;
    private Calendar startTime = Calendar.getInstance();
    private Card flippedCard;
    private Card secondFlippedCard;
    private List<Card> cards;

    public Card getFlippedCard() {
        return flippedCard;
    }

    public Card getSecondFlippedCard() {
        return secondFlippedCard;
    }

    public User getUser() {
        return user;
    }

    public Calendar getStartTime() {
        return startTime;
    }
    
    
    @Inject
    private CardManager cm;
    
    
    @PostConstruct
    private void loadCards(int count){
        List<Card> allCards = cm.getAll();
        Collections.shuffle(cards);
        
        List<Card> lessCards = new ArrayList<Card>();
        
        for(int i=0; i < count; i++){
            lessCards.add(allCards.get(i));
            lessCards.add(allCards.get(i));
        }
        
        Collections.shuffle(lessCards);
        
        cards = lessCards;
    }
    
    
    public void flipCard(int arrayPosition){
        if(flippedCard == null){
            flippedCard = cards.get(arrayPosition);
        } else{
            secondFlippedCard = cards.get(arrayPosition);
            
            if(flippedCard.equals(secondFlippedCard)){
                flippedCard.setIsActive(false);
                secondFlippedCard.setIsActive(false);
                
                flippedCard = null;
                secondFlippedCard = null;
                
                                                                    
            } else{
                flippedCard = null;
                secondFlippedCard = null;
            }
        }
    }
    
    
    
 
    
    
    
}
