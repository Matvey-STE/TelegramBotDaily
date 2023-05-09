package com.matveyvs1987.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_photo")
public class AppPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // db creates first keys values
    private long id;
    private String telegramFieldId;
    @OneToOne
    private BinaryContent binaryContent;
    private Integer fileSize;
}
