package com.itsmv.api.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ServerOsGroup {
    W2K16("Windows", "Windows Server", "Windows Server 2016 Standard"),
    W2K19("Windows", "Windows Server", "Windows Server 2019 Standard"),
    CENT8("Linux", "CentOS", "'CentOS-8.3.2011"),
    SUSE12SP5("Linux", "SUSE", "SUSE Linux Enterprise Server 12 SP5");

    private final String osLine;
    private final String osCategory;
    private final String osDetail;

}
