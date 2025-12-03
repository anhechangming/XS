package com.cyd.xs.entity.User;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@TableName("user_search_history")
public class UserSearchHistory {
    @Id
    private Long id;
    private Long userId;
    private String keyword;
    private String searchAt;
}
