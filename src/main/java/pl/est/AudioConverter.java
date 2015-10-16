package pl.est;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;
import java.io.File;

public class AudioConverter {
	private Encoder encoder;
	private EncodingAttributes encodingAttributes;
	private AudioAttributes audioAttributes;

	public AudioConverter() {
		encoder = new Encoder();
		setupAudioAttributes();
		setupEncodingAttributes();
	}

	public File convert(File input, File output) {
		try {
			encoder.encode(input, output, encodingAttributes);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return output;
	}

	private void setupEncodingAttributes() {
		encodingAttributes = new EncodingAttributes();
		encodingAttributes.setFormat("wav");
		encodingAttributes.setAudioAttributes(audioAttributes);
	}

	private void setupAudioAttributes() {
		audioAttributes = new AudioAttributes();
		audioAttributes.setBitRate(new Integer(256000));
		audioAttributes.setChannels(new Integer(1));
		audioAttributes.setSamplingRate(new Integer(16000));
	}
}
