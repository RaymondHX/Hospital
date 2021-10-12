package com.han.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.han.yygh.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface DictService extends IService<Dict> {
    //根据数据id查询子数据列表
    List<Dict> findChlidData(Long id);

    //导出数据
    public void exportData(HttpServletResponse response);

    //导入数据
    public void importData(MultipartFile file);
}
