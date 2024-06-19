package com.example.hsb.client.record;

public class AccountRecord {
    private String collectionId;
    private String collectionName;
    private String created;
    private boolean emailVisibility;
    private String id;
    private boolean is_deleted;
    private String updated;
    private String username;
    private boolean verified;

    // Getters and setters

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public boolean isEmailVisibility() {
        return emailVisibility;
    }

    public void setEmailVisibility(boolean emailVisibility) {
        this.emailVisibility = emailVisibility;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Override
    public String toString() {
        return "Record{" +
                "collectionId='" + collectionId + '\'' +
                ", collectionName='" + collectionName + '\'' +
                ", created='" + created + '\'' +
                ", emailVisibility=" + emailVisibility +
                ", id='" + id + '\'' +
                ", is_deleted=" + is_deleted +
                ", updated='" + updated + '\'' +
                ", username='" + username + '\'' +
                ", verified=" + verified +
                '}';
    }
}
