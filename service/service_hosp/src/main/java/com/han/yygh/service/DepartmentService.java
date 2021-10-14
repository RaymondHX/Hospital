package com.han.yygh.service;

import com.han.yygh.model.hosp.Department;
import com.han.yygh.vo.hosp.DepartmentQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface DepartmentService {

    /**
     * 上传科室信息
     * @param paramMap
     */
    void save(Map<String, Object> paramMap);

    /**
            查询科室
 * @param page 当前页码
 * @param limit 每页记录数
 * @param departmentQueryVo 查询条件
 * @return
         */
    Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo);
}
