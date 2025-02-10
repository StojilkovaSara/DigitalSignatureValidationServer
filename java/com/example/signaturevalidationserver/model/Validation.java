package com.example.signaturevalidationserver.model;

import java.io.File;
import java.util.List;

public class Validation {
    List<Signature> signatureList;
    boolean isValid;

    public Validation(List<Signature> signatureList, boolean isValid) {
        this.signatureList = signatureList;
        this.isValid = isValid;
    }

    public List<Signature> getSignatureList() {
        return signatureList;
    }

    public boolean isValid() {
        return isValid;
    }
}
