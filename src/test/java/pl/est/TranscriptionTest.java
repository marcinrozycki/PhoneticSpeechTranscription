package pl.est;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TranscriptionTest {
	@Test
	public void souldReturnListOfLines_whenGivenFile() {
		// given
		Transcription transcriptor = Transcription.getInstance();
		String phonemeString = "T EH S T";
		// when
		String result = transcriptor.transcript(phonemeString);
		// then
		assertEquals("t\u0259st", result);
	}

}
