package org.uk.dog.portal.bean;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.uk.dog.portal.entities.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DogBean implements java.io.Serializable {
    
    private Long id;

    @NotBlank
    private String name;
    @NotBlank
    private String breed;
    @NotNull
    private DogSupplierBean dogSupplier;
    private String badgeId;
    private Gender gender;
    @Past
    private LocalDate birthDate;
    @Past
    private LocalDate dateAcquired;
    private CurrentStatus currentStatus;
    private LocalDate leavingDate;
    private LeavingReason leavingReason;
    private Long kennellingCharacteristicsId;
    private Boolean isDeleted = false;

    public Long getKennellingCharacteristicsId() {
        return kennellingCharacteristicsId;
    }

    public void setKennellingCharacteristicsId(Long kennellingCharacteristicsId) {
        this.kennellingCharacteristicsId = kennellingCharacteristicsId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @NotBlank String getBreed() {
        return breed;
    }

    public void setBreed(@NotBlank String breed) {
        this.breed = breed;
    }

    public String getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(String badgeId) {
        this.badgeId = badgeId;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public @Past LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(@Past LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public @Past LocalDate getDateAcquired() {
        return dateAcquired;
    }

    public void setDateAcquired(@Past LocalDate dateAcquired) {
        this.dateAcquired = dateAcquired;
    }

    public CurrentStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(CurrentStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public LocalDate getLeavingDate() {
        return leavingDate;
    }

    public void setLeavingDate(LocalDate leavingDate) {
        this.leavingDate = leavingDate;
    }

    public LeavingReason getLeavingReason() {
        return leavingReason;
    }

    public void setLeavingReason(LeavingReason leavingReason) {
        this.leavingReason = leavingReason;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public DogSupplierBean getDogSupplier() {
        return dogSupplier;
    }

    public void setDogSupplier(DogSupplierBean dogSupplier) {
        this.dogSupplier = dogSupplier;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

}
