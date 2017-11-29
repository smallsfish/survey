package com.hassdata.survey.util;

import com.hassdata.survey.dto.DisplayQuestionModel;
import com.hassdata.survey.dto.DisplayQuestionTypeModel;
import com.hassdata.survey.po.Options;
import com.hassdata.survey.po.Question;
import com.hassdata.survey.po.QuestionType;
import com.hassdata.survey.service.OptionsService;
import com.hassdata.survey.service.QuestionService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class QuestionnaireSort {
    public void questionSort(List<QuestionType> questionTypes, List<DisplayQuestionTypeModel> displayQuestionTypeModels, QuestionService questionService, OptionsService optionsService) {
        DisplayQuestionTypeModel displayQuestionTypeModel;
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
    }
}
