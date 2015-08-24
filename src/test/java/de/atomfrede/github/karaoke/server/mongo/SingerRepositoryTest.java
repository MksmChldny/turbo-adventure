package de.atomfrede.github.karaoke.server.mongo;

import com.mongodb.DB;
import de.atomfrede.github.karaoke.server.entity.Singer;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.integration.junit4.JMockit;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(JMockit.class)
public class SingerRepositoryTest {

	@Mocked
	DB database;
	@Mocked
	MongoCollection mongoCollection;
	@Mocked
	Jongo jongo;

	SingerRepository singerRepository;

	@Before
	public void setup() {
		singerRepository = new SingerRepository(database);
	}

	@Test
	public void assertThatCountIsReturned() {

		new NonStrictExpectations() {
			{

				mongoCollection.count();
				times = 1;
				result = 42;
			}
		};

		assertThat(singerRepository.count(), is(42L));
	}

	@Test
	public void assertThatEntityIsRemovedById() {

		new NonStrictExpectations() {
			{
				mongoCollection.remove("{_id:#}", "42");
				times = 1;
			}
		};

		singerRepository.delete("42");
	}

	@Test
	public void assertThatEntitiesAreRemovedById() {

		new NonStrictExpectations() {
			{

				mongoCollection.remove("{_id:#}", "42");
				times = 1;

				mongoCollection.remove("{_id:#}", "17");
				times = 1;
			}
		};

		Singer janeDoe = new Singer("42").setFirstname("Jane").setLastname("Doe");

		Singer johnDoe = new Singer("17").setFirstname("John").setLastname("Doe");

		singerRepository.delete(Arrays.asList(janeDoe, johnDoe));
	}

	@Test
	public void assertThatAllEntitiesAreRemoved() {

		new NonStrictExpectations() {
			{

				mongoCollection.drop();
				times = 1;
			}
		};

		singerRepository.deleteAll();
	}

	@Test
	public void assertThatOneCanBeFound() {

		new NonStrictExpectations() {
			{

				mongoCollection.findOne("{_id:#}", "42").as(Singer.class);
				times = 1;
				result = new Singer("42");

				mongoCollection.findOne("{_id:#}", "17").as(Singer.class);
				times = 1;
				result = null;
			}
		};

		assertThat(singerRepository.exists("42"), is(true));
		assertThat(singerRepository.exists("17"), is(false));
	}
}
