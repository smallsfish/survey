package com.hassdata.survey.controller.admin;

import com.hassdata.survey.dto.AdminUser;
import com.hassdata.survey.dto.IndexMenu;
import com.hassdata.survey.dto.MenuUrl;
import com.hassdata.survey.dto.RoleDTO;
import com.hassdata.survey.po.Admin_Role;
import com.hassdata.survey.po.Admin_User;
import com.hassdata.survey.po.Role;
import com.hassdata.survey.service.*;
import com.hassdata.survey.util.ArrayUtils;
import com.hassdata.survey.util.FileUploadUtils;
import com.hassdata.survey.util.ServerResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("system")
@Scope("prototype")
public class AdminUserController {

    @Resource
    private PasswordHelper passwordHelper;

    @Resource
    private ResourceService resourceService;

    @Resource
    private Admin_RoleService admin_roleService;

    @Resource
    private RoleService roleService;

    @Resource
    private AdminUserService adminUserService;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String getSystemLogin() {
        return "system/login";
    }

    @RequestMapping(value = "exit", method = RequestMethod.GET)
    @ResponseBody
    public String exitSystem() {
        if (SecurityUtils.getSubject().getSession(true).getAttribute("CurrentAdminUser") != null) {
            SecurityUtils.getSubject().getSession(true).removeAttribute("CurrentAdminUser");
        }
        SecurityUtils.getSubject().logout();
        return "success";
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String getSystemIndex(ModelMap map) {
        String account = (String) SecurityUtils.getSubject().getPrincipal();
        List<com.hassdata.survey.po.Resource> resources = resourceService.getAll(null);
        Map<String, com.hassdata.survey.po.Resource> resourceMap = new HashMap<>();
        resources.stream().forEach((r) -> resourceMap.put(r.getId() + "", r));
        List<com.hassdata.survey.po.Resource> resourceList = resourceService.getResourceByAccount(account);
        List<IndexMenu> indexMenuList = new ArrayList<>();
        List<MenuUrl> menuUrlList = null;
        IndexMenu indexMenu = null;
        MenuUrl menuUrl = null;
        String[][] strings = new String[resources.size()][3];
        Integer index = 0, i = 0;
        for (com.hassdata.survey.po.Resource r : resourceList) {
            if (r.getType().equals("menu")) {
                if (ArrayUtils.idExists(strings, r.getParentid() + "", false)) {
                    menuUrl = new MenuUrl();
                    menuUrl.setName(r.getName());
                    menuUrl.setUrl(r.getUrl());
                    menuUrl.setIconUrl(r.getIconurl());
                    Integer j = ArrayUtils.getIndex(strings, r.getParentid() + "");
                    indexMenuList.get(j).getMenuUrlList().add(menuUrl);
                    strings[i][0] = j + "";
                    strings[i][1] = r.getParentid() + "";
                    strings[i][2] = r.getId() + "";
                    i++;
                } else {
                    menuUrlList = new ArrayList<>();
                    com.hassdata.survey.po.Resource res = resourceMap.get(r.getParentid() + "");
                    indexMenu = new IndexMenu();
                    indexMenu.setName(res.getName());
                    menuUrl = new MenuUrl();
                    menuUrl.setName(r.getName());
                    menuUrl.setUrl(r.getUrl());
                    menuUrl.setIconUrl(r.getIconurl());
                    menuUrlList.add(menuUrl);
                    indexMenu.setMenuUrlList(menuUrlList);
                    indexMenuList.add(indexMenu);
                    strings[i][0] = index + "";
                    strings[i][1] = r.getParentid() + "";
                    strings[i][2] = r.getId() + "";
                    i++;
                    index++;
                }
            } else {
                if (ArrayUtils.idExists(strings, r.getParentid() + "", true)) {
                    continue;
                } else {
                    com.hassdata.survey.po.Resource reso = resourceMap.get(r.getParentid() + "");
                    if (ArrayUtils.idExists(strings, reso.getParentid() + "", false)) {
                        menuUrl = new MenuUrl();
                        menuUrl.setName(reso.getName());
                        menuUrl.setUrl(reso.getUrl());
                        menuUrl.setIconUrl(reso.getIconurl());
                        Integer j = ArrayUtils.getIndex(strings, reso.getParentid() + "");
                        strings[i][0] = j + "";
                        strings[i][1] = reso.getParentid() + "";
                        strings[i][2] = reso.getId() + "";
                        indexMenuList.get(j).getMenuUrlList().add(menuUrl);
                        i++;
                    } else {
                        menuUrlList = new ArrayList<>();
                        com.hassdata.survey.po.Resource resou = resourceMap.get(reso.getParentid() + "");
                        indexMenu = new IndexMenu();
                        indexMenu.setName(resou.getName());
                        strings[i][0] = index + "";
                        strings[i][1] = reso.getParentid() + "";
                        strings[i][2] = reso.getId() + "";
                        menuUrl = new MenuUrl();
                        menuUrl.setName(reso.getName());
                        menuUrl.setUrl(reso.getUrl());
                        menuUrl.setIconUrl(reso.getIconurl());
                        menuUrlList.add(menuUrl);
                        indexMenu.setMenuUrlList(menuUrlList);
                        indexMenuList.add(indexMenu);
                        i++;
                        index++;
                    }
                }
            }
        }
        map.addAttribute("menu", indexMenuList);
        return "index";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse systemLogin(Admin_User adminUser, @RequestParam(required = false) Integer remind) {
        boolean rememberMe = false;
        if (adminUser.getAccount().isEmpty()) {
            return ServerResponse.createByErrorMessage("请输入账号！");
        } else if (adminUser.getPassword().isEmpty()) {
            return ServerResponse.createByErrorMessage("请输入密码！");
        }
        if (remind != null) {
            rememberMe = true;
        }
        UsernamePasswordToken token = new UsernamePasswordToken(adminUser.getAccount(), adminUser.getPassword(), rememberMe);
        adminUser.setPassword(null);
        try {
            SecurityUtils.getSubject().login(token);
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("账号或密码错误");
        } catch (IncorrectCredentialsException e) {
            return ServerResponse.createByErrorMessage("账号或密码错误");
        } catch (LockedAccountException e) {
            return ServerResponse.createByErrorMessage("对不起，账号已锁定");
        } catch (ExcessiveAttemptsException e) {
            return ServerResponse.createByErrorMessage("重试次数过多，已锁定");
        }
        Admin_User admin_user = adminUserService.getOne(adminUser);
        SecurityUtils.getSubject().getSession().setAttribute("CurrentAdminUser", admin_user);
        Integer id = admin_user.getId();
        admin_user = new Admin_User();
        admin_user.setId(id);
        admin_user.setLastlogintime(new Date());
        adminUserService.updateParams(admin_user);
        return ServerResponse.createBySuccessMessage("登陆成功");
    }
    @RequestMapping(value = "userCenter", method = RequestMethod.GET)
    public String getUserCenter() {
        return "system/user/usercenter";
    }

    @RequiresPermissions("admin:add")
    @RequestMapping(value = "getAddAdminUser", method = RequestMethod.GET)
    public String getAddAdminUser(ModelMap map) {
        List<Role> roles=roleService.getAll(null);
        map.addAttribute("roles",roles);
        return "system/user/addAdminUser";
    }

    @RequiresPermissions("admin:update")
    @RequestMapping(value = "getEditorAdminUser", method = RequestMethod.GET)
    public String getEditorAdminUser(Integer id, ModelMap map) {
        Admin_User admin_user = adminUserService.find(id);
        AdminUser adminUser = new AdminUser();
        adminUser.setId(admin_user.getId());
        adminUser.setAccount(admin_user.getAccount());
        adminUser.setHeadimage(admin_user.getHeadimage());
        adminUser.setEmail(admin_user.getEmail());
        adminUser.setRole(null);
        adminUser.setIdentifier(admin_user.getIdentifier());
        adminUser.setRemarks(admin_user.getRemarks());
        adminUser.setCreatedatetime(format.format(admin_user.getCreatedatetime()));
        if (admin_user.getLastlogintime() == null) {
            adminUser.setLastlogintime("该用户没有登录过");
        } else {
            adminUser.setLastlogintime(format.format(admin_user.getLastlogintime()));
        }
        List<Role> roles=roleService.getAll(null);
        Admin_Role admin_role=new Admin_Role();
        admin_role.setAid(id);
        List<Admin_Role> admin_roleList=admin_roleService.getAll(admin_role);
        List<RoleDTO> roleDTOList=new ArrayList<>();
        RoleDTO roleDTO=null;
        for(Role r : roles){
            roleDTO=new RoleDTO();
            roleDTO.setId(r.getId());
            roleDTO.setAvailable(r.getAvailable());
            roleDTO.setDescription(r.getDescription());
            roleDTO.setChecked(false);
            for(Admin_Role ar : admin_roleList){
                if(ar.getRid()==r.getId()){
                    roleDTO.setChecked(true);
                }
            }
            roleDTOList.add(roleDTO);
        }
        map.addAttribute("roles",roleDTOList);
        map.addAttribute("adminUser", adminUser);
        return "system/user/adminEditor";
    }

    @RequestMapping(value = "adminUserDel", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse adminUserDel(Integer id) {
        adminUserService.delete(id);
        return ServerResponse.createBySuccessMessage("管理员删除成功！");
    }

    @RequestMapping(value = "getAdminUser", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getAdminUser(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        if (page == null || limit == null) {
            page = 1;
            limit = 30;
        }
        long count = adminUserService.getScrollCount(null);
        List<Admin_User> a_us = adminUserService.getScrollData(null, "id DESC", (page - 1) * limit, limit);
        List<AdminUser> aus = new ArrayList<>();
        AdminUser adminUser = null;
        int aid = 1;
        count = setAdminUserDTO(adminUser, a_us, aus, aid, count);
        return ServerResponse.createBySuccessForLayuiTable("请求成功", aus, count);
    }

    @RequiresPermissions("admin:add")
    @RequestMapping(value = "addAdminUser", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addAdminUser(Admin_User admin_user, MultipartHttpServletRequest request, MultipartFile file,String[] roles) {
        if (file.isEmpty()) {
            return ServerResponse.createByErrorMessage("请上传头像！");
        }
        String fn = file.getOriginalFilename();
        String suffix = fn.substring(fn.lastIndexOf('.') + 1, fn.length());
        long fileSize = file.getSize();
        if (fileSize > 512000) {
            return ServerResponse.createByErrorMessage("请上传小于500k的图片");
        }
        if (suffix.equals("jpg") || suffix.equals("JPG") || suffix.equals("jpeg") || suffix.equals("JPEG") || suffix.equals("PNG") || suffix.equals("png") || suffix.equals("GIF") || suffix.equals("gif")) {
        } else return ServerResponse.createByErrorMessage("请上传jpg/jpeg/png/gif格式的图片！");
        String path = request.getSession().getServletContext().getRealPath("uploadimage");
        String fileName = new Date().getTime() + "." + suffix;
        try {
            FileUploadUtils.uploadSingleImage(path, fileName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("上传图片失败");
        }
        admin_user.setHeadimage(fileName);
        admin_user.setPassword("000000");
        passwordHelper.encryptPassword(admin_user);
        admin_user.setCreatedatetime(new Date());
        adminUserService.save(admin_user);
        int maxId=adminUserService.getIdMax();
        List<Admin_Role> admin_roleList=new ArrayList<>();
        Admin_Role admin_role=null;
        for(String rid : roles){
            admin_role=new Admin_Role();
            admin_role.setAid(maxId);
            admin_role.setRid(Integer.parseInt(rid));
            admin_roleList.add(admin_role);
        }
        admin_roleService.saveBatch(admin_roleList);
        return ServerResponse.createBySuccessMessage("添加管理员成功");
    }

    @RequestMapping(value = "adminUserSearch", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse adminUserSearch(String account, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        if (page == null || limit == null) {
            page = 1;
            limit = 30;
        }
        Admin_User admin_user = new Admin_User();
        admin_user.setAccount("%" + account + "%");
        long count = adminUserService.getScrollByLikeCount(admin_user);
        List<Admin_User> a_us = adminUserService.getScrollDataByLike(admin_user, "id DESC", (page - 1) * limit, limit);
        List<AdminUser> aus = new ArrayList<>();
        AdminUser adminUser = null;
        int aid = 1;
        count = setAdminUserDTO(adminUser, a_us, aus, aid, count);
        return ServerResponse.createBySuccessForLayuiTable("搜索成功", aus, count);
    }

    private long setAdminUserDTO(AdminUser adminUser, List<Admin_User> a_us, List<AdminUser> aus, int aid, long count) {
        for (Admin_User au : a_us) {
            if (!au.getAccount().equals("admin")) {
                adminUser = new AdminUser();
                adminUser.setId(au.getId());
                adminUser.setAid(aid);
                adminUser.setAccount(au.getAccount());
                adminUser.setHeadimage(au.getHeadimage());
                adminUser.setEmail(au.getEmail());
                adminUser.setRole(null);
                adminUser.setIdentifier(au.getIdentifier());
                adminUser.setCreatedatetime(format.format(au.getCreatedatetime()));
                if (au.getLastlogintime() == null) {
                    adminUser.setLastlogintime("该用户没有登录过");
                } else {
                    adminUser.setLastlogintime(format.format(au.getLastlogintime()));
                }
                adminUser.setRemarks(au.getRemarks());
                aus.add(adminUser);
                aid++;
            } else {
                count--;
            }
        }
        return count;
    }

    @RequestMapping(value = "updateAdminHeadImage", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateAdminUserHeadImage(Integer id, MultipartFile file, HttpServletRequest request, String image) {
        Admin_User admin_user = new Admin_User();
        admin_user.setId(id);
        if (file.isEmpty()) {
            return ServerResponse.createByErrorMessage("请上传头像！");
        }
        String fn = file.getOriginalFilename();
        String suffix = fn.substring(fn.lastIndexOf('.') + 1, fn.length());
        long fileSize = file.getSize();
        if (fileSize > 512000) {
            return ServerResponse.createByErrorMessage("请上传小于500k的图片");
        }
        if (suffix.equals("jpg") || suffix.equals("JPG") || suffix.equals("jpeg") || suffix.equals("JPEG") || suffix.equals("PNG") || suffix.equals("png") || suffix.equals("GIF") || suffix.equals("gif")) {
        } else return ServerResponse.createByErrorMessage("请上传jpg/jpeg/png/gif格式的图片！");
        String path = request.getSession().getServletContext().getRealPath("uploadimage");
        String fileName = new Date().getTime() + "." + suffix;
        try {
            FileUploadUtils.uploadSingleImage(path, fileName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("上传图片失败");
        }
        File file1 = new File(path + "/" + image);
        if (file1.exists()) {
            file1.delete();
        }
        admin_user.setHeadimage(fileName);
        adminUserService.updateParams(admin_user);
        return ServerResponse.createBySuccess("图片上传成功", "uploadimage/" + fileName);
    }

    @RequiresPermissions("admin:update")
    @RequestMapping(value = "updateAdminUser", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateAdminUser(Admin_User admin_user,String[] roles) {
        adminUserService.updateParams(admin_user);
        for(String r : roles){
            Admin_Role admin_role=new Admin_Role();
            admin_role.setRid(Integer.parseInt(r));
            admin_role.setAid(admin_user.getId());
            admin_roleService.updateAdminRoleByAid(admin_role);
        }
        return ServerResponse.createBySuccessMessage("更新成功");
    }

    @RequestMapping(value = "getUpdateAdminPassword", method = RequestMethod.GET)
    public String getUpdateAdminPassword() {
        return "system/user/updateAdminPassword";
    }

    @RequestMapping(value = "adminPasswordUpdate", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse adminPasswordUpdate(String newPassword) {

        Admin_User admin_user = (Admin_User) SecurityUtils.getSubject().getSession(true).getAttribute("CurrentAdminUser");
        Admin_User adu = new Admin_User();
        adu.setId(admin_user.getId());
        if (!newPassword.equals("") && newPassword != null) {
            adu.setAccount(admin_user.getAccount());
            adu.setPassword(newPassword);
            passwordHelper.encryptPassword(adu);
            adminUserService.updateParams(adu);
            return ServerResponse.createBySuccessMessage("密码重置成功，下次登录将失效");
        }
        return ServerResponse.createByErrorMessage("密码重置失败");
    }

}
