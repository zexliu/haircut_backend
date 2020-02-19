package com.zex.cloud.haircut.params;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.RequestParam;

public class Pageable {

    Integer size = 10;

    Integer current = 1;

    Boolean isSearchCount = true;

    public Pageable(Integer size, Integer current, Boolean isSearchCount) {
        if (current != null && current > 1) {
            this.current = current;
        }
        if (size != null && size > 1) {
            this.size = size;
        }
        if (isSearchCount != null){
            this.isSearchCount = isSearchCount;
        }
    }

    public <T> Page<T> convert() {
        return new Page<>(this.current, this.size, this.isSearchCount);
    }

}
