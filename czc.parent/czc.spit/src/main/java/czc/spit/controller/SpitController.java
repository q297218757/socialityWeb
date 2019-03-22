package czc.spit.controller;

import czc.spit.pojo.Spit;
import czc.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController{

    @Autowired
    private SpitService spitService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
     return new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable  String id){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findById(id));
    }

    @RequestMapping(method = RequestMethod.POST )
    public Result save( @RequestBody Spit spit){
        spitService.save(spit);
        return new Result(true,StatusCode.OK,"保存成功");
    }
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT )
    public Result update(@PathVariable String id, @RequestBody Spit spit){
        spit.set_id(id);
         spitService.update(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE )
    public Result delete(@PathVariable String id){
        spitService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    @RequestMapping(value = "/findByParentid/{id}/{page}/{size}",method = RequestMethod.GET )
    public Result findByParentid(@PathVariable String id, @PathVariable int page,@PathVariable  int size){
        Page page1 = spitService.findByParentid(id,page,size);
       return  new Result(true,StatusCode.OK,"查找成功",new PageResult<>(page1.getTotalElements(),page1.getContent()));
    };

    @RequestMapping(value = "/thumbup/{spitId}",method = RequestMethod.PUT )
    public Result thumbup(@PathVariable String spitId){
        //判断用户是否已经点赞了
        String userId = "1";
        if(redisTemplate.opsForValue().get("thumbup_"+userId+spitId) != null ){
            return new Result(false ,StatusCode.OK,"不能重复点赞");
        }

        spitService.thumbup(spitId);
        redisTemplate.opsForValue().set("thumbup_"+userId,"1");
        return new Result(true,StatusCode.OK,"点赞成功");
    }
}
