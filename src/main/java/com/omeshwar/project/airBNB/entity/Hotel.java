package com.omeshwar.project.airBNB.entity;

import com.omeshwar.project.airBNB.converter.ListToJsonConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String city;

    @Column(columnDefinition = "json")
    @Convert(converter = ListToJsonConverter.class)
    private List<String> amenities;
    @Column(columnDefinition = "json")
    @Convert(converter = ListToJsonConverter.class)
    private List<String> photos;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Embedded
    private HotelContactInfo contactInfo;
    @Column(nullable = false)
    private Boolean isActive;

    @ManyToOne
    private User owner;

    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;
}
