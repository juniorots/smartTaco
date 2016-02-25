package br.com.dedoduro.framework.persistence;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

/**
 *
 * @author Rafael Quintino
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Log4j
@MappedSuperclass
@EntityListeners({EntityListener.class})
public abstract class DomainObject implements Serializable {

    protected static final long serialVersionUID = 1L;

    @Id
    private UUID id;
}
