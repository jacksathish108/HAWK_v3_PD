/*
 * 
 */
package sudo.jparepositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sudo.entities.ClientIdGenerator;

@Repository
public interface ClientIdGeneratorRepository extends JpaRepository<ClientIdGenerator, Long> {
	}
