package br.com.dedoduro.framework.persistence;

import java.util.UUID;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.NonNull;
import org.joda.time.LocalDateTime;

/**
 *
 * @author Quintino
 * @see http://www.objectdb.com/java/jpa/persistence/event
 */
public final class EntityListener {

    @PrePersist
    public void onPrePersist(DomainObject obj) {
        setId(obj);
        setCreationDateTime(obj);
        setLastUpdate(obj);
    }

    @PreUpdate
    public void onPreUpdate(DomainObject obj) {
        setLastUpdate(obj);
    }

    private void setId(@NonNull DomainObject obj) {
        if (obj.getId() == null) {
            obj.setId(UUID.randomUUID());
        }
    }

    private void setCreationDateTime(@NonNull DomainObject obj) {
        if (obj instanceof IHaveCreationDateTime) {
            IHaveCreationDateTime iHaveCreationDateTime = (IHaveCreationDateTime) obj;
            iHaveCreationDateTime.setCreationDateTime(new LocalDateTime());
        }
    }

    private void setLastUpdate(@NonNull DomainObject obj) {
        if (obj instanceof IHaveLastUpdate) {
            IHaveLastUpdate iHaveLastUpdate = (IHaveLastUpdate) obj;
            iHaveLastUpdate.setLastUpdate(new LocalDateTime());
        }
    }

}
