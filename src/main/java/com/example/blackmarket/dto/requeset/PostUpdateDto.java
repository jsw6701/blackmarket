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
public class PostUpdateDto {

    private String title;

    private String content;

    private String targetDate;

    private Long immediatePurchasePrice;

    private List<MultipartFile> files;

    private Long biddingPrice;

    private Long biddingUnit;

    private Long categoryId;
}
