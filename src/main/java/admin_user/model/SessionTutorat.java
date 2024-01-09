package admin_user.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "session_tutorat")
public class SessionTutorat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String date;

    @Column(name = "date_creation")
    private String dateCreation;

    @Column(name = "session_date_planifiee")
    private LocalDate sessionDatePlanifiee; // Mettez à jour le type ici

    private String description;

    private String nom;

    @ManyToOne
    @JoinColumn(name = "tuteur_id")
    private User tuteur;

    @ManyToMany
    @JoinTable(
            name = "etudiant_sessiontutorat",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "etudiant_id")
    )
    private Set<User> etudiants;

    public SessionTutorat() {
        super();
    }

    public SessionTutorat(String date, String dateCreation, LocalDate sessionDatePlanifiee, String description, String nom, User tuteur, Set<User> etudiants) {
        this.date = date;
        this.dateCreation = dateCreation;
        this.sessionDatePlanifiee = sessionDatePlanifiee;
        this.description = description;
        this.nom = nom;
        this.tuteur = tuteur;
        this.etudiants = etudiants;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getSessionDatePlanifiee() {
        return sessionDatePlanifiee;
    }

    public void setSessionDatePlanifiee(LocalDate sessionDatePlanifiee) {
        this.sessionDatePlanifiee = sessionDatePlanifiee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public User getTuteur() {
        return tuteur;
    }

    public void setTuteur(User tuteur) {
        this.tuteur = tuteur;
    }

    public Set<User> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(Set<User> etudiants) {
        this.etudiants = etudiants;
    }
}
