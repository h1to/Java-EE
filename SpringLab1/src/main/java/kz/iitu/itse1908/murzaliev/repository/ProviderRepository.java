package kz.iitu.itse1908.murzaliev.repository;

import kz.iitu.itse1908.murzaliev.database.Provider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProviderRepository extends CrudRepository<Provider, Long> {

}
