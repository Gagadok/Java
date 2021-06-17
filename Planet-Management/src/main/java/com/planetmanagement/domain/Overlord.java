package com.planetmanagement.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Gagadok
 */
@Entity
@Table(name="Overlord")
public class Overlord {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long Overlord_ID;
    
    private String Name;
    private int Age;

    public Overlord() {
    }
    
    public Overlord(String Name, int Age) {
        this.Name = Name;
        this.Age = Age;
    }
    
    public Long getOverlord_ID() {
        return Overlord_ID;
    }

    public void setOverlord_ID(Long Overlord_ID) {
        this.Overlord_ID = Overlord_ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }
    
    
    
}
