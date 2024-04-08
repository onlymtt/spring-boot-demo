package criff.academy.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import criff.academy.project.service.MessageService;
import criff.academy.project.model.Message;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/messages")
    public String viewMessages(Model model) {
        if (!model.containsAttribute("message")) {
            model.addAttribute("message", new Message());
        }
        model.addAttribute("messages", messageService.findAll());
        return "messages";
    }
    

    @PostMapping("/messages/send")
    public String sendMessage(@ModelAttribute("message") Message message) {
        // Ottieni l'username dell'utente autenticato
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Ottieni l'username dell'utente autenticato

        messageService.save(message, username); // Passa l'username al metodo save
        return "redirect:/messages";
    }
    
    

}
