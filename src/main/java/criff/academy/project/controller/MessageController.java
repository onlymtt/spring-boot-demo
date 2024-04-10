package criff.academy.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import criff.academy.project.service.MessageService;
import criff.academy.project.model.Message;
import criff.academy.project.repository.UserRepository;

@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/messages")
    public String viewMessages(Model model) {
        if (!model.containsAttribute("message")) {
            model.addAttribute("message", new Message());
        }
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("messages", messageService.findAll());
        return "messages";
    }

    @PostMapping("/messages/send")
    public String sendMessage(@ModelAttribute("message") Message message, 
                              @RequestParam(required = false) String receiverUsername, 
                              @RequestParam(defaultValue = "false") boolean sendPrivate) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean isBroadcast = !sendPrivate;
        
        messageService.save(message, username, isBroadcast, receiverUsername);
        return "redirect:/messages";
    }
}
