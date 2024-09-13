package com.app.my_project.service;

import org.springframework.stereotype.Service;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class GravarAudioFileService {

    private static final int GRAVACAO_DURATION_MS = 10000;

    public void gravarAudio() {
        AudioFormat format = new AudioFormat(16000, 16, 1, true, true);
        File wavFile = new File("C:\\Caminho\\Da\\Pasta\\NomeDoArquivo.wav");

        try (TargetDataLine microphone = AudioSystem.getTargetDataLine(format)) {
            microphone.open(format);
            microphone.start();

            AudioInputStream audioStream = new AudioInputStream(microphone);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    microphone.stop();
                    microphone.close();
                }
            }, GRAVACAO_DURATION_MS);

            new Thread(() -> {
                try {
                    AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, wavFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            Thread.sleep(GRAVACAO_DURATION_MS);
            timer.cancel();
        } catch (LineUnavailableException | InterruptedException ex) {
            ex.printStackTrace();
        }

        ConverterWavToMp3Service convertendo = new ConverterWavToMp3Service();
        convertendo.converterToMp3();
    }
}
