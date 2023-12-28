package com.yhw.pgyer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * created by yhw
 * date 2023/9/21
 */
public class App implements Serializable {
    private List<AppInfo> list;
    private int pageCount;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<AppInfo> getList() {
        return list;
    }

    public void setList(List<AppInfo> list) {
        this.list = list;
    }

    public static class AppInfo implements Serializable {
        private String buildKey;
        private String buildName;
        private String buildVersion;
        private String buildBuildVersion;
        private String buildUpdateDescription;
        private String buildCreated;
        private String buildFileSize;
        private String buildDownloadCount;
        private String buildFileKey;
        private String buildDescription;
        private String appKey;
        private String buildIdentifier;
        private String buildType; //应用类型（1:iOS; 2:Android）
        private String buildPassword;

        public String getBuildPassword() {
            return buildPassword;
        }

        public void setBuildPassword(String buildPassword) {
            this.buildPassword = buildPassword;
        }

        public String getBuildType() {
            return buildType;
        }

        public void setBuildType(String buildType) {
            this.buildType = buildType;
        }

        public String getBuildIdentifier() {
            return buildIdentifier;
        }

        public void setBuildIdentifier(String buildIdentifier) {
            this.buildIdentifier = buildIdentifier;
        }

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getBuildFileKey() {
            return buildFileKey;
        }

        public void setBuildFileKey(String buildFileKey) {
            this.buildFileKey = buildFileKey;
        }

        public String getBuildDescription() {
            return buildDescription;
        }

        public void setBuildDescription(String buildDescription) {
            this.buildDescription = buildDescription;
        }

        public String getBuildKey() {
            return buildKey;
        }

        public void setBuildKey(String buildKey) {
            this.buildKey = buildKey;
        }

        public String getBuildName() {
            return buildName;
        }

        public void setBuildName(String buildName) {
            this.buildName = buildName;
        }

        public String getBuildVersion() {
            return buildVersion;
        }

        public void setBuildVersion(String buildVersion) {
            this.buildVersion = buildVersion;
        }

        public String getBuildBuildVersion() {
            return buildBuildVersion;
        }

        public void setBuildBuildVersion(String buildBuildVersion) {
            this.buildBuildVersion = buildBuildVersion;
        }

        public String getBuildUpdateDescription() {
            return buildUpdateDescription;
        }

        public void setBuildUpdateDescription(String buildUpdateDescription) {
            this.buildUpdateDescription = buildUpdateDescription;
        }

        public String getBuildCreated() {
            return buildCreated;
        }

        public void setBuildCreated(String buildCreated) {
            this.buildCreated = buildCreated;
        }

        public String getBuildFileSize() {
            return buildFileSize;
        }

        public void setBuildFileSize(String buildFileSize) {
            this.buildFileSize = buildFileSize;
        }

        public String getBuildDownloadCount() {
            return buildDownloadCount;
        }

        public void setBuildDownloadCount(String buildDownloadCount) {
            this.buildDownloadCount = buildDownloadCount;
        }
    }
}
