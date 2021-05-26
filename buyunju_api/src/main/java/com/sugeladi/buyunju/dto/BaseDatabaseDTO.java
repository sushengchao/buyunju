package com.sugeladi.buyunju.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class BaseDatabaseDTO extends BaseDTO{

    private static final long serialVersionUID = 4658896169939613398L;

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    /**
     * 创建时间
     */
    private String createTime;
}
