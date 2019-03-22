package czc.search.service;

import czc.search.dao.ArticleDao;
import czc.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import util.IdWorker;

@Service
public class ArticleService {
    @Autowired
    ArticleDao articleDao;
    @Autowired
    IdWorker idWorker;

    public void save (Article article){
        article.setId(idWorker.nextId()+"");
        articleDao.save(article);
    }

    public Page<Article> findByKey(String key, int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Article> articles = articleDao.findByTitleOrContentLike(key,key,pageable);
        return articles;
    }
}
