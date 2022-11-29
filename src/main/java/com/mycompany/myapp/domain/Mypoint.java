package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.util.Date;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Mypoint.
 */
@Document(collection = "mypoint")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Mypoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("userid")
    private String userid;

    @Field("total_amount")
    private Long total_amount;

    @Field("unit_amount")
    private Long unit_amount;

    @Field("created_at")
    @CreatedDate
    private Date createdAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Mypoint id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return this.userid;
    }

    public Mypoint userid(String userid) {
        this.setUserid(userid);
        return this;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Long getTotal_amount() {
        return this.total_amount;
    }

    public Mypoint total_amount(Long total_amount) {
        this.setTotal_amount(total_amount);
        return this;
    }

    public void setTotal_amount(Long total_amount) {
        this.total_amount = total_amount;
    }

    public Long getUnit_amount() {
        return this.unit_amount;
    }

    public Mypoint unit_amount(Long unit_amount) {
        this.setUnit_amount(unit_amount);
        return this;
    }

    public void setUnit_amount(Long unit_amount) {
        this.unit_amount = unit_amount;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Mypoint createdAt(Date createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mypoint)) {
            return false;
        }
        return id != null && id.equals(((Mypoint) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Mypoint{" +
            "id=" + getId() +
            ", userid='" + getUserid() + "'" +
            ", total_amount=" + getTotal_amount() +
            ", unit_amount=" + getUnit_amount() +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
