package session13.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Юлия on 13.02.2016.
 */
@Entity
@Table(name = "Persons")
public class Persons {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "SEQ_PERSONS",
            allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name = "ID")
    private Long id;

   @ManyToOne
    private Company company;

    public Persons() {
    }

    public Persons(Company company) {
        this.company = company;

    }
    public Company  getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}