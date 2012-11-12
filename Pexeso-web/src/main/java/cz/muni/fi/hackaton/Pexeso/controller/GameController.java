/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.hackaton.Pexeso.controller;

import cz.muni.fi.hackaton.Pexeso.data.CardManager;
import cz.muni.fi.hackaton.Pexeso.model.Card;
import cz.muni.fi.hackaton.Pexeso.model.User;
import java.io.Serializable;
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
public class GameController  implements Serializable{
    private User user;
    private Calendar startTime = Calendar.getInstance();
    private Card flippedCard;
    private Card secondFlippedCard;
    private List<Card> cards;
    private Boolean isEnd = false;

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

    public Boolean getIsEnd() {
        return isEnd;
    }
    
    
    @Inject
    private CardManager cm;
    
    
    @PostConstruct
    private void loadCards(){
        List<Card> allCards = cm.getAll();
        Collections.shuffle(allCards);
        
        List<Card> lessCards = new ArrayList<Card>();
        
        for(int i=0; i < 18; i++){
            lessCards.add(allCards.get(i));
            lessCards.add(allCards.get(i));
        }
        
        Collections.shuffle(lessCards);
        
        cards = lessCards;
    }
    
    private void isEnd(){
        int counter = 0;
        for(Card c : cards){
            if(c.getIsActive()){
                counter++;
            } else{
                
            }
        }
        if(counter == 0){
            isEnd = true;
        }
    }
    
    
    public void flipCard(int arrayPosition){
        if(flippedCard == null){
            flippedCard = cards.get(arrayPosition);
        } else{
            secondFlippedCard = cards.get(arrayPosition);
            
            if(flippedCard.equals(secondFlippedCard)){
                flippedCard.setIsActive(false);
                secondFlippedCard.setIsActive(false);
                isEnd();
                
                flippedCard = null;
                secondFlippedCard = null;
                
                                                                    
            } else{
                flippedCard = null;
                secondFlippedCard = null;
            }
        }
    }
    
    public List<Card> getCards(){
       return cards;
    }
    
    
}
