package br.edu.ifsp.scl.pipegene.domain;

import java.util.Arrays;

public class ColumnOperation implements Operation {

    @Override
    public String getDescription() {
        return "Extrai as colunas informadas do dataset passado";
    }

    @Override
    public String getParamKey() {
        return "columns";
    }

    @Override
    public Object getParams() {
        return Arrays.asList(
                "Hugo_Symbol", "Chromosome",
                "Start_Position", "End_Position",
                "Reference_Allele", "Tumor_Seq_Allele2",
                "Variant_Classification", "Variant_Type",
                "Tumor_Sample_Barcode"
        );
    }
}
