package songs;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SongTest {
	Song testSong;
	Song testSong1;
	Song testSong2;


	@Before
	public void setUp() throws Exception {
		testSong = new Song("testSong.txt");
		testSong1 = new Song("testSong1.txt");
		testSong2 = new Song("testSong2.txt");
	}

	@Test
	public void testSong() {
		assertEquals(testSong1.getTitle(), "Magic");
		assertNotEquals(testSong1.getTitle(), "Bella Luna");
		assertNotEquals(testSong1.getTitle(), "Coldplay");
		assertEquals(testSong1.getArtist(), "Coldplay");
		assertNotEquals(testSong1.getArtist(), "Magic");
		assertNotEquals(testSong1.getArtist(), "Adam Levine");
		assertEquals(testSong.getTitle(), "Bella Luna");
		assertEquals(testSong.getArtist(), "Jason Mraz");	
		assertEquals(testSong.getarrayList().get(0).getDuration(), 0.2, .0000001);
		assertEquals(testSong.getarrayList().get(0).getPitch(), Pitch.C);
		assertEquals(testSong.getarrayList().get(0).getOctave(), 4);
		assertEquals(testSong.getarrayList().get(0).getAccidental(), Accidental.NATURAL);
		assertEquals(testSong.getarrayList().get(0).isRepeat(), false);
		assertEquals(testSong.getnoteLines(), 9);
		assertEquals(testSong.getarrayList().get(3).getDuration(), 0.4, .00000001);
		assertEquals(testSong.getarrayList().get(3).getPitch(), Pitch.R);
		assertEquals(testSong.getarrayList().get(3).getOctave(), 0);
		assertEquals(testSong.getarrayList().get(3).getAccidental(), null);
		assertEquals(testSong.getarrayList().get(3).isRepeat(), false);
	}
	
	//@Test(expected=IOException.class)
	//public void testError() {
	//	Song newSong = new Song("testSong2.txt");	 
	//}

	@Test
	public void testGetTitle() {
		assertEquals("Bella Luna" , testSong.getTitle());
		assertEquals("Magic" , testSong1.getTitle());
		assertEquals("Sugar" , testSong2.getTitle());
		assertTrue(testSong.getTitle().equals("Bella Luna"));
		assertFalse(testSong1.getTitle().equals("Bella Luna"));
		assertNotEquals("Jason Mraz" , testSong.getTitle());
		assertNotEquals("Coldplay" , testSong1.getTitle());
		assertNotEquals("" , testSong.getTitle());
		assertNotEquals(null , testSong.getTitle());
	}

	@Test
	public void testGetArtist() {
		assertEquals("Jason Mraz" , testSong.getArtist());
		assertEquals("Coldplay" , testSong1.getArtist());
		assertEquals("Adam Levine" , testSong2.getArtist());
		assertTrue(testSong.getArtist().equals("Jason Mraz"));
		assertFalse(testSong.getArtist().equals("Bella Luna"));
		assertNotEquals("Bella Luna" , testSong.getArtist());
		assertNotEquals("Magic" , testSong1.getArtist());
		assertNotEquals("Sugar" , testSong2.getArtist());
		assertNotEquals("" , testSong.getArtist());
		assertNotEquals(null , testSong.getArtist());

	}

	@Test
	public void testGetTotalDuration() {
		assertEquals(testSong.getTotalDuration(), 4.0, 0.00000001);
		assertEquals(testSong1.getTotalDuration(), 0.8, 0.00000001);
		assertEquals(testSong2.getTotalDuration(), 0.4, 0.00000001);
	}

	@Test
	public void testOctaveDown() {
		testSong.octaveDown();
		ArrayList<Note>testArray = testSong.getarrayList();
		assertEquals(testArray.get(0).getOctave(), 3);
		assertNotEquals(testArray.get(0).getOctave(), 4);
		assertEquals(testArray.get(1).getOctave(), 3);
		assertEquals(testArray.get(2).getOctave(), 1);
		assertNotEquals(testArray.get(2).getOctave(), 0);
		testSong1.octaveDown();
		ArrayList<Note>testArray1 = testSong1.getarrayList();
		assertEquals(testArray1.get(0).getOctave(), 2);
		assertNotEquals(testArray1.get(0).getOctave(), 3);
		assertEquals(testArray1.get(1).getOctave(), 2);
		assertEquals(testArray1.get(2).getOctave(), 3);
		assertNotEquals(testArray1.get(2).getOctave(), 5);
	}

	@Test
	public void testOctaveUp() {
		testSong.octaveUp();
		ArrayList<Note>testArray = testSong.getarrayList();
		assertEquals(testArray.get(0).getOctave(), 5);
		assertNotEquals(testArray.get(0).getOctave(), 4);
		assertNotEquals(testArray.get(0).getOctave(), 3);
		assertEquals(testArray.get(1).getOctave(), 5);
		assertEquals(testArray.get(2).getOctave(), 2);
		assertNotEquals(testArray.get(2).getOctave(), 1);
		assertEquals(testArray.get(8).getOctave(), 10);
		assertNotEquals(testArray.get(8).getOctave(), 11);
		testSong1.octaveUp();
		ArrayList<Note>testArray1 = testSong1.getarrayList();
		assertEquals(testArray1.get(0).getOctave(), 4);
		assertNotEquals(testArray1.get(0).getOctave(), 3);
		assertNotEquals(testArray1.get(0).getOctave(), 2);
		assertEquals(testArray1.get(1).getOctave(), 4);
		assertNotEquals(testArray1.get(1).getOctave(), 3);
		assertNotEquals(testArray1.get(1).getOctave(), 2);
		assertEquals(testArray1.get(2).getOctave(), 5);
		assertNotEquals(testArray1.get(2).getOctave(), 4);
		assertNotEquals(testArray1.get(2).getOctave(), 3);

	}

	@Test
	public void testChangeTempo() {
		testSong.changeTempo(2);
		ArrayList<Note> testArray = testSong.getarrayList();
		assertEquals(testArray.get(0).getDuration(), 0.4, .00000001);
		assertNotEquals(testArray.get(0).getDuration(), 0.2, .00000001);
		assertEquals(testArray.get(1).getDuration(), 0.8, .00000001);
		assertNotEquals(testArray.get(1).getDuration(), 0.4, .00000001);
		assertEquals(testArray.get(2).getDuration(), 0.8, .00000001);
		assertNotEquals(testArray.get(2).getDuration(), 0.4, .00000001);
		assertEquals(testArray.get(3).getDuration(), 0.8, .00000001);
		assertNotEquals(testArray.get(3).getDuration(), 0.2, .00000001);
		
	}

	@Test
	public void testReverse() {
		ArrayList<Note> testArray = testSong.getarrayList();
		testSong.reverse();
		Note testnote = new Note(0.4, Pitch.F, 10, Accidental.NATURAL, false);
		assertEquals(testnote, testArray.get(0));
		
		
		/**assertTrue(testArray.get(6).equals("C"));
		testSong1.reverse();
		ArrayList<Note> testArray1 = testSong1.getarrayList();
		assertTrue(testArray1.get(1).equals("F"));
		assertFalse(testArray1.get(1).equals("C"));
		testSong2.reverse();
		ArrayList<Note> testArray2 = testSong2.getarrayList();
		assertTrue(testArray2.get(1).equals("E"));
		assertFalse(testArray1.get(1).equals("C"));
		assertTrue(testArray2.get(6).equals("C"));	**/
	}

	@Test
	public void testToString() {
		assertEquals(testSong1.toString(), "[C, F, C]");
	
	}


}