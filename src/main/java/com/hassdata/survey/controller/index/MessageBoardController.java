package com.hassdata.survey.controller.index;

import com.hassdata.survey.dto.MessageBoardDTO;
import com.hassdata.survey.po.MessageBoard;
import com.hassdata.survey.service.MessageBoardService;
import com.hassdata.survey.util.ServerResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.plugin2.message.Message;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/system")
@Scope("prototype")
public class MessageBoardController {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

    @Resource
    private MessageBoardService messageBoardService;

    @RequestMapping(value = "getMessageBoardCenter", method = RequestMethod.GET)
    public String getMessageBoardCenter() {
        return "system/web/messageboard/messagecenter";
    }


    @RequestMapping(value = "getMSGList", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getMSGList(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "30") Integer limit) {
        Long count = messageBoardService.getScrollCount(null);
        List<MessageBoard> messageBoards = messageBoardService.getScrollData(null, "id desc", (page - 1) * limit, limit);
        List<MessageBoardDTO> messageBoardDTOS = new ArrayList<>();
        setMessageBoardDTO(messageBoards, messageBoardDTOS);
        return ServerResponse.createBySuccessForLayuiTable("查询成功", messageBoardDTOS, count);
    }

    private void setMessageBoardDTO(List<MessageBoard> messageBoards, List<MessageBoardDTO> messageBoardDTOS) {
        MessageBoardDTO messageBoardDTO = null;
        int i = 1;
        for (MessageBoard mb : messageBoards) {
            messageBoardDTO = new MessageBoardDTO();
            messageBoardDTO.setAid(i);
            messageBoardDTO.setId(mb.getId());
            messageBoardDTO.setIsread(mb.isIsread() ? "已读" : "未读");
            messageBoardDTO.setMsg(mb.getMsg());
            messageBoardDTO.setName(mb.getName());
            messageBoardDTO.setCreatetime(simpleDateFormat.format(mb.getCreatetime()));
            messageBoardDTO.setTelphone(mb.getTelphone());
            messageBoardDTOS.add(messageBoardDTO);
            i++;
        }
    }

    @RequiresPermissions("msg:update")
    @RequestMapping(value = "getEditorMsg", method = RequestMethod.GET)
    public String getEditorMsg(Long id, ModelMap map, boolean isRead) {
        MessageBoard msg = messageBoardService.find(id);
        map.addAttribute("msg", msg);
        if (!isRead) {
            MessageBoard board = new MessageBoard();
            board.setId(id);
            board.setIsread(true);
            messageBoardService.updateParams(board);
        }
        return "system/web/messageboard/editorMsg";
    }

    @RequiresPermissions("msg:update")
    @RequestMapping(value = "updateMsg", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateMsg(MessageBoard messageBoard) {
        messageBoard.setIsread(true);
        messageBoardService.updateParams(messageBoard);
        return ServerResponse.createBySuccessMessage("更新成功");
    }

    @RequestMapping(value = "searchMsg", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse searchMsg(HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "30") Integer limit) {
        MessageBoard messageBoard = new MessageBoard();
        String name = request.getParameter("name");
        try {
            name= URLDecoder.decode(URLDecoder.decode(name,"UTF-8"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (name != null && !name.equals("")) {
            messageBoard.setName("%" + name + "%");
        }
        long count = messageBoardService.getScrollByLikeCount(messageBoard);
        List<MessageBoard> messageBoards = messageBoardService.getScrollDataByLike(messageBoard, "id desc", (page - 1) * limit, limit);
        List<MessageBoardDTO> messageBoardDTOS = new ArrayList<>();
        setMessageBoardDTO(messageBoards, messageBoardDTOS);
        return ServerResponse.createBySuccessForLayuiTable("查询成功", messageBoardDTOS, count);
    }

    @RequiresPermissions("msg:delete")
    @RequestMapping(value = "msgDel", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse msgDel(Long id) {
        messageBoardService.delete(id);
        return ServerResponse.createBySuccessMessage("留言删除成功");
    }

}
