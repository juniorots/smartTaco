package br.com.dedoduro.modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Compra.class)
public abstract class Compra_ extends br.com.dedoduro.framework.persistence.DomainObject_ {

	public static volatile CollectionAttribute<Compra, Concurso> concursos;
	public static volatile SingularAttribute<Compra, Boolean> enviarEmail;
	public static volatile CollectionAttribute<Compra, Usuario> usuarios;

}

