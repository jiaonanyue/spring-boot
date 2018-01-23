package org.com.spring.boot.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import org.com.spring.boot.entity.BaseEntity;
import org.com.spring.boot.msg.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * BaseController 公共controller
 * @author jiaonanyue
 * @create 2017-08-03 9:18
 **/
public class BaseController<M extends IService<T>, T extends BaseEntity> {


    /**
     * 这个地方不能用@Resource
     */
    @Autowired
    protected M service;


    /**
     * 新增 选择字段
     * @param entity
     * @return
     */
    @PostMapping("")
    public ObjectRestResponse<T> insert(T entity){

        Boolean a = service.insert(entity);
        if(a){
            return new ObjectRestResponse<T>().rel(true).msg("新增数据成功");
        }
        return new ObjectRestResponse<T>().rel(false).msg("新增数据失败");
    }

    /**
     * 新增 全部字段
     * @param entity
     * @return
     */
    @PostMapping("/{entity}")
    public ObjectRestResponse<T> insertAllColumn(T entity){

        Boolean a = service.insertAllColumn(entity);
        if(a){
            return new ObjectRestResponse<T>().rel(true).msg("新增数据成功");
        }
        return new ObjectRestResponse<T>().rel(false).msg("新增数据失败");
    }

    /**
     * 批量新增
     * @param entityList
     * @return
     */
    @PostMapping("/insertBatch")
    public ObjectRestResponse<T> insertBatch(Page<T> entityList){

        Boolean a = service.insertBatch(entityList.getRecords());
        if(a){
            return new ObjectRestResponse<T>().rel(true).msg("批量新增成功");
        }
        return new ObjectRestResponse<T>().rel(false).msg("批量新增失败");
    }


    /**
     * 通过id更新 选择字段
     * @param entity
     * @return
     */
    @PutMapping("")
    public ObjectRestResponse<T> updateById(T entity){

        Boolean a = service.updateById(entity);
        if(a){
            return new ObjectRestResponse<T>().rel(true).msg("更新成功");
        }
        return new ObjectRestResponse<T>().rel(false).msg("更新失败");

    }

    /**
     * 通过id更新 全部字段
     * @param entity
     * @return
     */
    @PutMapping("/{entity}")
    public ObjectRestResponse<T> updateAllColumnById(T entity){

        Boolean a = service.updateAllColumnById(entity);
        if(a){
            return new ObjectRestResponse<T>().rel(true).msg("更新成功");
        }
        return new ObjectRestResponse<T>().rel(false).msg("更新失败");
    }


    /**
     * 存在更新记录，否插入一条记录
     * @param entity
     * @return
     */
    @PostMapping("/insertOrUpdate")
    public ObjectRestResponse<T> insertOrUpdate(T entity){

        Boolean a = service.insertOrUpdate(entity);
        if(a){
            return new ObjectRestResponse<T>().rel(true).msg("更新成功");
        }
        return new ObjectRestResponse<T>().rel(false).msg("更新失败");
    }


    /**
     * 通过ID 批量修改
     * @param page
     * @return
     */
    @PostMapping("/updateBatchById")
    public ObjectRestResponse<T> updateBatchById(Page<T> page){

        List<T> entityList = page.getRecords();

        if(null != entityList && entityList.size() >0){
            Boolean a = service.updateBatchById(entityList);
            if(a){
                return new ObjectRestResponse<T>().rel(true).msg("批量修改成功");
            }
            return new ObjectRestResponse<T>().rel(false).msg("批量修改失败");
        }
        return new ObjectRestResponse<T>().rel(false).msg("参数不对");
    }


    /**
     * 通过id删除
     * @param id
     * @return
     */
    @DeleteMapping("")
    public ObjectRestResponse<T> deleteById(Long id){

        Boolean a = service.deleteById(id);
        if(a){
            return new ObjectRestResponse<T>().rel(true).msg("删除成功");
        }
        return new ObjectRestResponse<T>().rel(false).msg("删除失败");
    }



    /**
     * 通过ID 批量删除
     * @param idList
     * @return
     */
    @DeleteMapping("/deleteBatchIds")
    public ObjectRestResponse<T> deleteBatchIds( Page<String> idList){

        if(null != idList){
            Boolean a = service.deleteBatchIds(idList.getRecords());
            if(a){
                return new ObjectRestResponse<T>().rel(true).msg("批量删除成功");
            }
            return new ObjectRestResponse<T>().rel(false).msg("批量删除失败");
        }
        return new ObjectRestResponse<T>().rel(false).msg("参数不对");
    }

    /**
     * 通过条件删除
     * @param entity
     * @return
     */
    @DeleteMapping("/{entity}")
    public ObjectRestResponse<T> delete( T entity){

        if(null != entity){
            Boolean a = service.delete(new EntityWrapper(entity));
            if(a){
                return new ObjectRestResponse<T>().rel(true).msg("删除成功");
            }
            return new ObjectRestResponse<T>().rel(false).msg("删除失败");
        }
        return new ObjectRestResponse<T>().rel(false).msg("参数不对");
    }


    /**
     * 通过id查询
     * @param id
     * @return
     */
    @GetMapping("")
    public ObjectRestResponse<T> getBase( Long id){

        return new ObjectRestResponse<T>().rel(true).result(service.selectById(id)).msg("查询成功");
    }


    /**
     * 通过条件查询
     * @return
     */
    @GetMapping("/{entity}")
    public List<T> getEntity(T entity){

        return service.selectList(new EntityWrapper<T>(entity));
    }

    /**
     * 查询全部数据
     * @return
     */
    @GetMapping("/all")
    public List<T> selectList(){
        return service.selectList(null);
    }


    /**
     * 分页查询
     * @param entity int current, int size
     * @return
     */
    @GetMapping("/pages")
    public Page<T> page(Page entity){
        return service.selectPage(entity);
    }

}
