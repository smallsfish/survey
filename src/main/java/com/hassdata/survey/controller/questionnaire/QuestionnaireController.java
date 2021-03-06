package com.hassdata.survey.controller.questionnaire;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hassdata.survey.dto.DisplayQuestionTypeModel;
import com.hassdata.survey.dto.DisplayQuestionnaireModel;
import com.hassdata.survey.dto.QuestionnaireModel;
import com.hassdata.survey.po.*;
import com.hassdata.survey.service.*;
import com.hassdata.survey.util.QuestionnaireSort;
import com.hassdata.survey.util.ServerResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @RequestMapping(value = "questionnaires", method = RequestMethod.GET)
    public String getQuestionnaires(ModelMap map) {
        long count = questionnaireService.getScrollCount(null);
        List<Questionnaire> questionnaires = questionnaireService.getScrollData(null, "id DESC", 0, 8);
        List<QuestionnaireModel> questionnaireModels = new ArrayList<>();
        QuestionnaireModel questionnaireModel = null;
        Question question = null;
        setQuestionnaireModelValue(questionnaires, questionnaireModels);
        map.addAttribute("count", count);
        map.addAttribute("qms", questionnaireModels);
        return "system/questionnaire/questionnaire";
    }

    @RequestMapping(value = "questionnairesList", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getQuestionnaires(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        if (page == null || limit == null) {
            page = 1;
            limit = 12;
        }
        List<Questionnaire> questionnaires = questionnaireService.getScrollData(null, "questionnairecreatetime DESC", (page - 1) * limit, limit);
        return getServerResponse(questionnaires, 0);
    }

    @RequestMapping(value = "questionnaireSearch", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse questionnaireSearch(HttpServletRequest request, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        if (page == null || limit == null) {
            page = 1;
            limit = 12;
        }
        String name = request.getParameter("name");
        try {
            name=URLDecoder.decode(URLDecoder.decode(name,"UTF-8"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setQuestionnairename("%" + name + "%");
        List<Questionnaire> questionnaires = questionnaireService.getScrollDataByLike(questionnaire, "questionnairecreatetime DESC", (page - 1) * limit, limit);
        long count = questionnaireService.getScrollByLikeCount(questionnaire);
        System.out.println(count);
        return getServerResponse(questionnaires, count);
    }

    private ServerResponse getServerResponse(List<Questionnaire> questionnaires, long count) {
        if (questionnaires.size() <= 0) {
            return ServerResponse.createByErrorMessage("没有查到相关问卷");
        } else {
            List<QuestionnaireModel> questionnaireModels = new ArrayList<>();
            QuestionnaireModel questionnaireModel = null;
            Question question = null;
            setQuestionnaireModelValue(questionnaires, questionnaireModels);
            return ServerResponse.createBySuccessForLayuiTable("搜索成功", questionnaireModels, count);
        }
    }

    private void setQuestionnaireModelValue(List<Questionnaire> questionnaires, List<QuestionnaireModel> questionnaireModels) {
        QuestionnaireModel questionnaireModel;
        Question question;
        for (Questionnaire q : questionnaires) {
            questionnaireModel = new QuestionnaireModel();
            questionnaireModel.setId(q.getId());
            questionnaireModel.setQuestionnairebegintime(q.getQuestionnairebegintime());
            questionnaireModel.setQuestionnaireendtime(q.getQuestionnaireendtime());
            questionnaireModel.setQuestionnairecreatetime(q.getQuestionnairecreatetime());
            questionnaireModel.setQuestionnairename(q.getQuestionnairename());
            questionnaireModel.setAuthor(adminUserService.find(q.getAid()).getAccount());
            question = new Question();
            question.setQuestionnaireid(q.getId());
            questionnaireModel.setQuestions(questionService.getAll(question).size());
            questionnaireModels.add(questionnaireModel);
        }
    }

    @RequiresPermissions("questionnaire:add")
    @RequestMapping(value = "questionnaireAdd", method = RequestMethod.GET)
    public String getQuestionnaireAdd() {
        return "system/questionnaire/questionnaireAdd";
    }

    @RequestMapping(value = "questionnaireAdd", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse questionnaireAdd(String json, HttpServletRequest request) {
        questionnaireAddUtils("", json, request, false);
        return ServerResponse.createBySuccessMessage("上传成功");
    }

    private void questionnaireAddUtils(String id, String json, HttpServletRequest request, boolean isEditor) {
        HttpSession session = request.getSession(true);
        Admin_User admin_user = (Admin_User) session.getAttribute("CurrentAdminUser");
        Questionnaire questionnaire = new Questionnaire();
        String questionnaireid = "";
        if (!isEditor) {
            questionnaireid = UUID.randomUUID().toString();
            questionnaire.setId(questionnaireid);
        } else {
            questionnaire.setId(id);
            questionnaireid = id;
        }
        questionnaire.setAid(admin_user.getId());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject jsonObject = JSONObject.parseObject(json);
        questionnaire.setQuestionnairename(jsonObject.getString("name"));
        questionnaire.setQuestionnairecomp(jsonObject.getString("comp"));
        questionnaire.setQuestionnairefrom(jsonObject.getString("where"));
        if (jsonObject.getString("daterange") != null) {
            String beginTime = jsonObject.getString("daterange").split(" ~ ")[0];
            String endTime = jsonObject.getString("daterange").split(" ~ ")[1];
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
        JSONArray jsonArrayTypes = jsonObject.getJSONArray("types");
        QuestionType questionType = null;
        JSONObject jsonObjectType = null;
        if (jsonArrayTypes != null) {
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
                    if (jsonArrayQuestion != null) {
                        for (int j = 0; j < jsonArrayQuestion.size(); j++) {
                            jsonObjectQuestion = jsonArrayQuestion.getJSONObject(j);
                            if (jsonObjectQuestion != null) {
                                question = new Question();
                                String questionid = UUID.randomUUID().toString();
                                question.setId(questionid);
                                question.setQuestionnaireid(questionnaireid);
                                question.setQuestionname(jsonObjectQuestion.getString("main"));
                                question.setQuestionstyle(jsonObjectQuestion.getString("type"));
                                question.setQuestionsort(jsonObjectQuestion.getInteger("sort"));
                                question.setQuestiontypeid(questionTypeId);
                                questionService.save(question);
                                JSONArray jsonArrayOption = jsonObjectQuestion.getJSONArray("options");
                                if (jsonArrayOption != null) {
                                    Options options = null;
                                    for (int z = 0; z < jsonArrayOption.size(); z++) {
                                        String option = jsonArrayOption.getString(z);
                                        if (option != null) {
                                            options = new Options();
                                            options.setOptionsname(option);
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
        }
    }

    @RequiresPermissions("questionnaire:delete")
    @RequestMapping(value = "questionnaireDel", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse questionnaireDelete(String id, HttpServletRequest request) {
        Question question = new Question();
        question.setQuestionnaireid(id);
        optionsService.deleteByQuestionid(questionService.getOne(question).getId());
        questionTypeService.deleteByQuestionnaireId(id);
        questionService.deleteByQuestionnaireId(id);
        if (questionnaireService.deleteByStringId(id) > 0) {
            return ServerResponse.createBySuccessMessage("问卷删除成功！");
        } else {
            return ServerResponse.createBySuccessMessage("问卷删除失败！");
        }
    }


    @RequiresPermissions("questionnaire:update")
    @RequestMapping(value = "questionnaireEditor", method = RequestMethod.GET)
    public String getQuestionnaireEditor(String id, ModelMap map) {
        Questionnaire questionnaire = questionnaireService.findByStringId(id);
        DisplayQuestionnaireModel displayQuestionnaireModel = new DisplayQuestionnaireModel();
        displayQuestionnaireModel.setQuestionnaire(questionnaire);
        QuestionType questionType = new QuestionType();
        questionType.setQuestionnaireid(questionnaire.getId());
        List<QuestionType> questionTypes = questionTypeService.getAll(questionType);
        DisplayQuestionTypeModel displayQuestionTypeModel = null;
        List<DisplayQuestionTypeModel> displayQuestionTypeModels = new ArrayList<>();
        QuestionnaireSort questionnaireSort = new QuestionnaireSort();
        questionnaireSort.questionSort(questionTypes, displayQuestionTypeModels, questionService, optionsService);
        displayQuestionnaireModel.setDisplayQuestionTypeModels(displayQuestionTypeModels);
        map.addAttribute("displayQuestionnaireModel", displayQuestionnaireModel);
        return "system/questionnaire/questionnaireEditor";
    }

    @RequiresPermissions("questionnaire:update")
    @RequestMapping(value = "questionnaireEditor", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse questionnaireEditor(String id, String json, HttpServletRequest request) {
        Question question = new Question();
        question.setQuestionnaireid(id);
        optionsService.deleteByQuestionid(questionService.getOne(question).getId());
        questionTypeService.deleteByQuestionnaireId(id);
        questionService.deleteByQuestionnaireId(id);
        questionnaireService.deleteByStringId(id);
        questionnaireAddUtils(id, json, request, true);
        return ServerResponse.createBySuccessMessage("修改成功");
    }

    @RequiresPermissions("questionnaire:view")
    @RequestMapping(value = "displayQuestionnaire", method = RequestMethod.GET)
    public String getDisplayQuestionnaire(String id, ModelMap map) {
        Questionnaire questionnaire = questionnaireService.findByStringId(id);
        DisplayQuestionnaireModel displayQuestionnaireModel = new DisplayQuestionnaireModel();
        displayQuestionnaireModel.setQuestionnaire(questionnaire);
        QuestionType questionType = new QuestionType();
        questionType.setQuestionnaireid(questionnaire.getId());
        List<QuestionType> questionTypes = questionTypeService.getAll(questionType);
        DisplayQuestionTypeModel displayQuestionTypeModel = null;
        List<DisplayQuestionTypeModel> displayQuestionTypeModels = new ArrayList<>();
        QuestionnaireSort questionnaireSort = new QuestionnaireSort();
        questionnaireSort.questionSort(questionTypes, displayQuestionTypeModels, questionService, optionsService);
        displayQuestionnaireModel.setDisplayQuestionTypeModels(displayQuestionTypeModels);
        map.addAttribute("displayQuestionnaireModel", displayQuestionnaireModel);
        return "system/questionnaire/index";
    }

}
