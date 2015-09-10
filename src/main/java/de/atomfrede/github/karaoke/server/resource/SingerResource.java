package de.atomfrede.github.karaoke.server.resource;

import com.codahale.metrics.annotation.Timed;
import de.atomfrede.github.karaoke.server.entity.Singer;
import de.atomfrede.github.karaoke.server.entity.Singers;
import de.atomfrede.github.karaoke.server.mongo.SingerRepository;
import io.dropwizard.jersey.PATCH;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Path("/singer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SingerResource {

	private final SingerRepository singerRepository;
	private static Logger log = LogManager.getLogger(SingerResource.class);

	public SingerResource(final SingerRepository singerRepository) {
		this.singerRepository = singerRepository;
	}

	@GET
	@Timed
	public Singers getSingers() {

		return new Singers(singerRepository.findAll());
	}

	@POST
	@Timed
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Singers saveNewSinger(@FormParam("firstname") String firstname, 
								 @FormParam("lastname") String lastname) {

		Singer newSinger = new Singer();
		newSinger.setFirstname(firstname).setLastname(lastname);

		singerRepository.save(newSinger);

		return new Singers(singerRepository.findAll());
	}

	
	@PATCH
	@Timed
	@Path("{singerId}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Singers updateSinger(@PathParam("singerId") String singerId, 
								@FormParam("firstname") String firstname,
								@FormParam("lastname") String lastname) {
		if (singerRepository.exists(singerId)) {
			Singer updateSinger = new Singer(singerId);
			updateSinger.setFirstname(firstname).setLastname(lastname);

			singerRepository.update(updateSinger);
			System.out.println("test");
			log.info("testLog");
			
			
		} else {
			System.out.println("testElse");
			log.info("testLogElse");
		}
		return new Singers(singerRepository.findAll());
	}

	@DELETE
	@Timed
	@Path("{singerId}")
	public Singers deleteSong(@PathParam("singerId") String singerId) {

		singerRepository.delete(singerId);

		return new Singers(singerRepository.findAll());
	}

	@GET
	@Timed
	@Path("{id}")
	public Singer getSinger(@PathParam("id") final String id) {
		Singer singer = singerRepository.findOne(id);
		if (singer != null) {
			return singer;
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
}
