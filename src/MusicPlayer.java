package songs;

import java.awt.BorderLayout;
import java.awt.Color;

/**
 * Music Player
 *
 * This instructor-provided file implements the graphical user interface (GUI)
 * for the Music Player program and allows you to test the behavior
 * of your Song class.
 */

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
//import javax.swing.Box;
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JFileChooser;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JSlider;
//import javax.swing.JSpinner;
//import javax.swing.JTextField;
//import javax.swing.SpinnerNumberModel;
//import javax.swing.UIManager;
import javax.swing.*;

/**
 * 
 * @author anthony and Avantika
 *
 */
public class MusicPlayer implements ActionListener, StdAudio.AudioEventListener {

	// instance variables
	private Song song;
	private boolean playing; // whether a song is currently playing

	// GUI components
	private JFrame frame;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;

	// this the label that says 'welcome to the music player'
	private JLabel statusLabel;

	private JFileChooser fileChooser;
	private JSlider currentTimeSlider;

	// these are the two labels that indicate time
	// to the right of the slider
	private JLabel currentTimeLabel;
	private JLabel totalTimeLabel;

	private JButton play;
	private JButton stop;
	private JButton pause;
	private JButton load;
	private JButton reverse;
	private JButton up;
	private JButton down;

	private JLabel tempo;
	private JTextField tempoText;
	private JButton changeTempo;
	private boolean loading = true;

	/**
	 * this method creates the music player GUI window and graphical components.
	 */
	public MusicPlayer() throws IOException {
		
		song = null;
		createComponents();
		doLayout();
		StdAudio.addAudioEventListener(this);
		frame.setVisible(true);
	}
	
	/**
	 * this method is called when the user interacts with graphical components, such as
	 * clicking on a button.
	 */
	public void actionPerformed(ActionEvent event) {
		String cmd = event.getActionCommand();
		if (cmd.equals("Play")) {
			this.playSong();
		} else if (cmd.equals("Pause")) {
			StdAudio.setPaused(!StdAudio.isPaused());
		} else if (cmd == "Stop") {
			StdAudio.setMute(true);
			StdAudio.setPaused(false);
			loading = true;
		} else if (cmd == "Load") {
			try {
				loadFile();
			} catch (IOException ioe) {
				System.out.println("not able to load from the file");
			}
		} else if (cmd == "Reverse") {
			song.reverse(); // implementing the reverse method
		} else if (cmd == "Up") {
			song.octaveUp(); // implementing the octave up method
		} else if (cmd == "Down") {
			song.octaveDown(); // implementing the octave down method
		} else if (cmd == "Change Tempo") {
			song.changeTempo(Double.valueOf(tempoText.getText()));// implementing the change tempo method
													
		}
	}

	/**
	 * this method is called when audio events occur in the StdAudio library. We use this to
	 * set the displayed current time in the slider.
	 */
	public void onAudioEvent(StdAudio.AudioEvent event) {
		// update current time
		if (event.getType() == StdAudio.AudioEvent.Type.PLAY
				|| event.getType() == StdAudio.AudioEvent.Type.STOP) {
			setCurrentTime(getCurrentTime() + event.getDuration());
		}
	}

	/**
	 * this method sets up the graphical components in the window and event listeners.
	 */
	private void createComponents() {
		// Creating all the components here
		frame = new JFrame("Music Player");
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		statusLabel = new JLabel("Welcome to the Music Player", JLabel.CENTER);
		fileChooser = new JFileChooser();
		currentTimeSlider = new JSlider();
		currentTimeLabel = new JLabel();
		totalTimeLabel = new JLabel();
		play = new JButton("Play");
		stop = new JButton("Stop");
		pause = new JButton("Pause");
		
		load = new JButton("Load");
		reverse = new JButton("Reverse");
		up = new JButton("Up");
		down = new JButton("Down");
		tempo = new JLabel("Tempo");
		tempoText = new JTextField("Enter your tempo, default is 1.");
		changeTempo = new JButton("Change Tempo");
		doEnabling();
	}

	/**
	 * Sets whether every button, slider, spinner, etc. should be currently
	 * enabled, based on the current state of whether a song has been loaded and
	 * whether or not it is currently playing. This is done to prevent the user
	 * from doing actions at inappropriate times such as clicking play while the
	 * song is already playing, etc.
	 */
	private void doEnabling() {
		// To enable the buttons appropriately
		statusLabel.setEnabled(true);
		// checking
		System.out.println("playing is " + playing);
		System.out.println("loading is " + loading);
		// condition when the file is not loaded
		if (playing == false) {
			// slider related - disable all
			currentTimeSlider.setEnabled(false);
			currentTimeLabel.setEnabled(false);
			totalTimeLabel.setEnabled(false);
			// control related - disable load button
			play.setEnabled(false);
			stop.setEnabled(false);
			pause.setEnabled(false);
			load.setEnabled(true);
			reverse.setEnabled(false);
			up.setEnabled(false);
			down.setEnabled(false);
			// tempo related - disable all
			tempo.setEnabled(false);
			tempoText.setEditable(false);
			changeTempo.setEnabled(false);
		} else {
			currentTimeSlider.setEnabled(false);
			currentTimeLabel.setEnabled(false);
			totalTimeLabel.setEnabled(false);
			// control related - disable load button
			play.setEnabled(false);
			stop.setEnabled(true);
			pause.setEnabled(true);
			load.setEnabled(false);
			reverse.setEnabled(true);
			up.setEnabled(true);
			down.setEnabled(true);
			// tempo related - disable all
			tempo.setEnabled(true);
			tempoText.setEditable(true);
			changeTempo.setEnabled(true);
		}
		if ((loading == false) && (playing == false)) {
			// slider related - disable all
			currentTimeSlider.setEnabled(false);
			currentTimeLabel.setEnabled(false);
			totalTimeLabel.setEnabled(false);
			// control related - disable load button
			play.setEnabled(true);
			stop.setEnabled(false);
			pause.setEnabled(false);
			load.setEnabled(false);
			reverse.setEnabled(false);
			up.setEnabled(false);
			down.setEnabled(false);
			// tempo related - disable all
			tempo.setEnabled(false);
			tempoText.setEditable(false);
			changeTempo.setEnabled(false);
		}

	}

	/**
	 * this method will perform the layout of the components within the graphical window. Also make
	 * the window a certain size and put it in the center of the screen.
	 */
	private void doLayout() {
		// Laying out the components
		frame.setSize(640, 350);
		
		// for centering
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);
		// defining layout type
		panel1.setLayout(new BorderLayout());
		// placing slider and time label components
		panel1.add(statusLabel, BorderLayout.NORTH);
		panel1.add(currentTimeSlider, BorderLayout.CENTER);
		panel1.add(currentTimeLabel, BorderLayout.EAST);
		panel1.add(totalTimeLabel, BorderLayout.EAST);

		// defining size of the JSlider
		Dimension d = currentTimeSlider.getPreferredSize();
		currentTimeSlider.setPreferredSize(new Dimension(d.width + 350,
				d.height + 50));
		// Set characteristics of the JSlider
		currentTimeSlider.setMajorTickSpacing(10);
		currentTimeSlider.setMinorTickSpacing(2);
		currentTimeSlider.setPaintTicks(true);
		currentTimeSlider.setPaintLabels(true);

		// ************* Panel 2 ***************
		// defining layout type
		panel2.setLayout(new FlowLayout());

		// placing control components with size
		panel2.add(play);
		play.setPreferredSize(new Dimension(75, 50));
		panel2.add(stop);
		stop.setPreferredSize(new Dimension(75, 50));
		panel2.add(pause);
		pause.setPreferredSize(new Dimension(75, 50));
		panel2.add(load);
		reverse.setPreferredSize(new Dimension(85, 50));
		panel2.add(reverse);
		load.setPreferredSize(new Dimension(75, 50));
		panel2.add(up);
		up.setPreferredSize(new Dimension(75, 50));
		panel2.add(down);
		down.setPreferredSize(new Dimension(75, 50));

		// adding listener to control elements
		play.addActionListener(this);
		stop.addActionListener(this);
		pause.addActionListener(this);
		reverse.addActionListener(this);
		load.addActionListener(this);
		up.addActionListener(this);
		down.addActionListener(this);

		// ************* Panel 3 ***************
		// defining layout type
		panel3.setLayout(new BorderLayout());

		// placing tempo components
		panel3.add(tempo, BorderLayout.WEST);
		panel3.add(tempoText, BorderLayout.CENTER);
		panel3.add(changeTempo, BorderLayout.EAST);

		// FIXME reading value from tempoText
		// tempoText.addKeyListener(l);

		// FIXME tempo adjusted as per the tempoText input
		changeTempo.addActionListener(this);

		// **** organizing panels in container ******
		frame.setLayout(new BorderLayout());
		frame.add(panel1, BorderLayout.NORTH);
		frame.add(panel2, BorderLayout.CENTER);
		frame.add(panel3, BorderLayout.SOUTH);
	}

	/**
	 * this method returns the estimated current time within the overall song, in seconds.
	 */
	private double getCurrentTime() {
		String timeStr = currentTimeLabel.getText();
		timeStr = timeStr.replace(" /", "");
		try {
			return Double.parseDouble(timeStr);
		} catch (NumberFormatException nfe) {
			return 0.0;
		}
	}

	/**
	 * this method pops up a file-choosing window for the user to select a song file to be
	 * loaded. If the user chooses a file, a Song object is created and used to
	 * represent that song.
	 */
	private void loadFile() throws IOException {
		loading = true;
		doEnabling();
		if (fileChooser.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File selected = fileChooser.getSelectedFile();
		if (selected == null) {
			return;
		}
		statusLabel.setText("Current song: " + selected.getName());
		String filename = selected.getAbsolutePath();
		System.out.println("Loading song from " + selected.getName() + " ...");

		// Creating a song from the file
		song = new Song(filename);

		tempoText.setText("1.0");
		setCurrentTime(0.0);
		updateTotalTime();
		System.out.println("Loading complete.");
		System.out.println("Song: " + song);
		loading = false;
		doEnabling();
	}

	/**
	 * this metho will intitiate the playing of the current song in a separate thread (so that
	 * it does not lock up the GUI). You do not need to change this method. It
	 * will not compile until you make your Song class.
	 */
	private void playSong() {
		if (song != null) {
			setCurrentTime(0.0);
			Thread playThread = new Thread(new Runnable() {
				public void run() {
					StdAudio.setMute(false);
					System.out.println("in playSong");
					playing = true;
					doEnabling();
					String title = song.getTitle();
					String artist = song.getArtist();
					double duration = song.getTotalDuration();
					System.out.println("Playing \"" + title + "\", by "
							+ artist + " (" + duration + " sec)");
					song.play();
					System.out.println("Playing complete.");
					playing = false;
					doEnabling();
				}
			});
			playThread.start();
		}
	}

	/**
	 * this method sets the current time display slider/label to show the given time in
	 * seconds. Bounded to the song's total duration as reported by the song.
	 */
	private void setCurrentTime(double time) {
		double total = song.getTotalDuration();
		time = Math.max(0, Math.min(total, time));
		currentTimeLabel.setText(String.format("%08.2f /", time));
		currentTimeSlider.setValue((int) (100 * time / total));
	}

	/**
	 * this method updates the total time label on the screen to the current total duration.
	 */
	private void updateTotalTime() {
		
	}
}

