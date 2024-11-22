package net.engineeringdigest.journalApp.entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class JournalEntry {
    @Id
    private String id;
    @Column(nullable = false)
    private String title;
    private String content;
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
