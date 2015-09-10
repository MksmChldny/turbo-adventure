package de.atomfrede.github.karaoke.server.entity;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SingerTest {

    @Test
    public void shouldBeSet() {

        Singer singer = new Singer()
                .setFirstname("Firstname")
                .setLastname("Lastname");

        assertThat(singer.firstname(), is("Firstname"));
        assertThat(singer.lastname(), is("Lastname"));
    }
    
    @Test
    public void returnId () {
    	
    	Singer singer = new Singer();
    	
    	singer.id();
    }   
}
