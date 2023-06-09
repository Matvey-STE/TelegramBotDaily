package com.matveyvs1987.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.persistence.*;

//@Data // bad practice because of hash that include id field
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder // builder pattern
@NoArgsConstructor
@AllArgsConstructor
@Entity // entity connect to database
@Table (name = "row_data") // name of the table
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)

public class RawData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // db creates first keys values
    private long id;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Update event;
}
