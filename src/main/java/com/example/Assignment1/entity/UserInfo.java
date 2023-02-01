package com.example.assignment1.entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table()
public class UserInfo {
    @Id
    @GeneratedValue
    private int id;
    @JsonProperty("first_name")
    private String fName;
    @JsonProperty("last_name")
    private String lName;
    @JsonProperty("user_name")
    private String emailID;
    private String password;
}
