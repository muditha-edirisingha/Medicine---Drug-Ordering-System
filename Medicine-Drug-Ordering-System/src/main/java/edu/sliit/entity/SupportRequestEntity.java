package edu.sliit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class SupportRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supportId;

    private Integer customerId;
    private Integer supportOfficerId;

    private String subject;
    private String description;

    private LocalDateTime requestDate;

    private String priority;
    private String status;

    private String resolution;
    private LocalDateTime resolvedDate;
}
