package sudo.product.jparepositorys;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerInfoRepositoryImpl implements AnswerInfoRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public String generateCustomID(String procedureName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(procedureName);
        query.registerStoredProcedureParameter("generatedID", String.class, ParameterMode.OUT);
        query.execute();
        return (String) query.getOutputParameterValue("generatedID");
    }
}
