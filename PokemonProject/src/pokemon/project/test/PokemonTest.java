package pokemon.project.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pokemon.project.Pokemon;

public class PokemonTest {

	Pokemon pokemon;
	
	@BeforeEach
	public void setUp() throws Exception {
		pokemon = new Pokemon();
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void testID() {
		pokemon.setId(1);
		assertEquals(1, pokemon.getId());
	}
	
	@Test
	public void testName() {
		pokemon.setName("Charmander");
		assertEquals("Charmander", pokemon.getName());
	}
	
	@Test
	public void testGeneration() {
		pokemon.setGeneration("First");
		assertEquals("First", pokemon.getGeneration());
	}
	
	@Test
	public void testType() {
		pokemon.setType("Fire");
		assertEquals("Fire", pokemon.getType());
	}
	
	@Test
	public void testWeakness() {
		pokemon.setWeakness("Water");
		assertEquals("Water", pokemon.getWeakness());
	}
	
	@Test
	public void testCategory() {
		pokemon.setCategory("Dragon");
		assertEquals("Dragon", pokemon.getCategory());
	}
	
	@Test
	public void testDescription() {
		pokemon.setDescription("New Pokemon");
		assertEquals("New Pokemon", pokemon.getDescription());
	}
	
	@Test
	public void testRegion() {
		pokemon.setRegion("Kanto");
		assertEquals("Kanto", pokemon.getRegion());
	}
	
	@Test
	public void testGender() {
		pokemon.setGender("male");
		assertEquals("male", pokemon.getGender());
	}
	
	@Test
	public void testHeight() {
		pokemon.setHeight("10");
		assertEquals("10", pokemon.getHeight());
	}
	
	@Test
	public void testWeight() {
		pokemon.setWeight("100");
		assertEquals("100", pokemon.getWeight());
	}
	
	@Test
	public void testAbility() {
		pokemon.setAbility("Strength");
		assertEquals("Strength", pokemon.getAbility());
	}
	
	@Test
	public void testPicture() {
		pokemon.setPicture("New Pic");
		assertEquals("New Pic", pokemon.getPicture());
	}

}
