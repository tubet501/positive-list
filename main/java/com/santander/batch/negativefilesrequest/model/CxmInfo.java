package com.santander.batch.negativefilesrequest.model;

import com.santander.batch.negativefilesrequest.model.dto.CxmRes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Cxm info
 * response processed.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CxmInfo {

    /**
     * The Cxm
     * result indicator.
     */
    @Schema(description = "Cxm Result Indicator", example = "Y", allowableValues = {"Y", "N", "E"})
    private String cxmResultIndicator;

    /**
     * The person code.
     */
    private String cdPersona;

    /**
     * Date of case
     */
    private String dataDateTime;

    /**
     * The Cxm response.
     */
    private CxmRes cxmRes;

    /**
     * To line string.
     *
     * @return the string
     */
    public String toLine(){
        StringBuilder line = new StringBuilder()
                .append(this.getCdPersona()+"\n")
                .append(this.getDataDateTime()+"\n");
        return line.toString();
    }

    /**
     * To error line string.
     *
     * @return the string
     */
    public String toErrorLine(){
        StringBuilder line = new StringBuilder()
                .append(this.getCxmResultIndicator()+"\n")
                .append(this.getCdPersona()+"\n")
                .append(this.getDataDateTime()+"\n");
        return line.toString();
    }
}

