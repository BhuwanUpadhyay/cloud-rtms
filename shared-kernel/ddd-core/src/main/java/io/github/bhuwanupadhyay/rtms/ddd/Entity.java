package io.github.bhuwanupadhyay.rtms.ddd;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.*;

@MappedSuperclass
public abstract class Entity<ID extends ValueObject> {

  public static final String ENTITY_ID_IS_REQUIRED = "EntityIdIsRequired";

  /** Rely on a RDBMS generated sequence */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long rawId;

  /** Globally unique identifier of the Aggregate or Entity */
  @Embedded private ID id;

  private LocalDateTime createdAt;

  protected Entity() {}

  public Entity(ID id) {
    DomainAsserts.begin(id).notNull(DomainError.create(this, ENTITY_ID_IS_REQUIRED)).end();
    this.id = id;
  }

  public ID getId() {
    return this.id;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || this.getClass() != o.getClass()) return false;
    Entity<?> entity = (Entity<?>) o;
    return Objects.equals(this.id, entity.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }
}
