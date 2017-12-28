package com.hassdata.survey.controller.data;

import com.hassdata.survey.dto.*;
import com.hassdata.survey.po.County;
import com.hassdata.survey.po.Options;
import com.hassdata.survey.po.Question;
import com.hassdata.survey.po.Questionnaire;
import com.hassdata.survey.service.*;
import com.hassdata.survey.util.ServerResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("system")
@Scope("prototype")
public class DataCenterController {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private ScoreService scoreService;

    @Resource
    private CountyService countyService;

    @Resource
    private CityService cityService;

    @Resource
    private ProvinceService provinceService;

    @Resource
    private QuestionService questionService;

    @Resource
    private OptionsService optionsService;

    @Resource
    private QuestionnaireService questionnaireService;

    @RequestMapping(value = "data", method = RequestMethod.GET)
    public String getDataCenter(ModelMap map) {
        long count = questionnaireService.getScrollCount(null);
        List<Questionnaire> questionnaireList = questionnaireService.getScrollData(null, "id desc", 0, 12);
        List<DataCenterDTO> dataCenterDTOS = new ArrayList<>();
        setDataDTO(questionnaireList, dataCenterDTOS);
        map.addAttribute("qs", dataCenterDTOS);
        map.addAttribute("count", count);
        return "system/data/datacenter";
    }

    @RequestMapping(value = "dataList", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse dataList(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "12") Integer limit) {
        List<Questionnaire> questionnaireList = questionnaireService.getScrollData(null, "id desc", (page - 1) * limit, limit);
        List<DataCenterDTO> dataCenterDTOS = new ArrayList<>();
        setDataDTO(questionnaireList, dataCenterDTOS);
        return ServerResponse.createBySuccess("请求成功", dataCenterDTOS);
    }

    private void setDataDTO(List<Questionnaire> questionnaireList, List<DataCenterDTO> dataCenterDTOS) {
        DataCenterDTO dataCenterDTO = null;
        for (Questionnaire q : questionnaireList) {
            dataCenterDTO = new DataCenterDTO();
            dataCenterDTO.setId(q.getId());
            dataCenterDTO.setName(q.getQuestionnairename());
            boolean b = false, e = false;
            if (q.getQuestionnairebegintime() == null || q.getQuestionnairebegintime().equals("")) {
                dataCenterDTO.setB("未设");
            } else {
                dataCenterDTO.setB(format.format(q.getQuestionnairebegintime()));
                b = true;
            }
            if (q.getQuestionnaireendtime() == null || q.getQuestionnaireendtime().equals("")) {
                dataCenterDTO.setE("未设");
            } else {
                dataCenterDTO.setE(format.format(q.getQuestionnaireendtime()));
                e = true;
            }
            Date d = new Date();
            if (b && e) {
                //如果两个都有时间限制
                if (d.getTime() > q.getQuestionnaireendtime().getTime()) {
                    //当前时间大于问卷结束时间
                    setQuestionnaireAllowView(dataCenterDTO, q);
                } else if (d.getTime() < q.getQuestionnaireendtime().getTime()) {
                    //当前时间小于问卷开始时间
                    setQuestionnaireNoBegin(dataCenterDTO);
                } else {
                    //当前时间在正在问卷调查中
                    dataCenterDTO.setButton("问卷正在调查中");
                    dataCenterDTO.setUrl("javascript:;");
                    dataCenterDTO.setCityNumber("暂无");
                    dataCenterDTO.setStudentNumber("0人");
                    dataCenterDTO.setSchoolNumber("0所");
                }
            } else if (b && !e) {
                //如果开始时间有限制，结束时间无限制
                if (d.getTime() < q.getQuestionnairebegintime().getTime()) {
                    //当前时间小于问卷开始时间
                    setQuestionnaireNoBegin(dataCenterDTO);
                } else {
                    //可以进行问卷分析
                    setQuestionnaireAllowView(dataCenterDTO, q);
                }
            } else if (!b && e) {
                //如果开始时间无限制，结束时间有限制
                if (d.getTime() > q.getQuestionnaireendtime().getTime()) {
                    //可以分析
                    setQuestionnaireAllowView(dataCenterDTO, q);
                } else {
                    setQuestionnaireNoBegin(dataCenterDTO);
                }
            } else {
                //无时间限制
                //随时进行问卷分析
                setQuestionnaireAllowView(dataCenterDTO, q);
            }
            dataCenterDTOS.add(dataCenterDTO);
        }
    }

    private void setQuestionnaireAllowView(DataCenterDTO dataCenterDTO, Questionnaire q) {
        dataCenterDTO.setButton("数据分析");
        dataCenterDTO.setUrl("system/da?id=" + q.getId());
        dataCenterDTO.setCityNumber("暂无");
        dataCenterDTO.setSchoolNumber(scoreService.getQuestionnaireWithUserId(q.getId()).size() + "所");
        dataCenterDTO.setStudentNumber(scoreService.getQuestionnaireWithStudentId(q.getId()).size() + "人");
    }

    private void setQuestionnaireNoBegin(DataCenterDTO dataCenterDTO) {
        dataCenterDTO.setButton("问卷未开始");
        dataCenterDTO.setUrl("javascript:;");
        dataCenterDTO.setCityNumber("暂无");
        dataCenterDTO.setStudentNumber("0人");
        dataCenterDTO.setSchoolNumber("0所");
    }

    @RequestMapping(value = "dataSearch", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse dataSearch(HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "12") Integer limit) {
        String name = request.getParameter("name");
        try {
            name = URLDecoder.decode(URLDecoder.decode(name, "UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setQuestionnairename("%" + name + "%");
        long count = questionnaireService.getScrollByLikeCount(questionnaire);
        List<Questionnaire> questionnaireList = questionnaireService.getScrollDataByLike(questionnaire, "id desc", (page - 1) * limit, limit);
        List<DataCenterDTO> dataCenterDTOS = new ArrayList<>();
        setDataDTO(questionnaireList, dataCenterDTOS);
        return ServerResponse.createBySuccessForLayuiTable("搜索成功", dataCenterDTOS, count);
    }

    @RequestMapping(value = "dataExcel", method = RequestMethod.GET)
    public void dataExcel(String id, HttpServletResponse response) {
        XSSFWorkbook xss = new XSSFWorkbook();
        XSSFRow row = null;
        XSSFCell cell = null;
        //表头样式
        CellStyle titleStyle = xss.createCellStyle();
        titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        Font titleFont = xss.createFont();
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setBoldweight((short) 400);
        titleStyle.setFont(titleFont);
        // 列头样式
        CellStyle headerStyle = xss.createCellStyle();
        headerStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        headerStyle.setFillBackgroundColor(new XSSFColor().getIndexed());
        Font headerFont = xss.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerStyle.setFont(headerFont);
        // 单元格样式
        CellStyle cellStyle = xss.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        Font cellFont = xss.createFont();
        cellFont.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
        cellStyle.setFont(cellFont);
        Questionnaire questionnaire = questionnaireService.findByStringId(id);
        Question question = new Question();
        question.setQuestionnaireid(questionnaire.getId());
        List<Question> questionList = questionService.getAll(question);
        XSSFSheet xssfSheet = null;
        Collections.sort(questionList, new Comparator<Question>() {
            @Override
            public int compare(Question o1, Question o2) {
                return o1.getQuestionsort() - o2.getQuestionsort();
            }
        });
        List<County> countyList = countyService.getAll(null);
        for (Question q : questionList) {
            xssfSheet = xss.createSheet(q.getQuestionname().split(":")[0]);
            row = xssfSheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellStyle(titleStyle);
            cell.setCellValue(q.getQuestionname());
            row = xssfSheet.createRow(1);
            cell = row.createCell(0);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("选项");
            int cellCount = 1;
            for (County c : countyList) {
                cell = row.createCell(cellCount++);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(c.getCountyname());
            }
            Options options=new Options();
            options.setQuestionid(q.getId());
            List<Options> optionsList=optionsService.getAll(options);
            int rowCount=2;
            for(Options o : optionsList){
                row = xssfSheet.createRow(rowCount++);
                cell = row.createCell(0);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(o.getOptionsname());
                int cc=1;
                for(County c : countyList){
                    cell = row.createCell(cc++);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(scoreService.getSelectCountByCountyId("%"+o.getId()+"%",c.getId(),questionnaire.getId()));
                }
            }
        }
        String filename= null;
        try {
            filename = "attachment; filename="+new String(questionnaire.getQuestionnairename().getBytes("GBK"),"iso-8859-1")+".xlsx";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            response.reset();
            response.setHeader("Content-Disposition", filename);
            response.setContentType("application/octet-stream;charset=UTF-8");
            ServletOutputStream outputStream = response.getOutputStream();
            xss.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresPermissions("data:view")
    @RequestMapping(value = "da", method = RequestMethod.GET)
    public String getDataAnalyse(ModelMap map, String id) {
        Question question = new Question();
        question.setQuestionnaireid(id);
        List<Question> questionList = questionService.getAll(question);
        List<County> countyList=countyService.getAll(null,"id DESC");
        Collections.sort(questionList, new Comparator<Question>() {
            @Override
            public int compare(Question o1, Question o2) {
                return o1.getQuestionsort()-o2.getQuestionsort();
            }
        });
        for(int i=0;i<questionList.size();i++){
            questionList.get(i).setQuestionname(questionList.get(i).getQuestionname().split(":")[0]);
        }
        map.addAttribute(questionList);
        map.addAttribute(countyList);
        return "system/data/da";
    }


    @RequestMapping(value = "getDataByQuestionIdOrCountyId",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getDataByQuestionIdOrCountyId(String questionid,Integer countyid){
        Question question=questionService.findByStringId(questionid);
        DataDTO dataDTO=new DataDTO();
        dataDTO.setQuestionname(question.getQuestionname());
        Options options=new Options();
        options.setQuestionid(question.getId());
        List<Options> optionsList=optionsService.getAll(options);
        List<OptionDTO> optionDTOS=new ArrayList<>();
        OptionDTO optionDTO=null;
        if(countyid==0){
            countyid=null;
        }
        for (Options o : optionsList){
            optionDTO=new OptionDTO();
            optionDTO.setName(o.getOptionsname());
            optionDTO.setCount(scoreService.getSelectCountByOptionId("%"+o.getId()+"%",countyid));
            optionDTOS.add(optionDTO);
        }
        dataDTO.setOptionDTOS(optionDTOS);
        return ServerResponse.createBySuccess(dataDTO);
    }

    /**
     * 分割字符串,如果是问题18个一行，否则12个一行
     *
     * @param isQuestion
     * @param str
     * @return
     */
    private String splitName(boolean isQuestion, String str) {
        String s = "";
        String[] sc = str.split("");
        if (isQuestion) {
            for (int i = 0; i < sc.length; i++) {
                if (i != 0 && i % 18 == 0) {
                    s += "\\n" + sc[i];
                } else {
                    s += sc[i];
                }
            }
        } else {
            for (int i = 0; i < sc.length; i++) {
                if (i != 0 && i % 12 == 0) {
                    s += "\\n" + sc[i];
                } else {
                    s += sc[i];
                }
            }
        }
        System.out.println(s);
        return s;
    }
}
