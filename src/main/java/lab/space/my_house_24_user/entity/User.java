package lab.space.my_house_24_user.entity;

import jakarta.persistence.*;
import lab.space.my_house_24_user.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 25,nullable = false)
    private String firstname;

    @Column(length = 25,nullable = false)
    private String lastname;

    @Column(length = 25,nullable = false)
    private String surname;

    @Column(length = 100,nullable = false)
    private String password;

    @Column(length = 100,nullable = false)
    private String email;

    @Column(length = 20,nullable = false)
    private String number;

    @Column(length = 20,nullable = false)
    private String viber;

    @Column(length = 20,nullable = false)
    private String telegram;

    @Enumerated(EnumType.STRING)
    @Column(length = 50,nullable = false, name = "status")
    private UserStatus userStatus;

    @Column(length = 1000)
    private String notes;

    @Column(length = 150)
    private String filename;

    @Column(nullable = false)
    private Instant date;

    @Column(nullable = false)
    private Instant addDate;

    @Column(nullable = false)
    private Boolean duty;

    @Column
    private Boolean theme;

    @OneToMany(mappedBy = "user")
    private List<Apartment> apartmentList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CashBox> cashBoxList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<MastersApplication> applicationList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
