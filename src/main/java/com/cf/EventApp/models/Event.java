package com.cf.EventApp.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime start_date;   // default should be today's date
    private LocalDateTime end_date;     // no default. null is acceptable.
    private String location;            // google api location information

    private String description;         // event description
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;                  // user that created the event

}
