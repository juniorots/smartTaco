package br.com.dedoduro.framework.persistence;

import org.objenesis.ObjenesisHelper;
import java.util.HashMap;
import javax.persistence.metamodel.Attribute;
import net.vidageek.mirror.dsl.Mirror;

/**
 *
 * @author Rafael Quintino
 */
public final class DomainObjectAssembler {

    private DomainObjectAssembler() {
    }

    public static <DO extends DomainObject> Operations<DO> on(Class<DO> domainObject) {
        DO instance = (DO) ObjenesisHelper.newInstance(domainObject);
        return new Operations(instance);
    }

    public static final class Operations<DO extends DomainObject> {

        private final DO instance;

        private final HashMap<String, Object> values = new HashMap();

        private boolean isDone;

        private Operations(DO instance) {
            this.instance = instance;
        }

        public <V> Operations<DO> set(Attribute<? super DO, V> field, V value) {
            values.put(field.getName(), value);
            return this;
        }

        public DO done() {
            if (!isDone) {
                applyValues();
                isDone = true;
            }
            return instance;
        }

        private void applyValues() {
            if (!values.isEmpty()) {
                Mirror mirror = new Mirror();
                for (String field : values.keySet()) {
                    mirror.on(instance).set().field(field).withValue(values.get(field));
                }
            }
        }

    }
}
