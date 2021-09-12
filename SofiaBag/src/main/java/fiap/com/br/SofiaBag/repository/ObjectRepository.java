package fiap.com.br.SofiaBag.repository;

import fiap.com.br.SofiaBag.entity.Object;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ObjectRepository extends JpaRepository<Object, String> {

    List<Object> findObjectByUserIdAndReminder(String id, Date reminder);
    Object findObjectByCdRfidAndUserId(String objectId, String userId);
}
