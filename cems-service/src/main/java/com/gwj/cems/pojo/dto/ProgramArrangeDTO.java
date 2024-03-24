package com.gwj.cems.pojo.dto;

import com.gwj.cems.pojo.entity.Program;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProgramArrangeDTO {

    List<Program> amList;
    List<Program> pmList;
    List<Program> freeList;
    Date playTime;
}
