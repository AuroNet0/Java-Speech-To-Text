package com.app.my_project.service;

import com.google.cloud.speech.v1p1beta1.*;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class TranscreverService {

    public String transcreverAudio(String audioFilePath) throws Exception {

        Path path = Paths.get(audioFilePath);
        byte[] audioBytes = Files.readAllBytes(path);
        ByteString audioBytesStr = ByteString.copyFrom(audioBytes);

        StringBuilder transcricao = new StringBuilder();

        try (SpeechClient speechClient = SpeechClient.create()) {

            RecognitionConfig recognitionConfig = RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.MP3)
                    .setSampleRateHertz(16000)
                    .setLanguageCode("pt-BR")
                    .build();
            RecognitionAudio recognitionAudio = RecognitionAudio.newBuilder()
                    .setContent(audioBytesStr)
                    .build();
            RecognizeResponse recognizeResponse = speechClient.recognize(recognitionConfig,
                    recognitionAudio);
            for (SpeechRecognitionResult result : recognizeResponse.getResultsList()) {
                for (SpeechRecognitionAlternative alternative : result.getAlternativesList()) {
                    transcricao.append(alternative.getTranscript()).append("\n");
                }
            }
        }
        return transcricao.toString();
    }

}

