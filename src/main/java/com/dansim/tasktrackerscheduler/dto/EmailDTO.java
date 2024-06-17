package com.dansim.tasktrackerscheduler.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmailDTO  {
    private String recipientAddress;
    private String title;
    private String text;


}
