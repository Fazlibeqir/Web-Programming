package mk.ukim.finki.wp.september2021.repository;

import mk.ukim.finki.wp.september2021.model.News;
import mk.ukim.finki.wp.september2021.model.NewsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News,Long> {
    List<News> findAllByPriceLessThanAndType(Double price, NewsType type);

    List<News> findAllByPriceLessThan(Double price);

    List<News> findAllByType(NewsType type);

    Optional<News> findByName(String username);

}
