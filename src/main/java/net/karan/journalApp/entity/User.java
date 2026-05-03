package net.karan.journalApp.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "users")
@Data
public class User {
    @Id
    private ObjectId  id;
    @Indexed(unique = true)
    @NonNull
    private String username ;
    @NonNull
    private String password ;
    @DBRef  //(creating a reference b/w users nd journal entries) (je is acting as foreign ker)
    private List<JournalEntry> journalEntries=new ArrayList<>();
    private List<String> roles;
}
