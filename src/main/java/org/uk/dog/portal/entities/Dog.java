package org.uk.dog.portal.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "dogs")
@NoArgsConstructor
@AllArgsConstructor
public class Dog implements Serializable {
    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", breed='" + breed + '\'' +
                ", dogSupplier=" + dogSupplier +
                ", badgeId='" + badgeId + '\'' +
                ", gender=" + gender +
                ", birthDate=" + birthDate +
                ", dateAcquired=" + dateAcquired +
                ", currentStatus=" + currentStatus +
                ", leavingDate=" + leavingDate +
                ", leavingReason=" + leavingReason +
                ", kennellingCharacteristicsId=" + kennellingCharacteristicsId +
                ", isDeleted=" + isDeleted +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String breed;

    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", nullable = false)
    private DogSupplier dogSupplier;

    private String badgeId;
    private Gender gender;
    private LocalDate birthDate;
    private LocalDate dateAcquired;
    private CurrentStatus currentStatus;
    private LocalDate leavingDate;
    private LeavingReason leavingReason;
    private Long kennellingCharacteristicsId;
    private Boolean isDeleted = false;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public DogSupplier getDogSupplier() {
        return dogSupplier;
    }

    public String getBadgeId() {
        return badgeId;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public LocalDate getDateAcquired() {
        return dateAcquired;
    }

    public CurrentStatus getCurrentStatus() {
        return currentStatus;
    }

    public LocalDate getLeavingDate() {
        return leavingDate;
    }

    public LeavingReason getLeavingReason() {
        return leavingReason;
    }

    public Long getKennellingCharacteristicsId() {
        return kennellingCharacteristicsId;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setDogSupplier(DogSupplier dogSupplier) {
        this.dogSupplier = dogSupplier;
    }

    public void setBadgeId(String badgeId) {
        this.badgeId = badgeId;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setDateAcquired(LocalDate dateAcquired) {
        this.dateAcquired = dateAcquired;
    }

    public void setCurrentStatus(CurrentStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public void setLeavingDate(LocalDate leavingDate) {
        this.leavingDate = leavingDate;
    }

    public void setLeavingReason(LeavingReason leavingReason) {
        this.leavingReason = leavingReason;
    }

    public void setKennellingCharacteristicsId(Long kennellingCharacteristicsId) {
        this.kennellingCharacteristicsId = kennellingCharacteristicsId;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}