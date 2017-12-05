package com.hassdata.survey.controller.index;

import com.hassdata.survey.dto.PictureDTO;
import com.hassdata.survey.dto.PictureTypeDTO;
import com.hassdata.survey.po.Admin_User;
import com.hassdata.survey.po.Pictures;
import com.hassdata.survey.po.Picturetype;
import com.hassdata.survey.service.AdminUserService;
import com.hassdata.survey.service.PictureService;
import com.hassdata.survey.service.PictureTypeService;
import com.hassdata.survey.util.FileUploadUtils;
import com.hassdata.survey.util.ServerResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("system")
@Scope("prototype")
public class PictureController {

    @Resource
    private PictureService pictureService;

    @Resource
    private AdminUserService adminUserService;

    @Resource
    private PictureTypeService pictureTypeService;

    @RequestMapping(value = "getPictureCenter", method = RequestMethod.GET)
    public String getPictureCenter(ModelMap map) {
        List<Picturetype> picturetypeList = pictureTypeService.getAll(null);
        map.addAttribute("pls", picturetypeList);
        return "system/web/picture/picturecenter";
    }


    @RequestMapping(value = "getAddPictures", method = RequestMethod.GET)
    public String getAddPictures(ModelMap map) {
        List<Picturetype> picturetypeList = pictureTypeService.getAll(null);
        map.addAttribute("pls", picturetypeList);
        return "system/web/picture/addPicture";
    }

    @RequestMapping(value = "addPicture", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addPicture(Pictures pictures, MultipartFile file, HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        Admin_User adminUser = (Admin_User) session.getAttribute("CurrentAdminUser");
        if (file.isEmpty()) {
            return ServerResponse.createByErrorMessage("请上传缩略图！");
        }
        String fn = file.getOriginalFilename();
        String suffix = fn.substring(fn.lastIndexOf('.') + 1, fn.length());
        long fileSize = file.getSize();
        if (fileSize > 2 * 1024 * 1024) {
            return ServerResponse.createByErrorMessage("请上传小于2M的图片");
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
        pictures.setPictureurl(fileName);
        pictures.setOperator(adminUser.getId());
        pictureService.save(pictures);
        return ServerResponse.createBySuccessMessage("图片上传成功");
    }


    @RequestMapping(value = "getPicturesTypeList", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getPicturesTypeList(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "30") Integer limit) {
        long count = pictureTypeService.getScrollCount(null);
        List<Picturetype> picturetypeList = pictureTypeService.getScrollData(null, "id desc", (page - 1) * limit, limit);
        List<PictureTypeDTO> pictureTypeDTOList = new ArrayList<>();
        int i = 1;
        PictureTypeDTO pictureTypeDTO = null;
        for (Picturetype pt : picturetypeList) {
            pictureTypeDTO = new PictureTypeDTO();
            pictureTypeDTO.setAid(i);
            pictureTypeDTO.setId(pt.getId());
            pictureTypeDTO.setName(pt.getName());
            pictureTypeDTOList.add(pictureTypeDTO);
            i++;
        }
        return ServerResponse.createBySuccessForLayuiTable("查找成功", pictureTypeDTOList, count);
    }


    @RequestMapping(value = "getPicturesList", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getPicturesList(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "30") Integer limit) {
        long count = pictureService.getScrollCount(null);
        List<Pictures> picturesList = pictureService.getScrollData(null, "id desc", (page - 1) * limit, limit);
        List<PictureDTO> pictureDTOS = new ArrayList<>();
        int i = 1;
        setPictureDTO(picturesList, pictureDTOS, i);
        return ServerResponse.createBySuccessForLayuiTable("查询成功", pictureDTOS, count);
    }


    @RequestMapping(value = "getAddPicturesType", method = RequestMethod.GET)
    public String getAddPicturesType() {
        return "system/web/picture/addPictureType";
    }

    @RequestMapping(value = "addPictureType", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addPictureType(Picturetype picturetype) {
        pictureTypeService.save(picturetype);
        return ServerResponse.createBySuccessMessage("类型添加成功");
    }


    @RequestMapping(value = "getEditorPictures", method = RequestMethod.GET)
    public String getEditorPictures(Long id, ModelMap map) {
        Pictures pictures = pictureService.find(id);
        map.addAttribute("p", pictures);
        List<Picturetype> picturetypeList = pictureTypeService.getAll(null);
        map.addAttribute("pls", picturetypeList);
        return "system/web/picture/editorPicture";
    }


    @RequestMapping(value = "updatePictureImage", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updatePictureImage(Long id, MultipartFile file, HttpServletRequest request, String image) {
        Pictures pictures = new Pictures();
        pictures.setId(id);
        if (file.isEmpty()) {
            return ServerResponse.createByErrorMessage("请上传缩略图！");
        }
        String fn = file.getOriginalFilename();
        String suffix = fn.substring(fn.lastIndexOf('.') + 1, fn.length());
        long fileSize = file.getSize();
        if (fileSize > 2 * 1024 * 1024) {
            return ServerResponse.createByErrorMessage("请上传小于2M的图片");
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
        pictures.setPictureurl(fileName);
        pictureService.updateParams(pictures);
        return ServerResponse.createBySuccess("图片图修改成功", "uploadimage/" + fileName);
    }


    @RequestMapping(value = "picturestypeDel", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse picturestypeDel(Integer id, HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("uploadimage");
        pictureTypeService.delete(id);
        Pictures pictures = new Pictures();
        pictures.setPicturetype(id);
        pictureService.getAll(pictures).stream().forEach((p) -> {
            pictureService.delete(p.getId());
            File file1 = new File(path + "/" + p.getPictureurl());
            if (file1.exists()) {
                file1.delete();
            }
        });
        return ServerResponse.createBySuccessMessage("删除成功");
    }

    @RequestMapping(value = "picturesDel", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse picturesDel(Long id, HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("uploadimage");
        File file1 = new File(path + "/" + pictureService.find(id).getPictureurl());
        if (file1.exists()) {
            file1.delete();
        }
        pictureService.delete(id);
        return ServerResponse.createBySuccessMessage("删除成功");
    }

    @RequestMapping(value = "editorPicture", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse editorPicture(Pictures pictures) {
        pictureService.updateParams(pictures);
        return ServerResponse.createBySuccessMessage("图片修改成功");
    }

    @RequestMapping(value = "pictureSearch", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse pictureSearch(HttpServletRequest request, Integer status, Integer picturestype, @RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "30") Integer limit) {
        Pictures pictures = new Pictures();
        String picturetitle = null;
        try {
            picturetitle = new String(request.getParameter("picturestitle").getBytes("iso-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (!picturetitle.equals("")) {
            pictures.setPicturetitle("%" + picturetitle + "%");
        }
        if (status != 2) {
            pictures.setStatus(status == 1 ? true : false);
        }
        if (picturestype != 0) {
            pictures.setPicturetype(picturestype);
        }
        long count = pictureService.getScrollByLikeCount(pictures);
        List<Pictures> picturesList = pictureService.getScrollDataByLike(pictures, "id desc", (page - 1) * limit, limit);
        List<PictureDTO> pictureDTOS = new ArrayList<>();
        int i = 1;
        setPictureDTO(picturesList, pictureDTOS, i);
        return ServerResponse.createBySuccessForLayuiTable("查询成功", pictureDTOS, count);
    }

    private void setPictureDTO(List<Pictures> picturesList, List<PictureDTO> pictureDTOS, int i) {
        PictureDTO pictureDTO;
        for (Pictures p : picturesList) {
            pictureDTO = new PictureDTO();
            pictureDTO.setAid(i);
            pictureDTO.setId(p.getId());
            pictureDTO.setOperator(adminUserService.find(p.getOperator()).getAccount());
            pictureDTO.setPicturetitle(p.getPicturetitle());
            pictureDTO.setPictureurl(p.getPictureurl());
            pictureDTO.setPicturetype(pictureTypeService.find(p.getPicturetype()).getName());
            pictureDTO.setStatus(p.getStatus() ? "显示" : "隐藏");
            pictureDTOS.add(pictureDTO);
            i++;
        }
    }

}
