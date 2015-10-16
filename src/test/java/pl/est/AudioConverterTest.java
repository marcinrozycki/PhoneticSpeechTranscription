package pl.est;

import static org.junit.Assert.*;
import it.sauronsoftware.jave.*;

import java.io.File;

import org.junit.Test;

import pl.est.AudioConverter;

public class AudioConverterTest {
	AudioConverter converter = new AudioConverter();
	Encoder encoder = new Encoder();

	@Test
	public void convertTest() throws InputFormatException, EncoderException {
		// given
		final File input = new File("src/test/resources/input.wav");
		final File output = new File("src/test/resources/output.wav");
		// when
		converter.convert(input, output);
		// then
		assertEquals(encoder.getInfo(input).getAudio().getSamplingRate(), 44100);
		assertEquals(encoder.getInfo(output).getAudio().getSamplingRate(),
				16000);
		assertEquals(encoder.getInfo(output).getAudio().getChannels(), 1);
	}
}
