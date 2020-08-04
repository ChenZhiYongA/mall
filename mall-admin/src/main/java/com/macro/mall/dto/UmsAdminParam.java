package com.macro.mall.dto;

import com.macro.mall.model.UmsAdmin;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * 用户登录参数
 * Created by macro on 2018/4/26.
 */
@Getter
@Setter
public class
UmsAdminParam extends UmsAdmin {
    @ApiModelProperty(value = "用户名", required = true)
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @ApiModelProperty(value = "密码", required = true)
    @NotEmpty(message = "密码不能为空")
    private String password;
    @ApiModelProperty(value = "用户头像")
    private String icon;
    @ApiModelProperty(value = "邮箱")
    @Email(message = "邮箱格式不合法")
    private String email;
    @ApiModelProperty(value = "用户昵称")
    private String nickName;
    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "输入QQ号")
    private String qqid;
    @ApiModelProperty(value = "输入手机号")
    private String phone;
    @ApiModelProperty(value = "输入团队名称")
    private String teamname;
    @ApiModelProperty(value = "输入微信号")
    private String wechatid;
    @ApiModelProperty(value = "输入地址")
    private String location;
    @ApiModelProperty(value = "输入团长ID")
    private String pid;
    @ApiModelProperty(value = "审核状态")
    private String shenghe;
    @ApiModelProperty(value = "类型")
    private String types;

}
