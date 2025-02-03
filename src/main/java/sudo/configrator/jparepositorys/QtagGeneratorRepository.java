package sudo.configrator.jparepositorys;
/*
 * 
 */


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sudo.configrator.entities.QtagGenerator;

@Repository
public interface QtagGeneratorRepository extends JpaRepository<QtagGenerator, Long> {

}
