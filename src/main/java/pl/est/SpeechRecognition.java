package pl.est;

import java.io.*;

import edu.cmu.sphinx.api.*;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.TimeFrame;

public class SpeechRecognition {
	
	private AudioConverter converter = new AudioConverter();

	public String speechRec(File input, File output) throws IOException {
		
		final long start = System.currentTimeMillis();
		final Configuration configuration = new Configuration();
		configuration.setAcousticModelPath("resource:/en-us");
		configuration.setDictionaryPath("resource:/cmudict-en-us.dict");
		final Context context = new Context(configuration);
		context.setLocalProperty("decoder->searchManager", "allphoneSearchManager");
		final Recognizer recognizer = context.getInstance(Recognizer.class);
		final InputStream stream = new FileInputStream(converter.convert(input, output));
		recognizer.allocate();
		context.setSpeechSource(stream, TimeFrame.INFINITE);
		final Result result = recognizer.recognize();
		final long stop = System.currentTimeMillis();
		recognizer.deallocate();
		System.out.println("Czas wykonania:" + (stop - start));
		final String cleanresult = result.getBestFinalResultNoFiller().replace("SIL ", "").replace(" SIL", "").replace("+BREATH+", "");
		return cleanresult;

	}
}
