package org.uk.dog.portal.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "kennelling_characteristics")
public class KennellingCharacteristics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean aggressive;
    private boolean socialWithOtherDogs;
    private boolean requiresSpecialDiet;
    private String feedingInstructions;
    private String behavioralNotes;
    private boolean requiresSeparateKennel;
    private String exerciseRequirements;
    private String medicalNotes;

}