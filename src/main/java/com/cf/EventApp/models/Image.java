package com.cf.EventApp.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "images")
@Getter
@Setter
@EqualsAndHashCode
public class Image {

    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;                    // image id auto_incremented in database
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;                // Event with a cover photo
    @Column(name = "image_path")
    private String image_path;          // Path of image that will be stored in the application files

}
