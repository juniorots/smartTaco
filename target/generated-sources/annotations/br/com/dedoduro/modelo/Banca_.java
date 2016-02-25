package br.com.dedoduro.modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Banca.class)
public abstract class Banca_ extends br.com.dedoduro.framework.persistence.DomainObject_ {

	public static volatile CollectionAttribute<Banca, Concurso> concursos;
	public static volatile SingularAttribute<Banca, String> urlImagem;
	public static volatile SingularAttribute<Banca, String> nomeBanca;

}

