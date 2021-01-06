package com.webflix.ratings.api.v1.resources;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.webflix.ratings.models.entities.RatingEntity;
import com.webflix.ratings.services.beans.RatingBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
//import java.net.http.HttpClient;
//import java.net.http.HttpResponse;
import java.util.List;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.concurrent.ThreadLocalRandom;


@ApplicationScoped
@Path("/ratings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")
public class RatingsResource {

	@Inject
	private RatingBean ratingBean;

	@GET
	public Response getRatings() {
		List<RatingEntity> list = ratingBean.getRatings();
		return Response.ok(list).build();
	}

	@GET
	@Path("/{videoId}")
	public Response getRating(@HeaderParam("ID-Token") String idTokenString,
							  @PathParam("videoId") Integer videoId) {
		String userId = ratingBean.manageUser(idTokenString);

		if (userId != null) {

			System.out.println("User ID: " + userId);

			RatingEntity re = ratingBean.getRating(videoId, userId);

			if (re == null)
				return Response.status(Response.Status.NOT_FOUND).build();

			return Response.status(Response.Status.OK).entity(re).build();

		} else {

			return Response.status(Response.Status.UNAUTHORIZED).build();

		}
	}

	@POST
	@Path("/{videoId}")
	public Response postRating(@HeaderParam("ID-Token") String idTokenString,
							   @PathParam("videoId") Integer videoId, RatingEntity re) {
		String userId = ratingBean.manageUser(idTokenString);

		if (userId != null) {

			System.out.println("User ID: " + userId);

			if (videoId == null || re.getRating() == null)
				return Response.status(Response.Status.BAD_REQUEST).build();

			if (!(re.getRating() >= 1 && re.getRating() <= 5))
				return Response.status(Response.Status.BAD_REQUEST).build();

			re.setVideo_id(videoId);
			re.setUser_id(userId);

			re = ratingBean.setRating(re);

			return Response.status(Response.Status.OK).entity(re).build();

		} else {

			return Response.status(Response.Status.UNAUTHORIZED).build();

		}
	}

}
