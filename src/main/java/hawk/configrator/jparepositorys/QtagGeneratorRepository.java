package hawk.configrator.jparepositorys;
/*
 * 
 */


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hawk.configrator.entities.QtagGenerator;

@Repository
public interface QtagGeneratorRepository extends JpaRepository<QtagGenerator, Long> {

}
