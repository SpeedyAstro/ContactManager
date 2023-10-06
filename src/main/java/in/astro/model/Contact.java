package in.astro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cid;
    private String name;
    private String secondName;
    private String work;
    private String email;
    private String phoneNo;
    private String image = null;
    @Column(length = 500)
    private String description;
    @ManyToOne
    @JsonIgnore
    private User user;
}
