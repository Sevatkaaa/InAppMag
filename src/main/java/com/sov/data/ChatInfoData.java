package com.sov.data;

import lombok.Data;

import java.util.List;

@Data
public class ChatInfoData {
    private List<MessageData> messages;
    private InvestorData investorData;
    private ProjectData projectData;
    private boolean confirmed;
}
