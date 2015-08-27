package de.atomfrede.github.karaoke.server.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import javax.ws.rs.WebApplicationException;

import de.atomfrede.github.karaoke.server.mongo.SongRepository;
import io.dropwizard.testing.junit.ResourceTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import de.atomfrede.github.karaoke.server.entity.Song;
import de.atomfrede.github.karaoke.server.entity.Songs;


public class SongResourceTest {
	
	private static final SongRepository repository = mock(SongRepository.class);
	
	
	@ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new SongResource(repository)).build();

    @Test(expected = WebApplicationException.class)
    public void shouldReturnNotFoundWhenNotFound() {

        when(repository.findOne("321")).thenReturn(null);

        try {
            resources.client().target("/song/321").request().get(Song.class);
        } catch (WebApplicationException e) {

            assertThat(e.getResponse().getStatus(), is(405));
            throw e;
        }


    }

    @Test
    public void shouldGetAllSongs() {

        Song SongA = new Song().setTitle("SongA").setInterpreter("InterpreterA");
        Song SongB = new Song().setTitle("SongB").setInterpreter("InterpreterB");

        when(repository.findAll()).thenReturn(Arrays.asList(SongA, SongB));

        Songs songs = resources.client().target("/song").request().get(Songs.class);

        assertThat(songs.getSongs(), notNullValue());

    }

    @Test
    public void shouldGetSingleSong() {

        when(repository.findOne("321")).thenReturn(new Song("321").setTitle("SongA").setInterpreter("InterpreterA"));

        Song song = resources.client().target("/song/321").request().get(Song.class);

        assertThat(song, notNullValue());
        assertThat(song.title(), is("SongA"));
        assertThat(song.interpreter(), is("InterpreterA"));
    }

}
