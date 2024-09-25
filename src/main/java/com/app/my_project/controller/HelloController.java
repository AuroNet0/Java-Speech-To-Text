package com.app.my_project.controller;

import com.app.my_project.service.ClassificacaoService;
import com.app.my_project.service.TranscreverService;
import com.app.my_project.service.GravarAudioFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HelloController {

    @Autowired
    private TranscreverService appService;

    @Autowired
    private GravarAudioFileService gravarAudioFileService;

    @Autowired
    ClassificacaoService classificacaoService;

    private boolean isRecording = false;

    @PostMapping("/iniciar-gravacao")
    public String iniciarGravacao(Model model) {
        try {
            gravarAudioFileService.iniciarGravacaoAudio();
            isRecording = true;
            model.addAttribute("isRecording", isRecording);
            model.addAttribute("message", "Gravação iniciada.");
            return "hello";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Erro ao iniciar a gravação: " + e.getMessage());
            return "hello";
        }
    }

    // Parar gravação de áudio, transcrever e classificar
    @PostMapping("/parar-gravacao")
    public String pararGravacao(Model model) {
        try {
            gravarAudioFileService.pararGravacaoAudio();
            isRecording = false;
            model.addAttribute("isRecording", isRecording);
            String transcricao = appService.transcreverAudio("C:\\Users\\Auro Neto\\Music\\Teste\\refinado.mp3");
            model.addAttribute("message", transcricao);

            classificacaoService.classificacaoTexto(transcricao);
            String classification = classificacaoService.getResultadoClassificacao();
            model.addAttribute("classification", classification);

            return "Result";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Erro ao processar áudio: " + e.getMessage());
            return "Result";
        }
    }
    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("isRecording", isRecording);
        return "hello";
    }

}
