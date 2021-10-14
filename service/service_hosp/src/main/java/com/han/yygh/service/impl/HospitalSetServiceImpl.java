package com.han.yygh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.yygh.common.result.ResultCodeEnum;
import com.han.yygh.mapper.HospitalSetMapper;
import com.han.yygh.model.hosp.HospitalSet;
import com.han.yygh.service.HospitalSetService;
import com.han.yygh.vo.order.SignInfoVo;
import org.springframework.stereotype.Service;

@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet>implements HospitalSetService {

    //2 根据传递过来医院编码，查询数据库，查询签名
    @Override
    public String getSignKey(String hoscode) {
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        wrapper.eq("hoscode",hoscode);
        HospitalSet hospitalSet = baseMapper.selectOne(wrapper);
        return hospitalSet.getSignKey();
    }

    @Override
    public SignInfoVo getSignInfoVo(String hoscode) {
//        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
//        wrapper.eq("hoscode",hoscode);
//        HospitalSet hospitalSet = baseMapper.selectOne(wrapper);
//        if(null == hospitalSet) {
//            throw new HospitalException(ResultCodeEnum.HOSPITAL_OPEN);
//        }
//        SignInfoVo signInfoVo = new SignInfoVo();
//        signInfoVo.setApiUrl(hospitalSet.getApiUrl());
//        signInfoVo.setSignKey(hospitalSet.getSignKey());
//        return signInfoVo;
        return null;
    }


}

