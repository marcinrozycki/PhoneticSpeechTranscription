package pl.est;

import static org.assertj.core.api.Assertions.*;

import java.io.*;

import org.junit.Test;

import pl.est.SpeechRecognition;

public class SpeechRecognitionTest {

	@Test
	public void shouldReturnString_whenGivenAudioInputWithSpeech()
			throws IOException {

		// given
		final SpeechRecognition speechRec = new SpeechRecognition();
		final File input = new File("src/test/resources/goodby44.wav");
		final File output = new File("src/test/resources/output.wav");
		// when
		final String result = speechRec.speechRec(input, output);

		// then
		assertThat(result).isEqualTo("G UH DH AH B AY");

	}
}