package de.atomfrede.github.karaoke.server.resource;

import de.atomfrede.github.karaoke.server.entity.Singer;
import de.atomfrede.github.karaoke.server.entity.Singers;
import de.atomfrede.github.karaoke.server.mongo.SingerRepository;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.WebApplicationException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SingerResourceTest {

    private static final SingerRepository repository = mock(SingerRepository.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new SingerResource(repository)).build();

    @Test(expected = WebApplicationException.class)
    public void shouldReturnNotFoundWhenNotFound() {

        when(repository.findOne("123")).thenReturn(null);

        try {
            resources.client().target("/singer/123").request().get(Singer.class);
        } catch (WebApplicationException e) {

            assertThat(e.getResponse().getStatus(), is(405));
            throw e;
        }


    }

    @Test
    public void shouldGetAllSingers() {

        Singer johnDoe = new Singer().setFirstname("John").setLastname("Doe");
        Singer janeDoe = new Singer().setFirstname("Jane").setLastname("Doe");

        when(repository.findAll()).thenReturn(Arrays.asList(johnDoe, janeDoe));

        Singers singers = resources.client().target("/singer").request().get(Singers.class);

        assertThat(singers.getSingers(), notNullValue());

    }

    @Test
    public void shouldGetSingleSinger() {

        when(repository.findOne("123")).thenReturn(new Singer("123").setFirstname("John").setLastname("Doe"));

        Singer singer = resources.client().target("/singer/123").request().get(Singer.class);

        assertThat(singer, notNullValue());
        assertThat(singer.firstname(), is("John"));
        assertThat(singer.lastname(), is("Doe"));
    }

}
