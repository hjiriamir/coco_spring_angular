package tn.esprit.coco.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;

    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String password;
    private String gender;
    private String address;
    @Column(nullable = false)
    private LocalDate dateOfBirth;
    private String pictureUrl;


    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();



//// special amir
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @ManyToMany(mappedBy = "passengers")
    private List<Ride>rides;
    @OneToMany(mappedBy = "driver")
    private List<Ride> drives;
    @OneToMany(mappedBy = "passenger")
    private List<Reservation> reservations;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "security_id")
    private Security security;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite>favorites;
//// syrine
    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private List<Product> Products;
    @OneToOne(mappedBy="user")
    private WishList wishlist;

/// hadil
    @ManyToMany(cascade = CascadeType.ALL, mappedBy="user")
    private Set<Accommodation> accommodations;
    @OneToMany(mappedBy ="user")
    private List<Booking> bookings;

//// ramzi
    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;
//verifier
    @OneToMany(mappedBy = "user")
    private List<CommentLike> commentLikes;

    @ManyToMany
    @JoinTable(
            name = "user_chatRoom",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chatRoom_id"))
    private List<ChatRoom> chatRooms;

    // ghribi
    @ManyToOne
    private  Trip trip;

    @OneToOne
    private Subscription subscription;

    @OneToOne
    private DailyTicket dailyTicket ;
}
