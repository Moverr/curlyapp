package com.example.timetracker.data;

import java.util.List;

public class ApiResponse<T> {

    private Meta meta;
    private List<T> data;

    public List<T> getData() {
        return data;
    }



    public static class Meta {
        private int result_count;
        private int total_count;
        private int filter_count;

        public int getResultCount() {
            return result_count;
        }
    }


    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
