package com.example.dell.gestorasesorias.callbacks;

import com.example.dell.gestorasesorias.base.BaseCallback;
import com.example.dell.gestorasesorias.data.models.AlumnosResponse;

/**
 * Created by Dell on 04/11/2018.
 */

public interface AlumnosCallback  extends BaseCallback{
    void onAlumnosResponse(AlumnosResponse alumnosResponse);
}
