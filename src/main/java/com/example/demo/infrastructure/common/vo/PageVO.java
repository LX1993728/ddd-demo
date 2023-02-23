package com.example.demo.infrastructure.common.vo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @apiNote 分页VO包装类
 */

@Data
@NoArgsConstructor
public class PageVO {
    private List<?> list;
    private Long totalPages;
    private Long currentPage;
    private Long pageSize;

    public PageVO(List<?> list, Long totalPages, Long currentPage, Long pageSize) {
        this.list = list;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}
