package com.han.yygh.cmn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.yygh.cmn.mapper.DictMapper;
import com.han.yygh.model.cmn.Dict;
import com.han.yygh.cmn.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    public List<Dict> findChlidData(Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        List<Dict> dictList = baseMapper.selectList(queryWrapper);
        for(Dict dict:dictList){
            Long dictId = dict.getId();
            boolean isChild = isChildren(dictId);
            dict.setHasChildren(isChild);
        }
        return dictList;
    }

    //判断id下面是否有子节点
    private boolean isChildren(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(wrapper);
        // 0>0    1>0
        return count>0;
    }
}
