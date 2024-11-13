package net.engineeringdigest.journalApp.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class JournalEntry {
    @Id
    private String id;
    private String title;
    private String content;

}
