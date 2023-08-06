# common-lib
Different utility classes, among them are:
- FieldMapper (to deep copy properties from source to destination with fields having same name and type - like from entity to dto conversion) - uses reflection
- Optimistic and Pessimistic distributed locks based on spring-integration JdbcLockRegistry(can be changed to Redis or similar)
- Serializers and deserializers of different date classes (WebConfig.java, DateMapper.java)
- Loggin aspect
- An improved JpaRepository from https://vladmihalcea.com/best-spring-data-jparepository - HibernateRepository.java
