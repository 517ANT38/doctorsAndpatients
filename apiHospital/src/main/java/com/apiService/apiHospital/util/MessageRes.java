package com.apiService.apiHospital.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MessageRes {
    private String msg;
    private  String timeStamp;
}
