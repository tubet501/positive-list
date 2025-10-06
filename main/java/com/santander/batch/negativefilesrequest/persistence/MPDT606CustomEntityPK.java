package com.santander.batch.negativefilesrequest.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The MPDT606CustomEntityPK.
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class MPDT606CustomEntityPK implements Serializable {

    /**
     * The Constant serialVersionUID.
     * <p>
     * For the implementation of
     * serializable.
     */
    private static final long serialVersionUID = 1L;

    /**
     * the cdgenti.
     */
    @Column(name = " E1494_CDGENTI", length = 4)
    private String cdgenti;

    /**
     * the valores.
     */
    @Column(name = "E1494_VALORES", length = 3)
    private String valores;

    /**
     * the campoopc.
     */
    @Column(name = "E1494_CAMPOOPC", length = 14)
    private String campoopc;


}
