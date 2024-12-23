package com.code.ecommercebackend.dtos.response.statistics;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class RevenueMonth extends Revenue {
    private int month;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RevenueMonth that = (RevenueMonth) o;
        return month == that.month;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(month);
    }
}
