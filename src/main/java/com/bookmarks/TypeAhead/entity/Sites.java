package com.bookmarks.TypeAhead.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Sites {

    @Id
    private String id;
    private String SiteName;
    private String URL;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private Long userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSiteName() {
        return SiteName;
    }

    public void setSiteName(String siteName) {
        SiteName = siteName;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

}
