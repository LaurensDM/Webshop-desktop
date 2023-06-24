package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.domein.Box;
import springboot.domein.TransportDienst;

@Repository
public interface BoxRepository extends JpaRepository<Box, String> {

    Box findById(Integer id);
    Box findTopByOrderByIdDesc();


}
