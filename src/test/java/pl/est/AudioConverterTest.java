package pl.est;

import static org.junit.Assert.*;
//import it.sauronsoftware.jave.*;

import java.io.File;

import org.junit.Test;

import ws.schild.jave.*;

public class AudioConverterTest {
	AudioConverter converter = new AudioConverter();

	@Test
	public void convertTest() throws InputFormatException, EncoderException {
		// given
		final File input = new File("src/test/resources/goodby44.wav");
		final File output = new File("src/test/resources/output.wav");
		// when
		converter.convert(input, output);
		// then
		MultimediaObject inputObj =  new MultimediaObject(input);
		MultimediaObject outputObj =  new MultimediaObject(output);
		assertEquals(inputObj.getInfo().getAudio().getSamplingRate(), 44100);
		assertEquals(outputObj.getInfo().getAudio().getSamplingRate(),
				16000);
		assertEquals(outputObj.getInfo().getAudio().getChannels(), 1);
	}
}
