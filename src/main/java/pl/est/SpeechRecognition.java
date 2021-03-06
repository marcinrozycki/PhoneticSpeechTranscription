package pl.est;

import java.io.*;

import edu.cmu.sphinx.api.*;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.TimeFrame;

public class SpeechRecognition {

	private static final String RESOURCE_CMUDICT_EN_US_DICT = "resource:/cmudict-en-us.dict";
	private static final String RESOURCE_EN_US = "resource:/en-us";
	private static final String ALLPHONE_SEARCH_MANAGER = "allphoneSearchManager";
	private static final String DECODER_SEARCH_MANAGER = "decoder->searchManager";
	private static final String LANGUAGE_MODEL = "resource:/en-us.lm.dmp";

	private AudioConverter converter = new AudioConverter();
	Configuration configuration;

	public SpeechRecognition() {
		configuration = config();
	}

	public String speechRec(File input, File output) throws IOException {
		Recognizer recognizer = setupRecognizer(input, output);
		Result result = recognizer.recognize();
		recognizer.deallocate();
		return cleanResult(result);
	}

	public String fullSpeechRec(File input) throws IOException {
		Configuration config = config();
		StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(config);
		InputStream stream = new FileInputStream(input);

		recognizer.startRecognition(stream);
		SpeechResult result;
		result = recognizer.getResult();
		recognizer.stopRecognition();
		return result.getHypothesis();
	}

	private Recognizer setupRecognizer(File input, File output)
			throws IOException {
		Context context = setupContext(configuration);
		Recognizer recognizer = context.getInstance(Recognizer.class);
		InputStream stream = new FileInputStream(converter.convert(input,
				output));
		recognizer.allocate();
		context.setSpeechSource(stream, TimeFrame.INFINITE);
		return recognizer;
	}

	private String cleanResult(final Result result) {
		String replaceSilence = result.getBestFinalResultNoFiller().replace(
				"SIL ", "");
		replaceSilence = replaceSilence.replace(" SIL", "");
		return replaceSilence.replace("+BREATH+", "");
	}

	private Context setupContext(final Configuration configuration)
			throws IOException {
		final Context context = new Context(configuration);
		context.setLocalProperty(DECODER_SEARCH_MANAGER,
				ALLPHONE_SEARCH_MANAGER);
		return context;
	}

	private Configuration config() {
		final Configuration configuration = new Configuration();
		configuration.setAcousticModelPath(RESOURCE_EN_US);
		configuration.setDictionaryPath(RESOURCE_CMUDICT_EN_US_DICT);
		configuration.setLanguageModelPath(LANGUAGE_MODEL);
		return configuration;
	}
}
