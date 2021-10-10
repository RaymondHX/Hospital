package com.han.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.han.yygh.model.cmn.Dict;

import java.util.List;

public interface DictService extends IService<Dict> {
    //根据数据id查询子数据列表
    List<Dict> findChlidData(Long id);
}
