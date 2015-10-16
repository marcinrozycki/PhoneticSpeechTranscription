package pl.est;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.TextField;
import java.io.File;
import java.io.IOException;





import pl.est.SpeechRecognition;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.Font;
public class MainWindow  {

	private JFrame frame;
		
	public static void main(String[] args) {
		System.setProperty("file.encoding", "UTF-8");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainWindow() {
		initialize();
	}

	
	private void initialize() {
		setupFrame();
		final SpeechRecognition speechRec = new SpeechRecognition();
		final Transcription transcriptor = Transcription.getInstance();
		final TextField textField = setupTextField();
		frame.getContentPane().add(textField);
				
		final JButton btnWczytaj = setupLoadButton(speechRec, transcriptor,
				textField);
		btnWczytaj.setBounds(321, 129, 89, 23);
		frame.getContentPane().add(btnWczytaj);
				
		final Recording recorder = new Recording();

	    final JButton capture = setupRecordButton();
	    frame.getContentPane().add(capture);
	    final JButton stop = setupStopButton();
	    frame.getContentPane().add(stop);
		final JButton play = setupPlayButton();
	    frame.getContentPane().add(play);
	    
	   ActionListener captureListener = new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        capture.setEnabled(false);
	        stop.setEnabled(true);
	        play.setEnabled(false);
	        recorder.captureAudio();
	      }
	    };
	    capture.addActionListener(captureListener);

	    ActionListener stopListener = 
	    new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        capture.setEnabled(true);
	        stop.setEnabled(false);
	        play.setEnabled(true);
	        recorder.setRunningState(false);
	        File tempFile;
			try {
				tempFile = File.createTempFile("recordingTempFile", ".wav");
				recorder.saveOutput(tempFile);
				getSpeechResult(tempFile, transcriptor, speechRec, textField);
				tempFile.delete();
			} catch (IOException e1) {
				textField.setText("Blad: Tymczasowy plik audio jest uszkodzony.");
				e1.printStackTrace();
			}
	        
	      }
	    };
	    stop.addActionListener(stopListener);
	    
	    ActionListener playListener =  new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        recorder.playAudio();
	      }
	    };
	    play.addActionListener(playListener);
	  
	}

	private JButton setupPlayButton() {
		final JButton play = new JButton("Odtw\u00F3rz");
	    play.setEnabled(false);
	    play.setBounds(222, 129, 89, 23);
		return play;
	}

	private JButton setupStopButton() {
		final JButton stop = new JButton("Stop");
	    stop.setEnabled(false);
	    stop.setBounds(123, 129, 89, 23);
		return stop;
	}

	private JButton setupRecordButton() {
		final JButton capture = new JButton("Nagraj");
	    capture.setBounds(20, 129, 89, 23);
	    capture.setEnabled(true);
		return capture;
	}

	private JButton setupLoadButton(final SpeechRecognition speechRec,
			final Transcription transcriptor, final TextField textField) {
		final JButton btnWczytaj = new JButton("Wczytaj");
		btnWczytaj.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						JFileChooser chooser = new JFileChooser();
						chooser.showOpenDialog(null);
						if(chooser.getSelectedFile() != null){
							getSpeechResult(chooser.getSelectedFile(), transcriptor, speechRec, textField); 
						}
					}
				}
		);
		return btnWczytaj;
	}

	private void setupFrame() {
		frame = new JFrame();
		frame.setTitle("Transkrypcja Fonetyczna Mowy w  Jezyku Angielskim");
		frame.setBounds(100, 100, 450, 203);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}

	private TextField setupTextField() {
		final TextField textField = new TextField();
		textField.setFont(new Font("Arial", Font.PLAIN, 20));
		textField.setEditable(false);
		textField.setBounds(10, 10, 414, 113);
		return textField;
	}
	
	private void getSpeechResult(File audioFile, Transcription transcriptor, SpeechRecognition speechRec, TextField textField ) {
		System.out.println(audioFile.getPath());
		String result;
		try {
			File temp = File.createTempFile("tempfile", ".wav");
			String recognizedPhonemes = speechRec.speechRec(audioFile, temp);
			result = transcriptor.transcript(recognizedPhonemes);
			textField.setText(result);
			temp.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
