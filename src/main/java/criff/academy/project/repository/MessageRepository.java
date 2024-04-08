package criff.academy.project.repository;

import criff.academy.project.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE (m.sender.id = ?1 AND m.receiver.id = ?2) OR (m.sender.id = ?2 AND m.receiver.id = ?1) ORDER BY m.timestamp ASC")
    List<Message> findAllMessagesBetweenUsers(Long userId1, Long userId2);

    // Aggiunta del metodo utilizzando le convenzioni di Spring Data JPA
    List<Message> findAllBySenderIdAndReceiverId(Long senderId, Long receiverId);
}
