package com.hassdata.survey.controller.role;

import com.hassdata.survey.dto.RoleDTO;
import com.hassdata.survey.po.Role;
import com.hassdata.survey.po.Role_Resource;
import com.hassdata.survey.service.ResourceService;
import com.hassdata.survey.service.RoleService;
import com.hassdata.survey.service.Role_ResourceService;
import com.hassdata.survey.util.ServerResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
        setRoleDTO(roleList, roleDTOList);
        return ServerResponse.createBySuccessForLayuiTable("请求成功",roleDTOList,count);
    }


    @RequestMapping(value = "roleDel",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse roleDel(Integer id){
        Role_Resource role_resource=new Role_Resource();
        role_resource.setRid(id);
        List<Role_Resource> role_resources=role_resourceService.getAll(role_resource);
        for(Role_Resource rr : role_resources){
            role_resourceService.delete(rr.getId());
        }
        roleService.delete(id);
        return ServerResponse.createBySuccessMessage("删除成功");
    }

    @RequestMapping(value = "roleAvailableUpdate" ,method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse roleAvailableUpdate(Integer id,Integer available){
        Role role=new Role();
        role.setAvailable(available==1 ? true:false);
        role.setId(id);
        roleService.updateParams(role);
        return ServerResponse.createBySuccessMessage("状态设置成功");
    }


    @RequestMapping(value = "searchRole" , method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse searchRole(String name,@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "30") Integer limit){
        Role role=new Role();
        role.setRolename("%"+name+"%");
        long count = roleService.getScrollByLikeCount(role);
        List<Role> roleList = roleService.getScrollDataByLike(role, "id desc", (page - 1) * limit, limit);
        List<RoleDTO> roleDTOList = new ArrayList<>();
        setRoleDTO(roleList, roleDTOList);
        return ServerResponse.createBySuccessForLayuiTable("请求成功",roleDTOList,count);
    }

    @RequestMapping(value = "getAddRole",method = RequestMethod.GET)
    public String getAddRole(ModelMap map){
        List<com.hassdata.survey.po.Resource> resourceList=resourceService.getAll(null);
        com.hassdata.survey.po.Resource re=null;
        for(com.hassdata.survey.po.Resource r : resourceList){
            if(r.getParentid()==0){
                re=r;
            }
        }
        resourceList.remove(re);
        map.addAttribute("res",resourceList);
        return "system/role/addRole";
    }

    @RequestMapping(value = "addRole",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addRole(Role role,String ids){
        System.out.println(role.getRolename());
        System.out.println(role.getAvailable());
        System.out.println(role.getDescription());
        System.out.println(ids);
        Integer maxId=roleService.getIdMax()+1;
        role.setId(maxId);
        roleService.save(role);
        Role_Resource role_resource=null;
        List<Role_Resource> role_resources=new ArrayList<>();
        String[] id=ids.split(",");
        for(String i : id){
            role_resource=new Role_Resource();
            role_resource.setRid(maxId);
            role_resource.setReid(Integer.parseInt(i));
            role_resources.add(role_resource);
        }
        role_resourceService.saveBatch(role_resources);
        return ServerResponse.createBySuccessMessage("角色添加成功");
    }


    private void setRoleDTO(List<Role> roleList, List<RoleDTO> roleDTOList) {
        RoleDTO roleDTO = null;
        int i = 1;
        for (Role r : roleList) {
            if(r.getRolename().equals("admin")) continue;
            String res = "";
            roleDTO = new RoleDTO();
            roleDTO.setAid(i);
            roleDTO.setId(r.getId());
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
    }

}
