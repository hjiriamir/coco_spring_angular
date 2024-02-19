package tn.esprit.coco.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chatRoomName;

    @ManyToMany(mappedBy = "chatRooms")
    private List<User> users;

    @OneToMany(mappedBy = "chatRoom")
    private List<Message> messages;
}
