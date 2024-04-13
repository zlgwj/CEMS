package com.gwj.cems.pojo.vo;

import com.gwj.cems.pojo.entity.Notice;
import lombok.Data;

@Data
public class NoticeVO extends Notice {

    private Long readCount;

}
