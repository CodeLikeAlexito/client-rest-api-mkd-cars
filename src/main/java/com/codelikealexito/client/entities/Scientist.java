package com.codelikealexito.client.entities;

import com.codelikealexito.client.util.DateAudit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "scientists", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Scientist extends DateAudit {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String password;
    private String city;
    private String address;
    private String phone;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "scientist_roles",
            joinColumns = @JoinColumn(
                    name = "scientist_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))

    private Collection<Role> roles;

    private Scientist(Long id, String username, String firstName, String lastName, String email, String password,
                      String city, String address, String phone , Collection<Role> roles) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.roles = roles;
    }

    public static Scientist createUserWithFullInformation(Long id, String username, String firstName, String lastName, String email, String password,
                                                          String city, String address, String phone , Collection<Role> roles) {
        return new Scientist(null, username, firstName, lastName, email, password, city, address, phone, roles);
    }

    public static Scientist updateScientist(Long id, String username, String firstName, String lastName, String email, String password,
                                            String city, String address, String phone , Collection<Role> roles) {
        return new Scientist(id, username, firstName, lastName, email, password, city, address, phone, roles);
    }

}
