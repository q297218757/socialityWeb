package cn.czc.service;

import cn.czc.dao.LabelDao;
import cn.czc.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LabelService {
    @Autowired
    LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;

    public List<Label> findall(){
        return labelDao.findAll();
    };

    public Label findById(String id){
        return labelDao.findById(id).get();
    }

    public void save(Label label){
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    public void update(Label label){
        labelDao.save(label);
    }

    public void deleteById(String id){
        labelDao.deleteById(id);
    }

    public List<Label> findSearch(Label label){
        return labelDao.findAll(
                new Specification<Label>() {
                    /**
                     *
                     * @param root 跟对象，也就是要把对象封装到哪个类中 where 类名=label.getId
                     * @param criteriaQuery 封装的都是查询关键字 比如group by等
                     * @param criteriaBuilder 用来封装对象
                     * @return
                     */
                    @Override
                    public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                        List<Predicate> list = new ArrayList();
                        if(label.getLabelname()!=null && !"".equals(label.getLabelname())){
                            Predicate predicate = criteriaBuilder.like(root.get("labelname").as(String.class),"%"+label.getLabelname()+"%"); //where labelname like "%模糊%"
                            list.add(predicate);
                        }
                        if(label.getState() != null && !"".equals(label.getState())){
                            Predicate predicate = criteriaBuilder.equal(root.get("state").as(String.class),label.getState()); //where labelname like "%模糊%"
                            list.add(predicate);
                        }
                        Predicate[] predicates = new Predicate[list.size()];
                        list.toArray(predicates);
                        return criteriaBuilder.and(predicates);
                    }
                });
    };

    public Page<Label> pageQuery(Label label,int page, int size){
        Pageable pageable = PageRequest.of(page-1,size);

        return labelDao.findAll(
                new Specification<Label>() {
                    /**
                     *
                     * @param root 跟对象，也就是要把对象封装到哪个类中 where 类名=label.getId
                     * @param criteriaQuery 封装的都是查询关键字 比如group by等
                     * @param criteriaBuilder 用来封装对象
                     * @return
                     */
                    @Override
                    public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                        List<Predicate> list = new ArrayList();
                        if(label.getLabelname()!=null && !"".equals(label.getLabelname())){
                            Predicate predicate = criteriaBuilder.like(root.get("labelname").as(String.class),"%"+label.getLabelname()+"%"); //where labelname like "%模糊%"
                            list.add(predicate);
                        }
                        if(label.getState() != null && !"".equals(label.getState())){
                            Predicate predicate = criteriaBuilder.equal(root.get("state").as(String.class),label.getState()); //where labelname like "%模糊%"
                            list.add(predicate);
                        }
                        Predicate[] predicates = new Predicate[list.size()];
                        list.toArray(predicates);
                        return criteriaBuilder.and(predicates);
                    }
                },pageable);
    }
}
