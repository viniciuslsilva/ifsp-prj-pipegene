package br.edu.ifsp.scl.pipegene.domain;

public interface Operation {

    String getDescription();

    String getParamKey();

    Object getParams();
}
