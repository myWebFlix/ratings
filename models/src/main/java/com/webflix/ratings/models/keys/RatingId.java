package com.webflix.ratings.models.keys;

import java.io.Serializable;
import java.util.Objects;

public class RatingId implements Serializable {
	private Integer video_id;
	private Integer user_id;

	public RatingId(Integer video_id, Integer user_id) {
		this.video_id = video_id;
		this.user_id = user_id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RatingId ratingId = (RatingId) o;
		return video_id.equals(ratingId.video_id) &&
				user_id.equals(ratingId.user_id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(video_id, user_id);
	}
}
