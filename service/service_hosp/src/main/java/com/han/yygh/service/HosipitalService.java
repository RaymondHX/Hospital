package com.han.yygh.service;


import com.han.yygh.model.hosp.Hospital;

import java.util.Map;

public interface HosipitalService {

    /**
     * 上传医院
     * @param paramMap 参数
     */
    public void save(Map<String,Object> paramMap);

    /**
     * 查询医院
     * @param hoscode
     * @return
     */
    Hospital getByHoscode(String hoscode);
}
