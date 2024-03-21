package com.kovachka.gatewaystatistics.entity;

public enum ExtServiceName {
    EXT_SERVICE_1("EXT_SERVICE_1"),
    EXT_SERVICE_2("EXT_SERVICE_2");

    private final String serviceName;

    ExtServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return this.serviceName;
    }
}

