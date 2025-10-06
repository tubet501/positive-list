package com.santander.batch.negativefilesrequest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Input file.
 *
 * Object returned by reader
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DecisionRisk {

    /**
     * Number case
     */
    private String idEmpr;

    /**
     * Number case
     */
    private String idCent;

    /**
     * Number case
     */
    private String codCaso;

    /**
     * Number case
     */
    private String version;

    /**
     * Number case
     */
    private String idSolcas;

    /**
     * The client code
     */
    private String cdPersona;

    /**
     * The date
     */
    private String dataDatePart;

    public String getNumCase() {
        StringBuilder line = new StringBuilder()
                .append(this.getIdEmpr())
                .append(this.getIdCent())
                .append(this.getCodCaso())
                .append(this.getVersion())
                .append(this.getIdSolcas());
        return line.toString();
    }
}
