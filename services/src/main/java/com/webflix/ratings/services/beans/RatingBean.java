package com.webflix.ratings.services.beans;

import com.webflix.ratings.models.entities.RatingEntity;
import com.webflix.ratings.models.keys.RatingId;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@RequestScoped
public class RatingBean {

	@PersistenceContext(unitName = "webflix-jpa")
	private EntityManager em;

	public List<RatingEntity> getRatings(){

		TypedQuery<RatingEntity> query = em.createNamedQuery("RatingEntity.getAll", RatingEntity.class);

		return query.getResultList();
	}

	public RatingEntity getRating(Integer video_id, Integer user_id){
//		return em.createQuery("SELECT re FROM RatingEntity re WHERE re.video_id = :video_id AND re.user_id = :user_id", RatingEntity.class)
//				.setParameter("video_id", video_id)
//				.setParameter("user_id", user_id)
//				.getSingleResult();
		return em.find(RatingEntity.class, new RatingId(video_id, user_id));
	}

	public RatingEntity setRating(RatingEntity re) {
		// Check if rating already exists
		RatingEntity re_old = getRating(re.getVideo_id(), re.getUser_id());

		try {
			beginTx();
			if (re_old == null) { // Create new rating
				em.persist(re);
			} else { // Update existing rating
				re = em.merge(re);
			}
			commitTx();
		} catch (Exception e) {
			rollbackTx();
		}

		if (re.getUser_id() == null || re.getVideo_id() == null) {
			throw new RuntimeException("Entity was not persisted");
		}

		return re;
	}

	// Transactions

	private void beginTx() {
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();
	}

	private void commitTx() {
		if (em.getTransaction().isActive())
			em.getTransaction().commit();
	}

	private void rollbackTx() {
		if (em.getTransaction().isActive())
			em.getTransaction().rollback();
	}

}
