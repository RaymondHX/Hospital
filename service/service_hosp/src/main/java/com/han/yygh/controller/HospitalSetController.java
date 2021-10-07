package com.han.yygh.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.han.yygh.common.MD5;
import com.han.yygh.common.result.Result;
import com.han.yygh.model.hosp.HospitalSet;
import com.han.yygh.service.HospitalSetService;
import com.han.yygh.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {

    // 注入服务
    @Autowired
    private HospitalSetService hospitalSetService;

    /**
     * 查询医院设置所有表信息
     */
    @ApiOperation(value = "逻辑删除医院设置信息")
    @GetMapping("findAll")
    public Result findAllHospital(){
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    /**
     * 根据id删除医院设置
     */
    @ApiOperation(value = "删除医院设置")
    @DeleteMapping("{id}")
    public Result deleteByID(@PathVariable Long id){
        boolean removed = hospitalSetService.removeById(id);
        if(removed){
            return Result.ok();
        }
        else {
            return Result.fail();
        }
    }

    /**
     * 带分页的条件查询
     */
    @PostMapping("findHospSetPage/{current}/{size}")
    public Result findHospSetPage(@PathVariable Long current,
                                  @PathVariable Long size,
                                  @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo){
        Page<HospitalSet> page = new Page<>(current,size);
        //构造一个Wrapper进行条件查询
        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<>();
        String hosname = hospitalSetQueryVo.getHosname();
        String hoscode = hospitalSetQueryVo.getHoscode();
        if(StringUtils.checkValNotNull(hoscode)){
            queryWrapper.eq("hoscode",hoscode);
        }
        if(StringUtils.checkValNotNull(hosname)){
            queryWrapper.like("hosname",hosname);
        }
        Page<HospitalSet> hospitalSetPage = hospitalSetService.page(page, queryWrapper);
        return Result.ok(hospitalSetPage);
    }

    /**
     * 添加医院设置
     */
    @PostMapping("addHospSet")
    public Result addHospSet(@RequestBody HospitalSet hospitalSet){
        //设置状态
        hospitalSet.setStatus(1);
        //设置签名
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));
        boolean save = hospitalSetService.save(hospitalSet);
        if(save){
            return Result.ok();
        }
        else {
            return Result.fail();
        }


    }

    /**
     * 根据id获取医院设置
     */
    @GetMapping("findHospSetById/{id}")
    public Result findHospSetById(@PathVariable Long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    /**
     * 修改医院设置
     */
    @PostMapping("updateHospSet")
    public Result updateHospSet(@RequestBody HospitalSet hospitalSet){
        boolean update = hospitalSetService.updateById(hospitalSet);
        if(update){
            return Result.ok();
        }
        else{
            return Result.fail();
        }
    }

    /**
     * 批量删除医院设置
     */
    @DeleteMapping("batchDeletHospSet")
    public Result batchDeletHospSet(@RequestBody List<Long> ids){
        boolean remove = hospitalSetService.removeByIds(ids);
        if(remove){
            return Result.ok();
        }
        else {
            return Result.fail();
        }
    }

    /**
     * 医院设置锁定和解锁
     */
    @PutMapping("HospSetLock/{id}/{status}")
    public Result HospSetLock(@PathVariable Long id, @PathVariable Integer status){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        hospitalSet.setStatus(status);
        boolean update = hospitalSetService.updateById(hospitalSet);
        if(update){
            return Result.ok();
        }
        else {
            return Result.fail();
        }
    }

    /**
     * 发送签名key
     */
    @PutMapping("sendKey/{id}")
    public Result sendKey(@PathVariable Long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        //todo 发送短信
        return Result.ok();
    }
}
