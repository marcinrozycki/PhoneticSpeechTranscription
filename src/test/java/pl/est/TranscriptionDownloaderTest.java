package pl.est;

import static org.junit.Assert.*;
import org.junit.Test;

public class TranscriptionDownloaderTest {

	
	@Test
	public void shouldReturnDownloadedTranscription_whenWordIsGiven(){
		//given
		String url = "http://www.oxfordlearnersdictionaries.com/definition/english/";
		String word = "transcription";
		String expectedTranscription = "trænˈskrɪpʃn";
		TranscriptionDownloader downloader = new TranscriptionDownloader(url);
		
		//when
		String transcription = downloader.getTranscriptionFromWeb(word);
		
		//then
		assertEquals(expectedTranscription, transcription);
	}
}
