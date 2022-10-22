package com.cf.EventApp.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@EqualsAndHashCode
public class Event {

    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;                    // event id auto_incremented
    private String title;               // name of the event
    private LocalDate start_date;       // default should be today's date
    private LocalTime start_time;       // default should be rounded up to nearest hour to current time.
    private LocalDate end_date;         // null/empty is acceptable.
    private LocalTime end_time;         // null/empty is acceptable.
    private String location;            // google api location information

    private String description;         // event description
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;                  // user that created the event

}
