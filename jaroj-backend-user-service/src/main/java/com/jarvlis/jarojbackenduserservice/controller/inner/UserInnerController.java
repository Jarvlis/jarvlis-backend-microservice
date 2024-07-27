package com.jarvlis.jarojbackenduserservice.controller.inner;


import com.jarvlis.jarojbackendmodel.entity.User;
import com.jarvlis.jarojbackendserviceclient.service.UserFeignClient;
import com.jarvlis.jarojbackenduserservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 该服务用于内部调用, 不提供给前端
 */
@RestController
@RequestMapping("/inner")
@Slf4j
public class UserInnerController implements UserFeignClient {

    @Resource
    private UserService userService;

    /**
     * 根据 id 获取用户
     *
     * @param userId
     * @return
     */
    @GetMapping("/get/id")
    @Override
    public User getById(@RequestParam("userId") Long userId) {
        return userService.getById(userId);
    }

    /**
     * 根据 id 获取用户列表
     *
     * @param idList
     * @return
     */
    @GetMapping("/get/ids")
    @Override
    public List<User> listByIds(@RequestParam("idList") Collection<Long> idList) {
        return userService.listByIds(idList);
    }
}
