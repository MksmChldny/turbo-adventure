package de.atomfrede.github.karaoke.server.mongo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.Arrays;
import de.atomfrede.github.karaoke.server.entity.Song;

import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.mongodb.DB;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class SongRepositoryTest {

	@Mocked
	DB database;
	@Mocked
	MongoCollection mongoCollection;
	@Mocked
	Jongo jongo;

	SongRepository songRepository;

	@Before
	public void setUp() {
		songRepository = new SongRepository(database);
		//Initialisierung der Instanzvariablen
	}

	@Test
	public void assertThatCountIsReturned() {

		new NonStrictExpectations() {
			{//einen bestimmten Wert zurückgeben
				
				mongoCollection.count();
				// Methode count der Klasse mongoCollection wird initialisiert
				times = 1;
				// wird genau einmal ausgeführt
				result = 24;
			} 
		};

		assertThat(songRepository.count(), is(24L));
		// assertThat (x, is(24)) 
	}

	@Test
	public void assertThatEntityIsRemovedById() {

		new NonStrictExpectations() {
			{
				
				mongoCollection.remove("{_id:#}", new ObjectId ("55d357aec7aefa137de14fe1"));
				times = 1;
			}
		};

		songRepository.delete("55d357aec7aefa137de14fe1");
		// deleteMethode aufrufen, id 24 löschen
	}

	@Test
	public void assertThatEntitiesAreRemovedById() {

		new NonStrictExpectations() {
			{

				mongoCollection.remove("{_id:#}", "55d357aec7aefa137de14fe1");
				times = 1;

				mongoCollection.remove("{_id:#}", "55d357aec7aefa137de14fe2");
				times = 1;
			}
		};

		Song SongA = new Song("55d357aec7aefa137de14fe1").setTitle("SongA").setInterpreter("InterpreterA");

		Song SongB = new Song("55d357aec7aefa137de14fe2").setTitle("SongA").setInterpreter("InterpreterB");

		songRepository.delete(Arrays.asList(SongA, SongB));
	}

	@Test
	public void assertThatAllEntitiesAreRemoved() {

		new NonStrictExpectations() {
			{

				mongoCollection.drop();
				// Drops this collection from the Database - verwirft die
				// gesamte Sammlung
				times = 1;
			}
		};

		songRepository.deleteAll();
	}
	
	@Test
	public void assertThatOneCanBeFound() {

		new NonStrictExpectations() {
			{

				mongoCollection.findOne("{_id:#}", "24").as(Song.class);
				// Returns one document that satisfies the specified query
				// criteria
				times = 1;
				result = new Song("24");

				mongoCollection.findOne("{_id:#}", "71").as(Song.class);
				times = 1;
				result = null;
			}
		};

		assertThat(songRepository.exists("24"), is(true));
		assertThat(songRepository.exists("71"), is(false));
	}
	
}
