package cn.czc.controller;

import cn.czc.pojo.Label;
import cn.czc.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/label")
public class LabelController {
    @Autowired
    private LabelService labelService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(method = RequestMethod.GET)
    public Result findall(){
        String str = (String) request.getHeader("Authorization");
        System.out.println(str);
        return new Result(true, StatusCode.OK,"查询成功",labelService.findall());
    };

    @RequestMapping(value = "/{labelId}",method = RequestMethod.GET)
    public Result findById(@PathVariable("labelId") String labelId){
        return new Result(true, StatusCode.OK,"查询成功",labelService.findById(labelId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Label label){
        labelService.save(label);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public Result delete(@PathVariable("labelId") String labelId){
        labelService.deleteById(labelId);
        return new Result(true, StatusCode.OK,"删除成功");
    }

    @RequestMapping(value = "/{labelId}",method = RequestMethod.PUT)
    public Result update(@PathVariable("labelId") String labelId,@RequestBody Label label){
        label.setId(labelId);
        labelService.update(label);
        return new Result(true, StatusCode.OK,"修改成功");
    }

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Label label){
        List<Label> labelList  = labelService .findSearch(label);
        return new Result(true, StatusCode.OK,"查询成功",labelList);
    };

    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    private Result pageQuery(@RequestBody Label label,@PathVariable int page, @PathVariable int size){
        Page<Label> pageData  = labelService .pageQuery(label,page,size);
        return new Result(true, StatusCode.OK,"查询成功",new PageResult<Label>(pageData.getNumberOfElements(),pageData.getContent()));
    }
}
