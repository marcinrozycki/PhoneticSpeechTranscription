package pl.est;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class Recording {
	protected boolean running;
	private ByteArrayOutputStream out;
	private AudioFormat format;
	private DataLine.Info info;

	public Recording() {
		this.format = getFormat();
		this.info = new DataLine.Info(TargetDataLine.class, format);
	}

	public void setRunningState(boolean state) {
		this.running = state;
	}

	public void captureAudio() {
		try {
			final TargetDataLine line = (TargetDataLine) AudioSystem
					.getLine(info);
			line.open(format);
			line.start();

			Runnable runner = new Runnable() {
				int bufferSize = (int) format.getSampleRate()
						* format.getFrameSize();
				byte buffer[] = new byte[bufferSize];

				public void run() {
					out = new ByteArrayOutputStream();
					running = true;
					try {
						while (running) {
							int count = line.read(buffer, 0, buffer.length);
							if (count > 0) {
								out.write(buffer, 0, count);
							}
						}
						out.close();
						line.close();
					} catch (IOException e) {
						System.err.println("I/O problems: " + e);
						System.exit(-1);
					}
				}
			};

			Thread captureThread = new Thread(runner);
			captureThread.start();
		} catch (LineUnavailableException e) {
			System.err.println("Line unavailable: " + e);
			System.exit(-2);
		}
	}

	private AudioInputStream byteArrayOutputStreamToAudioInputStream(
			ByteArrayOutputStream baos) {
		byte audio[] = baos.toByteArray();
		InputStream input = new ByteArrayInputStream(audio);
		final AudioFormat format = getFormat();
		return new AudioInputStream(input, format, audio.length
				/ format.getFrameSize());
	}

	public void playAudio() {
		try {
			byte audio[] = out.toByteArray();
			InputStream input = new ByteArrayInputStream(audio);
			final AudioFormat format = getFormat();
			final AudioInputStream ais = new AudioInputStream(input, format,
					audio.length / format.getFrameSize());
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			final SourceDataLine line = (SourceDataLine) AudioSystem
					.getLine(info);
			line.open(format);
			line.start();

			Runnable runner = new Runnable() {
				int bufferSize = (int) format.getSampleRate()
						* format.getFrameSize();
				byte buffer[] = new byte[bufferSize];

				public void run() {
					try {
						int count;
						while ((count = ais.read(buffer, 0, buffer.length)) != -1) {
							if (count > 0) {
								line.write(buffer, 0, count);
							}
						}
						line.drain();
						line.close();
					} catch (IOException e) {
						System.err.println("I/O problems: " + e);
						System.exit(-3);
					}
				}
			};
			Thread playThread = new Thread(runner);
			playThread.start();
		} catch (LineUnavailableException e) {
			System.err.println("Line unavailable: " + e);
			System.exit(-4);
		}
	}

	public void saveOutput(File outputFile) {
		try {
			AudioSystem.write(byteArrayOutputStreamToAudioInputStream(out),
					AudioFileFormat.Type.WAVE, outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private AudioFormat getFormat() {
		float sampleRate = 48000;
		int sampleSizeInBits = 16;
		int channels = 2;
		boolean signed = true;
		boolean bigEndian = true;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
				bigEndian);
	}

}
