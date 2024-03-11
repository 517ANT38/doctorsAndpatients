package com.dataservice.doctorsPatients.models.doctors;

import java.time.LocalDate;
import java.util.Set;

import com.dataservice.doctorsPatients.models.notes.Note;
import com.dataservice.doctorsPatients.models.util.FIODto;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "name", nullable = false)),
            @AttributeOverride(name = "family", column = @Column(name = "family",  nullable = false)),
            @AttributeOverride(name = "patronymic", column = @Column(name ="patronymic"))
    })
    private FIODto fio;
    @Column(unique = true)
    private String numPass;
    private String jobTitle;
    private LocalDate dateEmp;
    @OneToMany(mappedBy = "doctor")
    private Set<Note> notes;
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((fio == null) ? 0 : fio.hashCode());
        result = prime * result + ((numPass == null) ? 0 : numPass.hashCode());
        result = prime * result + ((jobTitle == null) ? 0 : jobTitle.hashCode());
        result = prime * result + ((dateEmp == null) ? 0 : dateEmp.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Doctor other = (Doctor) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (fio == null) {
            if (other.fio != null)
                return false;
        } else if (!fio.equals(other.fio))
            return false;
        if (numPass == null) {
            if (other.numPass != null)
                return false;
        } else if (!numPass.equals(other.numPass))
            return false;
        if (jobTitle == null) {
            if (other.jobTitle != null)
                return false;
        } else if (!jobTitle.equals(other.jobTitle))
            return false;
        if (dateEmp == null) {
            if (other.dateEmp != null)
                return false;
        } else if (!dateEmp.equals(other.dateEmp))
            return false;
        return true;
    }
    

    
}
