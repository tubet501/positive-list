package com.santander.batch.negativefilesrequest.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;

import java.io.Serializable;

/**
 * the MPDT606CustomEntity.
 * <p>
 * This class contains
 * a primary key.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "MPDT606")
@EntityListeners({TrimListener.class})
public class MPDT606CustomEntity implements Serializable {

    /**
     * The Constant serialVersionUID.
     * <p>
     * For the implementation of
     * serializable
     */
    private static final long serialVersionUID = 1L;

    /**
     * the mpdt606CustomEntityPK
     */
    @EmbeddedId
    private MPDT606CustomEntityPK mpdt606CustomEntityPK;


    /**
     * the dsvalor.
     */
    @Column(name = "E1494_DSVALOR", length = 30)
    private String dsvalor;


}
