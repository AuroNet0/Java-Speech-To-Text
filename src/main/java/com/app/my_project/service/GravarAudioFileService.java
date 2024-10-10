package com.app.my_project.service;

import org.springframework.stereotype.Service;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
@Service
public class GravarAudioFileService {
    private TargetDataLine microphone;
    private AudioInputStream audioStream;
    private File wavFile;

    public void iniciarGravacaoAudio() {
        AudioFormat format = new AudioFormat(16000, 16, 1, true, true);
        wavFile = new File("C:\\Caminho\\Do\\Arquivo\\Arquivo.wav");

        try {
            microphone = AudioSystem.getTargetDataLine(format);
            microphone.open(format);
            microphone.start();

            audioStream = new AudioInputStream(microphone);

            // Gravação assíncrona
            new Thread(() -> {
                try {
                    AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, wavFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    public void pararGravacaoAudio() {
        if (microphone != null) {
            microphone.stop();
            microphone.close();

            try {
                if (audioStream != null) {
                    audioStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            ConverterWavToMp3Service convertendo = new ConverterWavToMp3Service();
            convertendo.converterToMp3();
        }
    }
}
