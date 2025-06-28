
package com.mycompany.todo;


public class TodoStats {
    private int total;
    private int selesai;
    private int belumSelesai;

    public TodoStats(int total, int selesai, int belumSelesai) {
        this.total = total;
        this.selesai = selesai;
        this.belumSelesai = belumSelesai;
    }

    public int getTotal() {
        return total;
    }

    public int getSelesai() {
        return selesai;
    }

    public int getBelumSelesai() {
        return belumSelesai;
    }
}