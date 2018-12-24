package com.example.dell.gestorasesorias.data.models.error;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dell on 04/11/2018.
 */

public class Error {
    @SerializedName("error")
    private String error;

    @SerializedName("error_description")
    private String errorDescription;

    public Error(String error, String errorDescription) {
        this.error = error;
        this.errorDescription = errorDescription;
    }

    public Error() {

    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
