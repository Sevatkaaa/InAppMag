package com.sov.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeData {
    private Long projectId;
    private Long likes;
    private Long dislikes;
    private boolean liked;
    private boolean disliked;
}
