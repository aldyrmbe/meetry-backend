package com.meetry.backend.entity.user;

import com.meetry.backend.entity.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "mitra")
public class Mitra {
    @Id
    private String id;

    @Field(value = "name")
    private String name;

    @Field(value = "email")
    private String email;

    @Field(value = "password")
    private String password;

    @Field(value = "role")
    private Role role = Role.MITRA;
}
