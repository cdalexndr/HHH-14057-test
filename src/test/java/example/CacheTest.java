package example;

import java.util.UUID;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@SpringBootTest
@AutoConfigureCache(cacheProvider = CacheType.JCACHE)
@TestPropertySource(properties = {
    "spring.jpa.properties.javax.persistence.sharedCache.mode=DISABLE_SELECTIVE",
    "spring.jpa.properties.hibernate.cache.use_second_level_cache=true"
})
public class CacheTest extends AbstractTransactionalTestNGSpringContextTests {

  @Autowired
  PersonRepository personRepository;
  @Autowired
  EntityManager entityManager;

  private int addressId;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @BeforeClass
  public void init() {
    Person person = new Person(UUID.randomUUID().toString());
    person.addAddress(new Nickname(UUID.randomUUID().toString()));
    person = personRepository.save(person);

    Nickname address = person.getNicknames().iterator().next();
    this.addressId = address.getId();
    assert addressId > 0;

    //update to store in second level cache
    address.setCurrent(true);
    entityManager.flush();
    assert entityManager.getEntityManagerFactory().getCache().contains(Nickname.class, addressId);
  }

  @Test
  public void test() {
    assert entityManager.getEntityManagerFactory().getCache().contains(Nickname.class, addressId);
    Nickname address = entityManager.find(Nickname.class, addressId);
  }
}
