package com.matveyvs1987.entity;

import com.matveyvs1987.entity.enums.UserState;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder // builder pattern
@NoArgsConstructor
@AllArgsConstructor
@Entity // entity connect to database
@Table(name = "app_user") // name of the table
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // db creates first keys values
    private long id;
    private Long telegramUserId;
    @CreationTimestamp
    private LocalDateTime firstLocalDate;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private Boolean isActive;

    // save in DB as varchar (255), if ordinal return type int like  enum type
    @Enumerated(EnumType.STRING)
    private UserState userState;
}
