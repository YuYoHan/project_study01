package com.example.project1.entity.member;

import com.example.project1.domain.member.UserType;
import com.example.project1.entity.Base.BaseEntity;
import com.example.project1.entity.member.embedded.AddressEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = "member")
@Getter
@ToString
@NoArgsConstructor
public class MemberEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail;

    @Column(name = "user_pw")
    private String userPw;

    @Column(name = "nick_name")
    private String nickName;

    @Enumerated(EnumType.STRING)
    // ROLE_USER, ROLE_ADMIN
    private UserType userType;

    // OAuth2 가입할 때를 위해서
    private String provider;
    private String providerId;

    @Embedded
    private AddressEntity address;

    @Builder
    public MemberEntity(
            Long userId,
            String userName,
            String userEmail,
            String userPw,
            String nickName,
            UserType userType,
            String provider,
            String providerId,
            AddressEntity address) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPw = userPw;
        this.nickName = nickName;
        this.userType = userType;
        this.provider = provider;
        this.providerId = providerId;
        this.address = address;
    }


}
