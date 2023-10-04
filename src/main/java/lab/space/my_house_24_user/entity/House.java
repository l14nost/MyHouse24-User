package lab.space.my_house_24_user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "house")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @Column(length = 150)
    private String address;

    @Column(length = 150)
    private String image1;

    @Column(length = 150)
    private String image2;

    @Column(length = 150)
    private String image3;

    @Column(length = 150)
    private String image4;

    @Column(length = 150)
    private String image5;

    @OneToMany(mappedBy = "house")
    private List<Apartment> apartmentList = new ArrayList<>();

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Section> sectionList = new ArrayList<>();

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Floor> floorList = new ArrayList<>();

    @ManyToMany(mappedBy = "houseList")
    private List<Staff> staffList = new ArrayList<>();

    @OneToOne(mappedBy = "house", cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_id")
    private Chat chat;


}
