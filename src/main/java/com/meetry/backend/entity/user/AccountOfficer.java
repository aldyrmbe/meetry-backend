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
@Document(collection = "account_officer")
public class AccountOfficer {
    @Id
    private String id;

    @Field
    private String name;

    @Field
    private String email;

    @Field
    private String password;

    @Field
    private String profilePhoto;

    @Field
    private Role role;
}
