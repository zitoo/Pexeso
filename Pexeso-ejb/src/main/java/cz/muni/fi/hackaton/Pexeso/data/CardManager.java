/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.hackaton.Pexeso.data;

import cz.muni.fi.hackaton.Pexeso.model.Card;
import cz.muni.fi.hackaton.Pexeso.model.Member;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Martin Zitnik
 */
@Stateless
public class CardManager {

    @Inject
    private EntityManager em;

    public List<Card> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Card> criteria = cb.createQuery(Card.class);
        Root<Card> card = criteria.from(Card.class);
        criteria.select(card);
        List<Card> cards = em.createQuery(criteria).getResultList();

        return cards;
    }
}
