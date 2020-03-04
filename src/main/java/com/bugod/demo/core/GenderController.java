package com.bugod.demo.core;

import com.bugod.demo.common.constant.ErrorCodeEnum;
import com.bugod.demo.common.constant.GenderEnum;
import com.bugod.demo.common.entity.GenderPO;
import com.bugod.demo.common.entity.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * <pre>
 * Copyright (C) 2020 XXX股份有限公司
 * FileName: GenderController
 * Author:   虫神
 * Date:     2020/3/3 15:45
 * Description: 枚举 Controller
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间         Jira编号            描述
 *
 *
 * </pre>
 */

@Slf4j
@RestController
@RequestMapping("/gender")
@Api(tags = "性别枚举")
public class GenderController extends BaseController {

    @ApiOperation(value = "Get获取枚举", notes = "Get获取枚举")
    @GetMapping("/get")
    public ResultWrapper get(GenderPO request) {
        GenderEnum genderEnum = request.getGender();
        if (Objects.isNull(genderEnum)) {
           return error(ErrorCodeEnum.ARGS_NULL, "gender 不能为空");
        }
        return success(request);
    }

}
