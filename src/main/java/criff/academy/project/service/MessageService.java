package criff.academy.project.service;

import criff.academy.project.model.Message;
import criff.academy.project.repository.MessageRepository;
import criff.academy.project.repository.UserRepository;
import criff.academy.project.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository; // Assumo l'esistenza di UserRepository

    @Autowired
    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    // Metodo aggiornato per salvare un messaggio
    public Message save(Message message, String senderUsername, boolean isBroadcast, String receiverUsername) {
        User sender = userRepository.findByUsername(senderUsername);
        if (sender == null) {
            throw new IllegalArgumentException("Utente mittente non trovato.");
        }
        message.setSender(sender);

        if (!isBroadcast) {
            User receiver = userRepository.findByUsername(receiverUsername);
            if (receiver == null) {
                throw new IllegalArgumentException("Utente destinatario non trovato.");
            }
            message.setReceiver(receiver);
        } else {
            message.setReceiver(null); // Per i messaggi broadcast, il receiver è null
            message.setBroadcast(true);
        }

        if (message.getTimestamp() == null) {
            message.setTimestamp(LocalDateTime.now());
        }

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
