package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Mypoint} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MypointDTO implements Serializable {

    private String id;

    private String userid;

    private Long total_amount;

    private Long unit_amount;

    private LocalDate createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Long getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Long total_amount) {
        this.total_amount = total_amount;
    }

    public Long getUnit_amount() {
        return unit_amount;
    }

    public void setUnit_amount(Long unit_amount) {
        this.unit_amount = unit_amount;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MypointDTO)) {
            return false;
        }

        MypointDTO mypointDTO = (MypointDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mypointDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MypointDTO{" +
            "id='" + getId() + "'" +
            ", userid='" + getUserid() + "'" +
            ", total_amount=" + getTotal_amount() +
            ", unit_amount=" + getUnit_amount() +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
