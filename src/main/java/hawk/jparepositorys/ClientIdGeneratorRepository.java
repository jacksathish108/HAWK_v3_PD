/*
 * 
 */
package hawk.jparepositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hawk.entities.ClientIdGenerator;

@Repository
public interface ClientIdGeneratorRepository extends JpaRepository<ClientIdGenerator, Long> {
	}
