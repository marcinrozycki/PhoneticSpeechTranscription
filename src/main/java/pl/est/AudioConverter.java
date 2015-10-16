package pl.est;



import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;
import java.io.File;

public class AudioConverter {
	public File convert(File input, File output) {
		final Encoder encoder = new Encoder();
		final EncodingAttributes attributes = new EncodingAttributes();
		attributes.setFormat("wav");
		final AudioAttributes audio = new AudioAttributes();
		audio.setBitRate(new Integer(256000));
		audio.setChannels(new Integer(1));
		audio.setSamplingRate(new Integer(16000));
		attributes.setAudioAttributes(audio);

		final File source = input;
		final File target = output;
		try{
			encoder.encode(source, target, attributes);
		} catch (final Exception e) {
			e.printStackTrace();
		} 
		return target;
	}
}
