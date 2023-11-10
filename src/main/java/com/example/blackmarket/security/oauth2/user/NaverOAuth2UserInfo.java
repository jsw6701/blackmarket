package com.example.blackmarket.security.oauth2.user;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        Object list = attributes.get("response");
        String id = (String) ((Map) list).get("id");
        return id;
    }

    @Override
    public String getName() {
        Object list = attributes.get("response");
        String name = (String) ((Map) list).get("name");
        return name;
    }

    @Override
    public String getEmail() {
        System.out.println(attributes);
        System.out.println(attributes.containsKey("email"));
        Object list = attributes.get("response");
        String email = (String) ((Map) list).get("email");
        return email;
    }

    @Override
    public String getImageUrl() {
        Object list = attributes.get("response");
        String image = (String) ((Map) list).get("profile_image");
        return image;
    }
}
