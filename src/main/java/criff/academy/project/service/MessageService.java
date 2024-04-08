package criff.academy.project.service;

import criff.academy.project.model.Message;
import criff.academy.project.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // Metodo per salvare un messaggio
    public Message save(Message message) {
        // Logica di validazione del messaggio può essere aggiunta qui, se necessario.
        return messageRepository.save(message);
    }

    // Metodo per trovare tutti i messaggi tra due utenti
    public List<Message> findAllMessagesBetweenUsers(Long senderId, Long receiverId) {
        return messageRepository.findAllMessagesBetweenUsers(senderId, receiverId);
    }

    // Metodo aggiunto per recuperare tutti i messaggi
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    // Qui puoi aggiungere altri metodi utili per la gestione dei messaggi
}
