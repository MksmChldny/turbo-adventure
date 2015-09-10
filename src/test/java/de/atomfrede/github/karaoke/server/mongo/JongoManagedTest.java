package de.atomfrede.github.karaoke.server.mongo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.mongodb.DB;

import mockit.Mocked;
import mockit.integration.junit4.JMockit;

@RunWith (JMockit.class)
public class JongoManagedTest {

	@Mocked
	DB db;
	
	@Test
	public void dBisClosed() throws Exception{
		
		JongoManaged jongoManaged = new JongoManaged(db);
		jongoManaged.stop();
	}
	
}
