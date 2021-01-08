package com.webflix.ratings.models.entities;

import com.webflix.ratings.models.keys.RatingId;

import javax.persistence.*;

@Entity
@IdClass(RatingId.class)
@Table(name = "ratings_data")
@NamedQueries(value =
		{
				@NamedQuery(name = "RatingEntity.getAll",
						query = "SELECT re FROM RatingEntity re")
		})
public class RatingEntity {

	// Fields

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;

	@Id
	@Column(name = "video_id")
	private Integer video_id;

	@Id
	@Column(name = "user_id")
	private Integer user_id;

	@Column(name = "rating")
	private Integer rating;

	// Getters & Setters

//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}

	public Integer getVideo_id() {
		return video_id;
	}

	public void setVideo_id(Integer video_id) {
		this.video_id = video_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}
}
