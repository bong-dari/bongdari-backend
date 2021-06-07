package com.usbridge.bongdari.config.auth.dto;

import com.usbridge.bongdari.model.Member;
import com.usbridge.bongdari.model.enums.Gender;
import com.usbridge.bongdari.model.enums.Role;
import com.usbridge.bongdari.model.enums.SNS;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;
import java.util.StringTokenizer;

@Builder
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private Gender gender;
    private LocalDate birthDate;
    private String mobile;
    private SNS sns;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        OAuthAttributes oAuthAttributes = null;
        if("naver".equals(registrationId)){
            oAuthAttributes = ofNaver("id", attributes);
        } else if ("kakao".equals(registrationId)) {
            oAuthAttributes = ofKakao("id", attributes);
        }
        return oAuthAttributes;
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .gender(attributeToGender((String) response.get("gender")))
                .birthDate(attributeToBirthDate((String) response.get("birthyear"), (String) response.get("birthday")))
                .mobile((String) response.get("mobile"))
                .sns(SNS.NAVER)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao-account");
        Map<String, Object> profile = (Map<String, Object>) response.get("profile");
        return OAuthAttributes.builder()
                .name()
                .email()
                .gender()
                .birthDate()
                .mobile()
                .sns(SNS.KAKAO)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static Gender attributeToGender(String genderAttribute){
        Gender gen = Gender.ALL;
        if(genderAttribute.equals("M")){
            gen = Gender.MALE;
        } else if(genderAttribute.equals("F")){
            gen = Gender.FEMALE;
        }
        return gen;
    }

    private static LocalDate attributeToBirthDate(String birthYear, String birthDay, SNS sns){
        StringTokenizer st = new StringTokenizer(birthDay, "-");
        int month = sns == SNS.NAVER ? Integer.parseInt(st.nextToken()) : ;
        return LocalDate.of(Integer.parseInt(birthYear), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
    }

    public Member toEntity(){
        return Member.builder()
                .name(name)
                .nickname("")
                .email(email)
                .mobile(mobile)
                .smsAgreement(false)
                .isDeleted(false)
                .birthDate(birthDate)
                .gender(gender)
                .sns(sns)
                .role(Role.MEMBER)
                .build();
    }
}
