package com.gwj.cems.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuVo {
    private Long id;
    private String path;
    private String name;
    private String component;
    private Meta meta;
    private List<MenuVo> children;

    @Data
    public static class Meta {
        private String icon;
        private String title;
        private String link;
        private boolean hide;
        private boolean full;
        private boolean affix;
        private boolean keepAlive;
    }

}
