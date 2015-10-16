package pl.est;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class Transcription {
	private static Transcription instance;
	private HashMap<String, String> transcriptions;
	private CSVReader reader;

	private Transcription() {
		transcriptions = new HashMap<String, String>();
		readPhoneticDictionary(new File("slownik_fonetyczny.csv"));
	}

	public static Transcription getInstance() {
		if (instance == null) {
			instance = new Transcription();
		}
		return instance;

	}

	private void readPhoneticDictionary(File dictionaryPath) {
		try {
			reader = new CSVReader(new InputStreamReader(new FileInputStream(
					dictionaryPath), "UTF-8"), ';', '"', 0);
			List<String[]> dictionaryLines = reader.readAll();
			for (String[] symbols : dictionaryLines) {
				transcriptions.put(symbols[0], symbols[1]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String transcript(String recognizedPhonemes) {
		String result = new String();
		String[] phonemeList = recognizedPhonemes.split(" ");
		for (String phoneme : phonemeList) {
			System.out.print(phoneme + " ");
			if (phoneme != null) {
				result += transcriptions.get(phoneme);
			}
		}
		if (isResultEmpty(result)) {
			return "Nic nie rozpoznano";
		} else {
			return result;
		}
	}

	private boolean isResultEmpty(String result) {
		return result == "" || result.isEmpty() || result == null
				|| result == "null" || result.contentEquals("");
	}

}
