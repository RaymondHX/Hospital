package com.han.yygh.controller.api;

import com.han.yygh.common.exception.YyghException;
import com.han.yygh.common.result.ResultCodeEnum;
import com.han.yygh.common.util.MD5;
import com.han.yygh.model.hosp.Department;
import com.han.yygh.repository.DepartmentRepository;
import com.han.yygh.service.DepartmentService;
import com.han.yygh.service.HospitalSetService;
import com.han.yygh.common.helper.HttpRequestHelper;
import com.han.yygh.common.result.Result;
import com.han.yygh.service.HosipitalService;
import com.han.yygh.vo.hosp.DepartmentQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@Api(tags = "医院管理API接口")
@RequestMapping("/api/hosp")
public class ApiContrpller {
    @Autowired
    private HosipitalService hospitalService;
    @Autowired
    private HospitalSetService hospitalSetService;
    @Autowired
    private DepartmentService departmentService;
    @PostMapping("saveHospital")
    public Result saveHospital(HttpServletRequest request){
        //获取医院传递过来的信息
        Map<String, String[]> requestrMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestHelper.switchMap(requestrMap);
        //获医院系统中传递过来的签名
        String sign =(String) map.get("sign");
        //根据传递过来的医院编号，查询数据库，查询签名
        String hoscode = (String)map.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        //把数据库查询出来的签名进行MD5加密
        String encrypt = MD5.encrypt(signKey);
        //判断签名是否一致
        if(!encrypt.equals(sign)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        //修改传输过程中图片的编码问题
        String logoData = (String)map.get("logoData");
        logoData = logoData.replaceAll(" ","+");
        System.out.println(logoData);
        map.put("logoData",logoData);
        //调用service方法
        hospitalService.save(map);
        return Result.ok();
    }

    @ApiOperation(value = "获取医院信息")
    @PostMapping("hospital/show")
    public Result hospital(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
//必须参数校验 略
        String hoscode = (String)paramMap.get("hoscode");
        if(StringUtils.isEmpty(hoscode)) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }
//签名校验
        if(!HttpRequestHelper.isSignEquals(paramMap, hospitalSetService.getSignKey(hoscode))) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        return Result.ok(hospitalService.getByHoscode((String)paramMap.get("hoscode")));
    }

    //上传科室接口
    @PostMapping("saveDepartment")
    @ApiOperation(value = "上传科室信息")
    public Result saveDepartment(HttpServletRequest request) {
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //获取医院编号
        String hoscode = (String)paramMap.get("hoscode");
        //1 获取医院系统传递过来的签名,签名进行MD5加密
        String hospSign = (String)paramMap.get("sign");

        //2 根据传递过来医院编码，查询数据库，查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);

        //3 把数据库查询签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);

        //4 判断签名是否一致
        if(!hospSign.equals(signKeyMd5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        //调用service的方法
        departmentService.save(paramMap);
        return Result.ok();
    }

    //查询科室接口
    @PostMapping("department/list")
    public Result findDepartment(HttpServletRequest request) {
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //医院编号
        String hoscode = (String)paramMap.get("hoscode");
        //当前页 和 每页记录数
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 1 : Integer.parseInt((String)paramMap.get("limit"));
        //TODO 签名校验

        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);
        //调用service方法
        Page<Department> pageModel = departmentService.findPageDepartment(page,limit,departmentQueryVo);
        return Result.ok(pageModel);
    }
}
