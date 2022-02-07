package kz.iitu.itse1908.murzaliev.repository;

import kz.iitu.itse1908.murzaliev.database.Service;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ServiceRepository extends CrudRepository<Service, Long> {

}
