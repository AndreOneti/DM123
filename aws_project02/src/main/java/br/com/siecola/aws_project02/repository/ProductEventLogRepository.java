package br.com.siecola.aws_project02.repository;

import br.com.siecola.aws_project02.model.ProductEventLog;
import org.springframework.data.repository.CrudRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;

import java.util.List;

@EnableScan
public interface ProductEventLogRepository extends CrudRepository<ProductEventLog, String> {
    List<ProductEventLog> findAllByUsername(String username);
}