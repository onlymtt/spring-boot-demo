package criff.academy.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    

    @PostMapping("/send")
    public String sendMessage(Message message) {
        messageService.save(message);
        return "redirect:/messages";
    }
}
