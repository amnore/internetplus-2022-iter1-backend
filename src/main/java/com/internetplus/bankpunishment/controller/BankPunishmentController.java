package com.internetplus.bankpunishment.controller;

import com.internetplus.bankpunishment.bl.BankPunishmentBl;
import com.internetplus.bankpunishment.crawler.CrawlerStarter;
import com.internetplus.bankpunishment.crawler.processor.handler.PunishmentDetailPageHandler;
import com.internetplus.bankpunishment.entity.ApiResult;
import com.internetplus.bankpunishment.entity.BankPunishment;
import com.internetplus.bankpunishment.po.TestPO;
import com.internetplus.bankpunishment.utils.HandleFile;
import com.internetplus.bankpunishment.vo.BankPunishmentPageVO;
import com.internetplus.bankpunishment.vo.BankPunishmentQueryVO;
import com.internetplus.bankpunishment.vo.BankPunishmentStasticsVO;
import com.internetplus.bankpunishment.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: bank-punishment
 * @description:
 * @author: xzh
 * @date: 2021-10-20
 **/
@Slf4j
@RestController
@RequestMapping("/api/bankpunishment")//由于long在前后端传有精度损失，要用string
public class BankPunishmentController {//解决方案：①另设string字段②用fastjson转化；选fastjson

    @Autowired
    BankPunishmentBl bankPunishmentBl;

    private final Integer defaultPageSize = 20;
    private final Integer defaultPageNo = 0;

    @PostMapping("/insert")//发布状态由系统录入，即新建时一律尚未发布，前端传的state字段无用
    public ResultVO insertBankPunishment(@RequestBody BankPunishment bankPunishment) {
        System.out.println("insert "+bankPunishment);
        try {
            bankPunishment.setStatus("0");//发布状态由系统录入，即新建时一律尚未发布
            Long id = bankPunishmentBl.insertBankPunishment(bankPunishment);
            return ResultVO.buildSuccess(id.toString());//要传字符串保证精度不失
        }catch (Exception e){
            return ResultVO.buildFailure(500,e.getMessage());
        }
    }

    @PostMapping("/upload")
    public ResultVO uploadBankPunishmentByExcel(HttpServletRequest request) throws Exception{
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        MultipartFile file = multipartRequest.getFile(multipartRequest.getFileNames().next());

        if (file.isEmpty()) {
            return ResultVO.buildFailure(400,"文件不能为空");
        }
        InputStream inputStream = file.getInputStream();
        List<List<Object>> list = HandleFile.parseExcel(inputStream,file.getOriginalFilename());
        inputStream.close();
        try {
            Integer res = bankPunishmentBl.uploadBankPunishmentByExcel(list);
            return ResultVO.buildSuccess(res);
        } catch (Exception e) {
            return ResultVO.buildFailure(500, e.getMessage());
        }

    }


    @PostMapping("/update/{exceptNull}")//考虑到可能有发布后修改的需求，此处的更新不对状态做限制
    //如果不需要可以删掉ExceptNull参数
    public ResultVO updateBankPunishment(@RequestBody BankPunishment bankPunishment,@PathVariable boolean exceptNull) {
        System.out.println("update "+bankPunishment+";exceptNull:"+exceptNull);
        try {
            boolean success = false;
//            bankPunishment.setId(Long.parseLong(bankPunishment.getStringId()));
            if(exceptNull){//某字段若为null则不更新该字段
                success = bankPunishmentBl.updateBankPunishmentExceptNull(bankPunishment);
            }else {//全部字段强制覆盖
                success = bankPunishmentBl.updateBankPunishment(bankPunishment);
            }
            if(!success){
                throw new Exception("can't change as expected");
            }
            return ResultVO.buildSuccess("update successfully");
        }catch (Exception e){
            return ResultVO.buildFailure(500,e.getMessage());
        }
    }

    @PostMapping("/delete")
    public ResultVO deleteBankPunishment(@RequestBody List<String> IDList) {
        try {
            boolean allSuccess = true;
            StringBuilder failItems = new StringBuilder();
            for(String id:IDList){
                System.out.print("delete "+id+";");
                boolean success = bankPunishmentBl.deleteBankPunishment(Long.parseLong(id));
                if(!success){
                    failItems.append(id+",");
                    allSuccess = false;
                }
            }
            System.out.println();
            if(!allSuccess){
                throw new Exception("id("+failItems.toString()+") can't delete as expected");
            }
            return ResultVO.buildSuccess("delete successfully");
        }catch (Exception e){
            System.out.println();
            return ResultVO.buildFailure(500,e.getMessage());
        }
    }

    @PostMapping("/publish")//发布大概主要是前端展示上的操作，从编辑区列表转移到发布区列表之类
    public ResultVO publishBankPunishment(@RequestBody List<String> IDList) {
        try {
            boolean allSuccess = true;
            StringBuilder failItems = new StringBuilder();
            for(String id:IDList){
                System.out.print("publish "+id+";");
                boolean success = bankPunishmentBl.publishBankPunishment(Long.parseLong(id));
                if(!success){
                    failItems.append(id+",");
                    allSuccess = false;
                }
            }
            System.out.println();
            if(!allSuccess){
                throw new Exception("id("+failItems.toString()+") can't publish as expected");
            }
            return ResultVO.buildSuccess("publish successfully");
        }catch (Exception e){
            System.out.println();
            return ResultVO.buildFailure(500,e.getMessage());
        }
    }

    @PostMapping("/query")
    public ApiResult<BankPunishmentPageVO> queryBankPunishment(@RequestBody BankPunishmentQueryVO query) {
        if (query.getLimit() == null) {
            query.setLimit(20);
        }
        if (query.getOffset() == null) {
            query.setOffset(0);
        }

        List<BankPunishment> bankPunishments = bankPunishmentBl.selectBankPunishment(query);
        return ApiResult.success(new BankPunishmentPageVO(bankPunishments.size(), bankPunishments));
    }

    @GetMapping("/statistics")
    public ApiResult<BankPunishmentStasticsVO> bankPunishmentStastics() {

        HashMap<Integer, Integer> punishDateStastics = new HashMap<>();
        HashMap<String, Integer> punishmentTypeStastics = new HashMap<>();

        try{
            BankPunishmentQueryVO queryDate = new BankPunishmentQueryVO();
            for (int i=2010; i<=2022; i++) {
                queryDate.setPunishDate(String.valueOf(i));
                punishDateStastics.put(i, bankPunishmentBl.selectBankPunishment(queryDate).size());
            }

            BankPunishmentQueryVO queryType = new BankPunishmentQueryVO();
            queryType.setPunishmentType("个人");
            punishmentTypeStastics.put("个人", bankPunishmentBl.selectBankPunishment(queryType).size());
            queryType.setPunishmentType("企业");
            punishmentTypeStastics.put("企业", bankPunishmentBl.selectBankPunishment(queryType).size());

            BankPunishmentStasticsVO stasticsVO = new BankPunishmentStasticsVO(punishDateStastics, punishmentTypeStastics);
            return ApiResult.success(stasticsVO);
        } catch (Exception e) {
            return ApiResult.fail(e.getMessage());
        }
    }



//    @GetMapping("/select/{pageSize}/{pageNO}")//顺便写了一套select，有更好的写法可以把这个删掉
//    public ResultVO selectBankPunishment(@RequestBody BankPunishment bankPunishment,@PathVariable int pageSize,
//                                         @PathVariable int pageNO) {
//        System.out.println("select "+bankPunishment+";size:"+pageSize+";no:"+pageNO);
//        try {
////            if(bankPunishment.getId()!=null){
////                throw new Exception("id should be null");
////            }
////            if(bankPunishment.getStringId()!=null){
////                bankPunishment.setId(Long.parseLong(bankPunishment.getStringId()));
////            }
//            List<BankPunishment> bankPunishments = bankPunishmentBl.selectBankPunishment(bankPunishment);//根据非null字段搜索，全null则返回全体
//            int max=bankPunishments.size();
//            List<BankPunishment> resList = new ArrayList<>();
//            if(max!=0) {
//                int fromIndex = pageNO * pageSize;
//                int toIndex = (pageNO+1) * pageSize;
//                if (fromIndex >= max) {
//                    throw new Exception("pageNO overflow");
//                } else{
//                    resList = bankPunishments.subList(fromIndex, Math.min(toIndex, max));
//                }
//            }
////            for(BankPunishment bankPunishment1:resList){
////                bankPunishment1.setStringId(bankPunishment.getId().toString());
////                bankPunishment1.setId(null);
////            }
//            BankPunishmentPageVO bankPunishmentPageVO = new BankPunishmentPageVO(max,resList);
//            return ResultVO.buildSuccess(bankPunishmentPageVO);
//        }catch (Exception e){
//            return ResultVO.buildFailure(500,e.getMessage());
//        }
//    }

}
