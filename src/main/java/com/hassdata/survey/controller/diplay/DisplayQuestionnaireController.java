package com.hassdata.survey.controller.diplay;

import com.hassdata.survey.dto.DisplayQuestionTypeModel;
import com.hassdata.survey.dto.DisplayQuestionnaireModel;
import com.hassdata.survey.po.QuestionType;
import com.hassdata.survey.po.Questionnaire;
import com.hassdata.survey.service.OptionsService;
import com.hassdata.survey.service.QuestionService;
import com.hassdata.survey.service.QuestionTypeService;
import com.hassdata.survey.service.QuestionnaireService;
import com.hassdata.survey.util.QuestionnaireSort;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("display")
@Scope("prototype")
public class DisplayQuestionnaireController {
    @Resource
    private QuestionService questionService;
    @Resource
    private QuestionnaireService questionnaireService;
    @Resource
    private QuestionTypeService questionTypeService;
    @Resource
    private OptionsService optionsService;
    @RequestMapping(value = "displayQuestionnaire",method = RequestMethod.GET)
    public String getDisplayQuestionnaire(String id, ModelMap map){
        Questionnaire questionnaire = questionnaireService.findByStringId(id);
        DisplayQuestionnaireModel displayQuestionnaireModel=new DisplayQuestionnaireModel();
        displayQuestionnaireModel.setQuestionnaire(questionnaire);
        QuestionType questionType=new QuestionType();
        questionType.setQuestionnaireid(questionnaire.getId());
        List<QuestionType> questionTypes=questionTypeService.getAll(questionType);
        DisplayQuestionTypeModel displayQuestionTypeModel=null;
        List<DisplayQuestionTypeModel> displayQuestionTypeModels=new ArrayList<>();
        QuestionnaireSort questionnaireSort=new QuestionnaireSort();
        questionnaireSort.questionSort(questionTypes, displayQuestionTypeModels,questionService,optionsService);
        displayQuestionnaireModel.setDisplayQuestionTypeModels(displayQuestionTypeModels);
        map.addAttribute("displayQuestionnaireModel",displayQuestionnaireModel);
        map.addAttribute("questionnaireId",id);
        return "display/questionnaire/index";
    }
}
