package com.app.my_project.controller;

import com.app.my_project.service.ClassificacaoService;
import com.app.my_project.service.TranscreverService;
import com.app.my_project.service.GravarAudioFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {

    @Autowired
    private TranscreverService appService;

    @Autowired
    private GravarAudioFileService gravarAudioFileService;

    @Autowired
    ClassificacaoService classificacaoService;

    @PostMapping("/iniciar-gravacao")
    public ResponseEntity<String> iniciarGravacao() {
        try {
            gravarAudioFileService.iniciarGravacaoAudio();
            return ResponseEntity.ok("Gravação iniciada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao iniciar a gravação: " + e.getMessage());
        }
    }

    @PostMapping("/parar-gravacao")
    public ResponseEntity<Map<String, String>> pararGravacao() {
        try {
            gravarAudioFileService.pararGravacaoAudio();
            String transcricao = appService.transcreverAudio("C:\\Caminho\\Do\\Arquivo\\Arquivo.mp3");
            classificacaoService.classificacaoTexto(transcricao);
            String classification = classificacaoService.getResultadoClassificacao();

            Map<String, String> response = new HashMap<>();
            response.put("transcricao", transcricao);
            response.put("classification", classification);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/transcrever-arquivo")
    public ResponseEntity<Map<String, String>> transcreverArquivo() {
        try {
            var filePath = "C:\\Caminho\\Do\\Arquivo\\Arquivo.mp3";
            String transcricao = appService.transcreverAudio(filePath);
            classificacaoService.classificacaoTexto(transcricao);
            String classification = classificacaoService.getResultadoClassificacao();

            Map<String, String> response = new HashMap<>();
            response.put("transcricao", transcricao);
            response.put("classification", classification); // Adicione a classificação se necessário

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }


}
