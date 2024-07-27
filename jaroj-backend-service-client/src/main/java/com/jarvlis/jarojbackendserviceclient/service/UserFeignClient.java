package com.jarvlis.jarojbackendserviceclient.service;

import com.jarvlis.jarojbackendcommon.common.ErrorCode;
import com.jarvlis.jarojbackendcommon.exception.BusinessException;
import com.jarvlis.jarojbackendmodel.entity.User;
import com.jarvlis.jarojbackendmodel.enums.UserRoleEnum;
import com.jarvlis.jarojbackendmodel.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

import static com.jarvlis.jarojbackendcommon.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务
 *
 * @author <a href="https://github.com/Jarvlis">Jarvlis</a>
 */
@FeignClient(name = "jaroj-backend-user-service", path = "/api/user/inner")
public interface UserFeignClient {

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    default User getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    /**
     * 根据 id 获取用户
     * @param userId
     * @return
     */
    @GetMapping("/get/id")
    public User getById(@RequestParam("userId") Long userId);

    /**
     * 根据 id 获取用户列表
     * @param idList
     * @return
     */
    @GetMapping("/get/ids")
    public List<User> listByIds(@RequestParam("idList") Collection<Long> idList);

    /**
     * 是否为管理员
     *
     * @param loginUser
     * @return
     */
    default boolean isAdmin(User loginUser) {
        return loginUser != null && UserRoleEnum.ADMIN.getValue().equals(loginUser.getUserRole());
    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    default boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return isAdmin(user);
    }

    /**
     * 获取脱敏的用户信息
     *
     * @param user
     * @return
     */
    default UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}
