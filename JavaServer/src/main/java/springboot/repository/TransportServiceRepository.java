package springboot.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import springboot.domein.TransportDienst;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportServiceRepository extends JpaRepository<TransportDienst, String> {
    TransportDienst getTransportServiceById(Integer id);
    TransportDienst findById(Integer id);
    void deleteById(Integer id);
    TransportDienst findTopByOrderByIdDesc();
}
