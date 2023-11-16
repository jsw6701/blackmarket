package com.example.blackmarket.dto.response;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class AdminResp {
    
    @Getter
    @Setter
    public static class AdminBoardRespDto{
        private Integer id;
        private String title;
        private String content;
        private String name;
        private LocalDateTime createDate;
    }

    @Getter
    @Setter
    public static class AdminBoardSearchResqDto{
        private Integer id;
        private String title;
        private String content;
        private String name;
        private LocalDateTime createDate;
    }
}
