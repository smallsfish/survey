package com.hassdata.survey.controller.data;

import com.hassdata.survey.dto.DataCenterDTO;
import com.hassdata.survey.dto.OptionInfo;
import com.hassdata.survey.dto.QuestionCharts;
import com.hassdata.survey.po.Options;
import com.hassdata.survey.po.Question;
import com.hassdata.survey.po.Questionnaire;
import com.hassdata.survey.service.OptionsService;
import com.hassdata.survey.service.QuestionService;
import com.hassdata.survey.service.QuestionnaireService;
import com.hassdata.survey.service.ScoreService;
import com.hassdata.survey.util.ServerResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("system")
@Scope("prototype")
public class DataCenterController {

    private SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private ScoreService scoreService;

    @Resource
    private QuestionService questionService;

    @Resource
    private OptionsService optionsService;

    @Resource
    private QuestionnaireService questionnaireService;

    @RequestMapping(value = "data",method = RequestMethod.GET)
    public String getDataCenter(ModelMap map){
        long count=questionnaireService.getScrollCount(null);
        List<Questionnaire> questionnaireList=questionnaireService.getScrollData(null,"id desc" ,0,12);
        List<DataCenterDTO> dataCenterDTOS=new ArrayList<>();
        setDataDTO(questionnaireList, dataCenterDTOS);
        map.addAttribute("qs",dataCenterDTOS);
        map.addAttribute("count",count);
        return "system/data/datacenter";
    }

    @RequestMapping(value = "dataList" ,method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse dataList(@RequestParam(required = false,defaultValue = "1") Integer page,@RequestParam(required = false,defaultValue = "12") Integer limit){
        List<Questionnaire> questionnaireList=questionnaireService.getScrollData(null,"id desc" ,(page-1)*limit,limit);
        List<DataCenterDTO> dataCenterDTOS=new ArrayList<>();
        setDataDTO(questionnaireList, dataCenterDTOS);
        return ServerResponse.createBySuccess("请求成功",dataCenterDTOS);
    }

    private void setDataDTO(List<Questionnaire> questionnaireList, List<DataCenterDTO> dataCenterDTOS) {
        DataCenterDTO dataCenterDTO=null;
        for(Questionnaire q : questionnaireList){
            dataCenterDTO=new DataCenterDTO();
            dataCenterDTO.setName(q.getQuestionnairename());
            boolean b=false,e=false;
            if(q.getQuestionnairebegintime()==null || q.getQuestionnairebegintime().equals("")){
                dataCenterDTO.setB("随时");
            }else{
                dataCenterDTO.setB(format.format(q.getQuestionnairebegintime()));
                b=true;
            }
            if(q.getQuestionnaireendtime()==null || q.getQuestionnaireendtime().equals("")){
                dataCenterDTO.setE("随时");
            }else{
                dataCenterDTO.setE(format.format(q.getQuestionnaireendtime()));
                e=true;
            }
            Date d=new Date();
            if(b&&e){
                //如果两个都有时间限制
                if(d.getTime()>q.getQuestionnaireendtime().getTime()){
                    //当前时间大于问卷结束时间
                    setQuestionnaireAllowView(dataCenterDTO, q);
                }else if (d.getTime()<q.getQuestionnaireendtime().getTime()){
                    //当前时间小于问卷开始时间
                    setQuestionnaireNoBegin(dataCenterDTO);
                }else{
                    //当前时间在正在问卷调查中
                    dataCenterDTO.setButton("问卷正在调查中");
                    dataCenterDTO.setUrl("javascript:;");
                    dataCenterDTO.setCityNumber("暂无");
                    dataCenterDTO.setStudentNumber("0人");
                    dataCenterDTO.setSchoolNumber("0所");
                }
            }else if(b&&!e){
                //如果开始时间有限制，结束时间无限制
                if(d.getTime()<q.getQuestionnairebegintime().getTime()){
                    //当前时间小于问卷开始时间
                    setQuestionnaireNoBegin(dataCenterDTO);
                }else{
                    //可以进行问卷分析
                    setQuestionnaireAllowView(dataCenterDTO, q);
                }
            }else if(!b&&e){
                //如果开始时间无限制，结束时间有限制
                if(d.getTime()>q.getQuestionnaireendtime().getTime()){
                    //可以分析
                    setQuestionnaireAllowView(dataCenterDTO, q);
                }else{
                    setQuestionnaireNoBegin(dataCenterDTO);
                }
            }else{
                //无时间限制
                //随时进行问卷分析
                setQuestionnaireAllowView(dataCenterDTO, q);
            }
            dataCenterDTOS.add(dataCenterDTO);
        }
    }

    private void setQuestionnaireAllowView(DataCenterDTO dataCenterDTO, Questionnaire q) {
        dataCenterDTO.setButton("数据分析");
        dataCenterDTO.setUrl("system/da?id="+q.getId());
        dataCenterDTO.setCityNumber("暂无");
        dataCenterDTO.setSchoolNumber(scoreService.getQuestionnaireWithUserId(q.getId()).size()+"所");
        dataCenterDTO.setStudentNumber(scoreService.getQuestionnaireWithStudentId(q.getId()).size()+"人");
    }

    private void setQuestionnaireNoBegin(DataCenterDTO dataCenterDTO) {
        dataCenterDTO.setButton("问卷未开始");
        dataCenterDTO.setUrl("javascript:;");
        dataCenterDTO.setCityNumber("暂无");
        dataCenterDTO.setStudentNumber("0人");
        dataCenterDTO.setSchoolNumber("0所");
    }

    @RequestMapping(value = "dataSearch",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse dataSearch(HttpServletRequest request,@RequestParam(required = false,defaultValue = "1") Integer page,@RequestParam(required = false,defaultValue = "12") Integer limit){
        String name=null;
        try {
            name=new String(request.getParameter("name").getBytes("iso-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Questionnaire questionnaire=new Questionnaire();
        questionnaire.setQuestionnairename("%"+name+"%");
        long count=questionnaireService.getScrollByLikeCount(questionnaire);
        List<Questionnaire> questionnaireList=questionnaireService.getScrollDataByLike(questionnaire,"id desc" ,(page-1)*limit,limit);
        List<DataCenterDTO> dataCenterDTOS=new ArrayList<>();
        setDataDTO(questionnaireList, dataCenterDTOS);
        return ServerResponse.createBySuccessForLayuiTable("搜索成功",dataCenterDTOS,count);
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
            qc.setName(q.getQuestionname());
            Options op=new Options();
            op.setQuestionid(q.getId());
            List<Options> optionsList=optionsService.getAll(op);
            for(Options o : optionsList){
                oi=new OptionInfo();
                oi.setName(o.getOptionsname());
                oi.setCount((long)scoreService.getOptionCountWidthQuesitonnaire(id,q.getId(),"%"+o.getId()+"%").size());
                optionInfos.add(oi);
            }
            qc.setOptionInfos(optionInfos);
            questionCharts.add(qc);
        }
        map.addAttribute("ch",questionCharts);
        return "system/data/da";
    }
}
