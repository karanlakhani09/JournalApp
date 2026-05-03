package net.karan.journalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@Document(collection = "journal_entries")
@NoArgsConstructor
@Data// LOMBOK annotation helps in ease the boilerplate code ie @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode.
public class JournalEntry {
    @Id
    private ObjectId  id;      // data type h obj id mongo db ka
    private String title;
    private String content;
    private LocalDateTime date;
}

//    public LocalDateTime getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDateTime date) {
//        this.date = date;
//    }
//
//    public ObjectId getId() {
//        return id;
//    }
//
//    public void setId(ObjectId id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String context) {
//        this.content = context;
//    }
//}
