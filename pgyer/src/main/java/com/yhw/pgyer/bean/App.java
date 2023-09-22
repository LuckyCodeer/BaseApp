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
    }
}
