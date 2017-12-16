package com.hassdata.survey.controller.role;

import com.hassdata.survey.dto.RoleDTO;
import com.hassdata.survey.po.Role;
import com.hassdata.survey.service.ResourceService;
import com.hassdata.survey.service.RoleService;
import com.hassdata.survey.service.Role_ResourceService;
import com.hassdata.survey.util.ServerResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@Scope("prototype")
@RequestMapping("system")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private Role_ResourceService role_resourceService;

    @Resource
    private ResourceService resourceService;

    @RequestMapping(value = "roleCenter", method = RequestMethod.GET)
    public String roleCenter() {
        return "system/role/rolecenter";
    }

    @RequestMapping(value = "roleList", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse roleList(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "30") Integer limit) {
        long count = roleService.getScrollCount(null);
        List<Role> roleList = roleService.getScrollData(null, "id desc", (page - 1) * limit, limit);
        List<RoleDTO> roleDTOList = new ArrayList<>();
        RoleDTO roleDTO = null;
        int i = 1;
        for (Role r : roleList) {
            String res = "";
            roleDTO = new RoleDTO();
            roleDTO.setAid(i);
            roleDTO.setAvailable(r.getAvailable());
            roleDTO.setDescription(r.getDescription());
            List<String> ss=resourceService.getResourceNameByRoleId(r.getId());
            for (int j = 0; j < ss.size(); j++) {
                if(j==ss.size()-1){
                    res+=ss.get(j);
                }else{
                    res+=ss.get(j)+" , ";
                }
            }
            roleDTO.setResources(res);
            roleDTO.setRolename(r.getRolename());
            i++;
            roleDTOList.add(roleDTO);
        }
        return ServerResponse.createBySuccessForLayuiTable("请求成功",roleDTOList,count);
    }


//    @RequestMapping(value = "roleDel",method = RequestMethod.GET)
//    @ResponseBody
//    public ServerResponse roleDel(Integer id){
//        roleService
//    }


}
