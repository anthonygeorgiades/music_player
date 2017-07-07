package songs;

import java.io.BufferedReader;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * 
 * @author Anthony s Avantika
 * Public class song
 *First declare instance variables for each song
 */
public class Song {
	//Instance variables
	private String title; //each song has a title as a string
	private String artist; //each song has an artist as a string
	private double totalDuration; //each song has a total duration as a double
	//****IMPORTANT NOTE ARVIND TOLD ME TO CREATE ARRAYLSIT INSTEAD OF ARRAY EVEN THOUGH THE ASSIGNMENT SAYS OTHERWISE
	private ArrayList<Note>arrayList; //set an arraylist of notes
	private int noteLines; //each song has set number of notelines as an int

	/**
	 * 
	 * In this constructor you should populate your song's array of notes by reading note data from the specfied file
	 * The constructor is the only part of your code that should read data from the input filespecifideed le. The le format will be the same as described previously. You should use the split function to break up the contents of each note line.
	 * @param filename input parameter if filename typically as a txt file
	 * @throws IOException - see comment on line 40
	 */
	//****IMPORTANT NOTE ARVIND TOLD ME TO CREATE ARRAYLSIT INSTEAD OF ARRAY EVEN THOUGH THE ASSIGNMENT SAYS OTHERWISE
	public Song(String filename) throws IOException{ //throw exception if notes do not fit type of normal or rest note of proper length
		BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		title = bReader.readLine(); //using reader read title
		artist = bReader.readLine(); //using reader read artist
		String notes = bReader.readLine(); //using reader read notes as a string
		noteLines = Integer.valueOf(notes); //set notelines to value of notes
		arrayList = new ArrayList<Note>(); //create new arraylist of notes
		String line = bReader.readLine(); 
		//we will create an ArrayList of notes
		while(line!= null ){ //while line is not null 
			String [] fileArray = line.split("\\s+"); //split each line by the space
			if (fileArray.length == 5){ //if note is length 5, a regular note
				double duration = Double.parseDouble(fileArray[0]); //duration is in index 0
				Pitch pitch = Pitch.getValueOf(fileArray[1]); //pitch is in index 1
				int octave = Integer.parseInt(fileArray[2]); //ocatve is in index 2
				Accidental accidental = Accidental.getValueOf(fileArray[3]); //accidental is in index 3
				boolean repeat = Boolean.parseBoolean(fileArray[4]); //repeat boolean in index 4
				Note note = new Note(duration, pitch, octave, accidental, repeat);	//new note created with parameters above
				arrayList.add(note);

			}
			else if (fileArray.length==3){ //else if the lenght is not 5 but 3, a rest note, then do the following to set create of note
				double duration = Double.parseDouble(fileArray[0]);
				Pitch pitch = Pitch.getValueOf(fileArray[1]);
				boolean repeat = Boolean.parseBoolean(fileArray[2]);
				Note note = new Note(duration, repeat);
				arrayList.add(note);
			}
			else{
				throw new IOException(); //if length of note is note 5 or 3, not regular or rest, throw exception
			}
			line = bReader.readLine();
		}
		bReader.close();
	}

	/**
	 * this method Returns the title of the song
	 * @return Returns the title of the song
	 */
	public String getTitle(){
		return this.title;

	}

	/**
	 * this method Returns the artist
	 * @return Returns the artist
	 */
	public String getArtist(){
		return this.artist;
	}
	
	/**
	 * this method returns the note lines
	 * @return notelines
	 */
	public int getnoteLines(){
		return this.noteLines;
	}

	/**
	 * In this method you should return the total duration (length) of the song, in seconds
	 * In general this will be equal to the sum of the durations of the song's notes, but if repeated it counts twice towards the total
	 * @return
	 */
	public double getTotalDuration(){
		totalDuration=0.0; //we first set total duration to 0
		for(Note n : arrayList){	//we will iterate through each note n in the arrayList
			totalDuration += n.getDuration(); //we will first count the duration of each note once
		}
		/**
		 * next we will go count the repeated notes
		 */
		int location = 0; 
		int repeatStart = 0; //set variable repeatStart to 0
		int repeatEnd = 0; //set variable repeatEnd to 0
		for(Note n: arrayList){ //iterate through each note again to count repeat notes
			if (n.isRepeat()==true && repeatStart == 0){ //if the note boolean repeat returns true and our repeatStart is still 0 then we will begin counting again
				repeatStart = location; //repeat start is set to the exact location
			}
			else if (n.isRepeat()==true && repeatStart != 0){ //if repeat is true but we have already started the repeat counter, we know this is the second repeatnote of the repeat section
				repeatEnd = location;	//repeatend is set to this location
				for(int i =repeatStart; i<=repeatEnd; i++){ //now for each note between the repeatstart and repeat end, we count total duration, as to count the duration of the repeat notes
					totalDuration += arrayList.get(i).getDuration();
				}
				repeatStart = 0; //reset to 0 
				repeatEnd = 0;	//reset to 0
			}
			location++; //count through location
		}
		return totalDuration;	//return total duration
	}

	/**
	 * @return the arraylist of notes
	 */
	public ArrayList<Note>getarrayList(){
		return arrayList;
	}

	/**
	 * In this method you should play your song so that it can be heard on the computer's speakers.
	 * Essentially this consists of calling the play method on each Note in your array
	 * The notes should be played from the beginning of the list to the end, unless there are
	 * notes that are marked as being part of a repeated section.
	 * This method should not modify the state of your array.
	 */
	public  void play(){
		int location = 0; //we will follow same logic of the gettotalduration method to account for playing the repeat notes
		int repeatStart = 0; //set repeatStart to 0
		int repeatEnd = 0; //set repeatEnd to 0
		for(int start = 0; start < noteLines; start ++){ //set start to 0 and for each value less than noteliens (so for each note) count through
			Note n = arrayList.get(start); 
			if (n.isRepeat()==true && repeatStart == 0){
				repeatStart = location;
			}
			if (n.isRepeat()==true && repeatStart != 0){
				repeatEnd = location;		
				for(int i =repeatStart; i<=repeatEnd; i++){
					arrayList.get(i).play();
				}
				for(int i =repeatStart; i<=repeatEnd; i++){
					arrayList.get(i).play();
				}
			start = repeatEnd+1;
			}
			else {
				n.play();
			}
		}
	}

	/**
	 * In this method you should modify the state of the notes in your internal array so that
	 * they are all exactly 1 octave lower in pitch than their current state. For example, a
	 * C note in octave 4 would become a C note in octave 3. 
	 *
	 * **We will go through each octave that is not at 1 and move it down 1
	 * 
	 * return octaave of note
	 */
	public boolean octaveDown(){
		for(Note n: arrayList){
			if (n.getOctave()==1){
				return false; //special case
			}else{
				if(n.getOctave()!=1){
					int oct = n.getOctave();
					oct = oct-1;
					n.setOctave(oct);
				}
			}
		}
		return true;

	}

	/**
	 * 
	 * @return
	 */
	public boolean octaveUp(){
		for(Note n: arrayList){
			if (n.getOctave()==10){
				return false; //special case
			}else{
				if(n.getOctave()!=10){
					int oct = n.getOctave();
					oct = oct+1;
					n.setOctave(oct);
				}
			}
		}
		return true;

	}

	/**
	 * In this method you should multiply the duration of each note in your song by the given
	 * ratio. Passing a ratio of 1 will do nothing. A ratio of 3 will make each note's duration
	 * three times as long (slow down the song), or a ratio of 0.25 will make the song speed
	 * past at 4 times the speed.
	 * ***Change tempo of song by inputting new ratio as a double then multiplying ratio by current duration of each note
	 * @param ratio to be inputted
	 * 
	 */
	public void changeTempo(double ratio){
		for(Note n: arrayList){ //for each note n in the arraylist 
			Double dur = n.getDuration(); //set duration of each note to dur
			dur = dur*ratio; //set each duration of each note as dur*ratio inputted
			n.setDuration(dur); //set new duration after being multiplied by the ratio
		}
	}

	/**
	 * In this method you should reverse the order of the notes in your melody. Future calls to
	 * play would play the notes in the opposite of the order they were in before the call. For
	 * example, a song containing notes A, F, G, B would become B, G, F, A. This amounts
	 * to reversing the order of the elements of your internal array of notes. Do not make a
	 * complete copy of your internal array, and do not create any other data structures such
	 * as arrays, strings, or lists; just modify your array in-place.
	 * Please write the reversal code yourself, do not use an existing Java array reversal
	 * library
	 * 
	 * 
	 */
	public void reverse(){
		for(int i=0; i<noteLines/2; i++){ //starting at int i and going through the entire list of note lines / 2 (so if odd number, round down and do not reverse middle since since reversing itslef wouldnt change position
			Note a = arrayList.get(i); //for note a, go through arraylist and get note
			Note b = arrayList.get(noteLines-i-1); //for note b, go through arraylist and get the flipped note by calling notelines - i -1
			arrayList.set(i, b); //flip notes b
			arrayList.set(noteLines-i-1, a); //flip notes a
		}
	}

	/**
	 * 
	 * You are writing a toString method in your Song class simply for debugging and for
	 * the purposes of also being able to write some kind of unit test. It is near impossible
	 * to unit test sound being produced from a speaker. Make your toString method print
	 * out enough information. It will be useful here to know that Arrays.toString returns a
	 * string representation of an array.
	 * SINCE WE ARE USING AN ARRAYLIST WE CANNOT USE SIMILAR METHOD AS LISTED IN ASSIGNMENT
	 */
	public String toString(){
		String temp = "["; //create string temp with open bracket
		for (int i =0; i<arrayList.size(); i++){ //for each int i in the size of the arraylist, count through
			temp = temp + arrayList.get(i).getPitch(); //add the pitch, or note, of each note to temp
			if(i != arrayList.size()-1 ){ //if we are not at the last note in the list, then we will add a comma and a space to our string temp
				temp = temp + ", ";
				
			}
		}
		temp = temp + "]";	 //add close bracket to temp
		return temp; //return temp
	}

	
	//For checking
	/**
	 * For testing 
	 * @param args
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Song song = new Song("testSong.txt");
		System.out.println(song.toString());
		System.out.println(song.getArtist());
		System.out.println(song.getTitle());
		System.out.println(song.getTotalDuration());
	}
	
}