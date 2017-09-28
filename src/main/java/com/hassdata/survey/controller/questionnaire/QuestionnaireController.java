package com.hassdata.survey.controller.questionnaire;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hassdata.survey.dto.AdminUser;
import com.hassdata.survey.dto.QuestionnaireModel;
import com.hassdata.survey.po.*;
import com.hassdata.survey.service.*;
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
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("system")
@Scope("prototype")
public class QuestionnaireController {

    @Resource
    private QuestionnaireService questionnaireService;

    @Resource
    private AdminUserService adminUserService;

    @Resource
    private QuestionTypeService questionTypeService;

    @Resource
    private QuestionService questionService;

    @Resource
    private OptionsService optionsService;

    @RequestMapping(value = "questionnaires",method = RequestMethod.GET)
    public String getQuestionnaires(ModelMap map){
        long count=questionnaireService.getScrollCount(null);
        List<Questionnaire> questionnaires=questionnaireService.getScrollData(null,"id DESC",0,8);
        List<QuestionnaireModel> questionnaireModels=new ArrayList<>();
        QuestionnaireModel questionnaireModel=null;
        for (Questionnaire q : questionnaires){
            questionnaireModel=new QuestionnaireModel();
            questionnaireModel.setId(q.getId());
            questionnaireModel.setQuestionnairebegintime(q.getQuestionnairebegintime());
            questionnaireModel.setQuestionnaireendtime(q.getQuestionnaireendtime());
            questionnaireModel.setQuestionnairecreatetime(q.getQuestionnairecreatetime());
            questionnaireModel.setQuestionnairename(q.getQuestionnairename());
            questionnaireModel.setAuthor(adminUserService.find(q.getAid()).getAccount());
            questionnaireModels.add(questionnaireModel);
        }
        map.addAttribute("count",count);
        map.addAttribute("qms",questionnaireModels);
        return "system/questionnaire/questionnaire";
    }

    @RequestMapping(value = "questionnairesList",method = RequestMethod.GET)
    public String getQuestionnaires(@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer limit){
        if(page==null || limit==null){
            page=1;
            limit=8;
        }
        List<Questionnaire> questionnaires=questionnaireService.getScrollData(null,"id DESC",(page-1)*limit,limit);
        System.out.println(questionnaires.size());
        return "system/questionnaire/questionnaire";
    }

    @RequestMapping(value = "questionnaireAdd",method = RequestMethod.GET)
    public String getQuestionnaireAdd(){
        return "system/questionnaire/questionnaireAdd";
    }
    @RequestMapping(value = "questionnaireAdd",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse questionnaireAdd(String json, HttpServletRequest request){
        System.out.println(json);
        HttpSession session=request.getSession(true);
        Admin_User admin_user= (Admin_User) session.getAttribute("CurrentAdminUser");
        Questionnaire questionnaire=new Questionnaire();
        String questionnaireid=UUID.randomUUID().toString();
        questionnaire.setId(questionnaireid);
        System.out.println(admin_user.getId());
        questionnaire.setAid(admin_user.getId());
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject jsonObject=JSONObject.parseObject(json);
        questionnaire.setQuestionnairename(jsonObject.getString("name"));
        questionnaire.setQuestionnairecomp(jsonObject.getString("comp"));
        questionnaire.setQuestionnairefrom(jsonObject.getString("where"));
        if(jsonObject.getString("daterange")!=null){
            String beginTime=jsonObject.getString("daterange").split(" ~ ")[0];
            String endTime=jsonObject.getString("daterange").split(" ~ ")[1];
            try {
                questionnaire.setQuestionnairebegintime(format.parse(beginTime));
                questionnaire.setQuestionnaireendtime(format.parse(endTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        questionnaire.setQuestionnaireexplain(jsonObject.getString("explain"));
        try {
            questionnaire.setQuestionnairecreatetime(format.parse(format.format(new Date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        questionnaire.setQuestionnairejson(json);
        questionnaireService.save(questionnaire);
        JSONArray jsonArrayTypes=jsonObject.getJSONArray("types");
        QuestionType questionType=null;
        JSONObject jsonObjectType=null;
        if(jsonArrayTypes!=null) {
            for (int i = 0; i < jsonArrayTypes.size(); i++) {
                jsonObjectType = jsonArrayTypes.getJSONObject(i);
                if (jsonObjectType != null) {
                    questionType = new QuestionType();
                    String questionTypeId = UUID.randomUUID().toString();
                    questionType.setId(questionTypeId);
                    questionType.setQuestionnaireid(questionnaireid);
                    questionType.setQuestionTypecolor(null);
                    questionType.setQuestionTypename(jsonObjectType.getString("title"));
                    questionType.setQuestionTypesort(jsonObjectType.getInteger("sort"));
                    questionTypeService.save(questionType);
                    JSONArray jsonArrayQuestion = jsonObjectType.getJSONArray("questions");
                    JSONObject jsonObjectQuestion = null;
                    Question question = null;
                    if(jsonArrayQuestion!=null) {
                        for (int j = 0; j < jsonArrayQuestion.size(); j++) {
                            jsonObjectQuestion = jsonArrayQuestion.getJSONObject(j);
                            if (jsonObjectQuestion != null) {
                                question = new Question();
                                String questionid = UUID.randomUUID().toString();
                                question.setId(questionid);
                                question.setQuestionnaireid(questionnaireid);
                                question.setQuestionname(jsonObjectQuestion.getString("main"));
                                question.setQuestionstyle(jsonObjectQuestion.getString("type"));
                                question.setQuestiontypeid(questionTypeId);
                                questionService.save(question);
                                JSONArray jsonArrayOption = jsonObjectQuestion.getJSONArray("options");
                                if (jsonArrayOption != null) {
                                    Options options=null;
                                    for (int z=0;z<jsonArrayOption.size();z++){
                                        options=new Options();
                                        options.setOptionsname(jsonArrayOption.getString(z));
                                        options.setQuestionid(questionid);
                                        optionsService.save(options);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return ServerResponse.createBySuccessMessage("上传成功");
    }

}
