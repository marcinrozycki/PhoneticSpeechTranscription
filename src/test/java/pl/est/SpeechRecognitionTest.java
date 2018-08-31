package pl.est;

import static org.assertj.core.api.Assertions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class SpeechRecognitionTest {

	@Test
	public void shouldReturnString_whenGivenAudioInputWithSpeech()
			throws IOException {

		// given
		final SpeechRecognition speechRec = new SpeechRecognition();
		final File input = new File("src/test/resources/goodby44.wav");
		final File output = new File("src/test/resources/output.wav");
		// when
		long start = System.currentTimeMillis();
		final String result = speechRec.speechRec(input, output);
		long stop = System.currentTimeMillis();
		System.out.println("Czas wykonania:" + (stop - start));
		// then
		assertThat(result).isEqualTo("G UH D AH B AY");

	}

	@Test
	public void shouldReturnStrings_whenGivenAudioInputWithSpeech()
			throws IOException {

		// given
		final SpeechRecognition speechRec = new SpeechRecognition();
		final File output = new File("src/test/resources/output.wav");
		File f = new File("src/test/resources/testdb");
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
		ArrayList<String> results = new ArrayList<>();

		// when
		long start = System.currentTimeMillis();
		for(File file :files) {
			String result = speechRec.speechRec(file, output);
			String fileName = file.getName().replace(".wav", "");
			results.add(fileName + " : " + result);
		}
		long stop = System.currentTimeMillis();
		System.out.println("Operation time: " + (stop - start));
		// then
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (String res : results) {
			System.out.println(res);
		}
	}

	@Test
	public void shouldReturnTranscription_whenGivenAudioInputWithSpeech()
			throws IOException {

		// given
		final SpeechRecognition speechRec = new SpeechRecognition();
		Transcription transcriptor = Transcription.getInstance();
		final File output = new File("src/test/resources/output.wav");
		File f = new File("src/test/resources/conttestdb");
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
		ArrayList<String> results = new ArrayList<>();

		// when
		long start = System.currentTimeMillis();
		for(File file :files) {
			String result = speechRec.speechRec(file, output);
			String cleanResult = transcriptor.transcript(result);
			String fileName = file.getName().replace(".wav", "");
			results.add(fileName + " : " + cleanResult);
		}
		long stop = System.currentTimeMillis();
		System.out.println("Operation time: " + (stop - start));
		// then
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (String res : results) {
			System.out.println(res);
		}
	}

	@Test
	public void shouldReturnFullRecognizedStrings_whenGivenAudioInputWithSpeech()
			throws IOException {

		// given
		final SpeechRecognition speechRec = new SpeechRecognition();
		final File output = new File("src/test/resources/output.wav");
		File f = new File("src/test/resources/conttestdb");
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
		ArrayList<String> results = new ArrayList<>();

		// when
		long start = System.currentTimeMillis();
		for(File file :files) {
			String result = speechRec.fullSpeechRec(file);
			String fileName = file.getName().replace(".wav", "");
			results.add(fileName + " : " + result);
		}
		long stop = System.currentTimeMillis();
		System.out.println("Operation time: " + (stop - start));
		// then
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (String res : results){
			System.out.println(res);
		}
	}
}
