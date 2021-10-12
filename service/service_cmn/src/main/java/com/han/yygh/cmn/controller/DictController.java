package com.han.yygh.cmn.controller;

import com.han.yygh.cmn.service.DictService;
import com.han.yygh.common.result.Result;
import com.han.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(description = "数据字典管理")
@RestController
@RequestMapping("admin/cmn/dict")
@CrossOrigin
public class DictController {

    @Autowired
    private DictService dictService;

    //导出为Excel文件
    @GetMapping(value = "/exportData")
    @ApiOperation(value = "导出")
    public void exportData(HttpServletResponse response){
        dictService.exportData(response);
    }
    //根据数据id查询子数据列表
    @ApiOperation(value = "根据数据id查询子数据列表")
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable Long id) {
        List<Dict> list = dictService.findChlidData(id);
        return Result.ok(list);
    }

    @ApiOperation(value = "导入")
    @PostMapping("importData")
    public Result importData(MultipartFile file) {
        dictService.importData(file);
        return Result.ok();
    }
}
