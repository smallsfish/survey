package com.hassdata.survey.controller.index;

import com.hassdata.survey.dto.*;
import com.hassdata.survey.po.*;
import com.hassdata.survey.service.*;
import com.hassdata.survey.util.ServerResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("index")
@Scope("prototype")
public class HomeController {

    private SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd");

    @Resource
    private MessageBoardService messageBoardService;

    @Resource
    private NewsService newsService;

    @Resource
    private AdminUserService adminUserService;

    @Resource ScoreService scoreService;

    @Resource
    private QuestionService questionService;

    @Resource
    private OptionsService optionsService;

    @Resource
    private PictureService pictureService;

    @Resource
    private NewsTypeService newsTypeService;

    @Resource
    private PictureTypeService pictureTypeService;

    @Resource
    private VideoService videoService;

    @Resource
    private LoopService loopService;

    @Resource
    private QuestionnaireService questionnaireService;


    @RequestMapping(value = "home",method = RequestMethod.GET)
    public String getIndexHome(ModelMap map){
        News news=new News();
        Pictures pictures=new Pictures();
        Video video=new Video();
        news.setStatus(true);
        pictures.setStatus(true);
        video.setStatus(true);
        List<News> newsList=newsService.getScrollData(news,"id desc" , 0,10);
        List<Video> videoList=videoService.getScrollData(video,"id desc" ,0,9);
        List<Pictures> picturesList=pictureService.getScrollData(pictures,"id desc",0,7);
        setLoopAttribute(map);
        map.addAttribute("news",newsList);
        map.addAttribute("videos",videoList);
        map.addAttribute("pcitures",picturesList);
        return "index/home";
    }

    @RequestMapping(value = "getNews",method = RequestMethod.GET)
    public String getNews(){
        return "index/new";
    }

    @RequestMapping(value = "news",method = RequestMethod.GET)
    public String getIndexNews(ModelMap map){
        setLoopAttribute(map);
        List<Newstype> newstypes=newsTypeService.getAll(null,"id desc");
        List<NewsIndexDTO> newsIndexDTOS=new ArrayList<>();
        List<NewsDTO> newsDTOS=null;
        NewsIndexDTO newsIndexDTO=null;
        NewsDTO newsDTO=null;
        for(Newstype nt : newstypes){
            newsDTOS=new ArrayList<>();
            newsIndexDTO=new NewsIndexDTO();
            newsIndexDTO.setTypeName(nt.getName());
            News news=new News();
            news.setNewstype(nt.getId());
            List<News> newsList=newsService.getAll(news,"id desc");
            for(News n : newsList){
                newsDTO=new NewsDTO();
                newsDTO.setId(n.getId());
                newsDTO.setImageurl(n.getImageurl());
                newsDTO.setCreatetime(simpleDateFormat1.format(n.getCreatetime()));
                newsDTO.setNewstitle(n.getNewstitle());
                newsDTOS.add(newsDTO);
            }
            newsIndexDTO.setNewsDTOList(newsDTOS);
            newsIndexDTOS.add(newsIndexDTO);
        }
        map.addAttribute("nids",newsIndexDTOS);
        return "index/news";
    }

    @RequestMapping(value = "newsDetail",method = RequestMethod.GET)
    public String newsDeatil(Long id,ModelMap map){
        setLoopAttribute(map);
        News news=newsService.find(id);
        NewsDTO newsDTO=new NewsDTO();
        newsDTO.setNewstitle(news.getNewstitle());
        newsDTO.setComeform(news.getComeform());
        newsDTO.setCreatetime(simpleDateFormat1.format(news.getCreatetime()));
        newsDTO.setOperator(adminUserService.find(news.getOperator()).getAccount());
        newsDTO.setNewscontent(news.getNewscontent());
        map.addAttribute("news",newsDTO);
        return "index/newsDetail";
    }

    @RequestMapping(value = "picture",method = RequestMethod.GET)
    public String getIndexPicture(ModelMap map){
        setLoopAttribute(map);
        List<Picturetype> picturetypeList=pictureTypeService.getAll(null);
        List<PictureIndexDTO> pictureIndexDTOS=new ArrayList<>();
        List<Pictures> picturesList=null;
        PictureIndexDTO pictureIndexDTO=null;
        for(Picturetype pt : picturetypeList){
            picturesList=new ArrayList<>();
            pictureIndexDTO=new PictureIndexDTO();
            pictureIndexDTO.setPictureTypeName(pt.getName());
            Pictures ps=new Pictures();
            ps.setPicturetype(pt.getId());
            picturesList=pictureService.getAll(ps,"id desc");
            pictureIndexDTO.setPicturesList(picturesList);
            pictureIndexDTOS.add(pictureIndexDTO);
        }
        map.addAttribute("pts",pictureIndexDTOS);
        return "index/pictures";
    }
    @RequestMapping(value = "video",method = RequestMethod.GET)
    public String getIndexVideo(ModelMap map){
        setLoopAttribute(map);
        List<Video> videoList=videoService.getAll(null,"id desc");
        map.addAttribute("vl",videoList);
        return "index/video";
    }
    @RequestMapping(value = "msg",method = RequestMethod.GET)
    public String getIndexMsg(ModelMap map){
        setLoopAttribute(map);
        return "index/msg";
    }

    @RequestMapping(value = "questionnaire",method = RequestMethod.GET)
    public String getIndexQuestionnaire(ModelMap map){
        setLoopAttribute(map);
        List<Questionnaire> questionnaireList=questionnaireService.getAll(null,"questionnairecreatetime desc");
        List<QuestionnaireIndexDTO> questionnaireIndexDTOS =new ArrayList<>();
        QuestionnaireIndexDTO questionnaireIndexDTO =null;
        Date nDate=new Date();
        for(Questionnaire q : questionnaireList){
            questionnaireIndexDTO =new QuestionnaireIndexDTO();
            questionnaireIndexDTO.setId(q.getId());
            questionnaireIndexDTO.setName(q.getQuestionnairename());
            questionnaireIndexDTO.setTime(simpleDateFormat1.format(q.getQuestionnairecreatetime()));
            if(q.getQuestionnairebegintime()==null && q.getQuestionnaireendtime()==null){
                questionnaireIndexDTO.setStatus(0);
            }else if(q.getQuestionnairebegintime()!=null && q.getQuestionnaireendtime()==null){
                if(nDate.getTime()<q.getQuestionnairebegintime().getTime()){
                    questionnaireIndexDTO.setStatus(1);
                }else{
                    questionnaireIndexDTO.setStatus(0);
                }
            }else if(q.getQuestionnairebegintime()==null && q.getQuestionnaireendtime()!=null){
                if(nDate.getTime()>q.getQuestionnaireendtime().getTime()){
                    questionnaireIndexDTO.setStatus(2);
                }else{
                    questionnaireIndexDTO.setStatus(0);
                }
            }else{
                if(nDate.getTime()<q.getQuestionnairebegintime().getTime()){
                    questionnaireIndexDTO.setStatus(1);
                }else if(nDate.getTime()>q.getQuestionnaireendtime().getTime()){
                    questionnaireIndexDTO.setStatus(2);
                }else{
                    questionnaireIndexDTO.setStatus(0);
                }
            }
            questionnaireIndexDTOS.add(questionnaireIndexDTO);
        }
        map.addAttribute("questionnaires",questionnaireIndexDTOS);
        return "index/questionnaire";
    }




    @RequestMapping(value = "data",method = RequestMethod.GET)
    public String getIndexData(ModelMap map){
        setLoopAttribute(map);
        List<Questionnaire> questionnaireList=questionnaireService.getAll(null,"questionnairecreatetime desc");
        List<DataIndexDTO> dataIndexDTOS =new ArrayList<>();
        DataIndexDTO dataIndexDTO =null;
        Date nDate=new Date();
        for(Questionnaire q : questionnaireList){
            dataIndexDTO =new DataIndexDTO();
            dataIndexDTO.setId(q.getId());
            dataIndexDTO.setName(q.getQuestionnairename());
            dataIndexDTO.setTime(simpleDateFormat1.format(q.getQuestionnairecreatetime()));
            if(q.getQuestionnairebegintime()==null && q.getQuestionnaireendtime()==null){
                dataIndexDTO.setStatus(0);
            }else if(q.getQuestionnairebegintime()!=null && q.getQuestionnaireendtime()==null){
                if(nDate.getTime()<q.getQuestionnairebegintime().getTime()){
                    dataIndexDTO.setStatus(2);
                }else{
                    dataIndexDTO.setStatus(0);
                }
            }else if(q.getQuestionnairebegintime()==null && q.getQuestionnaireendtime()!=null){
                if(nDate.getTime()>q.getQuestionnaireendtime().getTime()){
                    dataIndexDTO.setStatus(0);
                }else{
                    dataIndexDTO.setStatus(1);
                }
            }else{
                if(nDate.getTime()<q.getQuestionnairebegintime().getTime()){
                    dataIndexDTO.setStatus(2);
                }else if(nDate.getTime()>q.getQuestionnaireendtime().getTime()){
                    dataIndexDTO.setStatus(0);
                }else{
                    dataIndexDTO.setStatus(1);
                }
            }
            dataIndexDTOS.add(dataIndexDTO);
        }
        map.addAttribute("questionnaires",dataIndexDTOS);
        return "index/data";
    }


    @RequestMapping(value = "da",method = RequestMethod.GET)
    public String getDataAnalyse(ModelMap map,String id){
        Question question=new Question();
        question.setQuestionnaireid(id);
        List<Question> questionList=questionService.getAll(question);
        List<QuestionCharts> questionCharts=new ArrayList<>();
        List<OptionInfo> optionInfos=null;
        QuestionCharts qc=null;
        OptionInfo oi=null;
        for(Question q : questionList){
            optionInfos=new ArrayList<>();
            qc=new QuestionCharts();
            qc.setName(splitName(true,q.getQuestionname()));
            Options op=new Options();
            op.setQuestionid(q.getId());
            List<Options> optionsList=optionsService.getAll(op);
            for(Options o : optionsList){
                oi=new OptionInfo();
                oi.setName(splitName(false,o.getOptionsname()));
                oi.setCount((long)scoreService.getOptionCountWidthQuesitonnaire(id,q.getId(),"%"+o.getId()+"%").size());
                optionInfos.add(oi);
            }
            qc.setOptionInfos(optionInfos);
            questionCharts.add(qc);
        }
        map.addAttribute("ch",questionCharts);
        return "index/da";
    }


    private void setLoopAttribute(ModelMap map) {
        Loop loop=new Loop();
        loop.setIsshow(true);
        List<Loop> loops=loopService.getAll(loop,"sort desc");
        map.addAttribute("loops",loops);
    }


    @RequestMapping(value = "submitMsg",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse submitMsg(MessageBoard messageBoard){
        MessageBoard board = new MessageBoard();
        board.setTelphone(messageBoard.getTelphone());
        MessageBoard messageBoard1=messageBoardService.getOne(board);
        if(messageBoard1==null){
            messageBoard.setCreatetime(new Date());
            messageBoard.setIsread(false);
            messageBoardService.save(messageBoard);
        }else{
            String oDate=simpleDateFormat1.format(messageBoard1.getCreatetime());
            String nDate=simpleDateFormat1.format(new Date());
            if(oDate.equals(nDate)){
                return ServerResponse.createByErrorMessage("对不起，您今日已经留言了，请明日再来");
            }else{
                messageBoard.setCreatetime(new Date());
                messageBoard.setIsread(false);
                messageBoardService.save(messageBoard);
            }
        }
        return ServerResponse.createBySuccessMessage("留言成功");
    }




    /**
     * 分割字符串,如果是问题18个一行，否则12个一行
     * @param isQuestion
     * @param str
     * @return
     */
    private String splitName(boolean isQuestion,String str){
        String s="";
        String[] sc=str.split("");
        if(isQuestion){
            for(int i=0; i<sc.length;i++){
                if(i!=0 && i%18==0){
                    s+="\\n"+sc[i];
                }else{
                    s+=sc[i];
                }
            }
        }else{
            for(int i=0; i<sc.length;i++){
                if(i!=0 && i%12==0){
                    s+="\\n"+sc[i];
                }else{
                    s+=sc[i];
                }
            }
        }
        System.out.println(s);
        return s;
    }
}
