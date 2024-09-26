package com.app.my_project.service;

import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;

public class ConverterWavToMp3Service {

    public void converterToMp3() {
        File audioFile = new File("C:\\Caminho\\Da\\Pasta\\NomeDoArquivo.wav"); //arquivo bruto
        File mp3File = new File("C:\\Caminho\\Da\\Pasta\\NomeDoArquivo.mp3"); // convertido para transcrição

        try {
            if (!audioFile.exists()) {
                System.err.println("Arquivo WAV não encontrado.");
                return;
            }
            AudioAttributes audioAttributes = new AudioAttributes();
            audioAttributes.setCodec("libmp3lame");
            audioAttributes.setBitRate(64000);
            audioAttributes.setChannels(1);
            audioAttributes.setSamplingRate(22050);

            EncodingAttributes encodingAttributes = new EncodingAttributes();
            encodingAttributes.setOutputFormat("mp3");
            encodingAttributes.setAudioAttributes(audioAttributes);

            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(audioFile), mp3File, encodingAttributes);

        } catch (EncoderException e) {
            e.printStackTrace();
            System.err.println("Erro ao codificar o arquivo. Verifique o formato e a integridade do arquivo de entrada.");
        }
    }
}
