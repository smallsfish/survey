package com.hassdata.survey.controller.diplay;

import com.hassdata.survey.dto.DisplayQuestionModel;
import com.hassdata.survey.dto.DisplayQuestionTypeModel;
import com.hassdata.survey.dto.DisplayQuestionnaireModel;
import com.hassdata.survey.po.Options;
import com.hassdata.survey.po.Question;
import com.hassdata.survey.po.QuestionType;
import com.hassdata.survey.po.Questionnaire;
import com.hassdata.survey.service.OptionsService;
import com.hassdata.survey.service.QuestionService;
import com.hassdata.survey.service.QuestionTypeService;
import com.hassdata.survey.service.QuestionnaireService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        for (QuestionType qt : questionTypes){
            displayQuestionTypeModel=new DisplayQuestionTypeModel();
            displayQuestionTypeModel.setQuestionType(qt);
            Question question = new Question();
            question.setQuestiontypeid(qt.getId());
            List<Question> questions=questionService.getAll(question);
            DisplayQuestionModel displayQuestionModel=null;
            List<DisplayQuestionModel> displayQuestionModels=new ArrayList<>();
            for(Question q : questions){
                displayQuestionModel=new DisplayQuestionModel();
                displayQuestionModel.setQuestion(q);
                Options options=new Options();
                options.setQuestionid(q.getId());
                List<Options> optionsList=optionsService.getAll(options);
                displayQuestionModels.add(displayQuestionModel);
                displayQuestionModel.setOptionsList(optionsList);
            }
            Collections.sort(displayQuestionModels, new Comparator<DisplayQuestionModel>() {
                @Override
                public int compare(DisplayQuestionModel o1, DisplayQuestionModel o2) {
                    int i = o1.getQuestion().getQuestionsort() - o2.getQuestion().getQuestionsort();
                    if(i == 0){
                        return o1.getQuestion().getQuestionsort() - o2.getQuestion().getQuestionsort();
                    }
                    return i;
                }
            });
            displayQuestionTypeModels.add(displayQuestionTypeModel);
            displayQuestionTypeModel.setDisplayQuestionModels(displayQuestionModels);
        }
        Collections.sort(displayQuestionTypeModels, new Comparator<DisplayQuestionTypeModel>() {
            @Override
            public int compare(DisplayQuestionTypeModel o1, DisplayQuestionTypeModel o2) {
                int i = o1.getQuestionType().getQuestionTypesort() - o2.getQuestionType().getQuestionTypesort();
                if(i == 0){
                    return o1.getQuestionType().getQuestionTypesort() - o2.getQuestionType().getQuestionTypesort();
                }
                return i;
            }
        });
        displayQuestionnaireModel.setDisplayQuestionTypeModels(displayQuestionTypeModels);
        map.addAttribute("displayQuestionnaireModel",displayQuestionnaireModel);
        return "display/questionnaire/index";
    }
}
