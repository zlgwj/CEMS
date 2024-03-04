package com.gwj.cems.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeVo {

    private String label;

    private String value;

    private List<TreeVo> children;

}
