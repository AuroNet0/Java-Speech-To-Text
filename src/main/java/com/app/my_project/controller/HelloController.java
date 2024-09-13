package com.app.my_project.controller;

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

    @PostMapping("/gravar-audio")
    public String gravarAudio(Model model) {
        try {
            gravarAudioFileService.gravarAudio();
            String transcricao = appService.transcreverAudio("C:\\Users\\Auro Neto\\Music\\Teste\\refinado.mp3");
            model.addAttribute("message", transcricao);
            return "Result";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Erro ao processar áudio: " + e.getMessage());
            return "Result";
        }
    }
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
