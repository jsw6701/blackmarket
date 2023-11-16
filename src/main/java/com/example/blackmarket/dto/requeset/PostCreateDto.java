package com.example.blackmarket.dto.requeset;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateDto {

    private String title;

    private String content;

    private String targetDate;

    private List<MultipartFile> files;

    private Long immediatePurchasePrice;

    private Long biddingPrice;

    private Long biddingUnit;

    private Long categoryId;
}
