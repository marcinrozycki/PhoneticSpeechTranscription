package pl.est;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TranscriptionDownloader {
	
	private String dictionaryUrl;
	
	public TranscriptionDownloader(String onlineDictionaryUrl){
		this.dictionaryUrl = onlineDictionaryUrl;
	}
	
	public String getTranscriptionFromWeb(String word){
		String transcription = "";
		
		try {
			Document doc = Jsoup.connect(dictionaryUrl+word).get();
			Elements phons = doc.getElementsByClass("phon");
			Element phon = phons.get(0);
			String text = phon.text();
			transcription = text.replace("BrE", "").replace("//", "");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return transcription;
	}
}
