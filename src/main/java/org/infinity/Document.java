package org.infinity;

/**
 * Created by infinity on 03.12.15.
 */
public class Document {

    private int id;
    private String name;
    private long date;
    private long size;
    private String sha1;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getDate() {
        return date;
    }

    public long getSize() {
        return size;
    }

    public String getSha1() {
        return sha1;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }
}
