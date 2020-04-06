package com.loneliness.util.validation;

import javax.validation.Validation;
import javax.validation.Validator;

public class DataValidationFactory {
    private Validator validator ;

    private DataValidationFactory(Validator validator){
        this.validator = validator;
    }


}
